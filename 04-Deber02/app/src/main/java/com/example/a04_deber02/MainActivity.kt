package com.example.a04_deber02

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private var allClasses: ArrayList<ClassEntity> = arrayListOf()
    private lateinit var adapter: ArrayAdapter<ClassEntity>
    private var selectedRegisterPosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_class)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        DataBase.tables = SqliteHelper(this)
        val classesList = findViewById<ListView>(R.id.list_class)
        allClasses = DataBase.tables.getAllClasses()
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            allClasses
        )
        classesList.adapter = adapter
        adapter.notifyDataSetChanged() // To update the UI

        val btnRedirectCreateClass = findViewById<Button>(R.id.btn_redirect_create_class)
        btnRedirectCreateClass.setOnClickListener {
            goToActivity(CreateUpdateClass::class.java)
        }

        registerForContextMenu(classesList)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        // Set options for the context menu
        val inflater = menuInflater
        inflater.inflate(R.menu.class_menu, menu)

        // Get ID of the selected item of the list
        val register = menuInfo as AdapterView.AdapterContextMenuInfo
        selectedRegisterPosition = register.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.mi_edit_class -> {
                goToActivity(CreateUpdateClass::class.java, allClasses[selectedRegisterPosition])
                true
            }
            R.id.mi_delete_class -> {
                openDialog(allClasses[selectedRegisterPosition].id)
                true
            }
            R.id.mi_class_students -> {
                goToActivity(StudentsList::class.java, allClasses[selectedRegisterPosition])
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun goToActivity(
        activityClass: Class<*>,
        dataToPass: ClassEntity? = null
    ) {
        val intent = Intent(this, activityClass)
        dataToPass?.let {
            intent.putExtra("selectedClass", it)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun openDialog(registerIndex: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Está seguro de eliminar la clase?")
        builder.setPositiveButton(
            "Eliminar"
        ) { _, _ ->
            DataBase.tables.deleteClass(registerIndex)
            allClasses.clear()
            allClasses.addAll(DataBase.tables.getAllClasses())
            adapter.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)

        builder.create().show()
    }
}