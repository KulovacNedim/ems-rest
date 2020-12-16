package dev.ned.models.payloads;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
public class PhonePayload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Phone number is mandatory property")
    private String phoneNumber;
    @NotBlank(message = "Phone owner is mandatory property")
    private String phoneOwner;
    @NotBlank(message = "Phone type is mandatory property")
    private String phoneType;
    @ManyToOne
    @JoinColumn(name = "parent_data_id")
    private ParentDataPayload parentDataPayload;

    public PhonePayload() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneOwner() {
        return phoneOwner;
    }

    public void setPhoneOwner(String phoneOwner) {
        this.phoneOwner = phoneOwner;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public ParentDataPayload getParentDataPayload() {
        return parentDataPayload;
    }

    public void setParentDataPayload(ParentDataPayload parentDataPayload) {
        this.parentDataPayload = parentDataPayload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhonePayload that = (PhonePayload) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(phoneNumber, that.phoneNumber) &&
                Objects.equals(phoneOwner, that.phoneOwner) &&
                Objects.equals(phoneType, that.phoneType) &&
                Objects.equals(parentDataPayload, that.parentDataPayload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, phoneNumber, phoneOwner, phoneType, parentDataPayload);
    }
}
