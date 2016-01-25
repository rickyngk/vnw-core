package vietnamworks.com.vnwcore.entities;

import android.content.Context;

import org.json.JSONObject;

import R.helper.BindField;
import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.EntityX;
import vietnamworks.com.vnwcore.VNWAPI;

/**
 * Created by duynk on 1/13/16.
 */
public class Job extends EntityX {
    public Job() {
        super();
    }

    @BindField("job_detail") JobDetail jobDetail;
    @BindField("job_summary") JobSummary jobSummary;
    @BindField("job_company") Company company;

    public static void loadById(Context ctx, String id, final Callback callback) {
        VNWAPI.getJob(ctx, id, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
                if (result.hasError()) {
                    callback.onCompleted(context, CallbackResult.error(result.getError()));
                } else {
                    try {
                        JSONObject res = (JSONObject) result.getData();
                        JSONObject data = res.optJSONObject("data");
                        Job j = new Job();
                        j.importFromJson(data);
                        callback.onCompleted(context, CallbackResult.success(j));
                    } catch (Exception E) {
                        callback.onCompleted(context, CallbackResult.error(E.getMessage()));
                    }
                }
            }
        });
    }

    public JobDetail getJobDetail() {
        return jobDetail;
    }

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

    public JobSummary getJobSummary() {
        return jobSummary;
    }

    public void setJobSummary(JobSummary jobSummary) {
        this.jobSummary = jobSummary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
