package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class ModifyShortcutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modifier_shortcut)

        val spinner: Spinner = findViewById(R.id.spinnerShortcuts)
        // Populez la liste déroulante avec les commandes clavier
        // TODO : Remplacez le tableau par la liste des commandes clavier
        val keyboardCommands = listOf("Ctrl+C", "Ctrl+V", "Ctrl+Z", "Ctrl+X", "Ctrl+S")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, keyboardCommands)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val validerButton: Button = findViewById(R.id.buttonValider)
        validerButton.setOnClickListener {
            // Récupérez la commande sélectionnée dans la liste déroulante
            val selectedCommand = spinner.selectedItem.toString()

            // Envoyez la commande de retour à l'activité principale
            val resultIntent = Intent()
            resultIntent.putExtra("newShortcut", selectedCommand)
            setResult(RESULT_OK, resultIntent)
            // TODO : Est-ce que ca sauvegarde quelque part ?
            finish()
        }
    }
}
