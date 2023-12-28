package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.components.NoWorkoutHomeScreenContentVariant
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.components.WorkoutHomeScreenContentVariant
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**
 * Home screen. User can see or add a workout for the day here.
 *
 * @author Julia Drozdz
 */
@Composable
fun HomeScreen(
    navController: NavHostController,
    workoutsViewModel: WorkoutViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val day = getCurrentDayOfWeek()

    val workoutForTheDay = workoutsViewModel.getWorkoutForDay(day).observeAsState(null).value
    val allWorkouts = workoutsViewModel.allData.observeAsState(listOf()).value

    ApplicationScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarLabel = stringResource(id = R.string.home_top_bar_label)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HomeScreenContent(
                workoutForTheDay, allWorkouts,
                onWorkoutAssign = {
                    workoutsViewModel.updateWorkout(it)
                },
            )
        }
    }
}

@Composable
fun getCurrentDayOfWeek(): DaysInWeek {
    val currentDayOfWeek = LocalDate.now().dayOfWeek
    val name = currentDayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()).toString()

    return DaysInWeek.valueOf(name)
}

@Composable
fun HomeScreenContent(
    workoutForTheDay: Workout?,
    allWorkouts: List<Workout>,
    onWorkoutAssign: (Workout) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = getCurrentDayOfWeek().toString(),
        )

        if (workoutForTheDay == null) {
            NoWorkoutHomeScreenContentVariant(getCurrentDayOfWeek(), allWorkouts, onWorkoutAssign)
        } else {
            WorkoutHomeScreenContentVariant(workoutForTheDay)
        }
    }
}

/*************** Previews ***************/

@Composable
@Preview
fun HomeScreenContentPreview() {
    WorkoutPlannerTheme {
        Surface {
            HomeScreenContent(
                Workout(0, "Chest", 120, listOf(), listOf(DaysInWeek.Monday)),
                listOf()
            ) {}
        }
    }
}

@Composable
@Preview
fun HomeScreenNoContentPreview() {
    WorkoutPlannerTheme {
        Surface {
            HomeScreenContent(
                null,
                listOf(
                    Workout(
                        0,
                        "Chest",
                        120,
                        listOf(Exercise(0, "Crunches", 3, 20, 0.0F, R.drawable.crunches.toString()))
                    )
                )
            ) {}
        }
    }
}