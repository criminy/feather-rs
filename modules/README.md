# feather-rs modules #

These modules provide extra functionality for
feather-rs. Examples include:

	media - provides media exportation functionality
	auth - provides authentication functionality

The list goes on (or will go on) .

This module directory is specifically for official feather-rs modules, but
modules do not specifically have to be placed in here to be used with feather-rs.

Modules are maven projects which export some API and (optionally) some @Provider 
class.

Modules are included and used within a web project differently, depending on the JAX-RS implementation.

For example, to include a module package directory that contains @Provider in Jersey, you
must update your init-param that specifies packages (TODO: list name of init-param) inside
of web.xml .

## logging ##

Modules which require logging MUST use the same slf4j-api that the core library
uses.

TODO: in the future, there might be a logging module which provides a feather-rs
specific logging API that works with different slf4j apis AND other logging
facilities like log4j/commons-logging/etc. This will allow fluid
logging control.

