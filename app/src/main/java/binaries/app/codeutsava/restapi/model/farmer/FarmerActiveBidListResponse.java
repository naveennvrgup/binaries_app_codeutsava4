package binaries.app.codeutsava.restapi.model.farmer;

import java.io.Serializable;

public class FarmerActiveBidListResponse implements Serializable {
    public String id;
    public boolean isActive;
    public String transno;
    public String quantity;
    public String nbids;
    public String description;
    public String deadline;
    public Buyer buyer;
    public Type type;

    public class Type implements  Serializable{
        public String type;
    }

    public class Buyer implements  Serializable{

        public String name;
        public String contact;
        public String city;
        public String state;

        @Override
        public String toString() {
            return "Buyer{" +
                    "name='" + name + '\'' +
                    ", contact='" + contact + '\'' +
                    ", city='" + city + '\'' +
                    ", state='" + state + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "FarmerActiveBidListResponse{" +
                "id='" + id + '\'' +
                ", isActive='" + isActive + '\'' +
                ", transno='" + transno + '\'' +
                ", quantity='" + quantity + '\'' +
                ", nbids='" + nbids + '\'' +
                ", description='" + description + '\'' +
                ", deadline='" + deadline + '\'' +
                ", buyer=" + buyer +
                '}';
    }


}
