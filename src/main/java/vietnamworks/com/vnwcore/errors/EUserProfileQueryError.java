package vietnamworks.com.vnwcore.errors;

import R.helper.CallbackResult;

/**
 * Created by duynk on 1/25/16.
 */
public enum EUserProfileQueryError implements CallbackResult.ICallbackError {
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
    public boolean is(CallbackResult.ICallbackError code) {
        return this.code == code.getCode();
    }

    @Override
    public int getCode() {return this.code;}

    @Override
    public String getMessage() {return "";}
}
