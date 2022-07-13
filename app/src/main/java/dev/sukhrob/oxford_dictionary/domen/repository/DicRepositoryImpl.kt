package dev.sukhrob.oxford_dictionary.domen.repository

import dev.sukhrob.oxford_dictionary.data.remote.RemoteDataSource
import dev.sukhrob.oxford_dictionary.domen.model.Resource
import dev.sukhrob.oxford_dictionary.domen.model.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DicRepositoryImpl @Inject constructor(
    private val remote: RemoteDataSource
): DicRepository {

    override fun getWordFromOxford(str: String) = flow<Resource<Word>> {
        emit(Resource.Loading())
        val word = remote.getWordFromOxford(str)
        emit(Resource.Success(word))

    }.catch {
        emit(Resource.Error("Something is wrong"))
    }.flowOn(Dispatchers.IO)

}