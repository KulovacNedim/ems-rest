module persistence {
    exports dev.ned.repositories;
    requires transitive domain;

    requires spring.data.jpa;
    requires spring.context;
    requires java.persistence;
//    requires spring.security.core;
    requires spring.boot;
    requires spring.security.core;

    opens dev.ned.repositories to spring.core;

    exports dev.ned.repositories.seeder to spring.beans;
}