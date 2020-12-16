package dev.ned.models.role;

import javax.persistence.*;

@Entity
public class PhonePayload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phoneNumber;
    private String phoneOwner;
    private String phoneType;

    @ManyToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
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
}
