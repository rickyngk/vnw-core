package vietnamworks.com.vnwcore.errors;

import R.helper.IIErrorX;

/**
 * Created by duynk on 1/22/16.
 */
public enum ELoginError implements IIErrorX {
    UNKNOWN(-1),
    EMPTY_EMAIL (10000),
    INVALID_EMAIL (10001),
    EMPTY_PASSWORD (10002),
    WRONG_CREDENTIAL (10003)


    ;
    private final int code;

    ELoginError(int code) {
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
