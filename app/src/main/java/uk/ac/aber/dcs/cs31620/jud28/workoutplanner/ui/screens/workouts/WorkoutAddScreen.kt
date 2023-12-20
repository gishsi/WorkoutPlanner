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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.parseStringIntoInt
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 *  Screen for adding a workout
 *
 *  @author Julia Drozdz
 */
@Composable
fun WorkoutAddScreen(
    navController: NavHostController,
    workoutsViewModel: WorkoutViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()

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
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
            ) {
                WorkoutAddScreenContent(navController) {
                    workoutsViewModel.addWorkout(it)
                }
            }
        }
    }
}

@Composable
fun WorkoutAddScreenContent(
    navController: NavHostController,
    onWorkoutAdd: (Workout) -> Unit = {}
) {
    var workoutName by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("0") }


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


                Text(text = "Exercises added:")

                Text(
                    text = "Nothing yet.\nPress “Add” to add more exercises",
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.secondary,
                )

                var addingExercisesCancelRequired by rememberSaveable { mutableStateOf(false) }

                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        addingExercisesCancelRequired = true
                    }) {
                    Text(text = "Add")
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }

                if (addingExercisesCancelRequired) {
                    AddExercisesDialog(
                        onClose = { addingExercisesCancelRequired = false },
                    )
                }
            }

        }

        Row {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onWorkoutAdd(Workout(0, workoutName, duration.toInt()))

                    navController.navigate(Screen.Workouts.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                Text(text = "Add a workout")
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    }
}

@Composable
fun AddExercisesDialog(
    onClose: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Add an exercise") },
        text = {
            Row {
                Text("Bicep curl")
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                }
            }
        },
        modifier = modifier,
        dismissButton = {
            IconButton(onClick = onClose) {
                Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
            }
        },
        confirmButton = {
        })
}

@Preview
@Composable
fun WorkoutAddContentPreview() {
    val navController = rememberNavController()
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            WorkoutAddScreenContent(navController)
        }
    }
}

@Preview
@Composable
fun AddExercisesDialogPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            AddExercisesDialog(onClose = {})
        }
    }
}