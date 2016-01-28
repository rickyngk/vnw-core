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
import R.helper.CallbackResult;
import R.helper.Common;
import vietnamworks.com.vnwcore.entities.AppliedJob;
import vietnamworks.com.vnwcore.entities.Configuration;
import vietnamworks.com.vnwcore.entities.Job;
import vietnamworks.com.vnwcore.entities.JobApplyForm;
import vietnamworks.com.vnwcore.entities.JobSearchResult;
import vietnamworks.com.vnwcore.entities.RegisterInfo;
import vietnamworks.com.vnwcore.errors.ELoginError;
import vietnamworks.com.vnwcore.errors.ERegisterError;
import vietnamworks.com.vnwcore.errors.EUserProfileQueryError;
import vietnamworks.com.vnwcore.matchingscore.MatchingScoreTable;
import vietnamworks.com.volleyhelper.VolleyHelper;

/**
 * Created by duynk on 1/6/16.
 *
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
    private final static String API_REGISTER = "/users/registerWithoutConfirm";
    private final static String API_GET_ATTACHMENT = "/users/attachment-resume/token/%s";

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

    public static void searchJob(Context ctx, int page_index, int page_size, @NonNull String job_title, String job_location, String job_category, final Callback<ArrayList<JobSearchResult>> callback) {
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

        VolleyHelper.post(ctx, (isProduction ? productionServer : stagingServer) + API_JOB_SEARCH, header, input, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    callback.onCompleted(context, CallbackResult.<ArrayList<JobSearchResult>>error(result.getError()));
                } else {
                    ArrayList<JobSearchResult> ret = new ArrayList<JobSearchResult>();
                    try {
                        JSONObject res = (JSONObject) result.getData();
                        JSONObject data = res.optJSONObject("data");
                        JSONArray jobs = data.getJSONArray("jobs");
                        for (int i = 0; i < jobs.length(); i++) {
                            JobSearchResult j = new JobSearchResult();
                            j.importFromJson(jobs.getJSONObject(i));
                            ret.add(j);
                        }
                    } catch (Exception E) {
                        callback.onCompleted(context, CallbackResult.<ArrayList<JobSearchResult>>error(E.getMessage()));
                    }
                    callback.onCompleted(context, CallbackResult.success(ret));
                }
            }
        });
    }

    public static void searchJob(Context ctx, int max_record, @NonNull String job_title, String job_location, String job_category, Callback<ArrayList<JobSearchResult>> callback) {
        searchJob(ctx, 0, max_record, job_title, job_location, job_category, callback);
    }

    public static void jobTitleSuggestion(Context ctx, @NonNull String jobTitle, final Callback<ArrayList<String>> callback) {
        HashMap<String, String> m = new HashMap<>();
        m.put("query", jobTitle);

        VolleyHelper.stringRequest(ctx, "http://www.vietnamworks.com/jobseekers/job_title_auto_completed_ajax.php", null, m, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    callback.onCompleted(context, CallbackResult.<ArrayList<String>>error(result.getError()));
                } else {
                    Object re = result.getData();
                    try {
                        String str = (String) re;
                        JSONArray jArray = new JSONObject(str).getJSONArray("jobTitle");
                        ArrayList<String> arr = new ArrayList<String>();
                        for (int i = 0; i < jArray.length(); i++) {
                            arr.add(jArray.get(i).toString());
                        }
                        callback.onCompleted(context, CallbackResult.success(arr));
                    } catch (Exception E) {
                        callback.onCompleted(context, CallbackResult.<ArrayList<String>>error(E.getMessage()));
                    }
                }
            }
        });
    }

    public static void getJob(Context ctx, @NonNull String job_id, final Callback<Job> callback) {
        HashMap<String, Object> input = new HashMap<>();
        VolleyHelper.post(ctx, (isProduction ? productionServer : stagingServer) + String.format(API_JOB_VIEW, job_id), header, input, new Callback<Job>() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    callback.onCompleted(context, CallbackResult.<Job>error(result.getError()));
                } else {
                    try {
                        JSONObject res = (JSONObject) result.getData();
                        JSONObject data = res.optJSONObject("data");
                        Job j = new Job();
                        j.importFromJson(data);
                        callback.onCompleted(context, CallbackResult.success(j));
                    } catch (Exception E) {
                        callback.onCompleted(context, CallbackResult.<Job>error(E.getMessage()));
                    }
                }
            }
        });
    }

    public static void getConfiguration(final Context ctx, final Callback<Configuration> callback) {
        HashMap<String, Object> input = new HashMap<>();
        VolleyHelper.post(ctx, (isProduction ? productionServer : stagingServer) + API_CONFIG, header, input, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    callback.onCompleted(ctx, CallbackResult.<Configuration>error(result.getError()));
                } else {
                    try {
                        new Configuration();
                        JSONObject res = (JSONObject) result.getData();
                        JSONObject data = res.optJSONObject("data");
                        Configuration.instance.importFromJson(data);
                        callback.onCompleted(ctx, CallbackResult.success(Configuration.instance));
                    } catch (Exception E) {
                        callback.onCompleted(ctx, CallbackResult.<Configuration>error(E.getMessage()));
                    }
                }
            }
        });
    }

    public static void logout(Context ctx, @NonNull String token,  Callback callback) {
        HashMap<String, Object> input = new HashMap<>();
        VolleyHelper.post(ctx, (isProduction ? productionServer : stagingServer) + String.format(API_LOGOUT, token), header, input, callback);
    }

    public static void login(Context ctx, String email, String password, final Callback<vietnamworks.com.vnwcore.entities.Auth> callback) {
        if (email == null || email.isEmpty()) {
            callback.onCompleted(ctx, CallbackResult.<vietnamworks.com.vnwcore.entities.Auth>error(ELoginError.EMPTY_EMAIL));
        } else if (!Common.isValidEmail(email)) {
            callback.onCompleted(ctx, CallbackResult.<vietnamworks.com.vnwcore.entities.Auth>error(ELoginError.INVALID_EMAIL));
        } else if (password == null || password.isEmpty()) {
            callback.onCompleted(ctx, CallbackResult.<vietnamworks.com.vnwcore.entities.Auth>error(ELoginError.EMPTY_PASSWORD));
        } else {
            HashMap<String, Object> input = new HashMap<>();
            input.put("user_email", email);
            input.put("user_password", password);
            VolleyHelper.post(ctx, (isProduction ? productionServer : stagingServer) + API_LOGIN, header, input, new Callback() {
                @Override
                public void onCompleted(Context context, CallbackResult result) {
                    if (result.hasError()) {
                        callback.onCompleted(context, CallbackResult.<vietnamworks.com.vnwcore.entities.Auth>error(result.getError()));
                    } else {
                        try {
                            JSONObject data = (JSONObject) result.getData();
                            if (data.getJSONObject("meta").getString("message").equalsIgnoreCase("OK")) {
                                vietnamworks.com.vnwcore.entities.Auth auth = new vietnamworks.com.vnwcore.entities.Auth();
                                auth.importFromJson(data.getJSONObject("data"));
                                callback.onCompleted(context, CallbackResult.success(auth));
                            } else {
                                if (data.getJSONObject("meta").getString("code").equalsIgnoreCase("200")) {
                                    callback.onCompleted(context, CallbackResult.<vietnamworks.com.vnwcore.entities.Auth>error(ELoginError.WRONG_CREDENTIAL));
                                } else {
                                    callback.onCompleted(context, CallbackResult.<vietnamworks.com.vnwcore.entities.Auth>error());
                                }
                            }
                        } catch (Exception E) {
                            callback.onCompleted(context, CallbackResult.<vietnamworks.com.vnwcore.entities.Auth>error(E.getMessage()));
                        }
                    }
                }
            });
        }
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

    public static void applyJob(Context context, JobApplyForm form, final Callback<Object> callback) {
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
                                callback.onCompleted(context, CallbackResult.error(result.getError()));
                            } else {
                                callback.onCompleted(context, CallbackResult.error(result.getError()));
                            }
                        } else {
                            try {
                                JSONObject json = new JSONObject(result.getData().toString());
                                updateToken(json);
                                callback.onCompleted(context, CallbackResult.success());
                            }catch (Exception E) {
                                callback.onCompleted(context, CallbackResult.error(E.getMessage()));
                            }
                        }
                    }
                });
            } else {
                callback.onCompleted(context, CallbackResult.error("Invalid file"));
            }
        } else {
            //TODO: apply job with old resume
            callback.onCompleted(context, CallbackResult.error("Not support yet"));
        }
    }

    public static void loadMatchingScoreAsync(Context ctx, @NonNull final String[] jobs, @NonNull final String userId) {
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

    public static void getAppliedJobs(final Context ctx, final Callback<ArrayList<AppliedJob>> callback) {
        if (!Auth.hasLogin()) {
            ArrayList<AppliedJob> j = new ArrayList<AppliedJob>();
            CallbackResult<ArrayList<AppliedJob>> c = CallbackResult.error(EUserProfileQueryError.UN_AUTH);
            callback.onCompleted(ctx, c);
            return;
        }
        String url = String.format((isProduction ? productionServer : stagingServer) + API_APPLIED_JOBS, Auth.getAuthData().getProfile().getLoginToken());
        VolleyHelper.get(ctx, url, header, new Callback<ArrayList<AppliedJob>>() {
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
                        callback.onCompleted(ctx, CallbackResult.success(appliedJobs));
                    } catch (Exception E) {
                        CallbackResult<ArrayList<AppliedJob>> c = CallbackResult.error(E.getMessage());
                        callback.onCompleted(ctx, c);
                    }
                } else {
                    CallbackResult<ArrayList<AppliedJob>> c = CallbackResult.error(result.getError());
                    callback.onCompleted(ctx, c);
                }
            }
        });
    }

    public static void register(final Context ctx, RegisterInfo info, final Callback<Object> callback) {
        if (info.getEmail() == null || info.getEmail().isEmpty()) {
            callback.onCompleted(ctx, CallbackResult.error(ERegisterError.EMPTY_EMAIL));
        } else if (!Common.isValidEmail(info.getEmail())) {
            callback.onCompleted(ctx, CallbackResult.error(ERegisterError.INVALID_EMAIL));
        } else if (info.getFirstName() == null || info.getFirstName().isEmpty()) {
            callback.onCompleted(ctx, CallbackResult.error(ERegisterError.FIRST_NAME_MISSING));
        } else if (info.getLastName() == null || info.getLastName().isEmpty()) {
            callback.onCompleted(ctx, CallbackResult.error(ERegisterError.LAST_NAME_MISSING));
        } else {
            HashMap<String, Object> registerInfo = new HashMap<>();
            try {
                registerInfo = info.exportToHashMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String url = (isProduction ? productionServer : stagingServer) + API_REGISTER;
            VolleyHelper.post(ctx, url, header, registerInfo, new Callback() {
                @Override
                public void onCompleted(Context context, CallbackResult result) {
                    if (result.hasError()) {
                        String message = "";
                        try {
                            String tmpMessage = result.getError().getMessage();
                            JSONObject json = new JSONObject(tmpMessage);
                            JSONObject meta = json.getJSONObject("meta");
                            message = meta.getString("message");
                        } catch (Exception E) {
                            message = "";
                        }
                        if (message.isEmpty()) {
                            callback.onCompleted(context, CallbackResult.<Object>error(result.getError()));
                        } else {
                            callback.onCompleted(context, CallbackResult.<Object>error(result.getError()));
                        }
                    } else {
                        try {
                            JSONObject data = (JSONObject) result.getData();
                            JSONObject meta = data.getJSONObject("meta");
                            String meta_code = meta.get("code").toString();
                            String meta_message = meta.getString("message");

                            if (meta_code.equalsIgnoreCase("200")) {
                                //TODO
                                JSONObject d = data.getJSONObject("data");
                                System.out.println(d);
                                callback.onCompleted(context, CallbackResult.success());
                            } else {
                                if (meta_message.equalsIgnoreCase("duplicated")) {
                                    callback.onCompleted(context, CallbackResult.<Object>error(ERegisterError.DUPLICATED));
                                } else {
                                    callback.onCompleted(context, CallbackResult.<Object>error(meta_message));
                                }
                            }

                        } catch (Exception E) {
                            callback.onCompleted(context, CallbackResult.<Object>error(E.getMessage()));
                        }
                    }
                }
            });
        }
    }

    public static void getUserAttachmentId(final Context ctx, final Callback<String> callback) {
        if (!Auth.hasLogin()) {
            callback.onCompleted(ctx, CallbackResult.<String>error(EUserProfileQueryError.UN_AUTH));
            return;
        }
        String url = String.format((isProduction ? productionServer : stagingServer) + API_GET_ATTACHMENT, Auth.getAuthData().getProfile().getLoginToken());
        VolleyHelper.get(ctx, url, header, new Callback<ArrayList<AppliedJob>>() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (!result.hasError()) {
                    try {
                        JSONObject data = (JSONObject)result.getData();
                        updateToken(data);
                        String resumeId = data.getJSONObject("data").getString("resumeId");
                        callback.onCompleted(ctx, CallbackResult.success(resumeId));
                    } catch (Exception E) {
                        CallbackResult<String> c = CallbackResult.error(E.getMessage());
                        callback.onCompleted(ctx, c);
                    }
                } else {
                    CallbackResult<String> c = CallbackResult.error(result.getError());
                    callback.onCompleted(ctx, c);
                }
            }
        });
    }
}
