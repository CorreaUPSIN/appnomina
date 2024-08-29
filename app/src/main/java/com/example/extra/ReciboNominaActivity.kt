package com.example.extra

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReciboNominaActivity : AppCompatActivity() {

    private lateinit var numReciboEditText: EditText
    private lateinit var nombreNominaTextView: TextView
    private lateinit var puestoRadioGroup: RadioGroup
    private lateinit var horasTrabEditText: EditText
    private lateinit var horasExtraTrabEditText: EditText
    private lateinit var resultTextView: TextView
    private lateinit var calculateButton: Button
    private lateinit var clearButton: Button
    private lateinit var exitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recibonomina)

        numReciboEditText = findViewById(R.id.numReciboEditText)
        nombreNominaTextView = findViewById(R.id.nombreNominaTextView)
        puestoRadioGroup = findViewById(R.id.puestoRadioGroup)
        horasTrabEditText = findViewById(R.id.horasTrabEditText)
        horasExtraTrabEditText = findViewById(R.id.horasExtraTrabEditText)
        resultTextView = findViewById(R.id.resultTextView)
        calculateButton = findViewById(R.id.calculateButton)
        clearButton = findViewById(R.id.clearButton)
        exitButton = findViewById(R.id.exitButton)

        val name = intent.getStringExtra("EXTRA_NAME")
        nombreNominaTextView.text = name

        exitButton.setOnClickListener {
            finish()
        }

        calculateButton.setOnClickListener {
            calcularYActualizarResultados()
        }

        clearButton.setOnClickListener {
            numReciboEditText.text.clear()
            horasTrabEditText.text.clear()
            horasExtraTrabEditText.text.clear()
            puestoRadioGroup.clearCheck()
            resultTextView.text = ""
        }

        puestoRadioGroup.setOnCheckedChangeListener { _, _ ->
            calcularYActualizarResultados()
        }
    }

    private fun calcularYActualizarResultados() {
        val numRecibo = numReciboEditText.text.toString().toIntOrNull()
        val horasTrab = horasTrabEditText.text.toString().toFloatOrNull() ?: 0f
        val horasExtraTrab = horasExtraTrabEditText.text.toString().toFloatOrNull() ?: 0f

        if (numRecibo == null || numRecibo <= 0) {
            showError("Número de recibo inválido")
            return
        }

        val puesto = when (puestoRadioGroup.checkedRadioButtonId) {
            R.id.auxiliarRadioButton -> "Auxiliar"
            R.id.albanilRadioButton -> "Albañil"
            R.id.ingObraRadioButton -> "Ing. de Obra"
            else -> {
                showError("Seleccione un puesto")
                return
            }
        }

        val recibo = ReciboNomina(
            numRecibo = numRecibo,
            nombreNomina = nombreNominaTextView.text.toString(),
            puesto = puesto,
            horasTrab = horasTrab,
            horasExtraTrab = horasExtraTrab
        )

        val subtotal = recibo.calcularSubtotal()
        val impuestos = recibo.generarImpuestos()
        val total = recibo.calcularTotal()

        resultTextView.text = "Subtotal: $subtotal\nImpuestos: $impuestos\nTotal: $total"
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
