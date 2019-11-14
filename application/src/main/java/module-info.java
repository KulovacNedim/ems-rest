module application {
    requires spring.boot.autoconfigure;
    requires spring.webmvc;
    requires spring.context;
    requires spring.boot;

    requires java.sql;
    requires net.bytebuddy;
    requires com.fasterxml.classmate;

    opens dev.ned.ems.application to spring.core, spring.beans, spring.context;

    exports dev.ned.ems.application to spring.boot.devtools;
}