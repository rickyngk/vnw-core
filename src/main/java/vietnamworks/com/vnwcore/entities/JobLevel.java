package vietnamworks.com.vnwcore.entities;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/15/16.
 */
public class JobLevel extends EntityX {
    public JobLevel() {super();}

    @BindField("job_level_id") String id;
    @BindField("lang_vn") String vn;
    @BindField("lang_en") String en;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVn() {
        return vn;
    }

    public void setVn(String vn) {
        this.vn = vn;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }
}
