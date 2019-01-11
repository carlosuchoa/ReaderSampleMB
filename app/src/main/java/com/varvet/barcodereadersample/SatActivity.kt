package com.varvet.barcodereadersample

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_sat.*
import android.os.Environment
import kotlinx.android.synthetic.main.fragment_hct.*
import java.io.File
import java.lang.System.load

class SatActivity : Fragment(){

    var AbriuSat = false
/*
    companion object {

        fun newInstance(): SatActivity {

            return SatActivity()
        }
    }

    override fun onAttach(context: Context?) {

        super.onAttach(context)
    }

*/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {

            val selectedFile = data?.data //The uri with the location of the file
            pdfView1.fromUri(selectedFile).load() // Mostra o arquivo selecionado
        }

    }


    /*

    fun ExternoArquivo(){

        // Para chamar um app externo ao programa
        //Funcionando
        val i = Intent(Intent.ACTION_MAIN)
        startActivity(context.packageManager.getLaunchIntentForPackage("com.simplemobiletools.filemanager"));
        //startActivity(getPackageManager().getLaunchIntentForPackage("com.metago.astro"));
        return
    }

    fun leArquivos(){
        val path = "${Environment.getExternalStorageDirectory()}/SMB/public/sat/"
        //Create a Path object
        val sucesso = File(path)

       // editText6.append(path)
       // editText6.append("teste -> " + path)
        //Le os arquivos do diretorio no path sat
        sucesso.walkTopDown().forEach{

            if (it.getName().endsWith("pdf")) {
                //editText6.append(it.name + "\n")
                textView3.append(it.name + "\n")

                pdfView1.fromFile(File(it.name)).load()
            }

        }

    }



    fun loadPaginaLocal(){

        //val file = File(path)
        //val arquivos = file.listFiles()
        val dirnovo = "${Environment.getExternalStorageDirectory()}/SMB/public/sat/"
        val sucesso = File(dirnovo).listFiles()

        //editText6.append(File(dirnovo).toString())
       // File(dirnovo).walkTopDown().forEach {

        //    editText6.append("*.*")
        //}

        //Toast.makeText(context, "existe ->" + File(dirnovo).walkTopDown().forEach {(println(it))}, Toast.LENGTH_SHORT).show()
        //editText6.append((File(dirnovo).listFiles()).toString())
        //editText6.append(File(dirnovo).walkTopDown().forEach {(println(it))}.toString())
        //File(dirnovo).walkTopDown().forEach { editText6.append(println(SatActivity).toString())}


        //WebView2.measure(300, 300)
        //WebView2.loadUrl("http://127.0.0.1:8090/indexsat.php") //Localhost usando PHP server

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        super.onActivityCreated(savedInstanceState)

        //ExternoArquivo()
        //loadPaginaLocal()
        //leArquivos()
    }
*/
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

       val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

        startActivityForResult(Intent.createChooser(intent, "Selecione um SAT"), 111)


        return inflater.inflate(R.layout.fragment_sat,container,false)

    }


}