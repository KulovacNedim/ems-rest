module security {
    requires users;

    requires spring.context;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.security.web;
    requires org.apache.tomcat.embed.core;
    requires spring.webmvc;
    requires spring.web;
    requires spring.beans;
    requires java.validation;
    requires spring.core;
    requires com.fasterxml.jackson.databind;
    requires java.jwt;

    opens dev.ned.config to spring.core;
    opens dev.ned.config.filters to spring.core;
    exports dev.ned.config to spring.beans, spring.context, spring.web, com.fasterxml.jackson.databind;
    exports dev.ned.config.services to spring.beans;
    exports dev.ned.config.util to spring.beans, java.xml.bind;
    exports dev.ned.config.payload to spring.beans, com.fasterxml.jackson.databind;
    exports dev.ned.config.exceptions to spring.beans;
    exports dev.ned.config.controllers to spring.beans, spring.web;
    exports dev.ned.config.filters to spring.beans;
}