package vietnamworks.com.vnwcore.errors;

import R.helper.IIErrorX;

/**
 * Created by duynk on 1/22/16.
 */
public enum EApplyJobError implements IIErrorX {
    UNKNOWN(-1),
    BAD_REQUEST (400)


    ;
    private final int code;

    EApplyJobError(int code) {
        this.code = code;
    }

    @Override
    public boolean is(int code) {
        return this.code == code;
    }

    @Override
    public boolean is(IIErrorX code) {
        return this.code == code.value();
    }

    @Override
    public int value() {return this.code;}
}
