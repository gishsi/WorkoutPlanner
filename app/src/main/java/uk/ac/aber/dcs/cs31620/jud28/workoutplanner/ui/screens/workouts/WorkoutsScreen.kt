package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.components.WorkoutCard
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
            WorkoutsScreenContent(navController, workouts, deleteAction = {
                workoutsViewModel.deleteWorkout(it)
            })

        }
    }
}

@Composable
fun WorkoutsScreenContent(
    navController: NavHostController,
    workouts: List<Workout>,
    deleteAction: (Workout) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(workouts) { workout ->
                WorkoutCard(
                    workout = workout,
                    editAction = {
                        Log.d("_WORKOUTS", "Editing a workout [${it.name}]")

                        val serializedWorkout = Gson().toJson(workout)

                        // todo: needs to be the id, not the name
                        navController.navigate(
                            Screen.WorkoutEdit.route.replace(
                                "{workout}",
                                serializedWorkout
                            )
                        )
                    },
                    deleteAction = {
                        Log.d("_WORKOUTS", "Deleting a workout [${it.name}]")

                        deleteAction(workout)
                    },
                )
            }
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
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


@Preview
@Composable
fun WorkoutsScreenContentPreview() {
    val navController = rememberNavController()

    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            WorkoutsScreenContent(
                navController,
                workouts = listOf(
                    Workout(0, "Chest", 120, listOf()),
                    Workout(0, "Upper body", 90, listOf())
                )
            )
        }
    }
}

