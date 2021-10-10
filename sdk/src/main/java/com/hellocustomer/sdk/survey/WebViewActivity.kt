package com.hellocustomer.sdk.survey

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.hellocustomer.sdk.R
import com.hellocustomer.sdk.databinding.ActivityWebViewBinding


internal class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    private val urlArg: String
        get() = requireNotNull(intent.getStringExtra(EXTRA_URL))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWebViewBinding.inflate(layoutInflater)

        setupWebView(binding.webView, urlArg)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView(): Unit = with(binding) {
        toolbar.setNavigationIcon(R.drawable.ic_close)
        toolbar.setNavigationOnClickListener { finish() }
    }

    private fun setupWebView(webView: WebView, url: String) {
        webView.webViewClient = WebViewClient()

        val settings = webView.settings

        settings.javaScriptEnabled = true
        settings.allowContentAccess = true
        settings.domStorageEnabled = true

        webView.loadUrl(url)
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