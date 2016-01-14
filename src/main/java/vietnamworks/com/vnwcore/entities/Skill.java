package vietnamworks.com.vnwcore.entities;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/13/16.
 */
public class Skill extends EntityX {
    @BindField("skillName") String name;
    @BindField("skillWeight") int skillWeight;

    public Skill() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return skillWeight;
    }

    public void setWeight(int skillWeight) {
        this.skillWeight = skillWeight;
    }
}
