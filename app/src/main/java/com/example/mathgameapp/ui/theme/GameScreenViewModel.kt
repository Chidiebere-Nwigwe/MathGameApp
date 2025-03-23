package com.example.mathgameapp.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mathgameapp.data.MAX_NO_OF_QUESTIONS
import com.example.mathgameapp.data.MathQuestion
import com.example.mathgameapp.data.MathsQuestionsData
import com.example.mathgameapp.data.SCORE_INCREASE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class GameScreenViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private val mathsQuestionsData = MathsQuestionsData()
    private var currentQuestions: List<MathQuestion> = listOf()
    private lateinit var currentQuestion: MathQuestion

    fun setNumberOfQuestions(count: Int) {
        currentQuestions = mathsQuestionsData.getQuestions(count) // Get the requested number of questions
        _uiState.update { currentState ->
            currentState.copy(totalQuestions = count)
        }
    }
    private var usedQuestions: MutableSet<String> = mutableSetOf()

    var userAnswer by mutableStateOf("")
        private set

    init {
        resetGame()
    }

    fun resetGame() {
        usedQuestions.clear()
        _uiState.value = GameUiState(currentQuestion = pickRandomQuestionAndShuffle())
    }


    fun pickRandomQuestionAndShuffle(): String {
        // Continue picking up a new random question until you get one that hasn't been used before
        currentQuestion = mathsQuestionsData.allQuestionsAndAnswers.random()

        if (usedQuestions.contains(currentQuestion.question)) {
            return pickRandomQuestionAndShuffle()
        } else {
            usedQuestions.add(currentQuestion.question)
            return currentQuestion.question // Return the question
        }
    }

    fun updateUserAnswer(answer: String){
        userAnswer = answer
    }

    fun checkUserAnswer() {
        if (userAnswer.equals(currentQuestion.answer, ignoreCase = true)) {
            // User's answer is correct, increase the score
            // and call updateGameState() to prepare the game for next round
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            // User's guess is wrong, show an error
            _uiState.update { currentState ->
                currentState.copy(isAnswerWrong = true)
            }
        }
        // Reset user guess
        updateUserAnswer("")
    }

    private fun updateGameState(updatedScore: Int) {
        if (usedQuestions.size == _uiState.value.totalQuestions){
            //Last round in the game, update isGameOver to true, don't pick a new word
            _uiState.update { currentState ->
                currentState.copy(
                    isAnswerWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            // Normal round in the game
            _uiState.update { currentState ->
                currentState.copy(
                    isAnswerWrong = false,
                    currentQuestion = pickRandomQuestionAndShuffle(),
                    currentQuestionCount = currentState.currentQuestionCount.inc(),
                    score = updatedScore
                )
            }
        }
    }

    fun skipQuestion() {
        updateGameState(_uiState.value.score)
        // Reset user guess
        updateUserAnswer("")
    }
}
