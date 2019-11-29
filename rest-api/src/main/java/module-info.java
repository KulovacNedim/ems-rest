module rest.api {
    requires transitive domain;

    requires spring.web;

    exports dev.ned.rest to spring.beans, spring.web;
}