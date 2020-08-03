package binaries.app.codeutsava.restapi.activites;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import binaries.app.codeutsava.R;
import binaries.app.codeutsava.restapi.adapters.AdapterTutorial;

public class ActivityTutorial extends AppCompatActivity {

    private WebView webView;
    private RecyclerView recyclerView;

    @Override
    public AssetManager getAssets() {
        return getResources().getAssets();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        recyclerView = findViewById(R.id.tut_recycler);
        AdapterTutorial tut = new AdapterTutorial(ActivityTutorial.this);

        tut.setListener((url) -> {
            getTutorial(url);

            recyclerView.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(tut);
    }

    @Override
    public void onBackPressed() {
        onBackPress();
    }

    private void getTutorial(String url) {
        webView = findViewById(R.id.tutorial_web_view);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        webView.loadUrl(url);

        ImageView imageView = findViewById(R.id.tut_back);
        imageView.setOnClickListener(view -> onBackPress());
    }

    private void onBackPress() {
        if(webView!=null && webView.getVisibility() == View.VISIBLE) {
            webView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        } else {
            Intent intent = new Intent(ActivityTutorial.this, ActivityFarmer.class);
            ActivityTutorial.this.startActivity(intent);
            ActivityTutorial.this.finish();

            super.onBackPressed();
        }
    }
}
