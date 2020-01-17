package binaries.app.codeutsava.restapi.restapi;

import java.util.List;

import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;
import binaries.app.codeutsava.restapi.model.auth.LoginPayload;
import binaries.app.codeutsava.restapi.model.auth.LoginResponse;
import binaries.app.codeutsava.restapi.model.buyer.FarmerResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerDetailResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIServices {
    @POST(AppConstants.LOGIN_URL)
    Call<LoginResponse> sendLoginRequest(@Body LoginPayload loginPayload);

    @POST(AppConstants.LOGIN_URL)
    Call<LoginResponse> sendLoginRequest(@Body LoginPayload loginPayload);

    @GET(AppConstants.FARMER_DETAIL_URL)
    Call<FarmerDetailResponse> getFarmerDetail();

    @GET(AppConstants.FARMER_BID_LIST_URL)
    Call<List<FarmerActiveBidListResponse>> getActiveBidList();

    @GET(AppConstants.FARMER_PRODUCE_LIST_URL)
    Call<List<FarmerProduceResponse>> getFarmerProduceList();

    @GET(AppConstants.BUYER_FOODGRAIN_LIST_URL)
    Call<List<BuyerFoodgrainResponse>> getBuyerFoodgrainList();

    @GET(AppConstants.BUYER_FOODGRAIN_LIST_URL+"{id}/")
    Call<List<FarmerResponse>> getBuyerFarmerList(@Path("id")int id);

}
