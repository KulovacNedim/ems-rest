module security {
    requires transitive domain;
    requires persistence;

    requires spring.context;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.security.web;
    requires org.apache.tomcat.embed.core;
    //requires jackson.databind;
    //requires java.jwt;
    requires spring.webmvc;
    requires spring.web;
    requires spring.beans;
    requires java.validation;
    requires spring.core;
    requires jjwt;
    requires com.fasterxml.jackson.databind;
    requires java.jwt;


    //requires java.xml.bind;

    opens dev.ned.config to spring.core;

    exports dev.ned.config to spring.beans, spring.context, spring.web, com.fasterxml.jackson.databind;
    exports dev.ned.config.services to spring.beans;
    exports dev.ned.config.util to spring.beans, java.xml.bind;
    exports dev.ned.config.payload to spring.beans, com.fasterxml.jackson.databind;


    /////
    exports dev.ned.config.exceptions to spring.beans;
    exports dev.ned.config.controllers to spring.beans;
}