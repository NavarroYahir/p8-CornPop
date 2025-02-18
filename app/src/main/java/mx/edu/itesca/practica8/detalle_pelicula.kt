package mx.edu.itesca.practica8

import android.os.Bundle
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

        val iv_pelicula_image: ImageView =findViewById(R.id.iv_pelicula_imagen)
        val tv_nombre_pelicula: TextView =findViewById(R.id.tv_nombre_pelicula)
        val tv_pelicula_desc: TextView=findViewById(R.id.tv_pelicula_desc)

        val bundle=intent.extras
        if (bundle!=null) {
            iv_pelicula_image.setImageResource(bundle.getInt("header"))
            tv_nombre_pelicula.setText(bundle.getString("titulo"))
            tv_pelicula_desc.setText(bundle.getString("sinopsis"))
        }
    }
}