package com.varvet.barcodereadersample


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import com.google.android.gms.vision.barcode.Barcode
import com.varvet.barcodereadersample.barcode.BarcodeCaptureActivity
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.util.*
import java.io.FileOutputStream
import java.io.InputStream
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.widget.*
import kotlinx.android.synthetic.main.activity_main.*

import android.app.Activity;
import android.graphics.PorterDuff
import android.net.Uri
import android.provider.SyncStateContract
import android.util.Log;
import android.view.KeyEvent
import android.webkit.WebSettings
import android.webkit.WebView
import com.github.barteksc.pdfviewer.PDFView
//import com.aditya.filebrowser.Constants
//import com.aditya.filebrowser.Constants

//import com.aditya.filebrowser.FileChooser
//import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.Constants
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main3.*

//import com.shockwave.pdfium.PdfDocument;
//import kotlinx.android.synthetic.main.activity_main3.*

import java.util.List;

class Main2Activity : AppCompatActivity() {

    // val file1 = "MB_" + "%1\$tY%1\$tm%1\$td%1\$tH%1\$tM.csv".format(Date())
    // val LerArq = File("$dirnovo1/$file1")
    // val arquivo = trataArquivo()            // Instanciando a classe local
    // val pdfArq = File("$diretorio/SA602.pdf")
    val pfArq = "${Environment.getExternalStorageDirectory()}" + "/STANAG1136.pdf"
    val pdfArq = File("$pfArq")
    var sucesso = true;
    var sucesso1 = true;
    val minhActivity = MainActivity()       // Instanciei a classe principal

    var  ArqRecebido:String = ""
    var  Id:String =""
    var  Codigo:String =""
    var  Composto:String=""

   // var nomeArquivo = minhActivity.file     // Peguei o nome do arquivo da class principal

    val diretorio = minhActivity.dirnovo    // Peguei o nome do diretorio da class principal
  //  var lerArq = File("$diretorio/$nomeArquivo") // Cria o local do arquivo e nome do arquivo


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //Entra a tela de login
        //val MyLogin = Intent(getApplicationContext(),LoginActivity::class.java)
        //startActivity(MyLogin)

        //Toast.makeText(this, "Vou Ler mail ="  mail, Toast.LENGTH_SHORT).show()
        //Toast.makeText(this, "Vou Ler extraPassW =" + extraPassW, Toast.LENGTH_SHORT).show()

        //trataArquivo().ArquivoName = ArqRecebido
        editText4.setSelection(0);  // Posicao do cursor no texto
        editText.background.setColorFilter(Color.rgb(106,201,215), PorterDuff.Mode.SRC_ATOP);
        editText5.background.setColorFilter(Color.rgb(106,201,215), PorterDuff.Mode.SRC_ATOP);
        /*****************************************/
        imageView4.visibility = View.INVISIBLE // era aparece o papagaio agora deixei invisivel para teste no futuro
        imageView5.visibility = View.INVISIBLE

        val intRecebedora = getIntent() //Recebendo da outra tela Intent
        val parametros = intRecebedora.getExtras()
        if(parametros != null){

            var arquivo:String = parametros.getString("chave_name1" )


            ArqRecebido = arquivo
            var lerArq = File("$diretorio/$ArqRecebido")

            //nomeArquivo = arquivo


        }
        var lerArq = File("$diretorio/$ArqRecebido")
        /******************************************************/

        //Toast.makeText(this, "Vou ver se arquivo existe", Toast.LENGTH_SHORT).show()

        Id = editText.getText().toString()

