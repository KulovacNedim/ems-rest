module application {
    requires spring.boot.autoconfigure;
    requires spring.webmvc;
    requires spring.context;
    requires spring.boot;

    requires java.sql;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;

    requires rest.api;

    opens dev.ned.application to spring.core, spring.beans, spring.context;

    exports dev.ned.application to spring.boot.devtools;
}