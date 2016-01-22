package vietnamworks.com.vnwcore.matchingscore;

/**
 * Created by duynk on 1/22/16.
 */
public class MatchingScoreItem {
    public enum EStatus {
        NO_VALUE, LOADING, LOADED
    }
    private EStatus status = EStatus.NO_VALUE;
    private int value = 0;
    protected MatchingScoreItem() {}

    public static MatchingScoreItem createLoadingItem(String userId, String jobId) {
        MatchingScoreItem i = new MatchingScoreItem();
        i.value = 0;
        i.status = EStatus.LOADING;
        return i;
    }

    public static MatchingScoreItem createLoadedItem(String userId, String jobId, int value) {
        MatchingScoreItem i = new MatchingScoreItem();
        i.value = 0;
        i.status = EStatus.LOADED;
        return i;
    }

    public boolean isLoading() {
        return status == EStatus.LOADING;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public EStatus getStatus() {
        return status;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
