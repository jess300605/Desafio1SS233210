package com.example.desafio1ss233210

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.desafio1ss233210.utils.Calculos
import java.text.DecimalFormat

class PromedioActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etNota1: EditText
    private lateinit var etNota2: EditText
    private lateinit var etNota3: EditText
    private lateinit var etNota4: EditText
    private lateinit var etNota5: EditText
    private lateinit var tvResultado: TextView

    private val CHANNEL_ID = "canal_promedio"

    // Lanzador para pedir permiso de notificaciones (Android 13+)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            // El usuario no otorgó el permiso; simplemente no se enviará notificación
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promedio)

        etNombre = findViewById(R.id.etNombre)
        etNota1 = findViewById(R.id.etNota1)
        etNota2 = findViewById(R.id.etNota2)
        etNota3 = findViewById(R.id.etNota3)
        etNota4 = findViewById(R.id.etNota4)
        etNota5 = findViewById(R.id.etNota5)
        tvResultado = findViewById(R.id.tvResultado)

        crearCanalNotificacion()
        pedirPermisoNotificaciones()

        val btnCalcular = findViewById<Button>(R.id.btnCalcularPromedio)
        btnCalcular.setOnClickListener {
            procesarPromedio()
        }

        val btnRegresar = findViewById<Button>(R.id.btnRegresar)
        btnRegresar.setOnClickListener {
            finish()
        }
    }

    private fun procesarPromedio() {
        val nombre = etNombre.text.toString().trim()

        if (nombre.isEmpty()) {
            etNombre.error = getString(R.string.error_nombre_vacio)
            return
        }

        val campos = listOf(etNota1, etNota2, etNota3, etNota4, etNota5)
        val notas = DoubleArray(5)

        for (i in campos.indices) {
            val texto = campos[i].text.toString().trim()

            if (texto.isEmpty()) {
                campos[i].error = getString(R.string.error_campo_vacio)
                return
            }

            val valor = texto.toDoubleOrNull()
            if (valor == null || !Calculos.notaEnRango(valor)) {
                campos[i].error = getString(R.string.error_rango_nota)
                return
            }

            notas[i] = valor
        }

        val promedio = Calculos.calcularPromedio(notas[0], notas[1], notas[2], notas[3], notas[4])
        val aprobado = Calculos.estaAprobado(promedio)

        val formato = DecimalFormat("#.##")
        val promedioTexto = formato.format(promedio)
        val estadoTexto = if (aprobado) getString(R.string.resultado_aprobado)
        else getString(R.string.resultado_reprobado)

        tvResultado.text = "Promedio: $promedioTexto — $estadoTexto"
        tvResultado.setTextColor(
            ContextCompat.getColor(this, if (aprobado) R.color.verdeExito else R.color.rojoError)
        )

        enviarNotificacion(promedioTexto, estadoTexto)
    }

    private fun crearCanalNotificacion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nombreCanal = getString(R.string.notif_canal_nombre)
            val canal = NotificationChannel(
                CHANNEL_ID, nombreCanal, NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(canal)
        }
    }

    private fun pedirPermisoNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun enviarNotificacion(promedio: String, estado: String) {
        // Verificar permiso antes de notificar (requerido en API 33+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(getString(R.string.notif_titulo))
            .setContentText("Promedio: $promedio — $estado")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(this).notify(1, builder.build())
    }
}