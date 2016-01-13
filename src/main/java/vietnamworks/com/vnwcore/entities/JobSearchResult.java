package vietnamworks.com.vnwcore.entities;

import R.helper.BaseEntity;
import R.helper.EntityField;

/**
 * Created by duynk on 1/7/16.
 */
public class JobSearchResult extends BaseEntity {
    @EntityField("job_id") public static String JOB_ID;
    @EntityField("job_title") public static String JOB_TITLE;
    @EntityField("job_company") public static String JOB_COMPANY;
    @EntityField("posted_date") public static String POSTED_DATE;
    @EntityField("job_location") public static String JOB_LOCATION;
    @EntityField("job_industry") public static String JOB_INDUSTRY;
    @EntityField("job_detail_url") public static String JOB_DETAIL_URL;
    @EntityField("job_logo_url") public static String JOB_LOGO_URL;
    @EntityField("job_video_url") public static String JOB_VIDEO_URL;
    @EntityField("salary_min") public static String SALARY_MIN;
    @EntityField("salary_max") public static String SALARY_MAX;
    @EntityField("skills") public static String SKILLS;
    @EntityField("benefits") public static String BENEFITS;
    @EntityField("views") public static String VIEWS;
    @EntityField("applied") public static String APPLIED;

    public JobSearchResult() {
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

    public String getJobId() {
        return getString(JOB_ID, "");
    }
}
