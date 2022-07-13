package dev.sukhrob.oxford_dictionary.domen.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sense(
    val def: String,
    val level: String?,
    val examples: List<String>,
    var isExpanded: Boolean = false
): Parcelable