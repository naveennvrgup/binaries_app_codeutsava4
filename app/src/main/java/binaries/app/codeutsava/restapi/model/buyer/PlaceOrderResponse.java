package binaries.app.codeutsava.restapi.model.buyer;

import java.io.Serializable;

public class PlaceOrderResponse implements Serializable {
    public boolean approved;
    public String type;
    public int seller;
    public int buyer;
    public int produce;
    public int warehouse ;
    public int quantity ;
    public int price;
}
