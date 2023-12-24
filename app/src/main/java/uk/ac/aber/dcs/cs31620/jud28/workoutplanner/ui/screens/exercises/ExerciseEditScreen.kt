package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components.ImageChoices
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

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
    exercise: Exercise,
) {
    val coroutineScope = rememberCoroutineScope()

    Log.d("EXE_LIST", "Edit screen for exercise id: [${exercise.id}]")


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
            ExerciseEditContent(navController, exercise) { updatedExercise ->
                exerciseViewModel.updateExercise(updatedExercise)
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
    onExerciseUpdate: (Exercise) -> Unit = {}
) {
    var exerciseName by remember { mutableStateOf(exercise.name) }
    var numOfSets by remember { mutableStateOf(exercise.numberOfSets.toString()) }
    var numOfRepetitions by remember { mutableStateOf(exercise.numberOfRepetitions.toString()) }
    var isDropset by remember { mutableStateOf(false) }
    var weight by remember { mutableFloatStateOf(exercise.weightInKilos) }
    var imageResource by remember { mutableStateOf(exercise.image) }

    // todo: dropset
    var weightFirst by remember { mutableFloatStateOf(0.0F) }
    var weightSecond by remember { mutableFloatStateOf(0.0F) }
    var weightThird by remember { mutableFloatStateOf(0.0F) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier.weight(2F)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Exercise name text field
                OutlinedTextField(
                    value = exerciseName,
                    onValueChange = {
                        val removedNewLines = it.replace(Regex("\n"), "")
                        exerciseName = removedNewLines.trim()
                    },
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxWidth(),
                )

                // Number of sets TextField
                OutlinedTextField(
                    value = numOfSets.toString(),
                    onValueChange = { numOfSets = it.filter { char -> char.isDigit() } },
                    label = { Text("Number of sets") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth(),
                )

                // Number of repetitions TextField
                OutlinedTextField(
                    value = numOfRepetitions.toString(),
                    onValueChange = { numOfRepetitions = it.filter { char -> char.isDigit() } },
                    label = { Text("Number of repetitions") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .fillMaxWidth(),
                )

                // Weight
                if (!isDropset) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = weight.toString(),
                        onValueChange = { weight = parseStringIntoFloat(it) },
                        label = { Text("Weight (in kg)") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    )
                }

                // Is dropset
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Checkbox(
                        checked = isDropset,
                        onCheckedChange = { isDropset = !isDropset },
                    )
                    Text("Dropset?")
                }

                if (isDropset) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = weightFirst.toString(),
                        onValueChange = {
                            // todo: try catch here
                            weightFirst = parseStringIntoFloat(it)
                        },
                        label = { Text("Weight (First)") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,)
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = weightSecond.toString(),
                        onValueChange = { weightSecond = parseStringIntoFloat(it) },
                        label = { Text("Weight (Second)") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,)
                    )
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = weightThird.toString(),
                        onValueChange = { weightThird = parseStringIntoFloat(it) },
                        label = { Text("Weight (Third)") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.secondary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
                            focusedLabelColor = MaterialTheme.colorScheme.secondary,
                            unfocusedLabelColor = MaterialTheme.colorScheme.secondary,)
                    )
                }

                // todo: functionality
                Text(text = "Choose an image")
                ImageChoices {
                    imageResource = it.toString()
                }

                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                        .weight(2F)
                )
            }


        }

        Row {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    // todo: Validation
                    onExerciseUpdate(
                        Exercise(
                            exercise.id,
                            exerciseName,
                            numOfSets.toInt(),
                            numOfRepetitions.toInt(),
                            exercise.weightInKilos,
                            imageResource,
                        )
                    )
                    navController.navigate(Screen.ExercisesList.route)
                }) {
                Text(text = "Edit")
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = "Edit",
                    modifier = Modifier
                        .width(16.dp)
                        .height(16.dp)
                )
            }
        }
    }
}


fun parseStringIntoFloat(input: String): Float {
    var converted: Float = 0.0F;

    try {
        converted = input.toFloat();
    } catch (e: Exception) {
        return 0.0F;
    }

    return converted;
}

@Preview
@Composable
fun ExerciseEditScreenPreview() {
    val navController = rememberNavController()

    WorkoutPlannerTheme {
        Surface {
            ExerciseEditContent(
                navController,
                Exercise(0, "Bicep curl", 3, 8, 12.5F, R.drawable.bicep_curl.toString())
            )
        }
    }
}