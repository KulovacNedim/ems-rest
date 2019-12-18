module application {
    requires spring.boot.autoconfigure;
    requires spring.webmvc;
    requires spring.context;
    requires spring.boot;

    requires rest.api;
    requires security;
    requires persistence;
    requires java.persistence;
    requires spring.data.jpa;
    requires spring.beans;

    opens dev.ned.application to spring.core, spring.beans, spring.context;

    exports dev.ned.application to spring.boot.devtools;
}