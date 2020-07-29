module users {
    requires spring.web;
//    exports dev.ned.users to application;
//    requires transitive domain;
//    requires persistence;

    requires spring.context;
//    requires spring.security.config;
//    requires spring.security.core;
//    requires spring.security.web;
//    requires org.apache.tomcat.embed.core;
    requires jackson.databind;
//    requires java.jwt;
//    requires spring.webmvc;
//    requires spring.web;
    requires spring.beans;
//    requires java.validation;
    requires spring.core;

    opens dev.ned.users to spring.core;

    exports dev.ned.users to spring.beans, spring.context, spring.web, jackson.databind;
}