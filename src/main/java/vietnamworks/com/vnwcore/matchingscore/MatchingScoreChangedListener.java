package vietnamworks.com.vnwcore.matchingscore;

/**
 * Created by duynk on 1/22/16.
 */
public interface MatchingScoreChangedListener {
    public void onChanged(String userId, String jobId, int score);
}
