package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
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
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.md_theme_dark_background

/**
 *  Screen for editing a workout
 *
 *  @author Julia Drozdz
 */
@Composable
fun WorkoutEditScreen(
    navController: NavHostController,
    workoutsViewModel: WorkoutViewModel = viewModel(),
    exerciseViewModel: ExerciseViewModel = viewModel(),
    workout: Workout
) {
    val coroutineScope = rememberCoroutineScope()

    val exercisesList = exerciseViewModel.allData.observeAsState(listOf()).value


    ApplicationScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarLabel = stringResource(id = R.string.edit_workout_top_bar_label)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
            ) {
                WorkoutEditScreenContent(navController, workout, { updatedWorkout ->
                    workoutsViewModel.updateWorkout(updatedWorkout)
                }, exercisesList)

            }
        }
    }

}

@Composable
fun WorkoutEditScreenContent(
    navController: NavHostController,
    workout: Workout,
    onWorkoutUpdate: (Workout) -> Unit = {},
    allExercises: List<Exercise> = listOf(),
) {
    var workoutName by remember { mutableStateOf(workout.name) }
    var duration by remember { mutableStateOf(workout.durationInMinutes.toString()) }

    val exercises = remember {
        mutableStateListOf<Exercise>().apply { addAll(workout.exercises) }
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
                    label = { Text("Workout duration") },
                    modifier = Modifier
                        .fillMaxSize(),
                )


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
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    onClick = {
                        addingExercisesCancelRequired = true
                    }) {
                    Text(
                        text = "Add",
                        fontWeight = FontWeight.Bold
                    )
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }

                if (addingExercisesCancelRequired) {
                    AddExercisesDialog(
                        onClose = { addingExercisesCancelRequired = false },
                        onExerciseAdd = { exercises.add(it) },
                        exercises = allExercises,
                    )
                }
            }

        }

        Row {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onWorkoutUpdate(
                        Workout(
                            workout.id,
                            workoutName,
                            duration.toInt(),
                            exercises,
                            workout.assignedToWeek
                        )
                    )

                    navController.navigate(Screen.Workouts.route)
                }) {
                Text(text = "Confirm")
                Icon(imageVector = Icons.Default.Create, contentDescription = "Edit workout")
            }
        }

    }
}

@Preview
@Composable
fun WorkoutEditContentPreview() {
    val navController = rememberNavController()
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            WorkoutEditScreenContent(navController, Workout(0, "Chest", 120))
        }
    }
}

@Preview
@Composable
fun WorkoutEditWithExercisesContentPreview() {
    val navController = rememberNavController()
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            WorkoutEditScreenContent(
                navController,
                Workout(
                    0,
                    "Chest",
                    exercises = listOf(
                        Exercise(
                            0,
                            "Dips",
                            3,
                            8,
                            0.0F,
                            R.drawable.dips.toString()
                        ),
                        Exercise(
                            0,
                            "Bench press",
                            3,
                            8,
                            70.0F,
                            R.drawable.bench_press.toString()
                        ),
                    )
                )

            )
        }
    }
}