package binaries.app.codeutsava.restapi.model.buyer;

import java.io.Serializable;

public class OrderStatusResponse implements Serializable {
    public int verified;
    public boolean in_transit;
    public boolean reached;
    public boolean is_delivery_applicable;
}

