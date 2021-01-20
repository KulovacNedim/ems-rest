package dev.ned.models.app_users;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PersonAuthorizedToTakeStudentsInAndOut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String imageUrl;
    private String familyRelationship;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
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
//        phones.forEach(phone -> phone.setPersonAuthorizedToTakeStudentsInAndOut(this));
        this.phones = phones;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        students.forEach(student -> student.getPersonsAuthorizedToTakeStudentsInAndOut().add(this));
        this.students = students;
    }

    // equals and hashcode
}
