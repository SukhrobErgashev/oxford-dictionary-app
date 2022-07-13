package dev.sukhrob.oxford_dictionary.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.sukhrob.oxford_dictionary.domen.model.Resource
import dev.sukhrob.oxford_dictionary.domen.model.Word
import dev.sukhrob.oxford_dictionary.domen.repository.DicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: DicRepository
) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _word = MutableLiveData<Word>()
    val word: LiveData<Word> get() = _word

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun getWordFromOxford(str: String) {
        repository.getWordFromOxford(str).onEach {
            when (it) {
                is Resource.Loading -> {
                    _loading.postValue(true)
                }
                is Resource.Success -> {
                    _loading.postValue(false)
                    _word.postValue(it.data)
                }
                is Resource.Error -> {
                    _loading.postValue(false)
                    _error.postValue(it.message)
                }
            }
        }.launchIn(viewModelScope)
    }

}