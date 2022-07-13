package dev.sukhrob.oxford_dictionary.domen.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Word(
    val headword: String,
    val transcription: String,
    val sound: String?,
    val senses: List<Sense>,
): Parcelable