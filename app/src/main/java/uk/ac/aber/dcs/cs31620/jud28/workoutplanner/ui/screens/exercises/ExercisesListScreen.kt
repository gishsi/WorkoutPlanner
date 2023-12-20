package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 * Screen for viewing all exercises
 *
 * @author Julia Drozdz
 */
@Composable
fun ExercisesListScreen(
    navController: NavHostController,
    exerciseViewModel: ExerciseViewModel = viewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    val exercisesList = exerciseViewModel.allData.observeAsState(listOf()).value


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
            ExercisesListContent(navController, exercisesList) {
                exerciseViewModel.deleteExercise(it)
            }
        }
    }
}

@Composable
fun ExercisesListContent(
    navController: NavHostController,
    exercisesList: List<Exercise>,
    onDelete: (Exercise) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxHeight()
    ) {
        LazyColumn(
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            items(exercisesList) { exercise ->
                ExerciseCard(
                    modifier = Modifier.padding(4.dp),
                    exercise = exercise,
                    editAction = {
                        Log.d("EXE_LIST", "Editing an exercise [${it.id}]")

                        val serializedExercise = Gson().toJson(it)

                        // todo: needs to be the id, not the name
                        navController.navigate(
                            Screen.ExerciseEdit.route.replace(
                                "{exercise}",
                                serializedExercise
                            )
                        )
                    },
                    deleteAction = {
                        Log.d("EXE_LIST", "Deleting an exercise [${it.name}]")
                        onDelete(exercise)
                    },
                    showAction = true,
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(8.dp)
                .weight(2F)
        )

        Button(
            modifier = Modifier.fillMaxWidth(), onClick = {
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


@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    editAction: (Exercise) -> Unit = {},
    deleteAction: (Exercise) -> Unit = {},
    showAction: Boolean = false,
    imageWidth: Dp = 64.dp,
    imageHeight: Dp = 64.dp,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = modifier
            .fillMaxWidth()
    ) {
        // todo: use constraint layout
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Image(
                    painter = painterResource(id = exercise.image.toInt()),
                    contentDescription = "Image of the [${exercise.name}] exercise",
                    modifier = Modifier
                        .width(imageWidth)
                        .height(imageHeight)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillHeight,
                )
            }

            Column(
                modifier = Modifier.weight(2F)
            ) {
                Text(
                    text = exercise.name,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "${exercise.numberOfSets} sets(s)",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "${exercise.numberOfRepetitions} repetition(s)",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "${exercise.weightInKilos} kg",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            if (showAction) {
                Column(modifier = Modifier.padding(0.dp)) {
                    Row(modifier = Modifier.padding(0.dp)) {
                        IconButton(
                            modifier = Modifier.padding(0.dp),
                            onClick = { editAction(exercise) }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = "Edit an exercise",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(0.dp),
                            )
                        }
                        IconButton(onClick = {
                            deleteConfirmationRequired = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete an exercise",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(0.dp),
                            )

                            if (deleteConfirmationRequired) {
                                RemoveExerciseFromListDialog(
                                    name = exercise.name,
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
private fun RemoveExerciseFromListDialog(
    name: String = "",
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val styledText = buildAnnotatedString {
        withStyle(
            style = MaterialTheme.typography.bodyMedium.toSpanStyle()
                .copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textDecoration = TextDecoration.Underline
                )
        ) {
            append(name)
        }
    }

    AlertDialog(onDismissRequest = { /* Do nothing */ },
        text = {
            Text(
                buildAnnotatedString {
                    append("You are about to remove an exercise called ")
                    append(styledText)
                    append(". Are you sure you want to proceed?")
                })
        },
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
        ExercisesListContent(
            navHostController, listOf(
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString())
            )
        )
    }
}

@Preview
@Composable
fun ExerciseCardPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            ExerciseCard(exercise = Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()))
        }
    }
}

@Preview
@Composable
fun ExerciseCardWithActionsPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            ExerciseCard(
                showAction = true,
                exercise = Exercise(0, "Bicep curl", 3, 10, 10F, R.drawable.dips.toString())
            )
        }
    }
}

@Preview
@Composable
fun DeleteConfirmationDialogPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            RemoveExerciseFromListDialog("Bicep curl", {}, {}, Modifier)
        }
    }
}