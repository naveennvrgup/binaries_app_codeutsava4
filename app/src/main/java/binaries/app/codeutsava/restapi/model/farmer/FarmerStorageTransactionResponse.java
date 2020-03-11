package binaries.app.codeutsava.restapi.model.farmer;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class FarmerStorageTransactionResponse implements Serializable {
//    public List<StorageTransactionModel> storageTransactionModelList;

    @Expose
    public int warehouse;


    public double quantity;
    public double cost;
    public String date;
    public String foodgrain;
    public String whName;
    public int fgDeadline;


//    public class StorageTransactionModel {
//        public int warehouse;
//        public double quantity;
//        public double cost;
//        public String date;
//        public String foodgrain;
//        public String whName;
//    }


}

