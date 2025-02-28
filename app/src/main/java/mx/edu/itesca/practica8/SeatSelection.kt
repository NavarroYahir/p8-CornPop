package mx.edu.itesca.practica8

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SeatSelection : AppCompatActivity() {

    companion object {
        const val TOTAL_SEATS = 20
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seat_selection)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle = intent.extras
        val movieName = bundle?.getString("titulo") ?: ""
        val movieHeader = bundle?.getInt("header") ?: 0
        val movieSinopsis = bundle?.getString("sinopsis") ?: ""
        val availableSeats = TOTAL_SEATS - ReservationManager.getReservations(movieName).size

        val title: TextView = findViewById(R.id.titleSeats)
        title.text = movieName

        val row1: RadioGroup = findViewById(R.id.row1)
        val row2: RadioGroup = findViewById(R.id.row2)
        val row3: RadioGroup = findViewById(R.id.row3)
        val row4: RadioGroup = findViewById(R.id.row4)

        fun updateReservedButtons(radioGroup: RadioGroup) {
            for (i in 0 until radioGroup.childCount) {
                val child = radioGroup.getChildAt(i)
                if (child is RadioButton) {
                    val seatNumber = child.text.toString().toIntOrNull()
                    if (seatNumber != null && ReservationManager.getReservations(movieName).contains(seatNumber)) {
                        child.isEnabled = false
                        child.setBackgroundResource(R.drawable.radio_disabled)
                    }
                }
            }
        }

        updateReservedButtons(row1)
        updateReservedButtons(row2)
        updateReservedButtons(row3)
        updateReservedButtons(row4)

        row1.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                row2.clearCheck()
                row3.clearCheck()
                row4.clearCheck()
            }
        }
        row2.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                row1.clearCheck()
                row3.clearCheck()
                row4.clearCheck()
            }
        }
        row3.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                row1.clearCheck()
                row2.clearCheck()
                row4.clearCheck()
            }
        }
        row4.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId != -1) {
                row1.clearCheck()
                row2.clearCheck()
                row3.clearCheck()
            }
        }

        val confirm: Button = findViewById(R.id.confirm)
        confirm.setOnClickListener {
            val selectedSeatId = when {
                row1.checkedRadioButtonId != -1 -> row1.checkedRadioButtonId
                row2.checkedRadioButtonId != -1 -> row2.checkedRadioButtonId
                row3.checkedRadioButtonId != -1 -> row3.checkedRadioButtonId
                row4.checkedRadioButtonId != -1 -> row4.checkedRadioButtonId
                else -> -1
            }

            if (selectedSeatId != -1) {
                val selectedSeat = findViewById<RadioButton>(selectedSeatId)
                val seatNumber = selectedSeat.text.toString().toIntOrNull()
                if (seatNumber == null) {
                    Toast.makeText(this, "Error al obtener el número de asiento", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                if (!selectedSeat.isEnabled) {
                    Toast.makeText(this, "La silla ya está ocupada", Toast.LENGTH_SHORT).show()
                } else {
                    selectedSeat.isEnabled = false
                    selectedSeat.setBackgroundResource(R.drawable.radio_disabled)

                    ReservationManager.reserveSeat(movieName, seatNumber)
                    Toast.makeText(this, "Silla $seatNumber ocupada", Toast.LENGTH_SHORT).show()

                    val updatedSeats = TOTAL_SEATS - ReservationManager.getReservations(movieName).size

                    val intent = Intent(this, detalle_pelicula::class.java)
                    intent.putExtra("titulo", movieName)
                    intent.putExtra("header", movieHeader)
                    intent.putExtra("sinopsis", movieSinopsis)
                    intent.putExtra("numberSeats", updatedSeats)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this, "Selecciona una silla", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
