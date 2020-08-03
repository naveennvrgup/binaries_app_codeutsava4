package binaries.app.codeutsava.restapi.utils;

public class AppConstants {
    //URL's

    public static final String BASE_URL = "http://c5d855b80c99.ngrok.io/";

    public static final String LOGIN_URL = "user/rest-auth/login/";
    public static final String FARMER_DETAIL_URL = "user/farmer-detail/";
    public static final String FARMER_BID_LIST_URL = "transaction/active_bid/";
    public static final String FARMER_PRODUCE_LIST_URL = "transaction/produce/";
    public static final String FARMER_FIND_WAREHOUSE_LIST_URL = "user/findWarehouse";
    public static final String FARMER_REPORT_PRODUCE_URL = "transaction/report_produce/";
    public static final String BUYER_FOODGRAIN_LIST_URL = "user/foodgrains/";
    public static final String CREATE_STORAGE_TRANSACTION_URL = "transaction/storagetransaction/";
    public static final String GRAPH_URL = "transaction/farmerDashboardGraph/";
    public static final String FARMER_RECOMMENDATION_URL = "user/getFarmerAI/";

    //TOKEN
    public static final String TEMP_FARM_TOKEN = "Token 63921eb897e84fda8925201d545ec2ce8128d264";

    // Filters
    public static final String FILTER_APPROVED = "Approved";
    public static final String FILTER_PENDING = "Pending";

    public static final String FILTER_ALL = "All";

    public static final String FILTER_ACTIVE = "Active";
    public static final String FILTER_INACTIVE = "Inactive";
}
