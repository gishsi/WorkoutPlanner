package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseCard
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

@Composable
fun WorkoutCard(
    workout: Workout,
    editAction: (Workout) -> Unit = {},
    deleteAction: (Workout) -> Unit = {},
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }
    var isVerbose by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(8.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Header information
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Outlined.WatchLater,
                        contentDescription = "Clock icon",
                    )
                    Text(
                        text = "${workout.durationInMinutes.toString()} minutes",
                    )
                }
            }

            // Expand more
            IconButton(onClick = { isVerbose = !isVerbose }) {
                if (isVerbose) {
                    Icon(
                        imageVector = Icons.Default.ExpandLess,
                        contentDescription = "Expand less",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = "Expand more",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        if (isVerbose) {
            Column {
                workout.exercises.forEach {
                    ExerciseCard(
                        showAction = false,
                        exercise = it,
                        containerColor = Color.White,
                        contentColor = Color.Black,
                    )
                }
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(onClick = { editAction(workout) }) {
                    Icon(
                        imageVector = Icons.Filled.Create,
                        contentDescription = stringResource(R.string.editExercise)
                    )
                }
                IconButton(onClick = {
                    deleteConfirmationRequired = true
                }) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = stringResource(R.string.deleteExercise),
                        tint = MaterialTheme.colorScheme.error,
                    )

                    if (deleteConfirmationRequired) {
                        DeleteWorkoutConfirmationDialog(
                            name = workout.name,
                            onDeleteConfirm = {
                                deleteAction(workout)
                                deleteConfirmationRequired = false
                            },
                            onDeleteCancel = { deleteConfirmationRequired = false },
                        )
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun WorkoutCardPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            WorkoutCard(
                workout = Workout(
                    0, "Chest", 120, listOf(
                        Exercise(0, "Bicep curl", 0, 0, 0F),
                        Exercise(0, "Bicep curl", 0, 0, 0F),
                        Exercise(0, "Bicep curl", 0, 0, 0F)
                    )
                )
            )
        }
    }
}