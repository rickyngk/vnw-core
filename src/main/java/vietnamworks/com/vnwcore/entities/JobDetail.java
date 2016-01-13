package vietnamworks.com.vnwcore.entities;

import R.helper.BaseEntity;
import R.helper.EntityArrayField;
import R.helper.EntityField;

/**
 * Created by duynk on 1/13/16.
 */
public class JobDetail extends BaseEntity {
    @EntityField("job_id") public static String JOB_ID;
    @EntityField("job_title") public static String JOB_TITLE;
    @EntityField("job_description") public static String JOB_DESCRIPTION;
    @EntityField("job_requirement") public static String JOB_REQUIREMENT;
    @EntityArrayField(value = "skills", type = Skill.class) public static String SKILLS;
    @EntityField(value = "saved", type = Integer.class) public static String SAVED;
    @EntityField(value = "applied", type = Integer.class) public static String APPLIED;
    @EntityField(value = "required_resume", type = Boolean.class) public static String REQUIRED_RESUME;
    @EntityField(value = "required_cover_letter", type = Boolean.class) public static String REQUIRED_COVER_LETTER;

    public JobDetail() {
        super();
    }
}
