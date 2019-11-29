module persistence {
    exports dev.ned.repositories;
    requires transitive domain;

    requires spring.data.jpa;
    requires spring.context;
    requires java.persistence;

    opens dev.ned.repositories to spring.core;
}