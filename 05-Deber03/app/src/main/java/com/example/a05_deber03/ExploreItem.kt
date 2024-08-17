package com.example.a05_deber03

import android.os.Parcel
import android.os.Parcelable

data class ExploreItem(
    val left: Int,
    val right: Int
) : Parcelable {

    private constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(left)
        parcel.writeInt(right)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<ExploreItem> {
        override fun createFromParcel(parcel: Parcel): ExploreItem {
            return ExploreItem(parcel)
        }

        override fun newArray(size: Int): Array<ExploreItem?> {
            return arrayOfNulls(size)
        }
    }
}