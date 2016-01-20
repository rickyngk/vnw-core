package vietnamworks.com.vnwcore.entities;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/20/16.
 */
public class Auth extends EntityX {
    public Auth() {super();}

    @BindField("profile") Profile profile;
    @BindField("coverletter") String coverLetter;
    @BindField("resumes") AuthResume resume;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public AuthResume getResume() {
        return resume;
    }

    public void setResume(AuthResume resume) {
        this.resume = resume;
    }
}
