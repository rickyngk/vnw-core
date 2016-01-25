package vietnamworks.com.vnwcore.entities;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/25/16.
 */
public class AppliedJob extends EntityX {
    public AppliedJob() {super();}

    @BindField("job_id") String jobId;
    @BindField("job_title") String jobTitle;
    @BindField("job_company") String jobCompany;
    @BindField("posted_date") String postedDate;
    @BindField("job_location") String jobLocations;
    @BindField("applied") Integer applied;
    @BindField("expired") Integer expired;
    @BindField("save_date") String savedDate;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobCompany() {
        return jobCompany;
    }

    public void setJobCompany(String jobCompany) {
        this.jobCompany = jobCompany;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getJobLocations() {
        return jobLocations;
    }

    public void setJobLocations(String jobLocations) {
        this.jobLocations = jobLocations;
    }

    public Integer getApplied() {
        return applied;
    }

    public void setApplied(Integer applied) {
        this.applied = applied;
    }

    public Integer getExpired() {
        return expired;
    }

    public void setExpired(Integer expired) {
        this.expired = expired;
    }

    public String getSavedDate() {
        return savedDate;
    }

    public void setSavedDate(String savedDate) {
        this.savedDate = savedDate;
    }
}
