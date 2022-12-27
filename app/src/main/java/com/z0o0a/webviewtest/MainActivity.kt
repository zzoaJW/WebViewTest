package com.z0o0a.webviewtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.z0o0a.webviewtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://www.naver.com/")

        binding.btnBack.setOnClickListener {
            binding.webView.goBack()
        }

        binding.btnFront.setOnClickListener {
            binding.webView.goForward()
        }

        binding.btnRefresh.setOnClickListener {
            binding.webView.reload()
        }
    }
}