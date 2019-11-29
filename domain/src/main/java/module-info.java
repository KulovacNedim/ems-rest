module domain {
    exports dev.ned.model;
//    exports dev.ned.intefaces;

    requires java.persistence;

    opens dev.ned.model to org.hibernate.orm.core, spring.core;
}