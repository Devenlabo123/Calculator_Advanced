package com.example.assignment2;

/*
 * Use a WebView widget to display a web page.
 * Enter URL, click button or press Enter key.
 *
 * If you click a link in WebView page, the link is opened in the default
 * browser. Similarly, if WebView gets a redirect (HTTP/1.1 301) from the web server
 * the default browser is used to open the web page. To prevent this
 * behavior and open the new web page in WebView, you need to intercept
 * URL loading and open in WebView.
 *
 * This example uses the back key to navigate back to the previous web page.
 */

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

public class web extends Activity {
    private EditText urlText;
    private Button goButton;
    private WebView webView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // ...
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web);

        // Get a handle to all user interface elements
        urlText = (EditText) findViewById(R.id.url_field);
        goButton = (Button) findViewById(R.id.go_button);
        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);

        //intercept URL loading and load in widget
        webView.setWebViewClient(new WebViewClient(){

            public boolean shouldOverrideUrlLoading(WebView view, String url){
                view.loadUrl(url);
                return true;
            }

        });


        // Set button to open browser
        goButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                webView.loadUrl(urlText.getText().toString());
            }
        });
        urlText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    webView.loadUrl(urlText.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    //the back key navigates back to the previous web page
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

