package com.varvet.barcodereadersample

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebChromeClient;
import kotlinx.android.synthetic.main.activity_main4.*
import android.webkit.WebViewClient



class Main4Activity : AppCompatActivity() {

   // var mywebview: WebView = null
   //permissão somente para desemvolvimento. Em produção deve ser retirado
    //val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
    //val permissionsExtLeitura = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
    //val permissionsExtEscrita = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main4)

        //permissão somente para desemvolvimento. Em produção deve ser retirado
        //ActivityCompat.requestPermissions(this, permissions,0)
        //ActivityCompat.requestPermissions(this, permissionsExtLeitura,0)
        //ActivityCompat.requestPermissions(this, permissionsExtEscrita,0)

        val mywebview: (WebView) = findViewById(R.id.WebView1)



        mywebview.getSettings().setLoadWithOverviewMode(true);
        mywebview.getSettings().setBuiltInZoomControls(true);
        mywebview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mywebview.setScrollbarFadingEnabled(false);
        mywebview.requestFocusFromTouch();


        mywebview.getSettings().setDomStorageEnabled(true)

        //mywebview.loadDataWithBaseURL("", "<HTML><BODY><H3>Test</H3></BODY></HTML>","text/html","utf-8","")
        mywebview.getSettings().setJavaScriptEnabled(true)

        mywebview.measure(1000, 1000)
        mywebview.getSettings().setUseWideViewPort(true)
        mywebview.getSettings().setLoadWithOverviewMode(true) //Carrega com tamanho real da pagina como desktop quando true


        //Habilitando o JavaScript
        mywebview.getSettings().setJavaScriptEnabled(true)
        //mywebview.settings.javaScriptEnabled = true

        //Usado com o Servers Ultimate ou Apache para criar o iP do server.
        //mywebview.loadUrl("http://127.0.0.1:8080/index.html") //Localhost
        mywebview.loadUrl("http://127.0.0.1:8080/app")
        //mywebview.loadUrl("http://www.marinha.mil.br")


        }



}
