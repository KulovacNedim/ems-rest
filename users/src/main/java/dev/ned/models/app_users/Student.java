package dev.ned.models.app_users;

import dev.ned.models.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Student extends User {

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "parent_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "parent_id"))
    private List<Parent> parents = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "student_authorized_persons",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "authorized_person_id"))
    private List<PersonAuthorizedToTakeStudentsInAndOut> personsAuthorizedToTakeStudentsInAndOut = new ArrayList<>();

    public List<Parent> getParents() {
        return parents;
    }

    public void setParents(List<Parent> parents) {
        parents.forEach(parent -> parent.getStudents().add(this));
        this.parents = parents;
    }

    public List<PersonAuthorizedToTakeStudentsInAndOut> getPersonsAuthorizedToTakeStudentsInAndOut() {
        return personsAuthorizedToTakeStudentsInAndOut;
    }

    public void setPersonsAuthorizedToTakeStudentsInAndOut(List<PersonAuthorizedToTakeStudentsInAndOut> personsAuthorizedToTakeStudentsInAndOuts) {
        personsAuthorizedToTakeStudentsInAndOuts.forEach(person -> person.getStudents().add(this));
        this.personsAuthorizedToTakeStudentsInAndOut = personsAuthorizedToTakeStudentsInAndOuts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Student student = (Student) o;
        return Objects.equals(parents, student.parents) &&
                Objects.equals(personsAuthorizedToTakeStudentsInAndOut, student.personsAuthorizedToTakeStudentsInAndOut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), parents, personsAuthorizedToTakeStudentsInAndOut);
    }
}
