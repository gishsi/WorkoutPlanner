package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
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
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components.ImageChoices
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components.photos
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
fun ExerciseAddContent(navController: NavHostController, onExerciseAdd: (Exercise) -> Unit = {}) {
    var exerciseName by remember { mutableStateOf("") }
    var numOfSets by remember { mutableStateOf("0") }
    var numOfRepetitions by remember { mutableStateOf("0") }
    var isDropset by remember { mutableStateOf(false) }
    var weight by remember { mutableFloatStateOf(0F) }
    var imageResource by remember { mutableStateOf(photos[0]) }

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
                    onValueChange = {
                        // If there are no values in the input it will return an empty string and throw an error.
                        // I found that this works well for Int, but parsing float using filter is much more involved.
                        // Therefore for floats I am using a simple parsing method.
                        numOfSets = it.filter { char -> char.isDigit() }
                    },
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
                        onCheckedChange = {
                            isDropset = !isDropset
                      },
                    )
                    Text("Dropset?")

                }

                if (isDropset) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(),
                        value = weightFirst.toString(),
                        onValueChange = {
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

                Text(text = "Choose an image")
                ImageChoices() {
                    imageResource = it
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
                    onExerciseAdd(
                        Exercise(
                            id = 0,
                            name = exerciseName,
                            numberOfSets = numOfSets.toInt(),
                            numberOfRepetitions = numOfRepetitions.toInt(),
                            weightInKilos = weight,
                            image = imageResource.toString(),
                            dropSetEnabled = isDropset,
                            firstWeight = weightFirst,
                            secondWeight = weightSecond,
                            thirdWeight = weightThird,
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
                Text(text = "Add an exercise")
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Preview
@Composable
fun ExerciseAddContentPreview() {
    val navController = rememberNavController()

    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            ExerciseAddContent(navController)
        }
    }
}