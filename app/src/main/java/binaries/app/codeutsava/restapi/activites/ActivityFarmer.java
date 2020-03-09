package binaries.app.codeutsava.restapi.activites;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterFarmerRecommendation;
import binaries.app.codeutsava.restapi.fragments.FragmentFarmerBottomSheet;
import binaries.app.codeutsava.restapi.model.farmer.FarmerDashboardRecommedationResponse;
import binaries.app.codeutsava.restapi.restapi.APIServices;
import binaries.app.codeutsava.restapi.restapi.AppClient;
import binaries.app.codeutsava.restapi.utils.AppConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFarmer extends BaseActivity {

    ImageView menuButton;
    BottomSheetBehavior sheetBehavior;
    RelativeLayout bottomSheet;
    AnyChartView chartView;
    List<List<String>> graphData;
    RecyclerView recommendationsRecycler, defRecycler;
    AdapterFarmerRecommendation rAdapter, dAdapter;

    TextView csEmptyText, rsEmptyText;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_farmer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    private void initViews() {
        bottomSheet = findViewById(R.id.farmerBottomSheet);
        sheetBehavior = BottomSheetBehavior.from(bottomSheet);
        menuButton = findViewById(R.id.farmer_menu_icon);
        chartView = findViewById(R.id.graph);

        csEmptyText = findViewById(R.id.cs_empty_text);
        rsEmptyText = findViewById(R.id.rs_empty_text);

        recommendationsRecycler = findViewById(R.id.recycler_recommend_rec);
        recommendationsRecycler.setLayoutManager(new LinearLayoutManager(this));

        defRecycler = findViewById(R.id.recycler_recommend_def);
        defRecycler.setLayoutManager(new LinearLayoutManager(this));

        menuButton.setOnClickListener(v -> {
            BottomSheetDialogFragment bottomSheetDialogFragment = new FragmentFarmerBottomSheet();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), "farmerBottomSheet");
        });

        chartView.setProgressBar(findViewById(R.id.graph_progress));

        getGraph();
        getRecommendations();
    }

    private void getGraph() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<List<List<String>>> call = apiServices.getGraphDetails(
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN));

        call.enqueue(new Callback<List<List<String>>>() {
            @Override
            public void onResponse(Call<List<List<String>>> call, Response<List<List<String>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    graphData = response.body();

                    Cartesian cartesian = AnyChart.line();

                    cartesian.animation(true);

                    cartesian.crosshair().enabled(true);
                    cartesian.crosshair().yLabel(true).yStroke((Stroke) null, null, null, (String) null, (String) null);

                    cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                    cartesian.title("Past Year Profit: ");

                    cartesian.yAxis(0).title("Amount Earned (x1000)");
                    cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

                    List<DataEntry> seriesData = new ArrayList<>();

                    for (List<String> data : graphData) {
                        seriesData.add(new CustomDataEntry(data.get(0).toUpperCase(),
                                Double.valueOf(data.get(1)),
                                Double.valueOf(data.get(2)),
                                Double.valueOf(data.get(3))));
                    }

                    Set set = Set.instantiate();
                    set.data(seriesData);

                    Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
                    Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
                    Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

                    Line series1 = cartesian.line(series1Mapping);
                    series1.name("Rice");
                    series1.hovered().markers().enabled(true);
                    series1.hovered().markers().type(MarkerType.CIRCLE).size(4d);
                    series1.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5d).offsetY(5d);

                    Line series2 = cartesian.line(series2Mapping);
                    series2.name("Wheat");
                    series2.hovered().markers().enabled(true);
                    series2.hovered().markers().type(MarkerType.CIRCLE).size(4d);
                    series2.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5d).offsetY(5d);

                    Line series3 = cartesian.line(series3Mapping);
                    series3.name("Corn");
                    series3.hovered().markers().enabled(true);
                    series3.hovered().markers().type(MarkerType.CIRCLE).size(4d);
                    series3.tooltip().position("right").anchor(Anchor.LEFT_CENTER).offsetX(5d).offsetY(5d);

                    cartesian.legend().enabled(true);
                    cartesian.legend().fontSize(13d);
                    cartesian.legend().padding(0d, 0d, 10d, 0d);
                    cartesian.interactivity().hoverMode(HoverMode.BY_X);

                    chartView.setChart(cartesian);
                    chartView.setZoomEnabled(true);
                }
            }

            @Override
            public void onFailure(Call<List<List<String>>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "GetGraph Call Failed.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getRecommendations() {
        APIServices apiServices = AppClient.getInstance().createService(APIServices.class);
        Call<FarmerDashboardRecommedationResponse> call = apiServices.getFarmerRecommendation(
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("token", AppConstants.TEMP_FARM_TOKEN)
        );

        call.enqueue(new Callback<FarmerDashboardRecommedationResponse>() {
            @Override
            public void onResponse(Call<FarmerDashboardRecommedationResponse> call, Response<FarmerDashboardRecommedationResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    rAdapter = new AdapterFarmerRecommendation(response.body().rec_crops, ActivityFarmer.this);
                    dAdapter = new AdapterFarmerRecommendation(response.body().def_crops, ActivityFarmer.this);

                    recommendationsRecycler.setAdapter(rAdapter);
                    defRecycler.setAdapter(dAdapter);

                    rAdapter.notifyDataSetChanged();
                    dAdapter.notifyDataSetChanged();
                }

                if(!response.isSuccessful() || response.body() == null || response.body().rec_crops == null)
                    rsEmptyText.setVisibility(View.VISIBLE);

                if(!response.isSuccessful() || response.body() == null || response.body().def_crops == null)
                    csEmptyText.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<FarmerDashboardRecommedationResponse> call, Throwable t) {
                csEmptyText.setVisibility(View.VISIBLE);
                rsEmptyText.setVisibility(View.VISIBLE);
            }
        });
    }

    private static class CustomDataEntry extends ValueDataEntry {
        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);

            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
