package org.megatome.frame2.front;

import org.megatome.frame2.Frame2Exception;
import org.megatome.frame2.event.Context;
import org.megatome.frame2.event.Event;
import org.megatome.frame2.event.EventHandler;
import org.megatome.frame2.jaxbgen.PurchaseOrder;

/**
 * 
 */
public class PurchaseOrderHandler implements EventHandler {

   public static final String NEW_COMMENT = "I've been handled!";

	/**
	 * Constructor for PurchaseOrderHandler.
	 */
	public PurchaseOrderHandler() {
		super();
	}

	public String handle(Event event, Context context) throws Frame2Exception {
      PurchaseOrder po = (PurchaseOrder) event;

      po.setComment(NEW_COMMENT);

      context.setRequestAttribute("key1",po);
		return "orderResult";
	}

}
