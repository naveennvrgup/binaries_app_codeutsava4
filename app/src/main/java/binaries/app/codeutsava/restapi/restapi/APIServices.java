package binaries.app.codeutsava.restapi.restapi;

import java.util.List;

import binaries.app.codeutsava.restapi.model.auth.LoginPayload;
import binaries.app.codeutsava.restapi.model.auth.LoginResponse;
import binaries.app.codeutsava.restapi.model.auth.SignupPayload;
import binaries.app.codeutsava.restapi.model.buyer.BidCreatePayload;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.model.buyer.FarmerResponse;
import binaries.app.codeutsava.restapi.model.buyer.PlaceBidResponse;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderPayload;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderResponse;
import binaries.app.codeutsava.restapi.model.farmer.ApproveOrderPayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerDashboardRecommedationResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerDetailResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerFindWarehouseResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerPlaceBidPayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerStorageTransactionResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionPayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerWarehouseTransactionResponse;
import binaries.app.codeutsava.restapi.model.farmer.PotentialBuyerResponse;
import binaries.app.codeutsava.restapi.model.farmer.ReportProducePayload;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIServices {

    @POST("user/rest-auth/login/")
    Call<LoginResponse> sendLoginRequest(@Body LoginPayload loginPayload);

    @POST("transaction/placeOrder/")
    Call<PlaceOrderResponse> placeOrderRequest(@Header("Authorization") String token, @Body PlaceOrderPayload payload);

    @GET("user/farmer-detail/")
    Call<FarmerDetailResponse> getUserDetail(@Header("Authorization") String token);

    @GET("transaction/active_bid/")
    Call<List<FarmerActiveBidListResponse>> getActiveBidList(@Header("Authorization") String token);

    @GET("transaction/produce/")
    Call<List<FarmerProduceResponse>> getFarmerProduceList(@Header("Authorization") String token);

    @GET("user/findWarehouse" + "/{quantity}" + "/{produceid}")
    Call<FarmerFindWarehouseResponse> getFarmerFindWarehouseList(@Header("Authorization") String token, @Path("quantity") String quantity, @Path("produceid") String produceid);

    @POST("transaction/report_produce/")
    Call<FarmerProduceResponse> postFarmerProduce(@Header("Authorization") String token, @Body ReportProducePayload reportProducePayload);

    @GET("user/foodgrains/")
    Call<List<BuyerFoodgrainResponse>> getBuyerFoodgrainList(@Header("Authorization") String token);

    @GET("user/foodgrains/" + "{id}/")
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

    @POST("transaction/storagetransaction/")
    Call<FarmerWarehouseTransactionResponse> postStorageTransaction(@Header("Authorization") String token, @Body FarmerWarehouseTransactionPayload farmerWarehouseTransactionPayload);

    @GET("transaction/getFarmerStoredWarehouse")
    Call<List<FarmerStorageTransactionResponse>> getFarmerStorageTransaction();

    @GET("user/get_potential_buyers/{foodgrain}/")
    Call<List<PotentialBuyerResponse>> getPotentialBuyerList(@Path("foodgrain") String foodgrain);

    @POST("user/")
    Call<SignupPayload> sendSignupRequest(@Header("Authorization") String token, @Body SignupPayload payload);

    @GET("transaction/farmerDashboardGraph/")
    Call<List<List<String>>> getGraphDetails(@Header("Authorization") String token);

    @GET("user/getFarmerAI/")
    Call<FarmerDashboardRecommedationResponse> getFarmerRecommendation(@Header("Authorization") String token);

    @POST("/transaction/createBid/")
    Call<FarmerActiveBidListResponse> createBid(@Header("Authorization") String token, @Body BidCreatePayload payload);

    @GET("transaction/farmerPlacedBids/{id}/")
    Call<List<PlaceBidResponse>> getFarmerResponseBideList(@Header("Authorization") String token, @Path("id") String id);

    @GET("transaction/pastBidsList/")
    Call<List<FarmerActiveBidListResponse>> getPastBidsList(@Header("Authorization") String token);

    @GET("transaction/farmerActiveBidList/")
    Call<List<FarmerActiveBidListResponse>> getFarmerActiveBidList(@Header("Authorization") String token);

    @POST("transaction/farmerPlaceBid/")
    Call<Boolean> farmerPlaceBid(@Header("Authorization") String token, @Body FarmerPlaceBidPayload payload);

    @GET("transaction/approveBid/{id}/")
    Call<String> approveBid(@Header("Authorization") String token, @Path("id") String id);
}
