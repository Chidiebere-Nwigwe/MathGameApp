package com.example.mathgameapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mathgameapp.ui.theme.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.mathgameapp.ui.theme.GameScreen

import com.example.mathgameapp.ui.theme.MathGameAppTheme
import com.example.mathgameapp.ui.theme.StartGameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MathGameAppTheme {
                // Create a NavController for navigation
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Set up the NavHost
                    NavHost(
                        navController = navController,
                        startDestination = "start_game_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("start_game_screen") {
                            StartGameScreen(navController = navController)
                        }
                        composable("game_screen/{numberOfQuestions}") { backStackEntry ->
                            val numberOfQuestions = backStackEntry.arguments?.getString("numberOfQuestions")?.toIntOrNull() ?: 5 // Default value to 5 if not valid
                            GameScreen(navController = navController, numberOfQuestions = numberOfQuestions)
                        }

                    }
                }
            }
        }
    }
}

//@Composable
//fun MathGameApp(
//
//    modifier: Modifier = Modifier) {
//
//    val gameViewModel: GameViewModel = viewModel()
//
//    val questionCount = gameViewModel.questionCount
//    val isNotNumber = gameViewModel.isNotNumber.value
//
//   // val questionCount = remember{ mutableStateOf("5") }
//    Column(
//        modifier = Modifier.padding(16.dp)
//                            .fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Math Game App"
//        )
//        Text(
//            text = "How many Questions do you want?"
//        )
//        Text(
//            text = "Minimum Number of Questions is 1"
//        )
//        Text(
//            text = "Maximum Number of Questions is 10"
//        )
//        OutlinedTextField(
//            value = questionCount.value.toString(),
//            onValueChange = {
//                gameViewModel.updateQuestionCount(it)
//            },
//            label = {
//                if(isNotNumber){
//                    Text("Wrong Number")
//                }else{
//                    Text("Number of Questions")
//                }
//                },
//            modifier = Modifier.padding(vertical = 8.dp),
//            isError = isNotNumber
//        )
//        Button(
//            onClick = {
//                gameViewModel.checkUserNoOfQuestions()
//            },
//            modifier = Modifier.padding(top = 16.dp)
//        ) {
//            Text(
//                text = "Start Game"
//            )
//        }
//    }
//
//}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    MathGameAppTheme {
//        MathGameApp()
//    }
//}