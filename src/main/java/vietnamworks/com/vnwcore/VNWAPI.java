package vietnamworks.com.vnwcore;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;

import java.util.HashMap;

import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.CallbackSuccess;
import vietnamworks.com.volleyhelper.VolleyHelper;

/**
 * Created by duynk on 1/6/16.
 */
public class VNWAPI {
    private final static String productionServer = "https://api.vietnamworks.com";
    private final static String stagingServer = "https://api-staging.vietnamworks.com";

    private final static String API_JOB_SEARCH = "/jobs/search";

    private static String stagingKey;
    private static String productionKey;
    private static HashMap<String, String> header = new HashMap<>();
    private static Context context;
    private static Boolean isProduction;

    public static void init(Context ctx, String stagingKey, String productionKey, boolean isProduction) {
        VNWAPI.stagingKey = stagingKey;
        VNWAPI.productionKey = productionKey;
        VNWAPI.context = ctx;
        setProductionMode(isProduction);
    }

    public static void setProductionMode(boolean isProduction) {
        VNWAPI.isProduction = isProduction;
        if (isProduction) {
            header.put("CONTENT-MD5", VNWAPI.productionKey);
        } else {
            header.put("CONTENT-MD5", VNWAPI.stagingKey);
        }
    }

    public static void searchJob(int page_index, int page_size, @NonNull String job_title, String job_location, String job_category, Callback callback) {
        HashMap<String, Object> input = new HashMap<>();
        input.put("job_title", job_title);
        if (job_location != null && !job_location.isEmpty()) {
            input.put("job_location", job_location);
        }
        if (job_category != null && !job_category.isEmpty()) {
            input.put("job_category", job_category);
        }
        if (page_index >= 0) {
            input.put("page_number", page_index);
        }
        if (page_size > 0) {
            input.put("page_size", page_size);
        }

        VolleyHelper.post(context, isProduction?productionServer:stagingServer + API_JOB_SEARCH, header, input, callback);
    }

    public static void searchJob(int max_record, @NonNull String job_title, String job_location, String job_category, Callback callback) {
        searchJob(0, max_record, job_title, job_location, job_category, callback);
    }

    public static void jobTitleSuggestion(@NonNull String jobTitle, final Callback callback) {
        HashMap<String, String> m = new HashMap<>();
        m.put("query", jobTitle);

        VolleyHelper.stringRequest(context, "http://www.vietnamworks.com/jobseekers/job_title_auto_completed_ajax.php", null, m, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    callback.onCompleted(context, new CallbackResult(result.getError()));
                } else {
                    Object re = result.getData();
                    try {
                        String str = (String)re;
                        JSONArray jArray = new JSONArray(str);
                        callback.onCompleted(context, new CallbackSuccess(jArray));
                    } catch (Exception E) {
                        callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackError(-1, E.getMessage())));
                    }
                }
            }
        });
    }
}
