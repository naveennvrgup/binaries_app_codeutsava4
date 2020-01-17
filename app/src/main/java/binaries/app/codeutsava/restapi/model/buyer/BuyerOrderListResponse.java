package binaries.app.codeutsava.restapi.model.buyer;

import java.io.Serializable;

public class BuyerOrderListResponse implements Serializable {
    public  String seller;
    public  int quantity;
    public  int price;
    public  String foodgraintype;
    public  String transno;
    public  boolean approved;
}
