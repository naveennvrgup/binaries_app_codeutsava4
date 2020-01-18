package binaries.app.codeutsava.restapi.model.farmer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FarmerWarehouseTransactionPayload {

    @SerializedName("produce")
    @Expose
    public int produceid;

    @SerializedName("warehouse")
    @Expose
    public int warehouseid;

    @SerializedName("quantity")
    @Expose
    public double quantiy;


    public int getProduceid() {
        return produceid;
    }

    public void setProduceid(int produceid) {
        this.produceid = produceid;
    }

    public int getWarehouseid() {
        return warehouseid;
    }

    public void setWarehouseid(int warehouseid) {
        this.warehouseid = warehouseid;
    }

    public double getQuantiy() {
        return quantiy;
    }

    public void setQuantiy(double quantiy) {
        this.quantiy = quantiy;
    }

}

