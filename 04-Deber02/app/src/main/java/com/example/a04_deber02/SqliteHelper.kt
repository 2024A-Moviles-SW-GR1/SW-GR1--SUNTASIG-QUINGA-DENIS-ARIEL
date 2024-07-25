package com.example.a04_deber02

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqliteHelper (
    context: Context? /* this */
) : SQLiteOpenHelper(context, "AndroidApp", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createClassTable = """
            CREATE TABLE CLASS(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name VARCHAR(100),
                semester INTEGER,
                group_class VARCHAR(50)
            );
        """.trimIndent()

        val createStudentTable = """
            CREATE TABLE STUDENT(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nameStudent VARCHAR(100),
                description VARCHAR(200),
                class_id INTEGER,
                FOREIGN KEY (class_id) REFERENCES CLASS(id) ON DELETE CASCADE
            );
        """.trimIndent()

        db?.execSQL(createClassTable)
        db?.execSQL(createStudentTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun getAllClasses(): ArrayList<ClassEntity> {
        val lectureDB = readableDatabase
        val queryScript = """
            SELECT * FROM CLASS
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(queryScript, emptyArray())
        val response = arrayListOf<ClassEntity>()

        if (queryResult.moveToFirst()) {
            do {
                response.add(
                    ClassEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getInt(2),
                        queryResult.getString(3)
                    )
                )
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun getStudentsByClass(class_id: Int): ArrayList<StudentEntity> {
        val lectureDB = readableDatabase
        val queryScript = """
            SELECT * FROM STUDENT WHERE class_id=?
        """.trimIndent()
        val queryResult = lectureDB.rawQuery(queryScript, arrayOf(class_id.toString()))
        val response = arrayListOf<StudentEntity>()

        if (queryResult.moveToFirst()) {
            do {
                response.add(
                    StudentEntity(
                        queryResult.getInt(0),
                        queryResult.getString(1),
                        queryResult.getString(2),
                        queryResult.getInt(3)
                    )
                )
            } while (queryResult.moveToNext())
        }
        queryResult.close()
        lectureDB.close()

        return response
    }

    fun createClass(
        name: String,
        semester: Int,
        group_class: String
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("name", name)
        valuesToStore.put("semester", semester)
        valuesToStore.put("group_class", group_class)

        val storeResult = writeDB.insert(
            "CLASS", // Table name
            null,
            valuesToStore // Values to insert
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun createStudent(
        nameStudent: String,
        description: String,
        class_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToStore = ContentValues()
        valuesToStore.put("nameStudent", nameStudent)
        valuesToStore.put("description", description)
        valuesToStore.put("class_id", class_id)

        val storeResult = writeDB.insert(
            "STUDENT", // Table name
            null,
            valuesToStore // Values to insert
        )
        writeDB.close()

        return storeResult.toInt() != -1
    }

    fun updateClass(
        id: Int,
        name: String,
        semester: Int,
        group_class: String
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues().apply {
            put("name", name)
            put("semester", semester)
            put("group_class", group_class)
        }

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "CLASS", // Table name
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun updateStudent(
        id: Int,
        nameStudent: String,
        description: String,
        class_id: Int
    ): Boolean {
        val writeDB = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("nameStudent", nameStudent)
        valuesToUpdate.put("description", description)
        valuesToUpdate.put("class_id", class_id)

        val parametersUpdateQuery = arrayOf(id.toString())
        val updateResult = writeDB.update(
            "STUDENT", // Table name
            valuesToUpdate,
            "id=?",
            parametersUpdateQuery
        )
        writeDB.close()

        return updateResult != -1
    }

    fun deleteClass(id: Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "CLASS",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }

    fun deleteStudent(id: Int): Boolean {
        val writeDB = writableDatabase
        val parametersDeleteQuery = arrayOf(id.toString())
        val deleteResult = writeDB.delete(
            "STUDENT",
            "id=?",
            parametersDeleteQuery
        )
        writeDB.close()

        return deleteResult != -1
    }
}