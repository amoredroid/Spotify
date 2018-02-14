package com.example.khent.spotify.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by khent on 2/4/2018.
 */
data class SongList(var Name:String="",var  Singer:String="", var Album: String="", var mSongPath: String=""): Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(Name)
        parcel.writeString(Singer)
        parcel.writeString(Album)
        parcel.writeString(mSongPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SongList> {
        override fun createFromParcel(parcel: Parcel): SongList {
            return SongList(parcel)
        }

        override fun newArray(size: Int): Array<SongList?> {
            return arrayOfNulls(size)
        }
    }
}