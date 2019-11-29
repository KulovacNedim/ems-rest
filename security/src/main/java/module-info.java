module security {
    requires transitive domain;

    requires spring.context;
    requires spring.security.config;
    requires spring.security.core;

    opens dev.ned.config to spring.core;

    exports dev.ned.config to spring.beans, spring.context;
}