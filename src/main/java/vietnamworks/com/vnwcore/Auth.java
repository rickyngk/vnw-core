package vietnamworks.com.vnwcore;

import android.content.Context;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.CallbackSuccess;
import R.helper.CodecX;
import R.helper.Common;
import R.helper.LocalStorage;
import vietnamworks.com.vnwcore.entities.Credential;
import vietnamworks.com.vnwcore.errors.ELoginError;

/**
 * Created by duynk on 1/20/16.
 */
public class Auth {
    private static ArrayList<String> recentEmails = new ArrayList<>();
    private static vietnamworks.com.vnwcore.entities.Auth auth;

    public static void login(Context ctx, final String email, final String password, final Callback callback) {
        if (email == null || email.isEmpty()) {
            callback.onCompleted(ctx, new CallbackResult(new CallbackResult.CallbackErrorInfo(ELoginError.EMPTY_EMAIL.value(), "")));
        } else if (!Common.isValidEmail(email)) {
            callback.onCompleted(ctx, new CallbackResult(new CallbackResult.CallbackErrorInfo(ELoginError.INVALID_EMAIL.value(), "")));
        } else if (password == null || password.isEmpty()) {
            callback.onCompleted(ctx, new CallbackResult(new CallbackResult.CallbackErrorInfo(ELoginError.EMPTY_PASSWORD.value(), "")));
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
                                auth = new vietnamworks.com.vnwcore.entities.Auth();
                                auth.importFromJson(data.getJSONObject("data"));

                                LocalStorage.set("vnw_auth_current_account", data);

                                LocalStorage.set("vnw_auth_recent_emails", LocalStorage.getString("vnw_auth_recent_emails", "") + ";" + email);
                                recentEmails.clear();
                                getRecentEmails();

                                String epassword = CodecX.encode(password);
                                LocalStorage.set("vnw_auth_current_credential", email + ";" + epassword);
                                callback.onCompleted(context, new CallbackSuccess());
                            } else {
                                if (data.getJSONObject("meta").getString("code").equalsIgnoreCase("200")) {
                                    callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackErrorInfo(ELoginError.WRONG_CREDENTIAL.value(), "")));
                                } else {
                                    callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackErrorInfo(-1, "")));
                                }
                            }
                        } catch (Exception E) {
                            callback.onCompleted(context, new CallbackResult(new CallbackResult.CallbackErrorInfo(-1, E.getMessage())));
                        }
                    }
                    }
            });
        }
    }

    public static void autoLogin(Context ctx, final Callback callback) {
        Credential credential = getCredential();
        if (credential == null) {
            callback.onCompleted(ctx, new CallbackResult(new CallbackResult.CallbackErrorInfo(-1, "")));
        } else {
            String password = credential.getPassword();
            String email = credential.getEmail();
            if (password == null || password.isEmpty() || email.isEmpty()) {
                callback.onCompleted(ctx, new CallbackResult(new CallbackResult.CallbackErrorInfo(-2, "")));
            } else {
                login(ctx, email, password, callback);
            }
        }
    }

    public static Credential getCredential() {
        String credential = LocalStorage.getString("vnw_auth_current_credential", null);
        if (credential == null) {
            return null;
        } else {
            int split_index = credential.indexOf(";");
            String email = credential.substring(0, split_index);
            String epassword = credential.substring(split_index + 1, credential.length());
            String password = CodecX.decode(epassword);
            return new Credential(email, password);
        }
    }

    public static void logout() {
        LocalStorage.remove("vnw_auth_current_account");
        LocalStorage.remove("vnw_auth_current_credential");
        auth = null;
    }

    public static ArrayList<String> getRecentEmails() {
        if (recentEmails == null || recentEmails.isEmpty()) {
            String strRecentEmail = LocalStorage.getString("vnw_auth_recent_emails", "");
            String []emails = strRecentEmail.split(";");
            HashMap<String, String> emailHash = new HashMap<String, String>();
            for (String e: emails) {
                if (e != null && e.isEmpty()) {
                    emailHash.put(e, "");
                }
            }
            StringBuilder sb = new StringBuilder();
            recentEmails = new ArrayList<String>();
            String delim = "";
            for (String k: emailHash.keySet()) {
                sb.append(delim);
                sb.append(k);
                recentEmails.add(k);
                delim = ";";
            }
            LocalStorage.set("vnw_auth_recent_emails", sb.toString());
        }
        return recentEmails;
    }

    public static vietnamworks.com.vnwcore.entities.Auth getAuthData() {
        return auth;
    }
}
