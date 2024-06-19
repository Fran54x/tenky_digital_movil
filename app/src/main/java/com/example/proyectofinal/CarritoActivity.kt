package com.example.proyectofinal

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CarritoActivity : AppCompatActivity(),SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null

    private lateinit var lvCarrito: ListView
    private lateinit var txtPago: TextView
    private lateinit var txtPago2: TextView
    private lateinit var txtPago3: TextView
    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val productos = mutableListOf<Producto>()
    private lateinit var adapter: CarritoAdapter
    private val CHANNEL_ID = "channel"
    private val NOTIFICATION_ID = 123
    private val PERMISSION_REQUEST_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.BODY_SENSORS), 1)
        }



        lvCarrito = findViewById(R.id.lvProductos)
        txtPago = findViewById(R.id.txtPago)
        txtPago2 = findViewById(R.id.txtPago2)
        txtPago3 = findViewById(R.id.txtPago3)

        adapter = CarritoAdapter(productos) { producto ->
            eliminarProductoDelCarrito(producto)
        }
        lvCarrito.adapter = adapter

        val imgRegreso: ImageView = findViewById(R.id.imgextra2)
        imgRegreso.setOnClickListener {
            finish()
        }

        val btnPagar: Button = findViewById(R.id.btnPagar)
        btnPagar.setOnClickListener {
            val userId = auth.currentUser?.uid ?: return@setOnClickListener
            FirestoreUtils.limpiarCarritoFirestore(userId,
                onSuccess = {
                    Carrito.clearCarrito()
                    checkNotificationPermission()
                    val intent = Intent(this, PrincipalActivity::class.java)
                    startActivity(intent)
                    finish()
                },
                onFailure = { e ->
                    Toast.makeText(this, "Error al limpiar carrito: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            )
        }
        cargarProductosDelCarrito()
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val lightLevel = event.values[0]
            if (lightLevel < 10) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Do something if sensor accuracy changes
    }

    private fun cargarProductosDelCarrito() {
        val userId = auth.currentUser?.uid ?: return
        db.collection("carritos").document(userId).collection("productos")
            .get()
            .addOnSuccessListener { documents ->
                productos.clear()
                for (document in documents) {
                    val producto = document.toObject(Producto::class.java)
                    productos.add(producto)
                }
                adapter.notifyDataSetChanged()
                actualizarTextos()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al cargar productos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun actualizarTextos() {
        val subtotal = calcularSubtotal()
        txtPago.text = "$ $subtotal"
        val montoFijo = 100.00
        txtPago2.text = "$ $montoFijo"
        val total = subtotal + montoFijo
        txtPago3.text = "$ $total"
    }

    private fun calcularSubtotal(): Double {
        var subtotal = 0.0
        for (producto in productos) {
            subtotal += producto.precio
        }
        return subtotal
    }

    private fun eliminarProductoDelCarrito(producto: Producto) {
        val userId = auth.currentUser?.uid ?: return
        FirestoreUtils.eliminarProductoDelCarrito(producto, userId,
            onSuccess = {
                productos.remove(producto)
                adapter.notifyDataSetChanged()
                actualizarTextos()
                Toast.makeText(this, "Producto eliminado del carrito", Toast.LENGTH_SHORT).show()
            },
            onFailure = { e ->
                Toast.makeText(this, "Error al eliminar producto: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        )
    }
    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), PERMISSION_REQUEST_CODE)
            } else {
                mostrarNotificacion("Pago Realizado")
            }
        } else {
            mostrarNotificacion("Pago Realizado")
        }
    }
    @SuppressLint("MissingPermission")
    private fun mostrarNotificacion(content: String) {
        // Crear el canal de notificación (solo es necesario en Android Oreo y superior)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Canal de notificación"
            val descriptionText = "Descripción del canal"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_active_24) // icono de la notificación
            .setContentTitle(content)
            .setContentText("Felicidades tu pago fue realizado con éxito")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true) // cancelar la notificación al hacer clic en ella

        with(NotificationManagerCompat.from(this)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }
}