package com.example.a06_examen02

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity() {

    private var allClasses: ArrayList<ClassEntity> = arrayListOf()
    private lateinit var adapter: ArrayAdapter<ClassEntity>
    private var selectedRegisterPosition = -1
    private lateinit var map: GoogleMap
    var mapPermissions = false

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

        requestPermission()
        startMapLogic()
    }

    private fun requestPermission() {
        val context = this.applicationContext
        val finePermissionName = android.Manifest.permission.ACCESS_FINE_LOCATION
        val coarsePermissionName = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val finePermission = ContextCompat.checkSelfPermission(context, finePermissionName)
        val coarsePermission = ContextCompat.checkSelfPermission(context, coarsePermissionName)
        val hasPermissions = finePermission == PackageManager.PERMISSION_GRANTED &&
                coarsePermission == PackageManager.PERMISSION_GRANTED
        if(hasPermissions) {
            mapPermissions = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(finePermissionName, coarsePermissionName),
                1)

        }
    }

    private fun startMapLogic() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync{ googleMap ->
            with(googleMap) {
                map = googleMap
                mapConfiguration()
                markAuthorsLocation()
            }
        }
    }

    private fun mapConfiguration() {
        val context = this.applicationContext
        with(map) {
            val finePermissionName = android.Manifest.permission.ACCESS_FINE_LOCATION
            val coarsePermissionName = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val finePermission = androidx.core.content.ContextCompat.checkSelfPermission(context, finePermissionName)
            val coarsePermission = androidx.core.content.ContextCompat.checkSelfPermission(context, coarsePermissionName)
            val hasPermissions = (finePermission == android.content.pm.PackageManager.PERMISSION_GRANTED) &&
                    (coarsePermission == android.content.pm.PackageManager.PERMISSION_GRANTED)
            if(hasPermissions) {
                map.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    private fun markAuthorsLocation() {
        val zoom = 10f
        var auxiliarMarkerTitle = ""
        var auxiliarLatLng: LatLng? = null

        this.allClasses.forEach {
            auxiliarLatLng = LatLng(it.latitude.toDouble(), it.longitude.toDouble())
            auxiliarMarkerTitle = it.name
            map.addMarker(
                MarkerOptions().position(auxiliarLatLng!!).title(auxiliarMarkerTitle)
            )!!.tag = auxiliarMarkerTitle
        }
        if (this.allClasses.size > 0) {
            map.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    auxiliarLatLng!!, zoom
                )
            )
        }
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