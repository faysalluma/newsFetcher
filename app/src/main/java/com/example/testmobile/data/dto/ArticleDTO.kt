package com.example.testmobile.data.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ArticleDTO(val title : String, val description : String ? = null, val url : String, val urlToImage : String ? = null, val publishedAt : String) : Parcelable
