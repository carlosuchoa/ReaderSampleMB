/**********************************************************
 *
 * NOME FANTASIA: MBQRCode
 * AUTOR: Carlos Uchoa
 * DATE : 04/06/2018
 * VERSION: 1.3.5 08/01/2019
 * RETURN: NULL
 *
 * Este App utiliza a biblioteca barcodeReadersample para criar um gerenciador de documentos e arquivos de codigos
 * adquiridos em construção de plataformas marítimas.
 *
 ***********************************************************/
package com.varvet.barcodereadersample

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.common.api.CommonStatusCodes
import com.google.android.gms.vision.barcode.Barcode
import com.varvet.barcodereadersample.barcode.BarcodeCaptureActivity
import java.io.PrintWriter
import java.lang.System.out
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.os.storage.StorageVolume
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import android.content.BroadcastReceiver
import android.media.MediaPlayer
import android.widget.EditText
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.content.IntentFilter;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.io .InputStreamReader
import java.io.BufferedReader
import java.util.*
import android.view.Menu
import android.view.MenuItem
import android.annotation.TargetApi
import android.net.wifi.WifiManager
import android.view.inputmethod.InputMethodManager
import com.varvet.barcodereadersample.R.id.editText2
import com.varvet.barcodereadersample.R.layout.activity_splash_screen
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.util.*


class MainActivity : AppCompatActivity() {


    public var saida1 = ""
    private var saida = ""
    private var i = 0
    private val requestPermissionWriteExternalStorage:Int = 0
    private lateinit var mResultTextView: TextView
    private lateinit var TextView2: TextView
    private lateinit var Botao4: Button
    private lateinit var mp: MediaPlayer  //audio

    var sucesso = true;

    companion object {
        const val REQUEST_PERMISSION = 1
        private val LOG_TAG = MainActivity::class.java.simpleName
        private val BARCODE_READER_REQUEST_CODE = 1

    }

    val dirnovo = "${Environment.getExternalStorageDirectory()}/MBQRCode"
    //val file = "%1\$tY%1\$tm%1\$td%1\$tH%1\$tM%1\$tS.csv".format(Date()) // Colocando a data no nome do arquivo
    //val file = "MB_"+"%1\$tY%1\$tm%1\$td%1\$tH%1\$tM.csv".format(Date())

    var file = "MBDadosQR.csv" // nome do arquivo
    var name1 = ""
    var nometotal:String = ""

        //permissão somente para desemvolvimento. Em produção deve ser retirado
        val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionsExtLeitura = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        val permissionsExtEscrita = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

    //@TargetApi   (Build.VERSION_CODES.LOLLIPOP) // Uchoa Se for necessário colocar esta versão de Android

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        /* sera usada pra criar uma tela inicial no futuro tipo Splash Screeen
        setContentView(R.layout.activity_splash_screen)
        */


        //Entra a tela de login antes de tudo
        val MyLogin = Intent(getApplicationContext(),LoginActivity::class.java)
        startActivity(MyLogin)

        setContentView(R.layout.activity_main)
        //permissão somente para desemvolvimento. Em produção deve ser retirado
        ActivityCompat.requestPermissions(this, permissions,0)
        ActivityCompat.requestPermissions(this, permissionsExtLeitura,0)
        ActivityCompat.requestPermissions(this, permissionsExtEscrita,0)

        mp = MediaPlayer.create (this, R.raw.sonarping)  //Carrega o som  do diretorio raw

        editText3.setVisibility(View.INVISIBLE)
        frameLayout.setVisibility(View.INVISIBLE)

        mResultTextView = findViewById(R.id.result_textview)
        TextView2 = findViewById(R.id.editText2)
        Botao4 = findViewById(R.id.button4)

        //scan_barcode_button.setVisibility(View.INVISIBLE) //Desabilita o Butao de Scan pra obrigar o usuario criar o arquivo primeiro

        scan_barcode_button.setVisibility(View.VISIBLE)

        findViewById<Button>(R.id.scan_barcode_button).setOnClickListener {
            val intent = Intent(applicationContext, BarcodeCaptureActivity::class.java)
            startActivityForResult(intent, BARCODE_READER_REQUEST_CODE)
            mp.start () //som de ping

        }



    }
