module security {
    requires transitive domain;
    requires persistence;

    requires spring.context;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.security.web;
    requires org.apache.tomcat.embed.core;
    requires jackson.databind;
    requires java.jwt;
    requires spring.webmvc;
    requires spring.web;
    requires spring.beans;
    requires java.validation;
    requires spring.core;

    opens dev.ned.config to spring.core;

    exports dev.ned.config to spring.beans, spring.context, spring.web, jackson.databind;
}