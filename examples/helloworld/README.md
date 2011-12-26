# Hello World #

This example implements a standard helloworld application in feather-rs

This application demonstrates:

    * Bootstraping feather-rs in Jersey.
    * Implementing feather-rs views.
    * Using feather-rs views alongside JAX-RS resources.
    * Implementing a 404 page in JAX-RS and feather-rs.
    * Mapping exceptions in JAX-RS.

The root URL of the application redirects to /lang/en . After that, you
can change the language by clicking the displayed links.

To show simple custom 404 error pages, you can click on 'xx' to show
an invalid language. You should get a simple 'page not found' page.

## Things to verify for a healthy setup ##

You should verify a few things:

    * The application loads and runs.
    * The redirect from / to /lang/en works.
    * Chinese language symbols show up correctly on /lang/cn .
