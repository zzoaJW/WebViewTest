package com.z0o0a.webviewtest

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.z0o0a.webviewtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://www.naver.com/")

        binding.webViewLayout.setOnRefreshListener {
            binding.webView.reload()

            binding.webViewLayout.isRefreshing = false
        }

        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

        binding.btnLinkCopy.setOnClickListener {
            val clip = ClipData.newPlainText("copy_url", binding.webView.url)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, binding.webView.url, Toast.LENGTH_SHORT).show()
        }

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