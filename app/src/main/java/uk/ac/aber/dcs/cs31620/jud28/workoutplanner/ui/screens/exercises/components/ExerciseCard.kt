package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 *  Exercise card represents an exercise in the application.
 *  There are two variants: actionable card, and not actionable card - defined by the showAction parameter.
 *
 *  @author Julia Drozdz [jud28]
 */
@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    editAction: (Exercise) -> Unit = {},
    deleteAction: (Exercise) -> Unit = {},
    showAction: Boolean = false,
    imageWidth: Dp = 64.dp,
    imageHeight: Dp = 64.dp,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier.padding(8.dp)
        ) {
            Column {
                Image(
                    painter = painterResource(id = exercise.image.toInt()),
                    contentDescription = "Image of the [${exercise.name}] exercise",
                    modifier = Modifier
                        .width(imageWidth)
                        .height(imageHeight)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.FillHeight,
                )
            }

            Column(
                modifier = Modifier.weight(2F)
            ) {
                Text(
                    text = exercise.name,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "${exercise.numberOfSets} sets(s)",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "${exercise.numberOfRepetitions} repetition(s)",
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                if (exercise.dropSetEnabled) {
                    Text(
                        text = "${exercise.firstWeight} kg | ${exercise.secondWeight} kg | ${exercise.thirdWeight} kg",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                } else {
                    Text(
                        text = "${exercise.weightInKilos} kg",
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }

            }

            if (showAction) {
                Column(modifier = Modifier.padding(0.dp)) {
                    Row(modifier = Modifier.padding(0.dp)) {
                        IconButton(
                            modifier = Modifier.padding(0.dp),
                            onClick = { editAction(exercise) }) {
                            Icon(
                                imageVector = Icons.Filled.Create,
                                contentDescription = "Edit an exercise",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(0.dp),
                            )
                        }
                        IconButton(onClick = {
                            deleteConfirmationRequired = true
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "Delete an exercise",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(0.dp),
                            )

                            if (deleteConfirmationRequired) {
                                RemoveExerciseFromListDialog(
                                    name = exercise.name,
                                    onDeleteConfirm = {
                                        deleteAction(exercise)
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
    }
}

@Preview
@Composable
fun ExerciseCardPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            ExerciseCard(exercise = Exercise(0, "Dips", 3, 10, 10F, R.drawable.dips.toString()))
        }
    }
}

@Preview
@Composable
fun ExerciseCardDropSetPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            ExerciseCard(
                exercise = Exercise(
                    0,
                    "Dips",
                    3,
                    10,
                    10F,
                    R.drawable.dips.toString(),
                    dropSetEnabled = true
                )
            )
        }
    }
}


@Preview
@Composable
fun ExerciseCardWithActionsPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        Surface {
            ExerciseCard(
                showAction = true,
                exercise = Exercise(0, "Bicep curl", 3, 10, 10F, R.drawable.dips.toString())
            )
        }
    }
}