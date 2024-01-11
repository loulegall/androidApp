package com.example.myapp

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top-level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val fab: FloatingActionButton = binding.appBarMain.fab
        fab.setImageResource(android.R.drawable.ic_input_add) // Change l'icône à "+"
        fab.setOnClickListener { view ->
            // Afficher la pop-up ici
            showCreateStreamDeckDialog()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun showCreateStreamDeckDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_create_stream_deck, null)
        builder.setView(dialogView)

        builder.setTitle("Créer un nouveau Stream Deck")

        val positiveButton = builder.setPositiveButton("Créer") { dialog, _ ->
            // Implémentez ici la logique de création du Stream Deck
            Snackbar.make(binding.root, "Stream Deck créé avec succès", Snackbar.LENGTH_LONG).show()
            dialog.dismiss()
        }

        builder.setNegativeButton("Annuler") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()

        // Ajoutez des boutons supplémentaires à la boîte de dialogue
        val modifyNameButton = Button(this)
        modifyNameButton.text = "Modifier Nom"

        val modifyShortcutButton = Button(this)
        modifyShortcutButton.text = "Modifier Shortcut"


        val linearLayout = dialogView.findViewById<LinearLayout>(R.id.linearLayoutButtons)
        linearLayout.addView(modifyNameButton)
        linearLayout.addView(modifyShortcutButton)

        modifyNameButton.setOnClickListener {
            // Implémentez ici la logique pour modifier le nom
            // Vous pouvez ouvrir une nouvelle activité ou une boîte de dialogue pour cela
            alertDialog.dismiss()
        }

        modifyShortcutButton.setOnClickListener {
            // Implémentez ici la logique pour modifier le raccourci
            // Vous pouvez ouvrir une nouvelle activité ou une boîte de dialogue pour cela
            alertDialog.dismiss()
        }

        alertDialog.show()
    }


}