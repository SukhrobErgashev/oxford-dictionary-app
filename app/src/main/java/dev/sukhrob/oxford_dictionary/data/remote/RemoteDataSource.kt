package dev.sukhrob.oxford_dictionary.data.remote

import dev.sukhrob.oxford_dictionary.domen.model.Sense
import dev.sukhrob.oxford_dictionary.domen.model.Word
import org.jsoup.Jsoup
import javax.inject.Inject

class RemoteDataSource @Inject constructor() {

    companion object {
        const val BASE_URL = "https://www.oxfordlearnersdictionaries.com/definition/english/"
    }

    fun getWordFromOxford(word: String): Word {
        val document = Jsoup.connect(BASE_URL + word).get()

        val headWord = document.select("h1.headword").text()
        val transcription = document.select("span.phonetics > div.phons_n_am > span.phon").text()
        val soundUrl =
            document.select("span.phonetics > div.phons_n_am > div.sound").attr("data-src-mp3")

        val multipleSensesPath = document.select("div.entry > ol.senses_multiple li.sense")
        val singleSensePath = document.select("div.entry > ol.sense_single li.sense")
        val senses = ArrayList<Sense>()

        singleSensePath.forEach { item ->
            val def = item.select("span.def").text().toString()
            val level = item.select("span.sensetop > div.symbols")
                .select("a").attr("href")

            val examples = ArrayList<String>()
            val examplesPath = item.select("ul.examples > li > span.x")
            examplesPath.forEach {
                examples.add(it.text().toString())
            }
            senses.add(Sense(def, level, examples))
        }

        multipleSensesPath.forEach { item ->
            val def = item.select("span.def").text().toString()
            val level = item.select("span.sensetop > div.symbols")
                .select("a").attr("href")

            val examples = ArrayList<String>()
            val examplesPath = item.select("ul.examples > li > span.x")
            examplesPath.forEach {
                examples.add(it.text().toString())
            }
            senses.add(Sense(def, level, examples))
        }
        return Word(headWord, transcription, soundUrl, senses)
    }
}