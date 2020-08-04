module application {
    requires spring.boot.autoconfigure;
    requires spring.webmvc;
    requires spring.context;
    requires spring.boot;

    requires security;
    requires spring.data.jpa;

    opens dev.ned.application to spring.core, spring.beans, spring.context;

    exports dev.ned.application to spring.boot.devtools;
}