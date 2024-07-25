package com.example.a04_deber02

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.os.Build
import android.widget.Button
import android.widget.EditText

class CreateUpdateStudent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create_update_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.view_create_update_student)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = findViewById<EditText>(R.id.input_student_name)
        val description = findViewById<EditText>(R.id.input_student_description)
        val classId = findViewById<EditText>(R.id.input_student_class_id)

        val selectedStudent = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                intent.getParcelableExtra("selectedStudent", StudentEntity::class.java)
            }
            else -> {
                intent.getParcelableExtra<StudentEntity>("selectedStudent")
            }
        }

        val selectedClass = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                intent.getParcelableExtra("selectedClass", ClassEntity::class.java)
            }
            else -> {
                intent.getParcelableExtra<ClassEntity>("selectedClass")
            }
        }

        val create = intent.getBooleanExtra("create", true)

        selectedClass?.let { classEntity ->
            if (create) {
                classId.setText(classEntity.id.toString())

                // Create a student
                findViewById<Button>(R.id.btn_create_update_student).setOnClickListener {
                    DataBase.tables?.createStudent(
                        name.text.toString(),
                        description.text.toString(),
                        classId.text.toString().toInt()
                    )
                    goToActivity(StudentsList::class.java, classEntity)
                }
            } else {
                selectedStudent?.let { student ->
                    name.setText(student.nameStudent)
                    description.setText(student.description)
                    classId.setText(student.class_id.toString())

                    // Update a student
                    findViewById<Button>(R.id.btn_create_update_student).setOnClickListener {
                        DataBase.tables?.updateStudent(
                            student.id,
                            name.text.toString(),
                            description.text.toString(),
                            classId.text.toString().toInt()
                        )
                        goToActivity(StudentsList::class.java, classEntity)
                    }
                }
            }
        }
    }

    private fun goToActivity(activityClass: Class<*>, dataToPass: ClassEntity) {
        val intent = Intent(this, activityClass).apply {
            putExtra("selectedClass", dataToPass)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        finish()
    }
}