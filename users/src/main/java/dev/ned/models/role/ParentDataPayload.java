package dev.ned.models.role;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class ParentDataPayload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String citizenID;
    private Date dob;
    private String city;
    private String street;
    private String employer;
    private String jobTitle;
    private String requestedRole;
    private String email;
    @OneToMany(mappedBy = "parentDataPayload", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
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
    }

    //
//    public ParentDataPayload(String firstName, String lastName, String citizenID, Date dob, String city, String street, String employer, String jobTitle, String requestedRole, String email, List<PhonePayload> phones) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.citizenID = citizenID;
//        this.dob = dob;
//        this.city = city;
//        this.street = street;
//        this.employer = employer;
//        this.jobTitle = jobTitle;
//        this.requestedRole = requestedRole;
//        this.email = email;
//        this.phones = phones;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getCitizenID() {
//        return citizenID;
//    }
//
//    public void setCitizenID(String citizenID) {
//        this.citizenID = citizenID;
//    }
//
//    public Date getDob() {
//        return dob;
//    }
//
//    public void setDob(Date dob) {
//        this.dob = dob;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getStreet() {
//        return street;
//    }
//
//    public void setStreet(String street) {
//        this.street = street;
//    }
//
//    public String getEmployer() {
//        return employer;
//    }
//
//    public void setEmployer(String employer) {
//        this.employer = employer;
//    }
//
//    public String getJobTitle() {
//        return jobTitle;
//    }
//
//    public void setJobTitle(String jobTitle) {
//        this.jobTitle = jobTitle;
//    }
//
//    public String getRequestedRole() {
//        return requestedRole;
//    }
//
//    public void setRequestedRole(String requestedRole) {
//        this.requestedRole = requestedRole;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public List<PhonePayload> getPhones() {
//        return phones;
//    }
//
//    public void setPhones(List<PhonePayload> phones) {
//        this.phones = phones;
//    }
}
