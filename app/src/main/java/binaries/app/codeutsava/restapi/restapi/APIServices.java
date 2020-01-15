package binaries.app.codeutsava.restapi.restapi;

import binaries.app.codeutsava.restapi.model.auth.LoginPayload;
import binaries.app.codeutsava.restapi.model.auth.LoginResponse;
import binaries.app.codeutsava.restapi.model.farmer.FarmerDetailResponse;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface APIServices {
    @POST(AppConstants.LOGIN_URL)
    Call<LoginResponse> sendLoginRequest(@Body LoginPayload loginPayload);

    @GET(AppConstants.FARMER_DETAIL_URL)
    Call<FarmerDetailResponse> getFarmerDetail();
}
