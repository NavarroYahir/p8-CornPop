package mx.edu.itesca.practica8

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class detalle_pelicula : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_pelicula)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val iv_pelicula_image: ImageView = findViewById(R.id.iv_pelicula_imagen)
        val tv_nombre_pelicula: TextView = findViewById(R.id.tv_nombre_pelicula)
        val tv_pelicula_desc: TextView = findViewById(R.id.tv_pelicula_desc)
        val seats: TextView = findViewById(R.id.seatLeft)

        val bundle = intent.extras
        if (bundle != null) {
            iv_pelicula_image.setImageResource(bundle.getInt("header"))
            tv_nombre_pelicula.text = bundle.getString("titulo")
            tv_pelicula_desc.text = bundle.getString("sinopsis")
            seats.text = bundle.getInt("numberSeats").toString()
        }

        val button: Button = findViewById(R.id.buyTickets)
        button.setOnClickListener {
            val intent = Intent(this, SeatSelection::class.java)
            intent.putExtra("titulo", tv_nombre_pelicula.text.toString())
            intent.putExtra("header", bundle?.getInt("header") ?: 0)
            intent.putExtra("sinopsis", tv_pelicula_desc.text.toString())
            intent.putExtra("numberSeats", bundle?.getInt("numberSeats") ?: 0)
            startActivity(intent)
        }
    }
}
