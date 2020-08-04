module users {
    exports dev.ned.repositories;
    exports dev.ned.models;

    requires java.persistence;
    requires com.fasterxml.jackson.annotation;
    requires spring.data.jpa;
    requires spring.context;
    requires spring.boot;
    requires spring.security.core;
    requires spring.web;

//    supports hibernate
    requires com.fasterxml.classmate;
    requires net.bytebuddy;

    exports dev.ned.controllers to spring.beans, spring.web;
    opens dev.ned.models to org.hibernate.orm.core, spring.core;
}