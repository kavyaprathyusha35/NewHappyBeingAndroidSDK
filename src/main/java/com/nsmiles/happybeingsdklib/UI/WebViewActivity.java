package com.nsmiles.happybeingsdklib.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.nsmiles.happybeingsdklib.R;

import androidx.annotation.Nullable;

/**
 * Created by Kavya on 6/8/2020.
 */
public class WebViewActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_layout);
        WebView webView = findViewById(R.id.webView1);
        TextView toolbar_tv = findViewById(R.id.toolbar_tv);
        Intent intent = getIntent();
        String url = "";
        if (intent.hasExtra("PAGE_URL")) {
            url = intent.getStringExtra("PAGE_URL");
        }
        if (url.equals("https://nsmiles.com/terms")) {
            toolbar_tv.setText("Terms of use");
        }
        else if(url.equals("https://nsmiles.com/privacy")) {
            toolbar_tv.setText("Privacy policy");
        }
        webView.loadUrl(url);

    }
}
