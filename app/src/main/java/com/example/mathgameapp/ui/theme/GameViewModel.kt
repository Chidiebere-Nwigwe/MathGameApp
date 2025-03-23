package com.example.mathgameapp.ui.theme

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _questionCount = mutableStateOf("5")
    val questionCount: State<String> = _questionCount

    private val _isNotNumber = mutableStateOf(false)
    val isNotNumber: State<Boolean> = _isNotNumber

    private val _isGameStarted = mutableStateOf(false)
    val isGameStarted: State<Boolean> = _isGameStarted




    // For the purpose of updating the UI and handling navigation
    fun updateQuestionCount(count: String) {
        _questionCount.value = count
        _isNotNumber.value = false // Reset error flag
    }

    fun checkUserNoOfQuestions() {
        val count = questionCount.value.toIntOrNull()

        if (count != null && count in 1..10) {
            // Input is valid, update game state and indicate that the game can start
            _isGameStarted.value = true
            _isNotNumber.value = false // Reset error flag
        } else {
            // Invalid input, show an error flag
            _isNotNumber.value = true
            _isGameStarted.value = false
        }
    }
}
