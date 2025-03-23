package com.example.mathgameapp.data

 val MAX_NO_OF_QUESTIONS = 10
const val SCORE_INCREASE = 20

data class MathQuestion(
    val question: String,
    val answer: String
)

class MathsQuestionsData {
    // A list of math questions and their answers
    var allQuestionsAndAnswers: List<MathQuestion> = listOf(
        MathQuestion("What is 5 + 3?", "8"),
        MathQuestion("What is 9 - 4?", "5"),
        MathQuestion("What is 12 * 3?", "36"),
        MathQuestion("What is 15 / 3?", "5"),
        MathQuestion("What is 7 + 6?", "13"),
        MathQuestion("What is 20 - 4?", "16"),
        MathQuestion("What is 3 * 9?", "27"),
        MathQuestion("What is 18 / 2?", "9"),
        MathQuestion("What is 10 + 11?", "21"),
        MathQuestion("What is 8 * 7?", "56")
    )

    // Function to get a subset of questions based on the requested number
    fun getQuestions(numberOfQuestions: Int): List<MathQuestion> {
        val maxQuestions = if (numberOfQuestions > allQuestionsAndAnswers.size) {
            allQuestionsAndAnswers.size
        } else {
            numberOfQuestions
        }
        return allQuestionsAndAnswers.shuffled().take(maxQuestions)
    }

}

