package dev.ned.models.payloads;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@Entity
public class RoleRequestPayload {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "parent_data_id")
    private ParentDataPayload parentData;

    @OneToMany(mappedBy = "roleRequestPayload", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StudentDataPayload> studentData;

    public RoleRequestPayload() {
    }

    public ParentDataPayload getParentData() {
        return parentData;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setParentData(ParentDataPayload parentData) {
        this.parentData = parentData;
    }

    public List<StudentDataPayload> getStudentData() {
        return studentData;
    }

    public void setStudentData(List<StudentDataPayload> studentDataPayloads) {
        this.studentData = studentDataPayloads;
        studentDataPayloads.forEach(entity -> entity.setRoleRequestPayload(this));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleRequestPayload payload = (RoleRequestPayload) o;
        return Objects.equals(id, payload.id) &&
                Objects.equals(parentData, payload.parentData) &&
                Objects.equals(studentData, payload.studentData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parentData, studentData);
    }
}





