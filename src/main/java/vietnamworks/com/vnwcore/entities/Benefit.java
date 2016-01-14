package vietnamworks.com.vnwcore.entities;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/14/16.
 */
public class Benefit extends EntityX {
    public Benefit() {super();}

    @BindField("benefitValue") String value;
    @BindField("benefitId") int id;
}
