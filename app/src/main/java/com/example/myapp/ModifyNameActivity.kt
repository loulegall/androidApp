package com.example.myapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class ModifyNameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifier_name)

        val nameEditText: EditText = findViewById(R.id.editTextName)
        val validerButton: Button = findViewById(R.id.buttonValider)

        validerButton.setOnClickListener {
            // Récupérez le nom entré dans le champ de texte
            val enteredName = nameEditText.text.toString()

            // Envoyez le nom de retour à l'activité précédente
            val resultIntent = Intent()
            resultIntent.putExtra("enteredName", enteredName)
            setResult(RESULT_OK, resultIntent)
            // TODO : Vous pouvez sauvegarder le nom où vous le souhaitez (base de données, préférences partagées, etc.)
            finish()
        }
    }
}