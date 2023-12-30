package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components.ExerciseCard

/**
 *  Details about a workout are shown after clicking the info icon on a workout entry.
 *
 *  @see WorkoutEntry
 *  @author Julia Drozdz [jud28]
 */
@Composable
fun WorkoutDetailDialog(
    weekDay: DaysInWeek,
    workout: Workout,
    onClose: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AlertDialog(
        onDismissRequest = { },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(weekDay.toString())
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "${workout.durationInMinutes} min")
                    Icon(
                        imageVector = Icons.Outlined.WatchLater,
                        contentDescription = stringResource(R.string.clock)
                    )
                }

                workout.exercises.forEach {
                    ExerciseCard(
                        exercise = it,
//                        imageWidth = 96.dp,
//                        imageHeight = 96.dp,
                    )
                }
            }
        },
        modifier = modifier,
        containerColor = Color.White,
        icon = { Icons.Outlined.Info },
        dismissButton = {
            TextButton(onClick = onClose) {
                Text(text = stringResource(R.string.close))
            }
        },
        confirmButton = {})
}

@Preview
@Composable
private fun WorkoutDetailDialogPreview() {
    WorkoutDetailDialog(
        DaysInWeek.Monday,
        Workout(
            0,
            "Chest",
            120,
            listOf(Exercise(0, "Crunches", 3, 20, 0.0F, R.drawable.crunches.toString()))
        ),
        onClose = { })
}