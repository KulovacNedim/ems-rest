module domain {
    exports dev.ned.model;

    requires java.persistence;
    requires jackson.annotations;

    opens dev.ned.model to org.hibernate.orm.core, spring.core, jackson.databind;
}