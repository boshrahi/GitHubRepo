package com.boshra.githubrepo.dataModel

import android.os.Parcel
import android.os.Parcelable

data class Owner(var login: String?="Not Available", var avatar_url: String?="Not Available") : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int {
        TODO("not implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented")
    }

    companion object CREATOR : Parcelable.Creator<Owner> {
        override fun createFromParcel(parcel: Parcel): Owner {
            return Owner(parcel)
        }

        override fun newArray(size: Int): Array<Owner?> {
            return arrayOfNulls(size)
        }
    }

}