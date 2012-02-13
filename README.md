# feather-rs #

feather-rs is a Java MVC library for writing web applications. feather-rs uses JAX-RS and Cambridge
to create a library for writing web-based applications using standard HTML and Java.  

## cambridge ##

Cambridge is an external templating library for Java. feather-rs has adopted it as the underlying templating system
to use alongside JAX-RS. Check it out here ( https://github.com/erdincyilmazel/Cambridge ) or the feather-rs specific branch 
here ( https://github.com/criminy/Cambridge ).

## feather-rs templating ##

feather-rs core templating system is a JSoup-based templating system which uses
CSS Selectors and a Java API to modify the HTML Dom tree. This is /neat/, however,
we've decided to go with cambridge indefinitely ( forever if it fits well or until this templating
system is further developed ).

Note: Many of the existing examples and documentation still use this templating system.

## Running the examples ##

The examples can be run by running:

    mvn install

on the root directory, then:
    
    cd examples/templating
    mvn jetty:run

This example will be running on http://localhost:8080/feather.rs.examples.templating/ (TODO: make contextpath more simple)
