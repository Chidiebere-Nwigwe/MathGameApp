package com.example.mathgameapp.ui.theme

import com.example.mathgameapp.data.MathQuestion

data class GameUiState (
    val currentQuestion: String = "",
    val currentQuestionCount: Int = 1,
    val totalQuestions: Int = 10,
    val score: Int = 0,
    val isAnswerWrong: Boolean = false,
    val isGameOver: Boolean = false,
    val currentQuestions: List<MathQuestion> = emptyList()
)


