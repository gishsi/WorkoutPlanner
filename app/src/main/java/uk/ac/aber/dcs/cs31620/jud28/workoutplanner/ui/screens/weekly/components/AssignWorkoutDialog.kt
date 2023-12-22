package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseCard
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

@Composable
fun AssignWorkoutDialog(
    weekName: DaysInWeek,
    workouts: List<Workout>,
    onClose: () -> Unit, modifier: Modifier = Modifier,
    onAddAction: (Workout) -> Unit = {},
) {
    var isVerbose by rememberSaveable { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = {},
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Choose what you will do on",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                )
                Text(
                    text = weekName.toString(),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
        },
        text = {
            LazyColumn() {
                items(workouts) { workout ->
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
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            // Header information
                            Column(
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = workout.name,
                                        style = MaterialTheme.typography.headlineSmall,
                                        fontWeight = FontWeight.Bold,
                                    )
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

                            // Add button
                            IconButton(
                                onClick = {
                                    val assignedWorkout = Workout(
                                        id = workout.id,
                                        name = workout.name,
                                        durationInMinutes = workout.durationInMinutes,
                                        assignedToWeek = weekName
                                    )

                                    onAddAction(assignedWorkout)
                                    onClose()
                                },
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primary),
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = stringResource(R.string.add),
                                    tint = Color.White,
                                )
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
                        }
                    }
                }
            }

        },
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        containerColor = Color.White,
        dismissButton = {
            TextButton(onClick = onClose) {
                Text(text = stringResource(R.string.close))
            }
        },
        confirmButton = {
        },
    )
}

@Preview
@Composable
fun AssignWorkoutDialogPreview() {
    WorkoutPlannerTheme {
        Surface {
            AssignWorkoutDialog(
                DaysInWeek.Monday,
                workouts = listOf(
                    Workout(
                        0,
                        "Chest",
                        120,
                        listOf(Exercise(0, "Crunches", 3, 20, 0.0F, R.drawable.crunches.toString()))
                    )
                ),
                onClose = {},
            )
        }
    }
}