package binaries.app.codeutsava.restapi.model.farmer;

import java.io.Serializable;
import java.util.List;

public class FarmerFindWarehouseResponse implements Serializable {
    public boolean ispresent;
    public List<WarehouseResponse> data;

    public class WarehouseResponse {
        public int whid;
        public String whname;
        public double distance;
        public double price;
        public double locx;
        public double locy;
        public double availstorage;
        public int centre;
        public String owner;
        public String sector;
        public String description;

    }
}
