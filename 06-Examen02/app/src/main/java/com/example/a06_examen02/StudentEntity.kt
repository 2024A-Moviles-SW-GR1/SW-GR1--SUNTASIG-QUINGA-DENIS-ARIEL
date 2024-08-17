package com.example.a06_examen02

import android.os.Parcel
import android.os.Parcelable

class StudentEntity(
    var id: Int,
    var nameStudent: String,
    var description: String,
    var class_id: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nameStudent)
        parcel.writeString(description)
        parcel.writeInt(class_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentEntity> {
        override fun createFromParcel(parcel: Parcel): StudentEntity {
            return StudentEntity(parcel)
        }

        override fun newArray(size: Int): Array<StudentEntity?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "$id - $nameStudent"
    }
}