package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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

    private val REQUEST_MODIFY_SHORTCUT = 1
    private val REQUEST_MODIFY_NAME = 2

    private var enteredName: String? = "TEST"

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

        val fab: FloatingActionButton = binding.appBarMain.fab.also {
            it.setImageResource(android.R.drawable.ic_input_add)
        } // Change l'icône à "+"
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
        // Dans votre activité principale (Main)

// Dans la méthode onCreate ou un endroit approprié
        // Dans votre activité principale (Main)

// ...

// Récupérez le conteneur de boutons
        val containerButtons = findViewById<LinearLayout>(R.id.containerButtons)

// ...

// Dans la lambda du bouton "Créer"
        val positiveButton = builder.setPositiveButton("Créer") { dialog, _ ->
            // Récupérez le nom entré depuis le résultat de ModifyNameActivity
            val enteredName = // Vous devez récupérer le nom entré, par exemple, depuis les résultats de l'activité ModifyNameActivity

                // Vérifiez si le nom n'est pas nul ou vide
                if (!enteredName.isNullOrEmpty()) {
                    // Créez un nouveau bouton
                    val newButton = Button(this)  // Assurez-vous de remplacer YourActivity par le nom de votre activité
                    newButton.text = enteredName

                    // Ajoutez le bouton au conteneur
                    containerButtons.addView(newButton)

                    // Ajoutez le comportement du bouton lorsque vous cliquez dessus
                    newButton.setOnClickListener {
                        // Implémentez ici le comportement lorsque le nouveau bouton est cliqué
                        Snackbar.make(binding.root, "Bouton $enteredName cliqué", Snackbar.LENGTH_LONG).show()
                    }

                    // Fermez la boîte de dialogue
                    dialog.dismiss()
                } else {
                    // Affichez un message d'erreur si le nom est nul ou vide
                    Snackbar.make(binding.root, "Le nom ne peut pas être vide", Snackbar.LENGTH_LONG).show()
                }
        }



        builder.setNegativeButton("Annuler") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()

        val modifyNameButton = Button(this)
        modifyNameButton.text = "Modifier Nom"

        val modifyShortcutButton = Button(this)
        modifyShortcutButton.text = "Modifier Shortcut"


        val linearLayout = dialogView.findViewById<LinearLayout>(R.id.linearLayoutButtons)
        linearLayout.addView(modifyNameButton)
        linearLayout.addView(modifyShortcutButton)


        modifyNameButton.setOnClickListener {
            val intent = Intent(this, ModifyNameActivity::class.java)
            startActivityForResult(intent, REQUEST_MODIFY_NAME)
        }
        // TODO : Trouver pourquoi le nom ne s'affiche à la place de "Dock" alors qu'il s'affiche dans la pop-up
        val enteredNameTextView = dialogView.findViewById<TextView>(R.id.textViewEnteredName)

        modifyShortcutButton.setOnClickListener {
            val intent = Intent(this, ModifyShortcutActivity::class.java)
            startActivityForResult(intent, REQUEST_MODIFY_SHORTCUT)
        }

        alertDialog.show()
        alertDialog.setOnShowListener {
            enteredNameTextView.text = enteredName
        }
    }

    // Override onActivityResult pour traiter les résultats des activités appelées pour la modification du nom et du raccourci

    // Ajoutez la méthode onActivityResult pour traiter le résultat de retour
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_MODIFY_SHORTCUT -> {
                if (resultCode == RESULT_OK) {
                    // Vérifiez si les données de résultat ne sont pas nulles
                    data?.let {
                        // Récupérez la nouvelle commande de raccourci
                        val newShortcut = it.getStringExtra("newShortcut")

                        // Affichez la commande de raccourci dans votre message ou faites ce que vous voulez avec elle
                        showToast("Nouveau raccourci sélectionné : $newShortcut")
                    }
                }
            }
            REQUEST_MODIFY_NAME -> {
                if (resultCode == RESULT_OK) {
                    // Vérifiez si les données de résultat ne sont pas nulles
                    data?.let {
                        // Récupérez le nouveau nom
                        val enteredName = it.getStringExtra("enteredName")

                        // Affichez le nouveau nom dans votre message ou faites ce que vous voulez avec lui
                        showToast("Nouveau nom entré : $enteredName")
                    }
                }
            }
        }
    }

    // Méthode pour afficher un message (vous devez adapter cela à votre implémentation)
    private fun showToast(message: String) {
        // Affichez votre message, par exemple, avec Toast
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}