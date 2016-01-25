package vietnamworks.com.vnwcore;

import android.content.Context;
import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import R.helper.Callback;
import R.helper.CallbackError;
import R.helper.CallbackResult;
import R.helper.CallbackSuccess;
import R.helper.Common;
import R.helper.IIErrorX;
import vietnamworks.com.vnwcore.entities.AppliedJob;
import vietnamworks.com.vnwcore.entities.JobApplyForm;
import vietnamworks.com.vnwcore.errors.EApplyJobError;
import vietnamworks.com.vnwcore.errors.EUserProfileQueryError;
import vietnamworks.com.vnwcore.matchingscore.MatchingScoreTable;
import vietnamworks.com.volleyhelper.VolleyHelper;

/**
 * Created by duynk on 1/6/16.
 */
public class VNWAPI {
    private final static String productionServer = "https://api.vietnamworks.com";
    private final static String stagingServer = "https://api-staging.vietnamworks.com";

    private final static String API_JOB_SEARCH = "/jobs/search";
    private final static String API_CONFIG = "/general/configuration";
    private final static String API_JOB_VIEW = "/jobs/view/job_id/%1$s";
    private final static String API_LOGIN = "/users/login";
    private final static String API_LOGOUT = "/users/logout/token/%s";
    private final static String API_APPLY = "/jobs/applyAttach";
    private final static String API_MATCHING_SCORE = "/jobs/matching-score";
    private final static String API_APPLIED_JOBS = "/jobs/applied/token/%s";

    private static String stagingKey;
    private static String productionKey;
    private static HashMap<String, String> header = new HashMap<>();
    private static Boolean isProduction;

    public static void init(String stagingKey, String productionKey, boolean isProduction) {
        VNWAPI.stagingKey = stagingKey;
        VNWAPI.productionKey = productionKey;
        setProductionMode(isProduction);
        if (!isProduction) {
            Common.acceptAllSSL();
        }
    }

    public static void setProductionMode(boolean isProduction) {
        VNWAPI.isProduction = isProduction;
        if (isProduction) {
            header.put("CONTENT-MD5", VNWAPI.productionKey);
        } else {
            header.put("CONTENT-MD5", VNWAPI.stagingKey);
        }
    }

    public static void searchJob(Context ctx, int page_index, int page_size, @NonNull String job_title, String job_location, String job_category, Callback callback) {
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

        VolleyHelper.post(ctx, (isProduction ? productionServer : stagingServer) + API_JOB_SEARCH, header, input, callback);
    }

    public static void searchJob(Context ctx, int max_record, @NonNull String job_title, String job_location, String job_category, Callback callback) {
        searchJob(ctx, 0, max_record, job_title, job_location, job_category, callback);
    }

