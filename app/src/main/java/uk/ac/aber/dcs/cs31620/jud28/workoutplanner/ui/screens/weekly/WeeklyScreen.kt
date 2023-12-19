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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseCard
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 * Provides insight of the week of the workouts.
 *
 * @author Julia Drozdz
 */
@Composable
fun WeeklyScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

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
                modifier = Modifier.fillMaxWidth()
            ) {
                WeekEntry("Monday", "Chest")
                WeekEntry("Tuesday")
                WeekEntry("Tuesday")
                WeekEntry("Tuesday")
                WeekEntry("Tuesday")
            }

        }
    }
}

data class WorkoutInWeekly(val name: String)

@Composable
fun WorkoutEntry(
    name: String,
) {
    var workoutInfoCancelRequired by rememberSaveable { mutableStateOf(false) }
    var deleteWorkoutCancelRequired by rememberSaveable { mutableStateOf(false) }


    Row(
        modifier = Modifier
//            .shadow(elevation = 5.dp, spotColor = Color.Transparent) // todo: figure out a way to make a shadow bottom and right https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier#(androidx.compose.ui.Modifier).shadow(androidx.compose.ui.unit.Dp,androidx.compose.ui.graphics.Shape,kotlin.Boolean,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = name,
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
            WorkoutDetailDialog(onClose = { workoutInfoCancelRequired = false })
        }

        if (deleteWorkoutCancelRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    // todo: call into workouts database, get the workout by name and reassign the "AssignedToDay" to "NotAssigned"
                    deleteWorkoutCancelRequired = false
                },
                onDeleteCancel = { deleteWorkoutCancelRequired = false },
            )
        }
    }
}

@Composable
fun WeekEntry(
    weekDay: String,
    workoutName: String = "",
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = weekDay,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (workoutName.isEmpty()) {
            NoWorkoutEntryVariant()
        } else {
            WorkoutEntry(name = workoutName)
        }
    }

}

@Preview
@Composable
fun WeekEntryPreview() {
    WorkoutPlannerTheme {
        WeekEntry(weekDay = "Tuesday")
    }
}

@Composable
fun NoWorkoutEntryVariant() {
    var showAssignWorkoutDialog by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
//            .shadow(elevation = 5.dp, spotColor = Color.Transparent) // todo: figure out a way to make a shadow bottom and right https://developer.android.com/reference/kotlin/androidx/compose/ui/Modifier#(androidx.compose.ui.Modifier).shadow(androidx.compose.ui.unit.Dp,androidx.compose.ui.graphics.Shape,kotlin.Boolean,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 4.dp, horizontal = 16.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.no_workout_in_weekly),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )

        Button(onClick = { showAssignWorkoutDialog = true }) {
            Text(text = "Add a workout")
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add to weekly")
        }

        if (showAssignWorkoutDialog) {
            AssignWorkoutDialog(
                onClose = { showAssignWorkoutDialog = false },
            )
        }

    }
}

@Preview
@Composable
fun NoWorkoutEntryVariantPreview() {
    WorkoutPlannerTheme {
        NoWorkoutEntryVariant()
    }
}

@Composable
fun WorkoutDetailDialog(onClose: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Chest") },
        text = {
            Column {
                Text("1 hour 30 minutes")
                ExerciseCard(exercise = Exercise(0, "Squat", 3, 10, 60F, "S"))
                ExerciseCard(exercise = Exercise(0, "Squat", 3, 10, 60F, "S"))
                ExerciseCard(exercise = Exercise(0, "Squat", 3, 10, 60F, "S"))
            }
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onClose) {
                Text(text = stringResource(R.string.close))
            }
        },
        confirmButton = {})
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

@Composable
fun AssignWorkoutDialog(
    onClose: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text("Assign a workout") },
        text = {
            Row {
                Text("Chest")
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
private fun WorkoutDetailDialogPreview() {
    WorkoutDetailDialog(onClose = { /*TODO*/ })
}


@Preview
@Composable
private fun WeeklyScreenPreview() {
    WorkoutPlannerTheme {
        val navController = rememberNavController()
        WeeklyScreen(navController)
    }
}