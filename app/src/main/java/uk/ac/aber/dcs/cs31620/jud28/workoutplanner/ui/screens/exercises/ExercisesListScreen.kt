package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components.ExerciseCard
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components.RemoveExerciseFromListDialog
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
    // https://stackoverflow.com/questions/53351465/sort-array-by-alphabet-using-kotlin
    val sortedExercises = exercisesList.sortedBy { it.name.uppercase() }

    val mapOfSorted = mutableMapOf<Char, MutableList<Exercise>>()

    for (exercise in sortedExercises) {
        try {
            val key = exercise.name.uppercase().first()

            if (mapOfSorted.containsKey(key)) {
                mapOfSorted[exercise.name.uppercase().first()]?.add(exercise)
            } else {
                mapOfSorted[exercise.name.uppercase().first()] = mutableListOf(exercise)
            }

        } catch (_: NoSuchElementException) {
        }
    }

    Column(
        modifier = Modifier
            .padding(32.dp)
            .fillMaxHeight()
    ) {
        LazyColumn(
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            mapOfSorted.forEach { (key, exercises) ->
                item {
                    Text(
                        text = key.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier.padding(4.dp)
                    )
                    for (exercise in exercises) {
                        ExerciseCard(
                            exercise = exercise,
                            editAction = {
                                Log.d("EXE_LIST", "Editing an exercise [${it.id}]")

                                val serializedExercise = Gson().toJson(it)

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
            Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.add))
        }
    }

}


@Preview
@Composable
fun ExerciseListScreenPreview() {
    val navHostController = rememberNavController()
    WorkoutPlannerTheme(dynamicColor = false) {
        ExercisesListContent(
            navHostController, listOf(
                Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()),
            )
        )
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