package com.example.mathgameapp.ui.theme

import android.app.Activity
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mathgameapp.R



@Composable
fun GameScreen(
    navController: NavController,
    gameViewModel: GameScreenViewModel = viewModel(),
    numberOfQuestions: Int
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)
    val gameUIState by gameViewModel.uiState.collectAsState()
    val questionsToDisplay = gameUIState.currentQuestions

    gameViewModel.setNumberOfQuestions(numberOfQuestions)
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.app_name),

            style = typography.titleLarge,
        )
        GameLayout(
            onUserAnswerChanged = {gameViewModel.updateUserAnswer(it)},
            userAnswer = gameViewModel.userAnswer,
            questionCount = gameUIState.currentQuestionCount,
            currentQuestion = gameUIState.currentQuestion,
            isAnswerWrong = gameUIState.isAnswerWrong,
            numberOfQuestions = numberOfQuestions,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(mediumPadding)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { gameViewModel.checkUserAnswer() }
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 16.sp
                )
            }

            OutlinedButton(
                onClick = { gameViewModel.skipQuestion() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    fontSize = 16.sp
                )
            }

            OutlinedButton(
                //onClick = {navController.popBackStack()},
                onClick = {
                    gameViewModel.resetGame()
                    navController.navigate("start_game_screen") },
                modifier = Modifier.fillMaxWidth()

            ) {
                Text(
                    text = stringResource(R.string.cancel),
                    fontSize = 16.sp
                    )
            }
        }

        GameStatus(score = gameUIState.score, modifier = Modifier.padding(20.dp))
        if(gameUIState.isGameOver){
            FinalScoreDialog(
                score = gameUIState.score,
                onPlayAgain = {gameViewModel.resetGame()}
            )
        }

    }
}

@Composable
fun GameStatus(score: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.score, score),
            style = typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun GameLayout(
    onUserAnswerChanged: (String)->Unit,
    userAnswer: String,
    questionCount: Int,
    currentQuestion: String,
    isAnswerWrong: Boolean,
    numberOfQuestions: Int,
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(mediumPadding)
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)
                    .background(colorScheme.surfaceTint)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
                    .align(alignment = Alignment.End),
               // text = stringResource(R.string.question_count, questionCount),
                text = "$questionCount / $numberOfQuestions",

                style = typography.titleMedium,
                color = colorScheme.onPrimary
            )
            Text(
                text = currentQuestion,
                style = typography.displayMedium
            )
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = typography.titleMedium
            )
            OutlinedTextField(
                value = userAnswer,
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,
                    unfocusedContainerColor = colorScheme.surface,
                    disabledContainerColor = colorScheme.surface,
                ),
                onValueChange =  onUserAnswerChanged ,
                label = {
                    if(isAnswerWrong){
                        Text(stringResource(R.string.wrong_answer))
                    } else{
                        Text(stringResource(R.string.enter_your_answer))
                    }

                },
                isError = isAnswerWrong,

//                keyboardOptions = KeyboardOptions.Default.copy(
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(
//                    onDone = { }
//                )
            )
        }
    }
}


/*
 * Creates and shows an AlertDialog with final score.
 */
@Composable
private fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Access the current context and safely cast it to a ComponentActivity
    val context = LocalContext.current
    val activity = context as? ComponentActivity // Safe cast to ComponentActivity

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back button.
        },
        title = { Text(text = stringResource(R.string.congratulations)) },
        text = { Text(text = stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    // Call finish() on the activity if it's not null
                    activity?.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MathGameAppTheme {
//        GameScreen()
//    }
//}