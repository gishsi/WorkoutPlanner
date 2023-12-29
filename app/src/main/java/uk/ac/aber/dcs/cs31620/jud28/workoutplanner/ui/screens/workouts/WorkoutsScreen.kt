package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    // https://stackoverflow.com/questions/53351465/sort-array-by-alphabet-using-kotlin
    val sortedWorkouts = workouts.sortedBy { it.name.uppercase() }

    val mapOfSorted = mutableMapOf<Char, MutableList<Workout>>()

    for (workout in sortedWorkouts) {
        try {
            val key = workout.name.uppercase().first()

            if (mapOfSorted.containsKey(key)) {
                mapOfSorted[workout.name.uppercase().first()]?.add(workout)
            } else {
                mapOfSorted[workout.name.uppercase().first()] = mutableListOf(workout)
            }

        } catch (_: NoSuchElementException) {
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (workouts.isEmpty()) {
            Column {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "No workouts yet",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = "Click below to add a new workout",
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Image(
                        painter = painterResource(R.drawable.undraw_fitness_tracker_3033_1),
                        contentDescription = stringResource(id = R.string.vec_graphics_no_workouts),
                        modifier = Modifier.padding(horizontal = 64.dp, vertical = 0.dp)
                    )

                    Spacer(modifier = Modifier.height(64.dp))

                    Button(
                        onClick = {
                            navController.navigate(Screen.WorkoutAdd.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 32.dp, vertical = 16.dp),
                    ) {
                        Text(
                            text = "Add a workout",
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                mapOfSorted.forEach { (key, workouts) ->
                    item {
                        Text(
                            text = key.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            fontStyle = FontStyle.Italic,
                            modifier = Modifier.padding(4.dp)
                        )
                        for (workout in workouts) {
                            WorkoutCard(
                                workout = workout,
                                editAction = {
                                    Log.d("_WORKOUTS", "Editing a workout [${it.name}]")

                                    val serializedWorkout = Gson().toJson(workout)

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
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add)
                )
            }
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

