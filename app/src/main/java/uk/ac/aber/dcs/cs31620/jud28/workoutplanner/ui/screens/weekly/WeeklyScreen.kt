package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components.NoWorkoutEntryVariant
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components.WeekEntry
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 * Provides insight of the week of the workouts.
 *
 * @author Julia Drozdz
 */
@Composable
fun WeeklyScreen(
    navController: NavHostController,
    workoutViewModel: WorkoutViewModel = viewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    val allWorkouts = workoutViewModel.allData.observeAsState(listOf()).value
    val assignedWorkouts = workoutViewModel.getWorkoutsForEachDay().observeAsState(listOf()).value

    val workoutsMap = mapOf(
        DaysInWeek.Monday to assignedWorkouts.filter { workout ->
            workout.assignedTo.contains(
                DaysInWeek.Monday
            )
        },
        DaysInWeek.Tuesday to assignedWorkouts.filter { workout ->
            workout.assignedTo.contains(
                DaysInWeek.Tuesday
            )
        },
        DaysInWeek.Wednesday to assignedWorkouts.filter { workout ->
            workout.assignedTo.contains(
                DaysInWeek.Wednesday
            )
        },
        DaysInWeek.Thursday to assignedWorkouts.filter { workout ->
            workout.assignedTo.contains(
                DaysInWeek.Thursday
            )
        },
        DaysInWeek.Friday to assignedWorkouts.filter { workout ->
            workout.assignedTo.contains(
                DaysInWeek.Friday
            )
        },
        DaysInWeek.Saturday to assignedWorkouts.filter { workout ->
            workout.assignedTo.contains(
                DaysInWeek.Saturday
            )
        },
        DaysInWeek.Sunday to assignedWorkouts.filter { workout ->
            workout.assignedTo.contains(
                DaysInWeek.Sunday
            )
        },
    )

    ApplicationScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarLabel = stringResource(id = R.string.weekly_top_bar_label)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            ) {
                workoutsMap.forEach { entry ->
                    WeekEntry(
                        weekDay = entry.key,
                        workouts = entry.value,
                        allWorkouts,
                        onWorkoutAssign = {
                            workoutViewModel.updateWorkout(it)
                        },
                        onWorkoutEntryDelete = {
                            workoutViewModel.updateWorkout(it)
                        })
                }
            }
        }

    }
}





