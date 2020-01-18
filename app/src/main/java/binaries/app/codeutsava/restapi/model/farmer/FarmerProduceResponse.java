package binaries.app.codeutsava.restapi.model.farmer;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class FarmerProduceResponse implements Serializable {
    public String id;
    public String grade;
    public String quantity;
    public String price;
    public String date;
    public Location location;
    public FoodGrain type;

    @Override
    public String toString() {
        return "FarmerProduceResponse{" +
                "id='" + id + '\'' +
                ", grade='" + grade + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", date='" + date + '\'' +
                ", loc=" + location +
                ", foodgrain=" + type + '\'' +
                '}';
    }

    public class Location {
        public int id;
        public String xloc;
        public String yloc;
        public int centre;

        @Override
        public String toString() {
            return "Location{" +
                    "id='" + id + '\'' +
                    "xloc='" + xloc + '\'' +
                    ", yloc='" + yloc + '\'' +
                    ",centre='" + centre + '\'' +
                    '}';
        }
    }

    public class FoodGrain {
        public String type;
        public int life;

        public FoodGrain(String name, int life) {
            this.type = name;
            this.life = life;
        }

        @NonNull
        @Override
        public String toString() {
            return "FoodGrain{" +
                    "name='" + type + '\'' +
                    ", life='" + life + '\'' +
                    '}';
        }
    }
}
