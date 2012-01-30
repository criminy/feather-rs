# feather-rs modules #

## current modules ##

These modules provide extra functionality for
feather-rs. Examples include:

	media 		- provides exposing of static files within the classpath through JAX-RS.
	auth 		- provides authentication functionality.
	log 		- provides logging functionality for the core feather-rs system and module authors.  
	wizard 		- provides wizard API and functionality.
	attr-expr 	- allows template authors to use expressions within their HTML attributes and values.
	event		- sync and async eventing api
		camel	- camel implementation of eventing api
 	growl		- jquery/growl-style notification support.
		gritter
		jgrowl
	data		- data services, pagination, etc.
	css		- compiled CSS Support

## modules information ##

### What are modules ###

Modules are simple maven projects which export some API, (optionally) some @Named classes,
and (optionally) some JAX-RS resources.

### Where are modules placed ###

Modules can be placed anywhere, as long as they are on the classpath at runtime and configured
correctly.

### How do I use a module within an application ###

Modules are included and used within a web project differently, depending on the @Inject and JAX-RS implementations.

For example, with Jersey and Spring 3, to enable the 'auth-shiro11' module (which contains @Named services):

    <context:component-scan base-package="feather.rs.auth.shiro11">

And to include the standard pages for /login, /logout, /accessDenied from the 'auth-api' module,
which includes JAX-RS resources:
    
    <context:component-scan base-package="feather.rs.auth.resources"/>

To include the default /media handler for the 'media' plugin, you include:

    <context:component-scan base-package="feather.rs.media.resources"/>

### How are module packages structured? ###

Since module package structure is important to classpath scanning, here is a rundown of how packages
are structured:

For a project with JUST JAX-RS resources:

	feather.rs.(moduleName) - abstract classes, interfaces, non-managed beans
	feather.rs.(moduleName).resources - JAX-RS resources
	feather.rs.(moduleName).resources.(submoduleName) - JAX-RS resources

For a project with JUST @Named resources:

	feather.rs.(moduleName) - Non-JAX-RS Managed Beans

For a project with both @Named and JAX-RS resources:

	feather.rs.(moduleName) - abstract classes, interfaces,non-managed beans
	feather.rs.(moduleName).resources - JAX-RS resources
	feather.rs.(moduleName).resources.(submoduleName) - JAX-RS resources
	feather.rs.(moduleName).(submoduleName) - Non-JAX-RS managed beans.

The pattern/convention here is that anything within a "resources"
is a JAX-RS resource annotated with @Path. Anything outside of the
"resources" package is a simple @Named resource. However, if 
a package "feather.rs.auth.shiro11" contains an @Named, there would
never be a "feather.rs.auth.shiro11.resources" . That way
there is no surprising behavior when applying component scanning.

