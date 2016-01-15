package vietnamworks.com.vnwcore.entities;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/14/16.
 */
public class Benefit extends EntityX {
    public Benefit() {super();}

    @BindField("benefitName") String name;
    @BindField("benefitValue") String value;
    @BindField("benefitId") String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
