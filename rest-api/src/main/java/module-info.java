module rest.api {
    requires spring.web;

    exports dev.ned.rest to spring.beans, spring.web;
}