    public static void jobTitleSuggestion(Context ctx, @NonNull String jobTitle, final Callback callback) {
        HashMap<String, String> m = new HashMap<>();
        m.put("query", jobTitle);

        VolleyHelper.stringRequest(ctx, "http://www.vietnamworks.com/jobseekers/job_title_auto_completed_ajax.php", null, m, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    callback.onCompleted(context, new CallbackResult(result.getError()));
                } else {
                    Object re = result.getData();
                    try {
                        String str = (String) re;
                        JSONArray jArray = new JSONObject(str).getJSONArray("jobTitle");
                        ArrayList<String> arr = new ArrayList<String>();
                        for (int i = 0; i < jArray.length(); i++) {
                            arr.add(jArray.get(i).toString());
                        }
                        callback.onCompleted(context, new CallbackSuccess(arr));
                    } catch (Exception E) {
                        callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackErrorInfo(-1, E.getMessage())));
                    }
                }
            }
        });
    }

    public static void getJob(Context ctx, @NonNull String job_id, Callback callback) {
        HashMap<String, Object> input = new HashMap<>();
        VolleyHelper.post(ctx, (isProduction ? productionServer : stagingServer) + String.format(API_JOB_VIEW, job_id), header, input, callback);
    }

    public static void getConfiguration(Context ctx, Callback callback) {
        HashMap<String, Object> input = new HashMap<>();
        VolleyHelper.post(ctx, (isProduction ? productionServer : stagingServer) + API_CONFIG, header, input, callback);
    }

    public static void logout(Context ctx, @NonNull String token,  Callback callback) {
        HashMap<String, Object> input = new HashMap<>();
        VolleyHelper.post(ctx, (isProduction ? productionServer : stagingServer) + String.format(API_LOGOUT, token), header, input, callback);
    }

    public static void login(Context ctx, @NonNull String email,  @NonNull String password, Callback callback) {
        HashMap<String, Object> input = new HashMap<>();
        input.put("user_email", email);
        input.put("user_password", password);
        VolleyHelper.post(ctx, (isProduction ? productionServer : stagingServer) + API_LOGIN, header, input, callback);
    }

    private static void updateToken(JSONObject response) {
        try {
            if (response != null) {
                JSONObject data = response.getJSONObject("data");
                if (data != null) {
                    String login_token = data.getString("login_token");
                    if (login_token != null && !login_token.isEmpty()) {
                        if (Auth.getAuthData() != null && Auth.getAuthData().getProfile() != null) {
                            Auth.getAuthData().getProfile().setLoginToken(login_token);
                        }
                    }
                }
            }
        }catch (Exception E) {
            E.printStackTrace();
        }
    }

    public static void applyJob(Context context, JobApplyForm form, final Callback callback) {
        if (form.getFileContents() != null) {
            File f = new File(form.getFileContents());
            if (f.isFile()) {
                HashMap<String, Object> input = new HashMap<>();
                if (form.getApplicationSubject() != null) {
                    input.put("application_subject", form.getApplicationSubject());
                }
                if (form.getCoverLetter() != null) {
                    input.put("cover_letter", form.getCoverLetter());
                }
                if (form.getLang() != null) {
                    input.put("lang", form.getLang().toString());
                }
                if (form.getJobId() != null) {
                    input.put("job_id", form.getJobId().toString());
                }
                input.put("email", form.getCredential().getEmail());
                input.put("password", form.getCredential().getPassword());

                //String url = "http://172.18.5.124/test.php";
                String url = (isProduction ? productionServer : stagingServer) + API_APPLY;
                VolleyHelper.postMultiPart(context, url, header, "file_contents", f, input, new Callback() {
                    @Override
                    public void onCompleted(Context context, CallbackResult result) {
                        if (result.hasError()) {
                            String message = "";
                            try {
                                String tmpMessage = result.getError().getMessage();
                                JSONObject json = new JSONObject(tmpMessage);
                                JSONObject meta = json.getJSONObject("meta");
                                message = meta.getString("message");
                            } catch (Exception E) {message = "";}
                            if (message.isEmpty()) {
                                callback.onCompleted(context, new CallbackError(result.getError()));
                            } else {
                                callback.onCompleted(context, new CallbackError(result.getError().getCode(), message));
                            }
                        } else {
                            try {
                                JSONObject json = new JSONObject(result.getData().toString());
                                updateToken(json);
                                callback.onCompleted(context, new CallbackSuccess());
                            }catch (Exception E) {
                                callback.onCompleted(context, new CallbackError(EApplyJobError.UNKNOWN, E.getMessage()));
                            }
                        }
                    }
                });
            } else {
                callback.onCompleted(context, new CallbackError(EApplyJobError.UNKNOWN,  "Invalid file"));
            }
        } else {
            //TODO: apply job with old resume
            callback.onCompleted(context, new CallbackError(EApplyJobError.UNKNOWN, "Not support yet"));
        }
    }

    public static void loadMatchingScore(Context ctx,@NonNull final String[] jobs, @NonNull final String userId, Callback callback) {
        StringBuilder sb = new StringBuilder();
        String delim = "/?";
        for (String job:jobs) {
            sb.append(delim);
            sb.append("jobId[]=");
            sb.append(job);
            delim = "&";
            MatchingScoreTable.create(userId, job);
        }
        sb.append("&userId=");
        sb.append(userId);
        String url = (isProduction ? productionServer : stagingServer) + API_MATCHING_SCORE + sb.toString();
        VolleyHelper.get(ctx, url, header, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (!result.hasError()) {
                    try {
                        JSONObject data = ((JSONObject)result.getData()).getJSONObject("data").getJSONObject("matchingScore");
                        Iterator<String> it = data.keys();
                        while (it.hasNext()) {
                            String job = it.next();
                            Integer score = data.getInt(job);
                            MatchingScoreTable.update(userId, job, score);
                        }
                    } catch (Exception E) {
                        E.printStackTrace();
                        for (String j: jobs) {
                            MatchingScoreTable.reset(userId, j);
                        }
                    }
                } else {
                    for (String j: jobs) {
                        MatchingScoreTable.reset(userId, j);
                    }
                }
            }
        });
    }


    public interface GetAppliedCallback {
        void onCompleted(Context context, GetAppliedJobsCallbackResult result);
    }

    public static class GetAppliedJobsCallbackResult extends CallbackResult {
        public GetAppliedJobsCallbackResult(CallbackErrorInfo error, Object data) {
            super(error, data);
        }

        public GetAppliedJobsCallbackResult(CallbackErrorInfo error) {
            super(error);
        }

        public static GetAppliedJobsCallbackResult error(int code, String message) {
            return new GetAppliedJobsCallbackResult(new CallbackErrorInfo(code, message));
        }

        public static GetAppliedJobsCallbackResult error(IIErrorX code) {
            return new GetAppliedJobsCallbackResult(new CallbackErrorInfo(code));
        }

        public static GetAppliedJobsCallbackResult success(ArrayList<AppliedJob> jobs) {
            return new GetAppliedJobsCallbackResult(null, jobs);
        }

        public ArrayList<AppliedJob> getAppliedJobs() {
            return (ArrayList<AppliedJob>)data;
        }
    }

    public static void getAppliedJobs(final Context ctx, final GetAppliedCallback callback) {
        if (Auth.getAuthData() == null || Auth.getAuthData().getProfile() == null || Auth.getAuthData().getProfile().getLoginToken() == null || Auth.getAuthData().getProfile().getLoginToken().isEmpty()) {
            callback.onCompleted(ctx, GetAppliedJobsCallbackResult.error(EUserProfileQueryError.UN_AUTH));
            return;
        }
        String url = String.format((isProduction ? productionServer : stagingServer) + API_APPLIED_JOBS, Auth.getAuthData().getProfile().getLoginToken());
        VolleyHelper.get(ctx, url, header, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (!result.hasError()) {
                    try {
                        JSONObject data = (JSONObject)result.getData();
                        updateToken(data);
                        JSONArray jobs = data.getJSONObject("data").getJSONArray("jobs");
                        ArrayList<AppliedJob> appliedJobs = new ArrayList<>();
                        for (int i = 0; i < jobs.length(); i++) {
                            AppliedJob j = new AppliedJob();
                            j.importFromJson(jobs.getJSONObject(i));
                            appliedJobs.add(j);
                        }
                        callback.onCompleted(ctx, GetAppliedJobsCallbackResult.success(appliedJobs));
                    } catch (Exception E) {
                        callback.onCompleted(ctx, GetAppliedJobsCallbackResult.error(-1, E.getMessage()));
                    }
                } else {
                    callback.onCompleted(ctx, GetAppliedJobsCallbackResult.error(result.getError().getCode(), result.getError().getMessage()));
                }
            }
        });
    }
}
