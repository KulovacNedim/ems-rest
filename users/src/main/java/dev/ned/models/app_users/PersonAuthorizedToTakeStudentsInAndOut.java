package dev.ned.models.app_users;

import dev.ned.jpa_audit.Auditable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class PersonAuthorizedToTakeStudentsInAndOut extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String familyRelationship;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "authorized_person_phones",
            joinColumns = @JoinColumn(name = "authorized_person_id"),
            inverseJoinColumns = @JoinColumn(name = "phone_id"))
    private List<Phone> phones = new ArrayList<>();

    @ManyToMany(mappedBy = "personsAuthorizedToTakeStudentsInAndOut", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Student> students = new ArrayList<>();

    public PersonAuthorizedToTakeStudentsInAndOut() {
    }

    // associate Phone with PersonAuthorizedToTakeStudentsInAndOut
    public void addPhone(Phone phone) {
        if (phones == null) {
            phones = new ArrayList<>();
        }
        phones.add(phone);
    }

    // associate Student with PersonAuthorizedToTakeStudentsInAndOut
    public void addStudent(Student student) {
        if (students == null) {
            students = new ArrayList<>();
        }
        student.getPersonsAuthorizedToTakeStudentsInAndOut().add(this);
        students.add(student);
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFamilyRelationship() {
        return familyRelationship;
    }

    public void setFamilyRelationship(String familyRelationship) {
        this.familyRelationship = familyRelationship;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        students.forEach(student -> student.getPersonsAuthorizedToTakeStudentsInAndOut().add(this));
        this.students = students;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonAuthorizedToTakeStudentsInAndOut that = (PersonAuthorizedToTakeStudentsInAndOut) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(imageUrl, that.imageUrl) &&
                Objects.equals(familyRelationship, that.familyRelationship) &&
                Objects.equals(phones, that.phones) &&
                Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, imageUrl, familyRelationship, phones, students);
    }
}
