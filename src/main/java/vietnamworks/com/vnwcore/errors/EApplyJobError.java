package vietnamworks.com.vnwcore.errors;

import R.helper.CallbackResult;

/**
 * Created by duynk on 1/22/16.
 *
 */
public enum EApplyJobError implements CallbackResult.ICallbackError {
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
    public boolean is(CallbackResult.ICallbackError code) {
        return this.code == code.getCode();
    }

    @Override
    public int getCode() {return this.code;}

    @Override
    public String getMessage() {return "";}
}
