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

    opens dev.ned.config to spring.core;

    exports dev.ned.config to spring.beans, spring.context;
}