package com.varvet.barcodereadersample


import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main5.*
import kotlinx.android.synthetic.main.fragment_hat.*
import kotlinx.android.synthetic.main.fragment_sat.*
import java.io.File

class HatActivity : Fragment(){

    var AbriuHat = false

    companion object {

        fun newInstance(): HatActivity {
            return HatActivity()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {

            val selectedFile2 = data?.data //The uri with the location of the file

            //pdfView2.fromUri(selectedFile2).load() // Mostra o arquivo selecionado

            //pdfView2.fromAsset("room.pdf").load()

        }
    }

    /*
    fun leArquivos(){
        val path = "${Environment.getExternalStorageDirectory()}/SMB/public/hat/"
        //Create a Path object
        val sucesso = File(path)

               sucesso.walkTopDown().forEach{

            if (it.getName().endsWith("pdf"))
            {
                //editText7.append(it.name + "\n")
                textView4.append(it.name + "\n")
            }

        }

    }
    fun loadPaginaLocal(){

        WebView3.measure(300, 300)


        WebView3.loadUrl("http://127.0.0.1:8090/indexhat.php") //Localhost usando PHP server

    }
*/
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //loadPaginaLocal()
        //leArquivos()
        pdfView2.fromAsset("room.pdf").load()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        //val intent = Intent()
         //       .setType("*/*")
         //       .setAction(Intent.ACTION_GET_CONTENT)
         //  startActivityForResult(Intent.createChooser(intent, "Selecione um HAT"), 111)


        return inflater.inflate(R.layout.fragment_hat,container,false)
    }


}