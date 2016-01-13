package vietnamworks.com.vnwcore.entities;

import R.helper.BaseEntity;
import R.helper.EntityField;

/**
 * Created by duynk on 1/13/16.
 */
public class Job extends BaseEntity {

    @EntityField(value = "job_detail", type = JobDetail.class) public static String JOB_DETAIL;

    public Job() {
        super();
    }
}
