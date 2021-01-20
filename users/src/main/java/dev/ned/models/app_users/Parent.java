package dev.ned.models.app_users;

import dev.ned.models.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Parent extends User {

    @ManyToMany(mappedBy = "parents", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Student> students = new ArrayList<>();

    @Embedded
    private ParentAdditionalData parentAdditionalData = new ParentAdditionalData();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        students.forEach(student -> student.getParents().add(this));
        this.students = students;
    }

    public ParentAdditionalData getParentAdditionalData() {
        return parentAdditionalData;
    }

    public void setParentAdditionalData(ParentAdditionalData parentAdditionalData) {
        this.parentAdditionalData = parentAdditionalData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Parent parent = (Parent) o;
        return Objects.equals(students, parent.students) &&
                Objects.equals(parentAdditionalData, parent.parentAdditionalData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), students, parentAdditionalData);
    }
}
