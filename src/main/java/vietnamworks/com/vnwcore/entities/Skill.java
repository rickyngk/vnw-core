package vietnamworks.com.vnwcore.entities;

import R.helper.BaseEntity;
import R.helper.EntityField;

/**
 * Created by duynk on 1/13/16.
 */
public class Skill extends BaseEntity {
    @EntityField("skillName") public static String SKILL_NAME;
    @EntityField("skillWeight") public static String SKILL_WEIGHT;

    public Skill() {
        super();
    }
}
