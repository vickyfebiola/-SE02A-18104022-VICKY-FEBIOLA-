package com.vickyfebiola_18104022.praktikum10.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Quote(
    var id: Int = 0,
    var title: String? = null,
    var author: String? = null,
    var publisher: String? = null,
    var description: String? = null,
    var category: String? = null,
    var date: String? = null
) : Parcelable