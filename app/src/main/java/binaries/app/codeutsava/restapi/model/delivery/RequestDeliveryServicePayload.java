package binaries.app.codeutsava.restapi.model.delivery;

import java.io.Serializable;

public class RequestDeliveryServicePayload implements Serializable {
    public String choice;
    public int destinationId;
    public double cost;
    public int serviceId;

    public RequestDeliveryServicePayload(String choice, int destinationId, double cost, int serviceId) {
        this.choice = choice;
        this.destinationId = destinationId;
        this.cost = cost;
        this.serviceId = serviceId;
    }
}
