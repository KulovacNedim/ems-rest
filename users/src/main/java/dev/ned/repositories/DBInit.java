package dev.ned.repositories;

import dev.ned.models.*;
import dev.ned.models.app_users.*;
import dev.ned.models.payloads.ParentDataPayload;
import dev.ned.models.payloads.PhonePayload;
import dev.ned.models.payloads.RoleRequestPayload;
import dev.ned.models.payloads.StudentDataPayload;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DBInit implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private NotificationRepository notificationRepository;
    private PersonAuthorizedToTakeStudentsInAndOutRepository authorizedPersonsRepository;

    public DBInit(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, NotificationRepository notificationRepository, PersonAuthorizedToTakeStudentsInAndOutRepository authorizedPersonsRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.notificationRepository = notificationRepository;
        this.authorizedPersonsRepository = authorizedPersonsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role("ADMIN");
        Role ceoRole = new Role("CEO");
        Role teacherRole = new Role("TEACHER");
        Permission editProfilePermission = new Permission("EDIT_PROFILE");
        Permission accessApiPermission = new Permission("ACCESS_API");

        User admin = new User();
        admin.setEnabled(false);
        admin.setLocked(false);
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("adm12345"));
        admin.setFirstName("admin name");
        admin.setLastName("admin name");
        admin.setDob(new Date());
        admin.setUcrn("admin ucrn");
        admin.addRole(adminRole);
        admin.addRole(ceoRole);
        admin.addPermission(editProfilePermission);

        Employee teacher = new Employee();
        teacher.setEnabled(true);
        teacher.setLocked(false);
        teacher.setEmail("teacher@gmail.com");
        teacher.setPassword(passwordEncoder.encode("tea12345"));
        teacher.setFirstName("teacher name");
        teacher.setLastName("last name");
        teacher.setDob(new Date());
        teacher.setUcrn("teacher ucrn");
        teacher.addRole(teacherRole);
        teacher.addPermission(accessApiPermission);

        NotEnabledReason reason = new NotEnabledReason(UserNotEnabledReasons.MISSING_ROLE, new Date(), teacher, true);
        teacher.setNotEnabledReasons(new ArrayList<>());
        teacher.getNotEnabledReasons().add(reason);

        this.userRepository.saveAll(Arrays.asList(admin, teacher));

        Parent parent = new Parent();
        parent.setEnabled(true);
        parent.setLocked(false);
        parent.setEmail("parent@gmail.com");
        parent.setPassword(passwordEncoder.encode("tea12345"));
        parent.setFirstName("parent name");
        parent.setLastName("last name");
        parent.setDob(new Date());
        parent.setUcrn("parent ucrn");
        NotEnabledReason reasonParent = new NotEnabledReason(UserNotEnabledReasons.MISSING_ROLE, new Date(), parent, true);
        parent.setNotEnabledReasons(new ArrayList<>());
        parent.getNotEnabledReasons().add(reasonParent);
        parent.getParentAdditionalData().setEmployer("IloveIT");

        Student student = new Student();
        student.setEnabled(true);
        student.setLocked(false);
        student.setEmail("student@gmail.com");
        student.setPassword(passwordEncoder.encode("tea12345"));
        student.setFirstName("student name");
        student.setLastName("last name");
        student.setDob(new Date());
        student.setUcrn("student ucrn");
        NotEnabledReason reasonStudent = new NotEnabledReason(UserNotEnabledReasons.MISSING_ROLE, new Date(), student, true);
        student.setNotEnabledReasons(new ArrayList<>());
        student.getNotEnabledReasons().add(reasonStudent);

        Student student2 = new Student();
        student2.setEnabled(true);
        student2.setLocked(false);
        student2.setEmail("student2@gmail.com");
        student2.setPassword(passwordEncoder.encode("tea12345"));
        student2.setFirstName("student2 name");
        student2.setLastName("last name");
        student2.setDob(new Date());
        student2.setUcrn("student2 ucrn");
        NotEnabledReason reasonStudent2 = new NotEnabledReason(UserNotEnabledReasons.MISSING_ROLE, new Date(), student2, true);
        student2.setNotEnabledReasons(new ArrayList<>());
        student2.getNotEnabledReasons().add(reasonStudent2);

        this.userRepository.saveAll(Arrays.asList(parent, student, student2));
        parent.addPermission(accessApiPermission);
        parent.addRole(teacherRole);
        student.addPermission(accessApiPermission);
        student.addRole(teacherRole);
        student2.addPermission(accessApiPermission);
        student2.addRole(teacherRole);

        parent.setStudents(Arrays.asList(student, student2));
        this.userRepository.saveAll(Arrays.asList(parent, student, student2));

        Phone apPhone = new Phone();
        apPhone.setPhoneNumber("061");
        apPhone.setPhoneOwner("owner name");
        apPhone.setPhoneType(PhoneType.PERSONAL);

        PersonAuthorizedToTakeStudentsInAndOut authorizedPerson = new PersonAuthorizedToTakeStudentsInAndOut();
        authorizedPerson.setFirstName("AP fname");
        authorizedPerson.setLastName("AP lname");
        authorizedPerson.setFamilyRelationship("grandfather");
        authorizedPerson.setImageUrl("url");
