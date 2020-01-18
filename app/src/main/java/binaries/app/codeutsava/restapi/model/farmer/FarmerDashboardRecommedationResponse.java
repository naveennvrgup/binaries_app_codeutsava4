package binaries.app.codeutsava.restapi.model.farmer;

import java.io.Serializable;
import java.util.List;

public class FarmerDashboardRecommedationResponse implements Serializable {
    public List<String> rec_crops;
    public List<String> def_crops;
}
