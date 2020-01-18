package binaries.app.codeutsava.restapi.restapi;

import java.util.List;

import binaries.app.codeutsava.restapi.model.auth.SignupPayload;
import binaries.app.codeutsava.restapi.model.buyer.BidCreatePayload;
import binaries.app.codeutsava.restapi.model.buyer.BidResponse;
import binaries.app.codeutsava.restapi.model.buyer.BuyerFoodgrainResponse;
import binaries.app.codeutsava.restapi.model.auth.LoginPayload;
import binaries.app.codeutsava.restapi.model.auth.LoginResponse;
import binaries.app.codeutsava.restapi.model.buyer.BuyerOrderListResponse;
import binaries.app.codeutsava.restapi.model.buyer.FarmerResponse;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderPayload;
import binaries.app.codeutsava.restapi.model.buyer.PlaceOrderResponse;
import binaries.app.codeutsava.restapi.model.farmer.ApproveOrderPayload;
import binaries.app.codeutsava.restapi.model.farmer.FarmerActiveBidListResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerDetailResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerFindWarehouseResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerProduceResponse;
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
    Call<PlaceOrderResponse> placeOrderRequest(@Body PlaceOrderPayload payload);

    @GET(AppConstants.FARMER_DETAIL_URL)
    Call<FarmerDetailResponse> getFarmerDetail();

    @GET(AppConstants.FARMER_BID_LIST_URL)
    Call<List<FarmerActiveBidListResponse>> getActiveBidList();

    @GET(AppConstants.FARMER_PRODUCE_LIST_URL)
    Call<List<FarmerProduceResponse>> getFarmerProduceList();

    @GET(AppConstants.FARMER_FIND_WAREHOUSE_LIST_URL + "/{quantity}"+"/{produceid}")
    Call<FarmerFindWarehouseResponse> getFarmerFindWarehouseList(@Path("quantity")String quantity, @Path("produceid")String produceid);

    @POST(AppConstants.FARMER_REPORT_PRODUCE_URL)
    Call<FarmerProduceResponse> postFarmerProduce(@Body ReportProducePayload reportProducePayload);

    @GET(AppConstants.BUYER_FOODGRAIN_LIST_URL)
    Call<List<BuyerFoodgrainResponse>> getBuyerFoodgrainList();

    @GET(AppConstants.BUYER_FOODGRAIN_LIST_URL + "{id}/")
    Call<List<FarmerResponse>> getBuyerFarmerList(@Path("id") int id);

    @GET("transaction/buyerOrders/")
    Call<List<BuyerOrderListResponse>> getBuyerOrders();

    @GET("transaction/farmerOrders/")
    Call<List<BuyerOrderListResponse>> getFarmerOrders();

    @POST("transaction/approveOrder/{id}/")
    Call<Boolean> approveOrder(@Path("id") int id,@Body ApproveOrderPayload payload);

    @GET("transaction/rejectOrder/{id}/")
    Call<Boolean> rejectOrder(@Path("id") int id);

    @POST(AppConstants.CREATE_STORAGE_TRANSACTION_URL)
    Call<FarmerWarehouseTransactionResponse> postStorageTransaction(@Body FarmerWarehouseTransactionPayload farmerWarehouseTransactionPayload);

    @GET("user/")
    Call<SignupPayload> sendSignupRequest(@Body SignupPayload payload);

    @GET("transaction/createBid/")
    Call<BidResponse> createBid(BidCreatePayload payload);

    @GET("transaction/farmerResponseBideList/")
    Call<SignupPayload> getFarmerResponseBideList();

    @GET("transaction/pastBidsList/")
    Call<SignupPayload> getPastBidsList();

    @GET("transaction/farmerActiveBidList/")
    Call<SignupPayload> getFarmerActiveBidList();

    @GET("transaction/farmerPlaceBid/")
    Call<SignupPayload> farmerPlaceBid();

    @GET("transaction/approveBid/")
    Call<SignupPayload> approveBid();









}
