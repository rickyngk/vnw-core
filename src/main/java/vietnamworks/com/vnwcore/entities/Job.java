package vietnamworks.com.vnwcore.entities;

import R.helper.BaseEntity;

/**
 * Created by duynk on 1/7/16.
 */
public class Job extends BaseEntity {

    public enum JobField implements BaseEntity.EntityField {
        JOB_ID("job_id"),
        JOB_TITLE("job_title"),
        JOB_COMPANY("job_company"),
        POSTED_DATE("posted_date"),
        JOB_LOCATION("job_location"),
        JOB_INDUSTRY("job_industry"),
        JOB_DETAIL_URL("job_detail_url"),
        JOB_LOGO_URL("job_logo_url"),
        JOB_VIDEO_URL("job_video_url"),
        SALARY_MIN("salary_min"),
        SALARY_MAX("salary_max"),
        SKILLS("skills"),
        BENEFITS("benefits"),
        VIEWS("views"),
        APPLIED("applied")
        ;

        private final String name;
        private JobField(String s) {
            name = s;
        }

        @Override public boolean equals(String otherName) {
            return otherName!=null && otherName.equals(this.name);
        }

        @Override public String toString() {
            return this.name;
        }
    }

    public Job() {
        super(JobField.values());
    }

    public void setJobTitle(String value) {
        set(JobField.JOB_TITLE, value);
    }

    public String getJobTitle() {
        return getString(JobField.JOB_TITLE, "");
    }

    public String getCompany() {
        return getString(JobField.JOB_COMPANY, "");
    }
}
