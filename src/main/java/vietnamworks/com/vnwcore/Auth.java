package vietnamworks.com.vnwcore;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

import R.helper.Callback;
import R.helper.CallbackResult;
import R.helper.CodecX;
import R.helper.LocalStorage;
import vietnamworks.com.vnwcore.entities.Credential;

/**
 * Created by duynk on 1/20/16.
 *
 */
public class Auth {
    private final static String LS_CURRENT_CREDENTIAL = "vnw_auth_current_credential";
    private final static String LS_RECENT_EMAILS = "vnw_auth_recent_emails";

    private static ArrayList<String> recentEmails = new ArrayList<>();
    private static vietnamworks.com.vnwcore.entities.Auth auth;

    public static void login(final Context ctx, final String email, final String password, final Callback<Object> callback) {
        VNWAPI.login(ctx, email, password, new Callback<vietnamworks.com.vnwcore.entities.Auth>() {
            @Override
            public void onCompleted(final Context context, CallbackResult<vietnamworks.com.vnwcore.entities.Auth> result) {
                if (result.hasError()) {
                    callback.onCompleted(context, CallbackResult.error(result.getError()));
                } else {
                    auth = result.getData();
                    LocalStorage.set(LS_RECENT_EMAILS, LocalStorage.getString(LS_RECENT_EMAILS, "") + ";" + email);
                    recentEmails.clear();
                    getRecentEmails();

                    String epassword = CodecX.encode(password);
                    LocalStorage.set(LS_CURRENT_CREDENTIAL, email + ";" + epassword);
                    VNWAPI.getUserAttachmentId(context, new Callback<String>() {
                        @Override
                        public void onCompleted(Context context, CallbackResult<String> result) {
                            if (!result.hasError()) {
                                auth.setAttachmentResumeId(result.getData());
                            }
                            callback.onCompleted(context, CallbackResult.success());
                        }
                    });
                }
            }
        });
    }

    public static void autoLogin(Context ctx, final Callback<Object> callback) {
        Credential credential = getCredential();
        if (credential == null) {
            callback.onCompleted(ctx, CallbackResult.error());
        } else {
            String password = credential.getPassword();
            String email = credential.getEmail();
            if (password == null || password.isEmpty() || email.isEmpty()) {
                callback.onCompleted(ctx, CallbackResult.error());
            } else {
                login(ctx, email, password, callback);
            }
        }
    }

    public static Credential getCredential() {
        String credential = LocalStorage.getString(LS_CURRENT_CREDENTIAL, null);
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

    public static void logout() {;
        LocalStorage.remove(LS_CURRENT_CREDENTIAL);
        auth = null;
    }

    public static ArrayList<String> getRecentEmails() {
        if (recentEmails == null || recentEmails.isEmpty()) {
            String strRecentEmail = LocalStorage.getString(LS_RECENT_EMAILS, "");
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
            LocalStorage.set(LS_RECENT_EMAILS, sb.toString());
        }
        return recentEmails;
    }

    public static vietnamworks.com.vnwcore.entities.Auth getAuthData() {
        return auth;
    }

    public static boolean hasLogin() {
        return getAuthData() != null && getAuthData().getProfile() != null && getAuthData().getProfile().getLoginToken() != null && !getAuthData().getProfile().getLoginToken().isEmpty();
    }
}
