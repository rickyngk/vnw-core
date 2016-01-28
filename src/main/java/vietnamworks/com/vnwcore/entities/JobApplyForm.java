package vietnamworks.com.vnwcore.entities;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 1/21/16.
 */
public class JobApplyForm extends EntityX {
    @BindField("job_id") Integer jobId;
    @BindField("resume_attach_id") String resumeAttachId;
    @BindField("application_subject") String applicationSubject;
    @BindField("cover_letter") String coverLetter;
    @BindField("lang") Integer lang;
    String fileContents;
    String token;
    Credential credential;

    public JobApplyForm(){super();}

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getFileContents() {
        return fileContents;
    }

    public void setFileContents(String fileContents) {
        this.fileContents = fileContents;
    }

    public String getResumeAttachId() {
        return resumeAttachId;
    }
    
    public void setResumeAttachId(String resumeAttachId) {
        this.resumeAttachId = resumeAttachId;
    }

    public String getApplicationSubject() {
        return applicationSubject;
    }

    public void setApplicationSubject(String applicationSubject) {
        this.applicationSubject = applicationSubject;
    }

    public String getCoverLetter() {
        return coverLetter;
    }

    public void setCoverLetter(String coverLetter) {
        this.coverLetter = coverLetter;
    }

    public Integer getLang() {
        return lang;
    }

    public void setLang(Integer lang) {
        this.lang = lang;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Credential getCredential() {
        return credential;
    }

    public void setCredential(Credential credential) {
        this.credential = credential;
    }
}
