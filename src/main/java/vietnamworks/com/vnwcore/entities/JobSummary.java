package vietnamworks.com.vnwcore.entities;

import java.util.ArrayList;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/14/16.
 */
public class JobSummary extends EntityX {
    public JobSummary() {
        super();
    }

    @BindField("deadline_to_apply") String deadLineToApply;
    @BindField("salary_range") String salaryRange;
    @BindField("salary_min") int minSalary;
    @BindField("salary_max") int maxSalary;
    @BindField("benefits") ArrayList<Benefit> benefits;
    @BindField("salary_visible") boolean salaryVisible;
    @BindField("job_location") String locations;
    @BindField("job_level") int level;
    @BindField("job_category") String categories;
    @BindField("prefer_language") int preferLanguage;

    public String getDeadLineToApply() {
        return deadLineToApply;
    }

    public void setDeadLineToApply(String deadLineToApply) {
        this.deadLineToApply = deadLineToApply;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
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

    public ArrayList<Benefit> getBenefits() {
        return benefits;
    }

    public void setBenefits(ArrayList<Benefit> benefits) {
        this.benefits = benefits;
    }

    public boolean isSalaryVisible() {
        return salaryVisible;
    }

    public void setSalaryVisible(boolean salaryVisible) {
        this.salaryVisible = salaryVisible;
    }

    public String getLocations() {
        return locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getPreferLanguage() {
        return preferLanguage;
    }

    public void setPreferLanguage(int preferLanguage) {
        this.preferLanguage = preferLanguage;
    }
}
