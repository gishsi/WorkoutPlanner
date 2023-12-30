package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.components.ExerciseInWorkoutFormCard
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 *  Screen for adding a workout
 *
 *  @author Julia Drozdz
 */
@Composable
fun WorkoutAddScreen(
    navController: NavHostController,
    workoutsViewModel: WorkoutViewModel = viewModel(),
    exerciseViewModel: ExerciseViewModel = viewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    val exercisesList = exerciseViewModel.allData.observeAsState(listOf()).value


    ApplicationScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarLabel = stringResource(id = R.string.add_workout_top_bar_label)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            WorkoutAddScreenContent(navController, {
                workoutsViewModel.addWorkout(it)
            }, exercisesList)
        }
    }
}

@Composable
fun WorkoutAddScreenContent(
    navController: NavHostController,
    onWorkoutAdd: (Workout) -> Unit = {},
    allExercises: List<Exercise>
) {
    var workoutName by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("0") }

    val exercises = remember {
        mutableStateListOf<Exercise>()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.weight(2F)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = workoutName,
                    onValueChange = {
                        val removedNewLines = it.replace(Regex("\n"), "")
                        workoutName = removedNewLines.trim()
                    },
                    label = { Text("Name") },
                    modifier = Modifier
                        .fillMaxSize(),
                )

                OutlinedTextField(
                    value = duration.toString(),
                    onValueChange = { duration = it.filter { char -> char.isDigit() } },
                    label = { Text("Workout duration (in minutes)") },
                    modifier = Modifier
                        .fillMaxSize(),
                )

                // Exercises
                Text(text = "Exercises added:")

                if (exercises.isEmpty()) {
                    Text(
                        text = "Nothing yet.\nPress “Add” to add more exercises",
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                } else {
                    for (exercise in exercises) {
                        ExerciseInWorkoutFormCard(exercise) {
                            exercises.remove(exercise)
                        }
                    }
                }

                var addingExercisesCancelRequired by rememberSaveable { mutableStateOf(false) }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    onClick = {
                        addingExercisesCancelRequired = true
                    }) {
                    Text(text = "Add")
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(R.string.add)
                    )
                }

                if (addingExercisesCancelRequired) {
                    AddExercisesDialog(
                        exercises = allExercises,
                        onClose = { addingExercisesCancelRequired = false },
                        onExerciseAdd = {
                            exercises.add(it)
                        }
                    )
                }
            }

        }

        Row {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onWorkoutAdd(Workout(0, workoutName, duration.toInt(), exercises))

                    navController.navigate(Screen.Workouts.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                Text(text = "Add a workout")
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    }
}

@Composable
fun AddExercisesDialog(
    onExerciseAdd: (Exercise) -> Unit,
    exercises: List<Exercise>,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Add an exercise") },
        text = {
            LazyColumn() {
                items(exercises) {
                    Button(
                        onClick = { onExerciseAdd(it) },
                        modifier = Modifier
                            .fillMaxWidth(),
                    ) {
                        Text(it.name)
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = stringResource(R.string.add)
                        )
                    }
                }
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onClose) {
                Text(text = stringResource(id = R.string.close))
            }
        },
        confirmButton = {
        })
}

@Preview
@Composable
fun WorkoutAddContentNoExercisesPreview() {
    val navController = rememberNavController()
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            WorkoutAddScreenContent(navController, allExercises = listOf())
        }
    }
}

@Preview
@Composable
fun AddExercisesDialogPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            AddExercisesDialog(
                exercises = listOf(
                    Exercise(
                        0,
                        "Dips",
                        3,
                        8,
                        0.0F,
                        R.drawable.dips.toString()
                    )
                ), onClose = {}, onExerciseAdd = {})
        }
    }
}