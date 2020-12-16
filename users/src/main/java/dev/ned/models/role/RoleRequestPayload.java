package dev.ned.models.role;

import javax.persistence.*;

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
    @OneToOne(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "student_data_id")
    private StudentDataPayload studentData;

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

    public StudentDataPayload getStudentData() {
        return studentData;
    }

    public void setStudentData(StudentDataPayload studentData) {
        this.studentData = studentData;
    }
}





