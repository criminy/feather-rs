# feather-rs #

feather-rs is a Java MVC library for writing web applications. feather-rs uses JAX-RS and a custom
templating solution to create a library for writing web-based applications using standard
HTML and Java.  

## Running the examples ##

The examples can be run by running:

    mvn install

on the root directory, then:
    
    cd examples/templating
    mvn jetty:run

This example will be running on http://localhost:8080/feather.rs.examples.templating/ (TODO: make contextpath more simple)
