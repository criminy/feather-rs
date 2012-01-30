The growl module provides jquery JGrowl support.

This works by exposing a URL called /growls/{sessionId} , which
is polled by the GrowlScript class and generated javascript.

To use growl to push messages, simply inject the growl service into your 
controller, then add the script reference in your view.

Controller Code:
	GrowlService growlService = -dependency-injected-;
	SecurityContext ctx = -provided-by-jax-rs-;

	growlService.push(ctx,"This is a simple message");

View Code:
	public void render(Html html) {
		html.addScript(GrowlScript.class);
	}

