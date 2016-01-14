package vietnamworks.com.vnwcore.entities;

import java.util.ArrayList;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/13/16.
 */
public class JobDetail extends EntityX {
    @BindField("job_id") String id;
    @BindField("job_title") String title;
    @BindField("job_description") String description;
    @BindField("job_requirement") String requirement;
    @BindField("skills") ArrayList<Skill> skills;
    @BindField("saved") int saved;
    @BindField("applied") int applied;
    @BindField("required_resume") boolean requiredResume;
    @BindField("required_cover_letter") boolean requiredCoverLetter;

    public JobDetail() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public int getSaved() {
        return saved;
    }

    public void setSaved(int saved) {
        this.saved = saved;
    }

    public int getApplied() {
        return applied;
    }

    public void setApplied(int applied) {
        this.applied = applied;
    }

    public boolean isRequiredResume() {
        return requiredResume;
    }

    public void setRequiredResume(boolean requiredResume) {
        this.requiredResume = requiredResume;
    }

    public boolean isRequiredCoverLetter() {
        return requiredCoverLetter;
    }

    public void setRequiredCoverLetter(boolean requiredCoverLetter) {
        this.requiredCoverLetter = requiredCoverLetter;
    }
}
