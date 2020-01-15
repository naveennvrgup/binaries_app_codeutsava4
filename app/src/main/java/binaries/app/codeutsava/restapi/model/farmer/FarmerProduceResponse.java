package binaries.app.codeutsava.restapi.model.farmer;

import java.io.Serializable;

public class FarmerProduceResponse implements Serializable {
    public String id;

    @Override
    public String toString() {
        return "FarmerProduceResponse{" +
                "id='" + id + '\'' +
                ", grade='" + grade + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", date='" + date + '\'' +
                ", loc=" + loc +
                '}';
    }

    public String grade;
    public String quantity;
    public String price;
    public String date;
    public Location loc;

    public class Location {
        public String xloc;
        public String yloc;

        @Override
        public String toString() {
            return "Location{" +
                    "xloc='" + xloc + '\'' +
                    ", yloc='" + yloc + '\'' +
                    '}';
        }
    }
}
