package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// TODO 2 create view model class
class GameViewModel : ViewModel() {
// mutable data must be all private
    private val _score = MutableLiveData(0)
    private val _currentWordCount = MutableLiveData(0)
    private val _currentScrambledWord = MutableLiveData<String>()
//    Add backing property to all the UI data above, by calling get() method
//    to a val variable
    val currentScrambledWord: LiveData<String> get() = _currentScrambledWord
    val currentWordCount: LiveData<Int> get() = _currentWordCount
    val score: LiveData<Int> get() = _score

    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String


    private fun getNextWord(){
        currentWord = allWordsList.random() // get random word
        val temp = currentWord.toCharArray() // turn the word into list of char
        temp.shuffle()

        while(String(temp).equals(currentWord,false)){
            temp.shuffle() // make sure the array of char is shuffled
        }

        if(wordsList.contains(currentWord)){
            getNextWord()
        } else {
            _currentScrambledWord.value = String(temp)
            _currentWordCount.value =(_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }


    }

    fun nextWord(): Boolean{
        return if(_currentWordCount.value!! < MAX_NO_OF_WORDS){
            getNextWord()
            true
        } else false
    }

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    private fun increaseScore(){
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }
    fun isUserWordCorrect(playerWord: String): Boolean{
        if(playerWord.equals(currentWord,true)){
            increaseScore()
            return true
        }
        return false
    }
    /*
    * Re-initializes the game data to restart the game.
    */
    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

}