package dev.ned.models.payloads;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
public class StudentDataPayload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name is mandatory property")
    public String firstName;
    @NotBlank(message = "Last name is mandatory property")
    public String lastName;
    @NotBlank(message = "Citizen ID is mandatory property")
    public String citizenID;
    @NotNull
    public Date dob;
    @ManyToOne
    @JoinColumn(name = "role_request_id")
    private RoleRequestPayload roleRequestPayload;

    public StudentDataPayload() {
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

    public RoleRequestPayload getRoleRequestPayload() {
        return roleRequestPayload;
    }

    public void setRoleRequestPayload(RoleRequestPayload roleRequestPayload) {
        this.roleRequestPayload = roleRequestPayload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDataPayload that = (StudentDataPayload) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(citizenID, that.citizenID) &&
                Objects.equals(dob, that.dob) &&
                Objects.equals(roleRequestPayload, that.roleRequestPayload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, citizenID, dob, roleRequestPayload);
    }
}
