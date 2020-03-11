package binaries.app.codeutsava.restapi.model.farmer;

import java.io.Serializable;

public class PotentialBuyerResponse implements Serializable {
    public String name;
    public String contact;

    public PotentialBuyerResponse(String name, String contact) {
        this.name = name;
        this.contact = contact;
    }
}
