package com.hellocustomer.sdk.survey

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity


internal class WebViewActivity : AppCompatActivity() {

    private val urlArg: String
        get() = requireNotNull(intent.getStringExtra(EXTRA_URL))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        webView.webViewClient = WebViewClient()

        val settings = webView.settings

        settings.javaScriptEnabled = true
        settings.allowContentAccess = true
        settings.domStorageEnabled = true

        setContentView(webView)

        webView.loadUrl(urlArg)
    }

    companion object {

        private const val EXTRA_URL = "EXTRA_URL"

        fun start(context: Context, url: String) {
            val intent = Intent(context, WebViewActivity::class.java).apply {
                putExtra(EXTRA_URL, url)
            }
            context.startActivity(intent)
        }
    }
}