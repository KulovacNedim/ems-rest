package dev.ned.models.payloads;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class ParentDataPayload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name is mandatory property")
    private String firstName;
    @NotBlank(message = "Last name is mandatory property")
    private String lastName;
    @NotBlank(message = "Citizen ID is mandatory property")
    private String citizenID;
    @NotNull
    private Date dob;
    @NotBlank(message = "City is mandatory property")
    private String city;
    @NotBlank(message = "Street is mandatory property")
    private String street;
    private String employer;
    private String jobTitle;
    @NotBlank(message = "Requested role is mandatory property")
    private String requestedRole;
    @NotBlank(message = "Email is mandatory property")
    private String email;
    @OneToMany(mappedBy="parentDataPayload", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @NotNull(message = "At least one phone is mandatory")
    public List<PhonePayload> phones;

    public ParentDataPayload() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCitizenID() {
        return citizenID;
    }

    public void setCitizenID(String citizenID) {
        this.citizenID = citizenID;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
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

    public String getRequestedRole() {
        return requestedRole;
    }

    public void setRequestedRole(String requestedRole) {
        this.requestedRole = requestedRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PhonePayload> getPhones() {
        return phones;
    }

    public void setPhones(List<PhonePayload> phones) {
        this.phones = phones;
        phones.forEach(entity -> entity.setParentDataPayload(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParentDataPayload that = (ParentDataPayload) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(citizenID, that.citizenID) &&
                Objects.equals(dob, that.dob) &&
                Objects.equals(city, that.city) &&
                Objects.equals(street, that.street) &&
                Objects.equals(employer, that.employer) &&
                Objects.equals(jobTitle, that.jobTitle) &&
                Objects.equals(requestedRole, that.requestedRole) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phones, that.phones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, citizenID, dob, city, street, employer, jobTitle, requestedRole, email, phones);
    }
}
