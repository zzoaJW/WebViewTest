package com.z0o0a.webviewtest

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACKSLASH
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.inputmethod.InputMethodManager
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

        binding.editTextUrl.setOnKeyListener { view, keyCode, keyEvent ->
            val inputStr = binding.editTextUrl.text.toString()

            // 엔터키 눌렀을 때
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                // 키보드 내리기
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.editTextUrl.windowToken, 0)

                // 입력 사이트 load
                binding.webView.loadUrl(inputStr)
            }

            // 백스페이스키 눌렀을 때
            if(keyCode == KeyEvent.KEYCODE_DEL && binding.editTextUrl.text.toString().isNotEmpty()){
                // 커서 위치
                val cursorPosiStart = binding.editTextUrl.selectionStart
                val cursorPosiEnd = binding.editTextUrl.selectionEnd
                var delInputStr = ""

                if(cursorPosiStart == cursorPosiEnd){// (1) 블록 설정 안됐을때
                    when {
                        cursorPosiEnd == inputStr.length -> {// 커서가 마지막에 위치
                            delInputStr = inputStr.slice(0 .. inputStr.length-2)
                        }
                        cursorPosiEnd == 1 -> {// 커서가 첫번째 글자 직후에 위치
                            delInputStr = inputStr.slice(1 .. inputStr.length-1)
                        }
                        cursorPosiEnd > 1 -> {// 커서가 중간에 위치
                            // 커서 위치 직전 1글자 제거
                            delInputStr = inputStr.slice(0 .. cursorPosiEnd-1) + inputStr.slice(cursorPosiEnd+1 .. inputStr.length-1)
                        }
                    }

                    if(cursorPosiEnd > 0){
                        Toast.makeText(this, "${cursorPosiEnd} / ${inputStr.length}", Toast.LENGTH_SHORT).show()
                        // 입력값 변경
                        binding.editTextUrl.setText(delInputStr)
                        // 커서 위치 설정
                        binding.editTextUrl.setSelection(cursorPosiEnd-1)
                    }
                }else{// (2) 블록 설정 됐을 때
                    delInputStr = inputStr.slice(0..cursorPosiStart-1) + inputStr.slice(cursorPosiEnd..inputStr.length-1)
                    if(cursorPosiStart > 0){
                        Toast.makeText(this, "${cursorPosiEnd} / ${inputStr.length}", Toast.LENGTH_SHORT).show()
                        // 입력값 변경
                        binding.editTextUrl.setText(delInputStr)
                        // 커서 위치 설정
                        binding.editTextUrl.setSelection(cursorPosiStart)
                    }
                }

            }

            true
        }
    }

    override fun onBackPressed() {
        // 키보드 내리기
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextUrl.windowToken, 0)
    }
}