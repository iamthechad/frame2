package org.megatome.frame2.front;

final class ViewProxy {
	private boolean redirect = false;
	private String view;
	
	public ViewProxy(ForwardProxy proxy, String view) {
		if (proxy != null) {
			this.redirect = proxy.isRedirect();
		}
		this.view = view;
	}
	
	public ViewProxy(String view) {
		this(null, view);
	}
	
	public String getView() {
		return this.view;
	}
	
	public boolean isRedirect() {
		return this.redirect;
	}
}
