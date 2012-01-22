# feather-rs modules #

These modules provide extra functionality for
feather-rs. Examples include:

	media - provides media exportation functionality.
	auth - provides authentication functionality.
	log - provides logging functionality for the core feather-rs system and module authors.  
	wizard - provides wizard API and functionality.

The list goes on (or will go on) .

This module directory is specifically for official feather-rs modules, but
modules do not specifically have to be placed in here to be used with feather-rs.

Modules are maven projects which export some API and optionally some @Named classes.

Modules are included and used within a web project differently, depending on the @Inject and JAX-RS implementations.

For example, with Jersey and Spring 3, to include the shiro11 security module
 (which contains @Named services):

    <context:component-scan base-package="feather.rs.auth.shiro11">

And to include the standard pages for /login, /logout, /accessDenied, which
includes @Named @Path resources:
    
    <context:component-scan base-package="feather.rs.auth.resources"/>

To include the default /media handler, you include:

    <context:component-scan base-package="feather.rs.media.resources"/>

The pattern/convention here is that anything within a "resources"
is a JAX-RS resource annotated with @Path. Anything outside of the
"resources" package is a simple @Named resource. However, if 
a package "feather.rs.auth.shiro11" contains an @Named, there would
never be a "feather.rs.auth.shiro11.resources" . That way
there is no surprising behavior when applying component scanning.

Most likely, any resources for 'shiro11' would go under 'feather.rs.auth.resources.shiro11' .

