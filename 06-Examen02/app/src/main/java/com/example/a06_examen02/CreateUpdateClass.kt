package com.example.a06_examen02

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.os.Build
import android.widget.Button
import android.widget.EditText


class CreateUpdateClass : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_update_class)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_create_update_class)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = findViewById<EditText>(R.id.input_class_name)
        val semester = findViewById<EditText>(R.id.input_class_semester)
        val groupClass = findViewById<EditText>(R.id.input_class_group)
        val latitude = findViewById<EditText>(R.id.latitude)
        val longitude = findViewById<EditText>(R.id.longitude)

        val selectedClass = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                intent.getParcelableExtra("selectedClass", ClassEntity::class.java)
            }
            else -> {
                intent.getParcelableExtra<ClassEntity>("selectedClass")
            }
        }

        val btnCreateUpdateClass = findViewById<Button>(R.id.btn_create_update_class)
        selectedClass?.let { classEntity ->
            name.setText(classEntity.name)
            semester.setText(classEntity.semester.toString())
            groupClass.setText(classEntity.groupClass)
            latitude.setText(classEntity.latitude)
            longitude.setText(classEntity.longitude)

            btnCreateUpdateClass.setOnClickListener {
                DataBase.tables?.updateClass(
                    classEntity.id,
                    name.text.toString(),
                    semester.text.toString().toInt(),
                    groupClass.text.toString(),
                    latitude.text.toString(),
                    longitude.text.toString()
                )
                goToActivity(MainActivity::class.java)
            }
        } ?: run {
            btnCreateUpdateClass.setOnClickListener {
                DataBase.tables?.createClass(
                    name.text.toString(),
                    semester.text.toString().toInt(),
                    groupClass.text.toString(),
                    latitude.text.toString(),
                    longitude.text.toString()
                )
                goToActivity(MainActivity::class.java)
            }
        }
    }

    private fun goToActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}