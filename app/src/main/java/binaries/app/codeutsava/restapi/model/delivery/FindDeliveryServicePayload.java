package binaries.app.codeutsava.restapi.model.delivery;

import java.io.Serializable;

public class FindDeliveryServicePayload implements Serializable {
    public int destinationId;
    public String choice; //SD or TD
    public String farmerContact;

    public FindDeliveryServicePayload(int destinationId, String choice, String farmerContact) {
        this.destinationId = destinationId;
        this.choice = choice;
        this.farmerContact = farmerContact;
    }
}
