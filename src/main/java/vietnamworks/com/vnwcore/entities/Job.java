package vietnamworks.com.vnwcore.entities;

import android.content.Context;

import R.helper.BindField;
import R.helper.Callback;
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

    public static void loadById(Context ctx, String id, final Callback<Job> callback) {
        VNWAPI.getJob(ctx, id, callback);
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
