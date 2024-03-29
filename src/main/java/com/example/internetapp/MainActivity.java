package com.example.internetapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.net.Network;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refreshLayout = findViewById(R.id.swiperefresh);
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.setWebViewClient(new WebViewClient());
        Button btnFetch = findViewById(R.id.downloadBtn);
        Button zoomIn = findViewById(R.id.button3);
        Button zoomOut = findViewById(R.id.button4);
        Button copyURL = findViewById(R.id.button5);
        Button cache = findViewById(R.id.button6);
        Button back = findViewById(R.id.button);
        Button forward = findViewById(R.id.button2);

        btnFetch.setOnClickListener(v -> {
            new PageLoader(findViewById(R.id.downloadBtn), findViewById(R.id.linearLayout), webView).start();
        });
        refreshLayout.setOnRefreshListener(
                () -> {
                    refreshLayout.setRefreshing(false);
                    if (btnFetch.getText().equals("Загрузка"))
                        Toast.makeText(MainActivity.this.getApplicationContext(),
                                "Обновлено", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(MainActivity.this.getApplicationContext(),
                                "Обновление...", Toast.LENGTH_SHORT).show();
                        webView.reload();
                    }
                }
        );
        zoomIn.setOnClickListener(v ->{
            webView.zoomIn();
        });
        zoomOut.setOnClickListener(v ->{
            webView.zoomOut();
        });
        copyURL.setOnClickListener(v ->{
            String url = webView.getUrl();
            ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("", url);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "URL " + url + " copied", Toast.LENGTH_SHORT).show();
        });
        cache.setOnClickListener(v ->{
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "Cache cleanded", Toast.LENGTH_SHORT).show();
            webView.clearCache(true);
        });
        back.setOnClickListener(v ->{
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "Going back", Toast.LENGTH_SHORT).show();
            webView.goBack();
        });
        forward.setOnClickListener(v ->{
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "Going forward", Toast.LENGTH_SHORT).show();
            webView.goForward();
        });
    }
}