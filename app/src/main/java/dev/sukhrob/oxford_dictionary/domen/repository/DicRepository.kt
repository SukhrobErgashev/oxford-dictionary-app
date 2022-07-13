package dev.sukhrob.oxford_dictionary.domen.repository

import dev.sukhrob.oxford_dictionary.domen.model.Resource
import dev.sukhrob.oxford_dictionary.domen.model.Word
import kotlinx.coroutines.flow.Flow

interface DicRepository {

    fun getWordFromOxford(str: String): Flow<Resource<Word>>

}