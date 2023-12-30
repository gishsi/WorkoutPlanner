package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout

/**
 *  Displays the name of workout assigned to the given day
 *
 *  @author Julia Drozdz [jud28]
 */
@Composable
fun WorkoutEntry(
    weekDay: DaysInWeek,
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
                weekDay = weekDay,
                workout = workout,
                onClose = { workoutInfoCancelRequired = false }
            )
        }

        if (deleteWorkoutCancelRequired) {
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    val assignedTo = workout.assignedTo.toMutableSet()
                    assignedTo.remove(weekDay)
                    val unassignedWorkout = Workout(
                        id = workout.id,
                        name = workout.name,
                        durationInMinutes = workout.durationInMinutes,
                        exercises = workout.exercises,
                        assignedTo = assignedTo.toList()
                    )

                    onWorkoutEntryDelete(unassignedWorkout)

                    deleteWorkoutCancelRequired = false
                },
                onDeleteCancel = { deleteWorkoutCancelRequired = false },
            )
        }
    }
}