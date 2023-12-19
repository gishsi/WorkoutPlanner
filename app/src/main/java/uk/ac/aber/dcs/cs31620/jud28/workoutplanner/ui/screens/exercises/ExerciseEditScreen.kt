package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme
import java.lang.Exception

/**
 * Screen used to edit exercises.
 *
 * @author Julia Drozdz
 */
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ExerciseEditScreen(
    navController: NavHostController,
    exerciseViewModel: ExerciseViewModel = viewModel(),
    id: Int = 0,
) {
    val coroutineScope = rememberCoroutineScope()

    val exerciseFromViewModel = exerciseViewModel.getExercise(id).observeAsState()

    exerciseFromViewModel.let { exercise ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ApplicationScaffold(
                navController = navController,
                coroutineScope = coroutineScope,
                topBarLabel = stringResource(id = R.string.edit_exercise_top_bar_label)
            ) { innerPadding ->
                Surface(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    exercise.value?.let {
                        ExerciseEditContent(navController, it) { exercise ->
                            exerciseViewModel.updateExercise(exercise)
                        }
                    }
                }
            }
        }
    }
}

/**
 * Edit an exercise form. Drop set option enables three additional input fields that represent weight in kilograms for consequent sets of an exercise.
 */
@Composable
fun ExerciseEditContent(
    navController: NavHostController,
    exercise: Exercise,
    onExerciseUpdate: (Exercise) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var exerciseName by remember { mutableStateOf(exercise.name) }
        var numOfSets by remember { mutableIntStateOf(exercise.numberOfSets) }
        var numOfRepetitions by remember { mutableIntStateOf(exercise.numberOfRepetitions) }
        // todo: should come from the model
        var isDropset by remember { mutableStateOf(false) }

        IconButton(onClick = {
            navController.navigate(Screen.ExercisesList.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close edit")
        }

        TextField(
            value = exerciseName,
            onValueChange = { exerciseName = it },
            label = { Text("Exercise name") })
        TextField(
            value = numOfSets.toString(),
            onValueChange = { numOfSets = parseStringIntoInt(it) },
            label = { Text(text = "Number of sets") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
        TextField(
            value = numOfRepetitions.toString(),
            onValueChange = { numOfRepetitions = parseStringIntoInt(it) },
            label = { Text("Number of repetitions") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )

        // todo: fix (around 0)
        if (!isDropset) {
            var weight by remember { mutableFloatStateOf(0.0F) }

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
            onExerciseUpdate(
                Exercise(
                    exercise.id,
                    exerciseName,
                    numOfSets,
                    numOfRepetitions,
                    exercise.weightInKilos,
                    exercise.image
                )
            )
            navController.navigate(Screen.ExercisesList.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Text(text = "Edit")
            Icon(imageVector = Icons.Default.Create, contentDescription = "Edit")
        }
    }
}


fun parseStringIntoFloat(input: String) : Float {
    var converted : Float = 0.0F;

    try {
        converted = input.toFloat();
    } catch(e: Exception) {
        return 0.0F;
    }

    return converted;
}

fun parseStringIntoInt(input: String) : Int {
    var converted : Int = 0;

    try {
        converted = input.toInt();
    } catch(e: Exception) {
        return 0;
    }

    return converted;
}

@Preview
@Composable
fun ExerciseEditScreenPreview() {
    val navController = rememberNavController()

    WorkoutPlannerTheme {
        ExerciseEditScreen(navController)
    }
}