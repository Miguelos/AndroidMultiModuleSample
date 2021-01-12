package me.miguelos.sample.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Beer(
    val id: Long = -1L,
    val name: String = "",
    val description: String? = "",
    val abv: Double? = null,
    val thumbnail: String? = ""
) : Parcelable
