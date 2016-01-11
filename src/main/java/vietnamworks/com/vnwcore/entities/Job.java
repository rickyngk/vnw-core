package vietnamworks.com.vnwcore.entities;

import R.helper.BaseEntity;
import R.helper.EntityField;

/**
 * Created by duynk on 1/7/16.
 */
public class Job extends BaseEntity {
    @EntityField("job_id") String JOB_ID;
    @EntityField("job_title") String JOB_TITLE;
    @EntityField("job_company") String JOB_COMPANY;
    @EntityField("posted_date") String POSTED_DATE;
    @EntityField("job_location") String JOB_LOCATION;
    @EntityField("job_industry") String JOB_INDUSTRY;
    @EntityField("job_detail_url") String JOB_DETAIL_URL;
    @EntityField("job_logo_url") String JOB_LOGO_URL;
    @EntityField("job_video_url") String JOB_VIDEO_URL;
    @EntityField("salary_min") String SALARY_MIN;
    @EntityField("salary_max") String SALARY_MAX;
    @EntityField("skills") String SKILLS;
    @EntityField("benefits") String BENEFITS;
    @EntityField("views") String VIEWS;
    @EntityField("applied") String APPLIED;

    public Job() {
        super();
    }

    public void setJobTitle(String value) {
        set(JOB_TITLE, value);
    }

    public String getJobTitle() {
        return getString(JOB_TITLE, "");
    }

    public String getCompany() {
        return getString(JOB_COMPANY, "");
    }
}
