package vietnamworks.com.vnwcore;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.Common;
import R.helper.LocalStorage;

/**
 * Created by duynk on 1/20/16.
 */
public class Auth {
    public enum ELoginError {
        EMPTY_EMAIL (10000),
        INVALID_EMAIL (10001),
        EMPTY_PASSWORD (10002),
        WRONG_CREDENTIAL (10003)


        ;
        private final int code;

        private ELoginError(int code) {
            this.code = code;
        }

        public boolean is(int code) {
            return this.code == code;
        }
    }

    private static ArrayList<String> recentEmails = new ArrayList<>();
    public static void login(Context ctx, final String email, final String password, final Callback callback) {
        if (email == null || email.isEmpty()) {
            callback.onCompleted(ctx, new CallbackResult(new CallbackResult.CallbackError(ELoginError.EMPTY_EMAIL.code, "")));
        } else if (!Common.isValidEmail(email)) {
            callback.onCompleted(ctx, new CallbackResult(new CallbackResult.CallbackError(ELoginError.INVALID_EMAIL.code, "")));
        } else if (password == null || password.isEmpty()) {
            callback.onCompleted(ctx, new CallbackResult(new CallbackResult.CallbackError(ELoginError.EMPTY_PASSWORD.code, "")));
        } else {
            VNWAPI.login(ctx, email, password, new Callback() {
                @Override
                public void onCompleted(Context context, CallbackResult result) {
                    if (result.hasError()) {
                        callback.onCompleted(context, new CallbackResult(result.getError()));
                    } else {
                        //save current account;
                        try {
                            JSONObject data = (JSONObject) result.getData();
                            if (data.getJSONObject("meta").getString("message").equalsIgnoreCase("OK")) {
                                vietnamworks.com.vnwcore.entities.Auth auth = new vietnamworks.com.vnwcore.entities.Auth();
                                auth.importFromJson(data.getJSONObject("data"));

                                LocalStorage.set("vnw_auth_current_account", data);

                                String strRecentEmail = LocalStorage.getString("vnw_auth_recent_emails", "");
                                StringBuilder sb = new StringBuilder(strRecentEmail);
                                if (strRecentEmail.length() > 0) {
                                    sb.append(";");
                                }
                                sb.append(email);
                                LocalStorage.set("vnw_auth_recent_emails", sb.toString());
                                recentEmails = new ArrayList<String>(Arrays.asList(strRecentEmail.split(";")));

                                String epassword = codec.encode(password);
                                LocalStorage.set("vnw_auth_current_credential", email + ";" + epassword);
                            } else {
                                if (data.getJSONObject("meta").getString("code").equalsIgnoreCase("200")) {
                                    callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackError(ELoginError.WRONG_CREDENTIAL.code, "")));
                                } else {
                                    callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackError(-1, "")));
                                }
                            }
                        } catch (Exception E) {
                            callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackError(-1, E.getMessage())));
                        }
                    }
                    }
            });
        }
    }

    public static void autoLogin(Context ctx, final Callback callback) {
        String credential = LocalStorage.getString("vnw_auth_current_credential", null);
        if (credential == null) {
            callback.onCompleted(ctx, new CallbackResult(new CallbackResult.CallbackError(-1, "")));
        } else {
            int split_index = credential.indexOf(";");
            String email = credential.substring(0, split_index);
            String epassword = credential.substring(split_index + 1, credential.length());
            String password = codec.decode(epassword);
            if (password == null || password.isEmpty() || email.isEmpty()) {
                callback.onCompleted(ctx, new CallbackResult(new CallbackResult.CallbackError(-2, "")));
            } else {
                login(ctx, email, password, callback);
            }
        }
    }

    public static void logout(Callback callback) {

    }
}
