package com.example.desafio1ss233210

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.desafio1ss233210.utils.Calculos
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalculadoraActivity : AppCompatActivity() {

    private lateinit var etNum1: EditText
    private lateinit var etNum2: EditText
    private lateinit var tvResultadoCalc: TextView
    private val formato = DecimalFormat("#.####")
    private val NOMBRE_ARCHIVO = "historial_calculadora.txt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)

        etNum1 = findViewById(R.id.etNum1)
        etNum2 = findViewById(R.id.etNum2)
        tvResultadoCalc = findViewById(R.id.tvResultadoCalc)

        findViewById<ImageButton>(R.id.btnSuma).setOnClickListener {
            operarDosNumeros { a, b -> Calculos.sumar(a, b) to "+" }
        }
        findViewById<ImageButton>(R.id.btnResta).setOnClickListener {
            operarDosNumeros { a, b -> Calculos.restar(a, b) to "-" }
        }
        findViewById<ImageButton>(R.id.btnMultiplicar).setOnClickListener {
            operarDosNumeros { a, b -> Calculos.multiplicar(a, b) to "*" }
        }
        findViewById<ImageButton>(R.id.btnDividir).setOnClickListener {
            dividirNumeros()
        }
        findViewById<ImageButton>(R.id.btnPotencia).setOnClickListener {
            operarDosNumeros { a, b -> Calculos.potencia(a, b) to "^" }
        }
        findViewById<ImageButton>(R.id.btnRaiz).setOnClickListener {
            raizNumero()
        }

        findViewById<Button>(R.id.btnVerHistorial).setOnClickListener {
            mostrarHistorial()
        }

        findViewById<Button>(R.id.btnRegresarCalc).setOnClickListener {
            finish()
        }
    }

    private fun leerNumeros(): Pair<Double, Double>? {
        val n1 = etNum1.text.toString().trim().toDoubleOrNull()
        val n2 = etNum2.text.toString().trim().toDoubleOrNull()

        if (n1 == null) {
            etNum1.error = getString(R.string.error_num_invalido)
            return null
        }
        if (n2 == null) {
            etNum2.error = getString(R.string.error_num_invalido)
            return null
        }
        return Pair(n1, n2)
    }

    private fun operarDosNumeros(operacion: (Double, Double) -> Pair<Double, String>) {
        val numeros = leerNumeros() ?: return
        val (resultado, simbolo) = operacion(numeros.first, numeros.second)
        mostrarResultado(resultado)
        guardarEnHistorial("${formato.format(numeros.first)} $simbolo ${formato.format(numeros.second)} = ${formato.format(resultado)}")
    }

    private fun dividirNumeros() {
        val numeros = leerNumeros() ?: return
        val resultado = Calculos.dividir(numeros.first, numeros.second)

        if (resultado == null) {
            tvResultadoCalc.text = getString(R.string.error_division_cero)
            etNum2.error = getString(R.string.error_division_cero)
            return
        }

        mostrarResultado(resultado)
        guardarEnHistorial("${formato.format(numeros.first)} / ${formato.format(numeros.second)} = ${formato.format(resultado)}")
    }

    private fun raizNumero() {
        val n1 = etNum1.text.toString().trim().toDoubleOrNull()
        if (n1 == null) {
            etNum1.error = getString(R.string.error_num_invalido)
            return
        }

        val resultado = Calculos.raizCuadrada(n1)
        if (resultado == null) {
            tvResultadoCalc.text = getString(R.string.error_raiz_negativa)
            etNum1.error = getString(R.string.error_raiz_negativa)
            return
        }

        mostrarResultado(resultado)
        guardarEnHistorial("√${formato.format(n1)} = ${formato.format(resultado)}")
    }

    private fun mostrarResultado(resultado: Double) {
        tvResultadoCalc.text = "= ${formato.format(resultado)}"
    }

    private fun guardarEnHistorial(operacion: String) {
        try {
            val timestamp = SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(Date())
            val linea = "[$timestamp] $operacion\n"
            openFileOutput(NOMBRE_ARCHIVO, MODE_APPEND).use { fos ->
                fos.write(linea.toByteArray())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun leerHistorial(): String {
        return try {
            val sb = StringBuilder()
            openFileInput(NOMBRE_ARCHIVO).use { fis ->
                BufferedReader(InputStreamReader(fis)).forEachLine { linea ->
                    sb.append(linea).append("\n")
                }
            }
            if (sb.isEmpty()) getString(R.string.historial_vacio) else sb.toString()
        } catch (e: Exception) {
            getString(R.string.historial_vacio)
        }
    }

    private fun mostrarHistorial() {
        AlertDialog.Builder(this)
            .setTitle(R.string.historial_titulo)
            .setMessage(leerHistorial())
            .setPositiveButton(R.string.btn_cerrar, null)
            .show()
    }
}