package dev.ned.models.app_users;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ParentAdditionalData {
    private String employer;
    private String jobTitle;
    private String education;

    public ParentAdditionalData() {
    }

    public ParentAdditionalData(String employer, String jobTitle, String education) {
        this.employer = employer;
        this.jobTitle = jobTitle;
        this.education = education;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentAdditionalData that = (ParentAdditionalData) o;
        return Objects.equals(employer, that.employer) &&
                Objects.equals(jobTitle, that.jobTitle) &&
                Objects.equals(education, that.education);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employer, jobTitle, education);
    }
}
