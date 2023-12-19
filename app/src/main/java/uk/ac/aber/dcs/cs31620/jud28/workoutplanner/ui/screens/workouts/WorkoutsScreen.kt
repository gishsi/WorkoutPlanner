package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 *  Workouts list view. Users can view a list of alphabetically ordered workouts.
 *
 *  @author Julia Drozdz
 */
@Composable
fun WorkoutsScreen(
    navController: NavHostController,
    workoutsViewModel: WorkoutViewModel = viewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val workouts = workoutsViewModel.allData.observeAsState(listOf()).value

    ApplicationScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarLabel = stringResource(id = R.string.workouts_list_top_bar_label)
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
                WorkoutsScreenContent(navController, workouts, deleteAction = {
                    workoutsViewModel.deleteWorkout(it)
                })
            }
        }
    }
}

@Composable
fun WorkoutsScreenContent(
    navController: NavHostController,
    workouts: List<Workout>,
    deleteAction: (Workout) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(workouts) { workout ->
                WorkoutCard(
                    workout = workout,
                    editAction = {
                        Log.d("_WORKOUTS", "Editing a workout [${it.name}]")

                        // todo: needs to be the id, not the name
                        navController.navigate(
                            Screen.WorkoutEdit.route.replace(
                                "{workout_id}",
                                workout.id.toString()
                            )
                        ) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    deleteAction = {
                        Log.d("_WORKOUTS", "Deleting a workout [${it.name}]")

                        deleteAction(workout)
                    },
                    showAction = true,
                )
            }
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(Screen.WorkoutAdd.route) {
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


@Composable
fun WorkoutCard(

    workout: Workout,
    editAction: (Workout) -> Unit = {},
    deleteAction: (Workout) -> Unit = {},
    showAction: Boolean = false,
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(workout.name)
            Text(workout.durationInMinutes.toString())
            if (showAction) {
                Column {
                    Row {
                        IconButton(onClick = { editAction(workout) }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = "Edit an exercise todo: resource"
                            )
                        }
                        IconButton(onClick = {
                            deleteConfirmationRequired = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete an exercise todo: resource"
                            )

                            if (deleteConfirmationRequired) {
                                DeleteConfirmationDialog(
                                    onDeleteConfirm = {
                                        deleteAction(workout)
                                        deleteConfirmationRequired = false
                                    },
                                    onDeleteCancel = { deleteConfirmationRequired = false },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// todo: make reusable
@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        text = { Text("") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.remove))
            }
        })
}


@Preview
@Composable
fun WorkoutsScreenContentPreview() {
    val navController = rememberNavController()

    WorkoutPlannerTheme(dynamicColor = false) {
        WorkoutsScreenContent(
            navController,
            workouts = listOf(
                Workout(0, "Chest", 120, listOf()),
                Workout(0, "Upper body", 90, listOf())
            )
        )
    }
}

@Preview
@Composable
fun WorkoutCardPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        WorkoutCard(workout = Workout(0, "Chest", 120, listOf()))
    }
}

@Preview
@Composable
fun DeleteConfirmationDialogPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        DeleteConfirmationDialog({}, {}, Modifier)
    }
}