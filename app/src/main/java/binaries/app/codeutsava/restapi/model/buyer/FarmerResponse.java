package binaries.app.codeutsava.restapi.model.buyer;

public class FarmerResponse {
    public Farmer farmer;
    public int quantity;

    public class Farmer {
        public String name;
        public String contact;
        public String address;
        public String city;
        public String state;
        public String dob;
        public String adhaar;
        public String role;
    }
}
