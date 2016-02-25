package vietnamworks.com.vnwcore.entities;

import java.util.ArrayList;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/7/16.
 */
public class JobSearchResult extends EntityX {
    @BindField("job_id") String id;
    @BindField("job_title") String title;
    @BindField("job_company") String company;
    @BindField("posted_date") String postedDate;
    @BindField("job_location") String jobLocation;
    @BindField("job_industry") String industry;
    @BindField("job_detail_url") String detailURL;
    @BindField("job_logo_url") String logoURL;
    @BindField("job_video_url") String videoURL;
    @BindField("salary") String salary;
    @BindField("salary_min") int minSalary;
    @BindField("salary_max") int maxSalary;
    @BindField("skills") ArrayList<Skill> skills;
    @BindField("benefits") String benefits;
    @BindField("views") int views;
    @BindField("applied") int applied;
    @BindField("job_level") String jobLevel;

    public JobSearchResult() {
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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getLocations() {
        return jobLocation;
    }

    public void setLocations(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getDetailURL() {
        return detailURL;
    }

    public void setDetailURL(String detailURL) {
        this.detailURL = detailURL;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(int minSalary) {
        this.minSalary = minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(int maxSalary) {
        this.maxSalary = maxSalary;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public void setSkills(ArrayList<Skill> skills) {
        this.skills = skills;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getApplied() {
        return applied;
    }

    public void setApplied(int applied) {
        this.applied = applied;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }
}
