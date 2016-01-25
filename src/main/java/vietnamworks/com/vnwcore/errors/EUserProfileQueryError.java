package vietnamworks.com.vnwcore.errors;

import R.helper.IIErrorX;

/**
 * Created by duynk on 1/25/16.
 */
public enum EUserProfileQueryError implements IIErrorX {
    UNKNOWN(-1),
    BAD_REQUEST (400),
    UN_AUTH(20000)


    ;
    private final int code;

    EUserProfileQueryError(int code) {
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
