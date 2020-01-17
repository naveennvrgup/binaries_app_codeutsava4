package binaries.app.codeutsava.restapi.model.farmer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReportProducePayload {

    @SerializedName("fid")
    @Expose
    private int fid;

    @SerializedName("grade")
    @Expose
    private String grade;

    @SerializedName("quantity")
    @Expose
    private Double quantity;

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @SerializedName("price")
    @Expose
    private Double price;



    public String getGrade() {
        return grade;
    }




    public void setGrade(String grade) {
        this.grade = grade;
    }


}

