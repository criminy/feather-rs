# Cambridge feather-rs #

This is a simple copy of the cambridge-jaxrs module. The /only/ difference (as of 2012-02-13) is 
the addition of @javax.inject.Named on top of the MessageBodyWriter, so that our container (EJB, Spring)
can find it.