        editText.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                button5.visibility = View.VISIBLE
                //imageView4.visibility = View.VISIBLE
                imageView5.visibility = View.INVISIBLE
                editText.background.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                return@OnKeyListener true
            }
            false
        })

        Codigo = editText5.getText().toString()

        editText5.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {

                button5.visibility = View.VISIBLE
                //imageView5.visibility = View.VISIBLE
                imageView4.visibility = View.INVISIBLE
                editText5.background.setColorFilter(Color.GREEN, PorterDuff.Mode.SRC_ATOP);
                return@OnKeyListener true
            }
            false
        })


        if (lerArq.exists()) { // Consertar lerArq. Acho que não será mais necesaario aqui. Talvez seja o nomeArquivo a variavel certa

            Toast.makeText(this, "Vou Ler o arquivo que existe", Toast.LENGTH_SHORT).show()

            //val textView: TextView = findViewById(R.id.editText3) as TextView
            //val textView5: TextView = findViewById(R.id.editText3) as TextView

            editText4.setSelection(0);  // Posicao do cursor no texto
            val stream: InputStream = lerArq.inputStream()
            val str = stream.bufferedReader().use { it.readText() }

            editText4.append("")
            editText4.append(str)

        } else {

            Toast.makeText(this, "CriarArquivo arquivoNovo", Toast.LENGTH_LONG).show()
            CriarArquivo()

        }
           //var passW = pegaSenha()
    }
/*
    fun pegaSenha():String{

        val intLogin = getIntent() //Recebendo da outra tela Intent
        val PassW = intLogin.getExtras()
        var Senha:String =""

        if(PassW != null){

            var arquivoSenha:String = PassW.getString("Password" )

            Senha = arquivoSenha

            Toast.makeText(this, "Vou Ler Password =" +  arquivoSenha, Toast.LENGTH_SHORT).show()

        }

        return Senha
    }
*/
    fun onClickArquivo(view: View){


        //Toast.makeText(this, "pdfArq ->" + pdfArq.toString(), Toast.LENGTH_LONG).show()

        // Para chamar um app externo ao programa
        val i = Intent(Intent.ACTION_MAIN)
        startActivity(getPackageManager().getLaunchIntentForPackage("com.simplemobiletools.filemanager"));
        //startActivity(getPackageManager().getLaunchIntentForPackage("com.metago.astro"));

        return
    }


    fun onClickMB(view: View){


        //Abre a pagina da Marinah
        //val webpage = Uri.parse("http://www.marinha.mil.br")
        //val webpage = Uri.parse("content://com.simplemobiletools.filemanager.provider/external_files/storage/emulated/0/SMB/CODIGOS/index.html")
        //val webpage = Uri.parse("file://storage/emulated/0/SMB/CODIGOS/index.html")

        //val webpage = Uri.parse("http://192.168.1.136:45253/SMB/index.html") // quando usando serverpara android
        //val webIntent = Intent(Intent.ACTION_VIEW, webpage)
        //startActivity(webIntent)

        // ativando o WebView do android
        val MyWeb = Intent(this,Main4Activity::class.java)
        startActivity(MyWeb)


        // ativando o PdfView do android no layout 5
        //val Mypdf = Intent(this,Main5Activity::class.java)
        //startActivity(Mypdf)

    }

    fun onClickBigramas(view: View){

        val ited = Intent(this,Main3Activity::class.java)
        startActivity(ited)

    }

    fun CriarArquivo() {

        // Cria o diretorio
        //sucesso = File(dir).mkdirs()
        sucesso = File(diretorio).mkdirs()


        val criadoDir = File(diretorio).exists()

        if (!criadoDir)
        {
            sucesso = File(diretorio).mkdirs()
        }

        if (criadoDir) {
            //val sd = File("filename.txt")
            val sd = File(ArqRecebido)

            if (!sd.exists()) {

                //Toast.makeText(this, "sucesso -> "  + sucesso.toString(), Toast.LENGTH_SHORT).show()
                sucesso = sd.mkdir()

            }

            if (sucesso) {
                // directory exists or already created

                val dest = File(sd, ArqRecebido)
                //Toast.makeText(this, "val dest-> " + dest.toString(), Toast.LENGTH_SHORT).show()
                try {
                    PrintWriter(dest).use { out -> out.println("") }
                } catch (e: Exception) {
                    // handle the exception
                }

            } else {

                //Toast.makeText(this, "Diretorio ja existe e não foi criado", Toast.LENGTH_SHORT).show()
                // directory creation is not successful

            }

        }

    }


    fun onClickVoltar(view: View) {

        finish()

    }

    fun onClickDelete(view: View) {
        var lerArq = File("$diretorio/$ArqRecebido")
        lerArq.delete()
        finish()
    }


    fun onClickSave(view: View) {


        val intRecebedora = getIntent() //Recebendo da outra tela Intent
        val parametros = intRecebedora.getExtras()
        // apaga os papagaios
        imageView4.visibility = View.INVISIBLE
        imageView5.visibility = View.INVISIBLE
        // Compoe a string dos 2 campos de entrada e coloca , no meio
        Composto = editText.getText().toString() + "," + editText5.getText().toString() + "\n"

        if(editText.getText().toString() == "" && editText5.getText().toString() == "" )
        {
            Composto = ""
        }

        editText4.append(Composto)

        editText.setText("")
        editText5.setText("")

        editText.background.setColorFilter(Color.rgb(106,201,215), PorterDuff.Mode.SRC_ATOP);
        editText5.background.setColorFilter(Color.rgb(106,201,215), PorterDuff.Mode.SRC_ATOP);

        if(parametros != null){

            var arquivo:String = parametros.getString("chave_name1" )

            ArqRecebido = arquivo
            //nomeArquivo = arquivo
        }
        Toast.makeText(this, "Gravando." + ArqRecebido, Toast.LENGTH_SHORT).show()
        CriarArquivo()


        var lerArq = File("$diretorio/$ArqRecebido") //uchoa

        lerArq.printWriter().use { out ->
            out.println(editText4.text.toString())

           // button5.visibility = View.INVISIBLE
        }
    }

}

