module rest.api {
    requires transitive domain;

    requires spring.web;
    requires persistence;
    requires java.sql;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;

    opens dev.ned.rest to jackson.databind;
    exports dev.ned.rest to spring.beans, spring.web;
}