package vietnamworks.com.vnwcore.matchingscore;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;

import R.helper.Callback;
import R.helper.CallbackResult;
import vietnamworks.com.vnwcore.VNWAPI;

/**
 * Created by duynk on 1/22/16.
 */
public class MatchingScoreTable {
    HashMap<String, MatchingScoreItem> data = new HashMap<>();
    static MatchingScoreTable instance = new MatchingScoreTable();
    static MatchingScoreChangedListener onMatchingScoreChangedListener;

    public static void create(@NonNull String user,@NonNull String job) {
        String key = user + "_" + job;
        MatchingScoreItem current = instance.data.get(key);
        if (current == null) {
            instance.data.put(key, MatchingScoreItem.createLoadingItem(user, job));
        } else {
            current.setStatus(MatchingScoreItem.EStatus.LOADING);
        }
    }

    public static void update(@NonNull String user,@NonNull String job, Integer value) {
        String key = user + "_" + job;
        MatchingScoreItem current = instance.data.get(key);
        if (current == null) {
            instance.data.put(key, MatchingScoreItem.createLoadedItem(user, job, value.intValue()));
        } else {
            current.setStatus(MatchingScoreItem.EStatus.LOADED);
            current.setValue(value);
        }
    }

    public static void reset(@NonNull String user,@NonNull String job) {
        String key = user + "_" + job;
        MatchingScoreItem current = instance.data.get(key);
        if (current != null && current.isLoading()) {
            current.setStatus(current.getValue() == 0?MatchingScoreItem.EStatus.NO_VALUE: MatchingScoreItem.EStatus.LOADED);
        }
    }

    public static void setOnMatchingScoreChangedListener(MatchingScoreChangedListener listener) {
        onMatchingScoreChangedListener = listener;
    }

    public static HashMap<String, MatchingScoreItem> get(Context ctx, String user, String... jobs) {
        ArrayList<String> miss = new ArrayList<>();
        HashMap<String, MatchingScoreItem> re = new HashMap<>();
        for (String j: jobs) {
            String key = user + "_" + j;
            MatchingScoreItem item =  instance.data.get(key);
            if (item == null || item.getStatus() == MatchingScoreItem.EStatus.NO_VALUE) {
                miss.add(j);
            }
            re.put(j, instance.data.get(key));
        }
        VNWAPI.loadMatchingScore(ctx, miss.toArray(new String[miss.size()]), user, new Callback() {
            @Override
            public void onCompleted(Context context, CallbackResult result) {
            }
        });
        return re;
    }
}
