package org.dipalme.proteApp.ui.home

import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_home_web_view.*
import org.dipalme.proteApp.R

class HomeWebView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_web_view)

        val url = intent.getStringExtra("url")

        //WebView
        homeWebView.webChromeClient = object : WebChromeClient() {
        }
        homeWebView.webViewClient = object : WebViewClient() {
        }

        val settings = homeWebView.settings
        settings.javaScriptEnabled = true
        homeWebView.loadUrl(url)

        btnBack.setOnClickListener {
            finish()
        }
    }

    override fun onBackPressed() {
        if (homeWebView.canGoBack()) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
