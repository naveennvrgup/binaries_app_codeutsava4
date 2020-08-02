package binaries.app.codeutsava.restapi.model.delivery;

import java.util.List;

public class FindDeliveryServiceResponse {
    public List<DeliveryServiceResponse> deliveryServiceList;
    public String choice; //SD or TD
    public int destinationId;
}
