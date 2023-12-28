package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 *  Represents a single exercise entry in the Workout form
 */
@Composable
fun ExerciseInWorkoutFormCard(exercise: Exercise, onDelete: (Exercise) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(MaterialTheme.colorScheme.tertiaryContainer),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = exercise.name,
            modifier = Modifier.padding(8.dp),
            color = MaterialTheme.colorScheme.onTertiaryContainer,
        )
        IconButton(onClick = { onDelete(exercise) }) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = stringResource(R.string.remove),
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
            )
        }
    }
}

@Preview
@Composable
fun ExerciseInWorkoutFormCardPreview() {
    WorkoutPlannerTheme {
        Surface {
            ExerciseInWorkoutFormCard(
                exercise = Exercise(
                    0,
                    "Bicep curl",
                    3,
                    8,
                    50.0F,
                    R.drawable.bicep_curl.toString()
                ),
                onDelete = {},
            )
        }
    }
}