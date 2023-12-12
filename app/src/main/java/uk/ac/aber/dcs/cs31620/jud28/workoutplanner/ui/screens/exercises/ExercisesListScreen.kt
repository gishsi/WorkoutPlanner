package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.bicepCurl
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.exercises
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.removeExercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 * Screen for viewing all exercises
 *
 * @author Julia Drozdz
 */
@Composable
fun ExercisesListScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val exercisesList = exercises.values.toList()

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
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
            ) {
                LazyColumn {
                    items(exercisesList) { exercise ->
                        ExerciseCard(
                            modifier = Modifier.padding(4.dp),
                            exercise = exercise,
                            editAction = {
                                Log.d("EXE_LIST", "Editing an exercise [${it.name}]")

                                // todo: needs to be the id, not the name
                                navController.navigate(
                                    Screen.ExerciseEdit.route.replace(
                                        "{exercise_id}",
                                        exercise.name
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
                                Log.d("EXE_LIST", "Deleting an exercise [${it.name}]")

                                removeExercise(it.name)
                            },
                            showAction = true,
                        )
                    }
                }
                Button(onClick = {
                    navController.navigate(Screen.ExerciseAdd.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }) {
                    Text(text = "Add an exercise")
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                }
            }

        }
    }
}

@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    editAction: (Exercise) -> Unit = {},
    deleteAction: (Exercise) -> Unit = {},
    showAction: Boolean = false,
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {


        // todo: use constraint layout
        Row {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.picture),
                    contentDescription = "Image of the [${exercise.name}] exercise"
                )
            }
            Column {
                Text(text = exercise.name)
                Text(text = "${exercise.numberOfSets} x ${exercise.numberOfRepetitions}")
                Text(text = "${exercise.weightInKilos} kg")
            }

            if (showAction) {
                Column {
                    Row {
                        IconButton(onClick = { editAction(exercise) }) {
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
                                        deleteAction(exercise)
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

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        text = { Text("You are about to remove an exercise called Biceps curl. Are you sure you want to proceed?") },
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
fun ExerciseListScreenPreview() {
    val navHostController = rememberNavController()
    WorkoutPlannerTheme(dynamicColor = false) {
        ExercisesListScreen(navHostController)
    }

}

@Preview
@Composable
fun ExerciseCardPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        ExerciseCard(exercise = bicepCurl)
    }
}

@Preview
@Composable
fun DeleteConfirmationDialogPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        DeleteConfirmationDialog({}, {}, Modifier)
    }
}