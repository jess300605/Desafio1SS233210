package com.example.desafio1ss233210

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.desafio1ss233210.utils.Calculos
import java.text.DecimalFormat

class SalarioActivity : AppCompatActivity() {

    private lateinit var etNombreEmpleado: EditText
    private lateinit var etSalarioBase: EditText
    private lateinit var tvSalarioBruto: TextView
    private lateinit var tvRenta: TextView
    private lateinit var tvAfp: TextView
    private lateinit var tvIsss: TextView
    private lateinit var tvSalarioNeto: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_salario)

        etNombreEmpleado = findViewById(R.id.etNombreEmpleado)
        etSalarioBase = findViewById(R.id.etSalarioBase)
        tvSalarioBruto = findViewById(R.id.tvSalarioBruto)
        tvRenta = findViewById(R.id.tvRenta)
        tvAfp = findViewById(R.id.tvAfp)
        tvIsss = findViewById(R.id.tvIsss)
        tvSalarioNeto = findViewById(R.id.tvSalarioNeto)

        findViewById<Button>(R.id.btnCalcularSalario).setOnClickListener {
            procesarSalario()
        }

        findViewById<Button>(R.id.btnRegresarSalario).setOnClickListener {
            finish()
        }
    }

    private fun procesarSalario() {
        val nombre = etNombreEmpleado.text.toString().trim()
        val salarioTexto = etSalarioBase.text.toString().trim()

        if (nombre.isEmpty()) {
            etNombreEmpleado.error = getString(R.string.error_campo_vacio)
            return
        }

        val salario = salarioTexto.toDoubleOrNull()

        if (salarioTexto.isEmpty() || salario == null || salario <= 0.0) {
            etSalarioBase.error = getString(R.string.error_salario_invalido)
            vibrarDispositivo()
            return
        }

        val afp = Calculos.calcularAfp(salario)
        val isss = Calculos.calcularIsss(salario)
        val rentaImponible = salario - afp - isss          // <-- nuevo paso intermedio
        val renta = Calculos.calcularRenta(rentaImponible)  // <-- ahora se calcula sobre esto, no sobre "salario"
        val neto = Calculos.calcularSalarioNeto(salario, renta, afp, isss)
        val formato = DecimalFormat("#.##")

        tvSalarioBruto.text = "${getString(R.string.label_salario_bruto)}: $${formato.format(salario)}"
        tvSalarioBruto.setTextColor(ContextCompat.getColor(this, R.color.verdeExito))

        tvRenta.text = "${getString(R.string.label_renta)}: $${formato.format(renta)}"
        tvAfp.text = "${getString(R.string.label_afp)}: $${formato.format(afp)}"
        tvIsss.text = "${getString(R.string.label_isss)}: $${formato.format(isss)}"

        tvSalarioNeto.text = "${getString(R.string.label_salario_neto)}: $${formato.format(neto)}"
        tvSalarioNeto.setTextColor(ContextCompat.getColor(this, R.color.rojoError))
    }

    private fun vibrarDispositivo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator.vibrate(
                VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE)
            )
        } else {
            @Suppress("DEPRECATION")
            val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(300)
            }
        }
    }
}