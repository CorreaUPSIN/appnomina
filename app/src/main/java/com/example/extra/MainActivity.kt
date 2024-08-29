package com.example.extra

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameEditText = findViewById<EditText>(R.id.nameEditText)
        val enterButton = findViewById<Button>(R.id.enterButton)
        val exitButton = findViewById<Button>(R.id.exitButton)

        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filtered = s.toString().filter { it.isLetter() || it.isWhitespace() }
                if (s.toString() != filtered) {
                    nameEditText.setText(filtered)
                    nameEditText.setSelection(filtered.length)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        enterButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()

            if (name.isNotEmpty()) {
                val intent = Intent(this, ReciboNominaActivity::class.java).apply {
                    putExtra("EXTRA_NAME", name)
                }
                startActivity(intent)
            } else {
                nameEditText.error = "Por favor, ingrese su nombre"
            }
        }

        exitButton.setOnClickListener {
            finish()
        }
    }
}