/**************************************************************************************
 *                              CLASSE trataArquivo
 * Autor: Uchoa
 * Data : 11/07/2018
 * OBJETIVO: Esta classe tem o o objetivo de manipular os arquivos criados
 *
 * Métodos:
 *   trataArquivo()
 *   leArquivo()
 *   criarArquivo()
 *   get()
 *   set()
 *
 *************************************************************************************/
/*
class trataArquivo(// instancia classe mae
        val main2Activity: Main2Activity = Main2Activity()) {

    var ArquivoName:String = "uchoa.txt"

    // getter
        get() = field

    // setter
        set(value)
        {
            field = value
        }

    class trataArquivo constructor( nome: String) {

        val customerKey = nome.toLowerCase()

    }// Construtor transforma o nome do arquivo em minusculo


    fun leArquivo()
    {


        if (main2Activity.lerArq.exists()) {

            val stream: InputStream = main2Activity.lerArq.inputStream()
            val str = stream.bufferedReader().use { it.readText() }
            //Toast.makeText(this, "STR->" + str, Toast.LENGTH_SHORT).show()
            main2Activity.editText4.append("")
            main2Activity.editText4.append(str)
        }else{

           Toast.makeText(main2Activity, "Arquivo nao existe ainda", Toast.LENGTH_LONG).show()
        }



    }

    fun criarArquivo(){


        // Cria o diretorio
        //sucesso = File(dir).mkdirs()
        main2Activity.sucesso = File(main2Activity.diretorio).mkdirs()


        val criadoDir = File(main2Activity.diretorio).exists()

        if (!criadoDir)
        {
            main2Activity.sucesso = File(main2Activity.diretorio).mkdirs()
        }

        if (criadoDir) {
            //val sd = File("filename.txt")
            val sd = File(main2Activity.nomeArquivo)

            if (!sd.exists()) {

                //Toast.makeText(this, "sucesso -> "  + sucesso.toString(), Toast.LENGTH_SHORT).show()
                main2Activity.sucesso = sd.mkdir()

            }

            if (main2Activity.sucesso) {
                // directory exists or already created

                val dest = File(sd, main2Activity.nomeArquivo)
                //Toast.makeText(this, "val dest-> " + dest.toString(), Toast.LENGTH_SHORT).show()
                try {
                    PrintWriter(dest).use { out -> out.println("") }
                } catch (e: Exception) {
                    // handle the exception
                }

            } else {

                //Toast.makeText(main2Activity, "Diretorio ja existe e não foi criado", Toast.LENGTH_SHORT).show()
                // directory creation is not successful

            }

        }



    }

}
*/