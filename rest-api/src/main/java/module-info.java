module rest.api {
    requires transitive domain;

    requires spring.web;
    requires persistence;

    exports dev.ned.rest to spring.beans, spring.web;
}