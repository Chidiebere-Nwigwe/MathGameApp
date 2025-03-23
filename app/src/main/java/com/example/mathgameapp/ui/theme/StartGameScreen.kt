package com.example.mathgameapp.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun StartGameScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {

    val gameViewModel: GameViewModel = viewModel()

    val questionCount = gameViewModel.questionCount
    val isNotNumber by gameViewModel.isNotNumber
    val isGameStarted by gameViewModel.isGameStarted

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Math Game App",
            fontSize = 40.sp,
            modifier = Modifier.padding(40.dp)
        )
        Text(
            text = "How many Questions do you want?",
            modifier = Modifier.padding(20.dp)
        )
        Text(
            text = "Minimum Number of Questions is 1"
        )
        Text(
            text = "Maximum Number of Questions is 10"
        )
        OutlinedTextField(
            value = questionCount.value.toString(),
            onValueChange = {
                gameViewModel.updateQuestionCount(it)
            },
            label = {
                if (isNotNumber) {
                    Text("Wrong Number")
                } else {
                    Text("Number of Questions")
                    if (isGameStarted) {
                        // Navigate to the game screen if the game has started
                        navController.navigate("game_screen/${gameViewModel.questionCount.value.toInt()}")
                    }
                }
            },
            modifier = Modifier.padding(vertical = 8.dp),
            isError = isNotNumber
        )
        Button(
            onClick = {
                gameViewModel.checkUserNoOfQuestions()
                if (isGameStarted) {
                    // Navigate to the game screen if the game has started
                    navController.navigate("game_screen/${gameViewModel.questionCount.value.toInt()}")
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "Start Game"
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StartGameScreenPreview() {
    StartGameScreen(navController = rememberNavController())
}