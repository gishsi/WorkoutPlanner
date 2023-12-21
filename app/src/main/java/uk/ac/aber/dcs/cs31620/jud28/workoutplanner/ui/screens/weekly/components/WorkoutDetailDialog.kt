package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Info
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
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseCard

@Composable
fun WorkoutDetailDialog(
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
                Text("Monday")
            }
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "Chest",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(imageVector = Icons.Outlined.AccessTime, contentDescription = "")

                    Text(
                        text = "1 hour 30 minutes",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

                ExerciseCard(
                    exercise = Exercise(
                        0,
                        "Deadlift",
                        3,
                        10,
                        60F,
                        R.drawable.deadlift.toString()
                    ),
                    containerColor = Color.White,
                    contentColor = Color.Black,
                )
            }
        },
        modifier = modifier,
        textContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        iconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
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
    WorkoutDetailDialog(onClose = { })
}