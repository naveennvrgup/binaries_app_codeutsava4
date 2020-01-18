package binaries.app.codeutsava.restapi.restapi;

import java.util.List;

import binaries.app.codeutsava.restapi.model.auth.LoginPayload;
import binaries.app.codeutsava.restapi.model.auth.LoginResponse;
import binaries.app.codeutsava.restapi.model.auth.SignupPayload;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.model.buyer.FarmerResponse;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderPayload;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderResponse;
import binaries.app.codeutsava.restapi.model.farmer.ApproveOrderPayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerDashboardRecommedationResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerDetailResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerFindWarehouseResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerStorageTransactionResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionPayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionResponse;
import binaries.app.codeutsava.restapi.model.farmer.ReportProducePayload;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIServices {

    @POST(AppConstants.LOGIN_URL)
    Call<LoginResponse> sendLoginRequest(@Body LoginPayload loginPayload);

    @POST("transaction/placeOrder/")
    Call<PlaceOrderResponse> placeOrderRequest(@Header("Authorization") String token, @Body PlaceOrderPayload payload);

    @GET(AppConstants.FARMER_DETAIL_URL)
    Call<FarmerDetailResponse> getUserDetail(@Header("Authorization") String token);

    @GET(AppConstants.FARMER_BID_LIST_URL)
    Call<List<FarmerActiveBidListResponse>> getActiveBidList(@Header("Authorization") String token);

    @GET(AppConstants.FARMER_PRODUCE_LIST_URL)
    Call<List<FarmerProduceResponse>> getFarmerProduceList(@Header("Authorization") String token);

    @GET(AppConstants.FARMER_FIND_WAREHOUSE_LIST_URL + "/{quantity}" + "/{produceid}")
    Call<FarmerFindWarehouseResponse> getFarmerFindWarehouseList(@Header("Authorization") String token, @Path("quantity") String quantity, @Path("produceid") String produceid);

    @POST(AppConstants.FARMER_REPORT_PRODUCE_URL)
    Call<FarmerProduceResponse> postFarmerProduce(@Header("Authorization") String token, @Body ReportProducePayload reportProducePayload);

    @GET(AppConstants.BUYER_FOODGRAIN_LIST_URL)
    Call<List<BuyerFoodgrainResponse>> getBuyerFoodgrainList(@Header("Authorization") String token);

    @GET(AppConstants.BUYER_FOODGRAIN_LIST_URL + "{id}/")
    Call<List<FarmerResponse>> getBuyerFarmerList(@Header("Authorization") String token, @Path("id") int id);

    @GET("transaction/buyerOrders/")
    Call<List<BuyerOrderListResponse>> getBuyerOrders(@Header("Authorization") String token);

    @GET("transaction/farmerOrders/")
    Call<List<BuyerOrderListResponse>> getFarmerOrders(@Header("Authorization") String token);

    @POST("transaction/approveOrder/{id}/")
    Call<Boolean> approveOrder(@Header("Authorization") String token, @Path("id") int id, @Body ApproveOrderPayload payload);

    @GET("transaction/rejectOrder/{id}/")
    Call<Boolean> rejectOrder(@Header("Authorization") String token, @Path("id") int id);

    @GET("transaction/getFarmerStoredWarehouse")
    Call<List<FarmerStorageTransactionResponse>> getFarmerStorageTransaction(@Header("Authorization") String token);

    @POST(AppConstants.CREATE_STORAGE_TRANSACTION_URL)
    Call<FarmerWarehouseTransactionResponse> postStorageTransaction(@Header("Authorization") String token, @Body FarmerWarehouseTransactionPayload farmerWarehouseTransactionPayload);

    @POST("user/")
    Call<SignupPayload> sendSignupRequest(@Header("Authorization") String token, @Body SignupPayload payload);

    @GET(AppConstants.GRAPH_URL)
    Call<List<List<String>>> getGraphDetails(@Header("Authorization") String token);

    @GET(AppConstants.FARMER_RECOMMENDATION_URL)
    Call<FarmerDashboardRecommedationResponse> getFarmerRecommendation(@Header("Authorization") String token);
}