/* UCHOA */
/* função paara criar no futuro uma tela inicial para o aplicativo. O intent tem que ter uma nova class a ser
   ainda criada que sera chamada quando iniciar o programa. AINDA TEMOS QUE COLOCAR UM DELAY
   como por exemplo:
   ++++++++++++++++++++++++++++
   Handler handle = new Handler();
     handle.postDelayed(new Runnable() {
     @Override
     public void run() {
          mostrarLogin();
     }
    }, 2000);
+++++++++++++++++++++++++++++++++
    private fun mostrarLogin() {
            val intent = Intent(this, xxxx.javaClass)
            startActivity(intent)
            finish()
    }
*/


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    val barcode = data.getParcelableExtra<Barcode>(BarcodeCaptureActivity.BarcodeObject)
                    val p = barcode.cornerPoints
                    mResultTextView.text = barcode.displayValue
                    val file = barcode.displayValue
                    saida = mResultTextView.text.toString() // variavel global
                    saida1= mResultTextView.text.toString()
                    write(nometotal)

                } else
                    mResultTextView.setText(R.string.no_barcode_captured)
            } else
                Log.e(LOG_TAG, String.format(getString(R.string.barcode_error_format),
                        CommonStatusCodes.getStatusCodeString(resultCode)))
        } else
            super.onActivityResult(requestCode, resultCode, data)
    }


    fun EntraNome():String{

       // val textView: TextView = findViewById(R.id.editText3) as TextView
        //editText3.setVisibility(View.VISIBLE)
        //frameLayout.setVisibility(View.VISIBLE)

        saida = editText3.getText().toString()
       // Toast.makeText(this, "saida -> " + saida, Toast.LENGTH_SHORT).show()

        var name1 = File(saida + ".csv").toString()
       // Toast.makeText(this, "name1 -> " + name1, Toast.LENGTH_SHORT).show()

        //editText3.setVisibility(View.INVISIBLE)
        //frameLayout.setVisibility(View.INVISIBLE)

        return name1
    }


    fun CriarArquivo(arquivo: String){

        file = arquivo
        Toast.makeText( this,"Arquivo file -> " + file, Toast.LENGTH_SHORT).show()
        // Cria o diretorio
        //sucesso = File(dir).mkdirs()
        sucesso = File(dirnovo).mkdirs()

        if(!File(dirnovo).exists()) {
            sucesso = File(dirnovo).mkdirs()
        }

        if(sucesso) {

            // Vai criar o arquivo no diretorio acima
            //val sd = File("filename.txt")
            Toast.makeText( this,"Arquivo file -> " + file, Toast.LENGTH_SHORT).show()
            val sd  = File(file).absoluteFile
            if (!sd.exists()) {
                sucesso = sd.mkdir()
            }

            if (sucesso) {
                // directory exists or already created
                val dest = File(sd, file)
                try {
                    PrintWriter(dest).use { out -> out.println("") }
                } catch (e: Exception) {
                    // handle the exception
                }

            } else {

                Toast.makeText(this, "Nao pode criar Diretorio.", Toast.LENGTH_SHORT).show()

            }

        }

    }

    fun onClickPtp(view: View){

        val int = Intent(getApplicationContext(),Main5Activity::class.java)
        startActivity(int)

    }

    fun onClickCancel(view: View){

        Botao4.visibility = View.INVISIBLE
        TextView2.visibility = View.INVISIBLE
        editText3.visibility = View.INVISIBLE
        frameLayout.visibility = View.INVISIBLE
        return

    }

    fun onClickEditor(view: View)
    {
        editText3.setVisibility(View.VISIBLE)
        frameLayout.setVisibility(View.VISIBLE)
        scan_barcode_button.setVisibility(View.VISIBLE)  //Habilita o botao SCAN
        val textView3: TextView = findViewById(R.id.editText3) as TextView

/***************************** Forca o teclado aparecer ***************************/
        editText3.requestFocus()
        // open the soft keyboard
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText3, InputMethodManager.SHOW_IMPLICIT)
/**********************************************************************************/




        Botao4.visibility = View.VISIBLE
        textView3.setOnClickListener {

            nometotal = EntraNome()

        CriarArquivo( nometotal)

            editText3.setVisibility(View.INVISIBLE)
            frameLayout.setVisibility(View.INVISIBLE)


        val int = Intent(getApplicationContext(),Main2Activity::class.java)
        val Parametros = Bundle()
        Parametros.putString("chave_name1",nometotal) //estou enviando name1 para a outrz tela
        int.putExtras(Parametros) // Informa a Bundle para enviar os parametros
            startActivity(int)    // inicia o processo de envio
            Botao4.visibility = View.INVISIBLE
    }

    }


    fun onClickSair(view: View)
    {

        finish()

    }

    override fun onStart() {
        super.onStart()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION)
        } else {
            //write()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSION -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //write()
            }
        }
    }

    private fun write( arq1: String) {

        file = arq1
        Toast.makeText( this,"Arq1 write file -> " + file, Toast.LENGTH_SHORT).show()
        try {

                File("$dirnovo/$file").appendText(saida + "\n")

        } catch (ioe: IOException) {
            Toast.makeText(this, "Nao pode escrever", Toast.LENGTH_SHORT).show()
        }

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

}
