package vietnamworks.com.vnwcore.entities;

import android.content.Context;

import org.json.JSONObject;

import R.helper.BaseEntity;
import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.CallbackSuccess;
import R.helper.EntityField;
import vietnamworks.com.vnwcore.VNWAPI;

/**
 * Created by duynk on 1/13/16.
 */
public class Job extends BaseEntity {

    @EntityField(value = "job_detail", type = JobDetail.class) public static String JOB_DETAIL;

    public Job() {
        super();
    }


    public static void loadById(Context ctx, String id, final Callback callback) {
        VNWAPI.getJob(ctx, id, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    callback.onCompleted(context, new CallbackResult(result.getError()));
                } else {
                    try {
                        JSONObject res = (JSONObject) result.getData();
                        JSONObject data = res.optJSONObject("data");
                        Job j = new Job();
                        j.importData(data);
                        callback.onCompleted(context, new CallbackSuccess(j));
                    } catch (Exception E) {
                        callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackError(-1, E.getMessage())));
                    }
                }
            }
        });
    }
}