//        authorizedPerson.setPhones(Arrays.asList(apPhone));

        PersonAuthorizedToTakeStudentsInAndOut authorizedPerson2 = new PersonAuthorizedToTakeStudentsInAndOut();
        authorizedPerson2.setFirstName("AP fname");
        authorizedPerson2.setLastName("AP lname");
        authorizedPerson2.setFamilyRelationship("grandfather");
        authorizedPerson2.setImageUrl("url");
        authorizedPerson2.setPhones(Arrays.asList(apPhone));

        authorizedPersonsRepository.saveAll(Arrays.asList(authorizedPerson, authorizedPerson2));
        authorizedPerson.addStudent(student);
        authorizedPerson.addStudent(student2);
        authorizedPersonsRepository.save(authorizedPerson);


        // NOTIFICATIONS
        RoleRequestPayload payload= new RoleRequestPayload();
        ParentDataPayload parentDataPayload = new ParentDataPayload();
        parentDataPayload.setFirstName("Nedim");
        parentDataPayload.setLastName("Kulovac");
        parentDataPayload.setCitizenID("2508");
        parentDataPayload.setDob(new Date());
        parentDataPayload.setCity("Tesanj");
        parentDataPayload.setStreet("Osmana Pobrica");
        PhonePayload phonePayload = new PhonePayload();
        phonePayload.setPhoneType("PERSONAL");
        phonePayload.setPhoneNumber("061061061");
        phonePayload.setPhoneOwner("Nedim");
        phonePayload.setParentDataPayload(parentDataPayload);
        parentDataPayload.setPhones(Arrays.asList(phonePayload));
        parentDataPayload.setRequestedRole("PARENT");
        parentDataPayload.setEmail("nedim@gmail.com");
        payload.setParentData(parentDataPayload);
        StudentDataPayload studentDataPayload = new StudentDataPayload();
        studentDataPayload.setFirstName("Malik");
        studentDataPayload.setLastName("Kulovac");
        studentDataPayload.setCitizenID("2508");
        studentDataPayload.setDob(new Date());
        payload.setStudentData(Collections.singletonList(studentDataPayload));

        Notification notification = new Notification();
        notification.setMessage("Request for role setup");
        notification.setType(NotificationType.ROLE_SETUP_REQUEST);
        notification.setCreatedAt(new Date());
        notification.addArg("payload_id", String.valueOf(payload.getId()));
        notification.addArg("parent_first_name", payload.getParentData().getFirstName());
        notification.addArg("parent_last_name", payload.getParentData().getLastName());
        notification.addArg("student_first_name", payload.getStudentData().get(0).getFirstName());
        notification.addArg("student_last_name", payload.getStudentData().get(0).getLastName());

        notificationRepository.save(notification);

        Role role1 = roleRepository.findById(1L).get();
        Role role2 = roleRepository.findById(3L).get();
        List<Role> rolesToNotify = new ArrayList<>();
        notification.setRolesToNotify(Arrays.asList(role1, role2));
        for (Role role : rolesToNotify) {
            notification.getRolesToNotify().add(role);
        }
        notificationRepository.save(notification);

        RoleRequestPayload payload2= new RoleRequestPayload();
        ParentDataPayload parentDataPayload2 = new ParentDataPayload();
        parentDataPayload2.setFirstName("Amela");
        parentDataPayload2.setLastName("MK");
        parentDataPayload2.setCitizenID("2508");
        parentDataPayload2.setDob(new Date());
        parentDataPayload2.setCity("Tesanj");
        parentDataPayload2.setStreet("Osmana Pobrica");
        PhonePayload phonePayload2 = new PhonePayload();
        phonePayload2.setPhoneType("PERSONAL");
        phonePayload2.setPhoneNumber("062062062");
        phonePayload2.setPhoneOwner("Amela");
        phonePayload2.setParentDataPayload(parentDataPayload2);
        parentDataPayload2.setPhones(Arrays.asList(phonePayload));
        parentDataPayload2.setRequestedRole("PARENT");
        parentDataPayload2.setEmail("amela@gmail.com");
        payload2.setParentData(parentDataPayload2);
        StudentDataPayload studentDataPayload2 = new StudentDataPayload();
        studentDataPayload2.setFirstName("Nani");
        studentDataPayload2.setLastName("Kulovac");
        studentDataPayload2.setCitizenID("2508");
        studentDataPayload2.setDob(new Date());
        payload2.setStudentData(Collections.singletonList(studentDataPayload2));

        Notification notification2 = new Notification();
        notification2.setMessage("Request for role setup");
        notification2.setType(NotificationType.ROLE_SETUP_REQUEST);
        notification2.setCreatedAt(new Date());
        notification2.addArg("payload_id", String.valueOf(payload2.getId()));
        notification2.addArg("parent_first_name", payload2.getParentData().getFirstName());
        notification2.addArg("parent_last_name", payload2.getParentData().getLastName());
        notification2.addArg("student_first_name", payload2.getStudentData().get(0).getFirstName());
        notification2.addArg("student_last_name", payload2.getStudentData().get(0).getLastName());

        notificationRepository.save(notification2);

        List<Role> rolesToNotify2 = new ArrayList<>();
        notification2.setRolesToNotify(Arrays.asList(role1, role2));
        for (Role role : rolesToNotify2) {
            notification2.getRolesToNotify().add(role);
        }
        notificationRepository.save(notification2);

        RoleRequestPayload payload3= new RoleRequestPayload();
        ParentDataPayload parentDataPayload3 = new ParentDataPayload();
        parentDataPayload3.setFirstName("Amela");
        parentDataPayload3.setLastName("MK");
        parentDataPayload3.setCitizenID("2508");
        parentDataPayload3.setDob(new Date());
        parentDataPayload3.setCity("Tesanj");
        parentDataPayload3.setStreet("Osmana Pobrica");
        PhonePayload phonePayload3 = new PhonePayload();
        phonePayload3.setPhoneType("PERSONAL");
        phonePayload3.setPhoneNumber("062062062");
        phonePayload3.setPhoneOwner("Amela");
        phonePayload3.setParentDataPayload(parentDataPayload3);
        parentDataPayload3.setPhones(Arrays.asList(phonePayload3));
        parentDataPayload3.setRequestedRole("PARENT");
        parentDataPayload3.setEmail("amela@gmail.com");
        payload3.setParentData(parentDataPayload3);
        StudentDataPayload studentDataPayload3 = new StudentDataPayload();
        studentDataPayload3.setFirstName("Nani");
        studentDataPayload3.setLastName("Kulovac");
        studentDataPayload3.setCitizenID("2508");
        studentDataPayload3.setDob(new Date());
        payload3.setStudentData(Collections.singletonList(studentDataPayload3));

        Notification notification3 = new Notification();
        notification3.setMessage("Request for role setup");
        notification3.setType(NotificationType.ROLE_SETUP_REQUEST);
        notification3.setCreatedAt(new Date());
        notification3.addArg("payload_id", String.valueOf(payload2.getId()));
        notification3.addArg("parent_first_name", payload2.getParentData().getFirstName());
        notification3.addArg("parent_last_name", payload2.getParentData().getLastName());
        notification3.addArg("student_first_name", payload2.getStudentData().get(0).getFirstName());
        notification3.addArg("student_last_name", payload2.getStudentData().get(0).getLastName());
        notification3.setTaskCreatorName("Administrator");

        notificationRepository.save(notification3);

        List<Role> rolesToNotify3 = new ArrayList<>();
        notification3.setRolesToNotify(Arrays.asList(role1, role2));
        for (Role role : rolesToNotify3) {
            notification3.getRolesToNotify().add(role);
        }
        notificationRepository.save(notification3);

        Notification notification4 = new Notification();
        notification4.setMessage("Test for task");
        notification4.setType(NotificationType.ROLE_SETUP_REQUEST);
        notification4.setCreatedAt(new Date());
        notification4.addArg("arg_01", "arg_01");
        notification4.addArg("arg_02", "arg_02");
        notification4.setTaskCreatorName("System user");

        notification4 = notificationRepository.save(notification4);

        User t = userRepository.findById(2L).get();

        notification4.setUserToNotify(t);
        notificationRepository.save(notification4);
    }
}

