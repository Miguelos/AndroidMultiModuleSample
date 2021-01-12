package me.miguelos.sample.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Beer(
    val id: Long = -1L,
    val name: String = "",
    val description: String? = "",
    val abv: Double? = null,
    val thumbnail: String? = ""
) : Parcelable, Comparable<Beer> {

    override fun compareTo(other: Beer) = when {
        id < other.id -> {
            -1
        }
        id > other.id -> {
            1
        }
        else -> {
            0
        }
    }
}
