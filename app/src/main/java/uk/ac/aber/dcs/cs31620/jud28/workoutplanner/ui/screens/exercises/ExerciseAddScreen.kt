package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 * Screen used to add exercises.
 *
 * @author Julia Drozdz
 */
@Composable
fun ExerciseAddScreen(
    navController: NavHostController,
    exerciseViewModel: ExerciseViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    ApplicationScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarLabel = stringResource(id = R.string.exercises_list_top_bar_label)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ExerciseAddContent(navController) { exercise: Exercise ->
                exerciseViewModel.addExercise(exercise)
            }
        }
    }
}

/**
 * Add an exercise form. Drop set option enables three additional input fields that represent weight in kilograms for consequent sets of an exercise.
 */
@Composable
fun ExerciseAddContent(navController: NavHostController, onExerciseAdd: (Exercise) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var exerciseName by remember { mutableStateOf("") }
        var numOfSets by remember { mutableStateOf(0) }
        var numOfRepetitions by remember { mutableStateOf(0) }
        var isDropset by remember { mutableStateOf(false) }

        TextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            label = { Text("Exercise name") },
        )
        TextField(
            value = numOfSets.toString(),
            onValueChange = { numOfSets = parseStringIntoInt(it)},
            label = { Text("Number of sets") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )

        TextField(
            value = numOfRepetitions.toString(),
            onValueChange = { numOfRepetitions = parseStringIntoInt(it)},
            label = { Text("Number of repetitions") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )

        // todo: fix
        var weight by remember { mutableStateOf(0F) }

        if (!isDropset) {
            TextField(
                value = weight.toString(),
                onValueChange = { weight = parseStringIntoFloat(it) },
                label = { Text("Weight (in kg)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
        }

        Checkbox(checked = isDropset, onCheckedChange = { isDropset = !isDropset })

        if (isDropset) {
            var weightFirst by remember { mutableFloatStateOf(0.0F) }
            var weightSecond by remember { mutableFloatStateOf(0.0F) }
            var weightThird by remember { mutableFloatStateOf(0.0F) }

            TextField(
                value = weightFirst.toString(),
                onValueChange = { weightFirst = parseStringIntoFloat(it) },
                label = { Text("Weight (First)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
            TextField(
                value = weightSecond.toString(),
                onValueChange = { weightSecond = parseStringIntoFloat(it) },
                label = { Text("Weight (Second)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
            TextField(
                value = weightThird.toString(),
                onValueChange = { weightThird = parseStringIntoFloat(it) },
                label = { Text("Weight (Third)") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )
        }

        Button(onClick = {
            onExerciseAdd(Exercise(0, exerciseName, numOfSets, numOfRepetitions, weight))
            navController.navigate(Screen.ExercisesList.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Text(text = "Add")
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
}


@Preview
@Composable
fun ExerciseAddScreenPreview() {
    val navController = rememberNavController()

    WorkoutPlannerTheme {
        ExerciseAddScreen(navController)
    }
}