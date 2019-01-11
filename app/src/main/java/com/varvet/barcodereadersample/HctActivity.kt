package com.varvet.barcodereadersample


import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_hat.*
import kotlinx.android.synthetic.main.fragment_hct.*
import kotlinx.android.synthetic.main.fragment_sat.*
import java.io.File

class HctActivity : Fragment(){

    var AbriuHct = false

    companion object {

        fun newInstance(): HctActivity {
            return HctActivity()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       // super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {

            val selectedFile3 = data?.data //The uri with the location of the file
            pdfView3.fromUri(selectedFile3).load() // Mostra o arquivo selecionado

        }
    }

/*
    fun leArquivos(){
        val path = "${Environment.getExternalStorageDirectory()}/SMB/public/hct/"
        //Create a Path object
        val sucesso = File(path)

        sucesso.walkTopDown().forEach{

            if (it.getName().endsWith("pdf"))
            {
                //editText8.append(it.name + "\n")
                textView5.append(it.name + "\n")
            }

        }

    }
    fun loadPaginaLocal(){

        //WebView4.measure(300, 300)


        //WebView4.loadUrl("http://127.0.0.1:8090/indexhct.php") //Localhost usando PHP server

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //loadPaginaLocal()
        leArquivos()
    }
*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //val intent = Intent()
           // .setType("*/*")
           // .setAction(Intent.ACTION_GET_CONTENT)
       // startActivityForResult(Intent.createChooser(intent, "Selecione um HCT"), 111)


        return inflater.inflate(R.layout.fragment_hct,container,false)
    }


}