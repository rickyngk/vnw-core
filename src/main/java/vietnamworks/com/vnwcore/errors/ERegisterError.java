package vietnamworks.com.vnwcore.errors;

import R.helper.CallbackResult;

/**
 * Created by duynk on 1/27/16.
 */
public enum ERegisterError implements CallbackResult.ICallbackError {
    UNKNOWN(-1),
    EMPTY_EMAIL (10000),
    INVALID_EMAIL (10001),
    FIRST_NAME_MISSING (10002),
    LAST_NAME_MISSING (10003)


    ;
    private final int code;

    ERegisterError(int code) {
        this.code = code;
    }

    @Override
    public boolean is(int code) {
        return this.code == code;
    }

    @Override
    public boolean is(CallbackResult.ICallbackError code) {
        return this.code == code.getCode();
    }

    @Override
    public int getCode() {return this.code;}

    @Override
    public String getMessage() {return "";}
}
