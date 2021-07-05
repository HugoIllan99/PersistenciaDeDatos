package com.example.persistenciadedatos

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    val nombreArchivo = "mi_archivo.txt"

    var btnGuardar:Button?=null
    var etInfo:EditText?=null
    var btnLeer:Button?=null
    var tvMostrar:TextView?=null

    var btnLeerP:Button?=null
    var btnGuardarP:Button?=null

    var etClave:EditText?=null
    var etValor:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

        btnGuardar!!.setOnClickListener {
            //validar que existe texto en info
            val datos = etInfo!!.text.toString()
            escribirDatos(datos)
        }

        btnLeer!!.setOnClickListener {
            tvMostrar!!.text = leerArchivo(nombreArchivo)
        }

        btnLeerP!!.setOnClickListener {
            val claveABuscar = etClave!!.text.toString()
            val datosEncontrado=getDato(claveABuscar)
            etValor!!.setText(datosEncontrado)

        }

        btnGuardarP!!.setOnClickListener {
            val claveAGuadar = etClave!!.text.toString()
            val valorAGuadar = etValor!!.text.toString()
            setDato(claveAGuadar,valorAGuadar)

        }
    }

    fun escribirDatos(text:String){
        val fos: FileOutputStream
        try {
            fos = openFileOutput(nombreArchivo, Context.MODE_PRIVATE)
            fos.write(text.toByteArray())
            fos.close()
            Log.wtf("Archivos", text)
        }catch (e:Exception){
            Log.wtf("Archivos", "Algo fallo ${e.message}")

            e.printStackTrace()
        }
    }

    fun leerArchivo(nombreArchivo:String):String{
        val inputStream:InputStream = openFileInput(nombreArchivo)
        val stringResultado = inputStream.bufferedReader().use { it.readLine() }
        return stringResultado
    }

    /*
    * Metodos para sharedPreference
    *
     */

    fun setDato(clave:String,valor:String){
        val prefs = getPreferences(Context.MODE_PRIVATE)
        prefs.edit().putString(clave,valor).apply()
    }

    fun getDato(clave:String):String{
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val dato = prefs.getString(clave,"No se econtro ${clave}")
        return  dato.toString()
    }

    fun initUI(){
        btnGuardar = findViewById(R.id.btnGuardar)
        etInfo = findViewById(R.id.etInfo)
        btnLeer = findViewById(R.id.btnLeer)
        tvMostrar = findViewById(R.id.tvMostrar)
        etClave = findViewById(R.id.etClave)
        etValor = findViewById(R.id.etValor)
        btnGuardarP = findViewById(R.id.btnGuardarPrefs)
        btnLeerP = findViewById(R.id.btnLeerPrefs)
    }

}