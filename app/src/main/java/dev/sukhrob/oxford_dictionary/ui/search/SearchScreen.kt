package dev.sukhrob.oxford_dictionary.ui.search

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.sukhrob.oxford_dictionary.databinding.SearchScreenBinding
import dev.sukhrob.oxford_dictionary.domen.model.Word
import dev.sukhrob.oxford_dictionary.presenter.adapter.SenseAdapter
import dev.sukhrob.oxford_dictionary.presenter.viewmodel.SearchViewModel
import dev.sukhrob.oxford_dictionary.ui.base.BaseFragment
import dev.sukhrob.oxford_dictionary.utils.enable
import dev.sukhrob.oxford_dictionary.utils.snackBar
import java.lang.Exception

@AndroidEntryPoint
class SearchScreen : BaseFragment<SearchScreenBinding>(SearchScreenBinding::inflate) {

    private val adapter: SenseAdapter by lazy { SenseAdapter() }
    private val viewModel: SearchViewModel by viewModels()
    private var mediaPlayer: MediaPlayer? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()
        setSearchBtnListener()
        //setSoundBtnListener()
    }

    private fun prepareMediaPlayer(str: String) {
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        try {
            mediaPlayer!!.setDataSource(str)
            mediaPlayer!!.prepare()
        } catch (exception: Exception) {
            Toast.makeText(requireContext(), "Something is wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setSoundBtnListener() {
        binding.btnSound.setOnClickListener {
            if (!mediaPlayer!!.isPlaying) {
                mediaPlayer!!.start()
            }
        }
    }

    private fun setSearchBtnListener() {
        binding.btnSearch.setOnClickListener {
            getWord()
        }
        binding.btnSearch.addTextChangedListener {
            if (it.isNullOrBlank()) {
                binding.btnSearch.enable(false)
            } else binding.btnSearch.enable(true)
        }
    }

    private fun getWord() {
        viewModel.getWordFromOxford(binding.etSearchView.text.toString())
    }

    private fun setupRecyclerView() {
        with(binding) {
            senseRecyclerview.layoutManager = LinearLayoutManager(requireContext())
            senseRecyclerview.adapter = this@SearchScreen.adapter
        }
    }

    private fun setupObservers() {
        viewModel.word.observe(viewLifecycleOwner, wordObserver)
        viewModel.loading.observe(viewLifecycleOwner, loadingObserver)
        viewModel.error.observe(viewLifecycleOwner, errorObserver)
    }

    private val wordObserver = Observer<Word> {
        adapter.submitList(it.senses)
        with(binding) {
            tvHeadword.text = it.headword
            tvTranscription.text = it.transcription
        }
//        Toast.makeText(requireContext(), it.sound, Toast.LENGTH_SHORT).show()
//        prepareMediaPlayer(it.sound!!)
    }

    private val loadingObserver = Observer<Boolean> {
        if (it) {
            showProgressBar()
        } else {
            hideProgressBar()
        }
    }

    private val errorObserver = Observer<String> {
        binding.btnSearch.snackBar(it, null)
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

}