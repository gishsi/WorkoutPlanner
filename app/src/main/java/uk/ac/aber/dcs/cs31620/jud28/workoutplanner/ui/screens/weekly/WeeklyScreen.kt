package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components.AssignWorkoutDialog
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components.WorkoutDetailDialog
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
    val  assignedWorkouts = workoutViewModel.getWorkoutsForEachDay().observeAsState(listOf()).value

    val workoutsMap = mapOf(
        DaysInWeek.Monday to assignedWorkouts.find { workout -> workout.assignedToWeek == DaysInWeek.Monday },
        DaysInWeek.Tuesday to assignedWorkouts.find { workout -> workout.assignedToWeek == DaysInWeek.Tuesday },
        DaysInWeek.Wednesday to assignedWorkouts.find { workout -> workout.assignedToWeek == DaysInWeek.Wednesday },
        DaysInWeek.Thursday to assignedWorkouts.find { workout -> workout.assignedToWeek == DaysInWeek.Thursday },
        DaysInWeek.Friday to assignedWorkouts.find { workout -> workout.assignedToWeek == DaysInWeek.Friday },
        DaysInWeek.Saturday to assignedWorkouts.find { workout -> workout.assignedToWeek == DaysInWeek.Saturday },
        DaysInWeek.Sunday to assignedWorkouts.find { workout -> workout.assignedToWeek == DaysInWeek.Sunday },
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
                workoutsMap.forEach {entry ->
                    WeekEntry(
                        weekDay = entry.key,
                        workout = entry.value,
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

@Composable
fun WorkoutEntry(
    workout: Workout,
    onWorkoutEntryDelete: (Workout) -> Unit,
) {
    var workoutInfoCancelRequired by rememberSaveable { mutableStateOf(false) }
    var deleteWorkoutCancelRequired by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = workout.name,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold,
        )

        Row() {
            IconButton(onClick = { workoutInfoCancelRequired = true }) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "Info",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }

            IconButton(onClick = { deleteWorkoutCancelRequired = true }) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }

        if (workoutInfoCancelRequired) {
            WorkoutDetailDialog(
                onClose = { workoutInfoCancelRequired = false }
            )
        }

        if (deleteWorkoutCancelRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    val unassignedWorkout = Workout(
                        id = workout.id,
                        name = workout.name,
                        durationInMinutes = workout.durationInMinutes,
                        assignedToWeek = DaysInWeek.NotAssigned
                    )

                    onWorkoutEntryDelete(unassignedWorkout)

                    deleteWorkoutCancelRequired = false
                },
                onDeleteCancel = { deleteWorkoutCancelRequired = false },
            )
        }
    }
}

@Composable
fun WeekEntry(
    weekDay: DaysInWeek,
    workout: Workout?,
    allWorkouts: List<Workout>,
    onWorkoutAssign: (Workout) -> Unit,
    onWorkoutEntryDelete: (Workout) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = weekDay.toString(),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (workout == null) {
            NoWorkoutEntryVariant(allWorkouts, weekDay, onWorkoutAssign)
        } else {
            WorkoutEntry(
                workout = workout,
                onWorkoutEntryDelete
            )
        }
    }

}

@Preview
@Composable
fun WeekEntryPreview() {
    WorkoutPlannerTheme {
        WeekEntry(
            weekDay = DaysInWeek.Sunday,
            workout = Workout(0, "Chest", 90, listOf(), DaysInWeek.NotAssigned),
            onWorkoutAssign = {},
            allWorkouts = listOf(),
            onWorkoutEntryDelete = {},
        )
    }
}

@Composable
fun NoWorkoutEntryVariant(
    allWorkouts: List<Workout>,
    weekDay: DaysInWeek,
    onWorkoutAssign: (Workout) -> Unit
) {
    var showAssignWorkoutDialog by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.no_workout_in_weekly),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { showAssignWorkoutDialog = true }) {
            Text(text = "Add a workout")
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add to weekly")
        }

        if (showAssignWorkoutDialog) {
            AssignWorkoutDialog(
                weekDay,
                workouts = allWorkouts,
                onClose = { showAssignWorkoutDialog = false },
                onAddAction = onWorkoutAssign,
            )
        }

    }
}

@Preview
@Composable
fun NoWorkoutEntryVariantPreview() {
    WorkoutPlannerTheme {
        NoWorkoutEntryVariant(listOf(), DaysInWeek.Monday, onWorkoutAssign = {})
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        text = { Text("This action will remove the workout from this day. It will not affect the workout. Proceed?") },
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