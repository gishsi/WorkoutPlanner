package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseCard
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components.photos
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

@Composable
fun WorkoutHomeScreenContentVariant(
    workout: Workout
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(text = "Focus for today")
                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
            Column(
                modifier = Modifier
            ) {
                Icon(imageVector = Icons.Outlined.WatchLater, contentDescription = stringResource(R.string.watchIcon))
                Text(
                    text = "${workout.durationInMinutes} min",
                    fontWeight = FontWeight.Bold,
                )
            }
        }

        workout.exercises.forEach {
            ExerciseCard(
                exercise = it,
                imageWidth = 96.dp,
                imageHeight = 96.dp,
            )
        }
    }
}

@Composable
@Preview
fun WorkoutHomeScreenContentVariantPreview() {
    WorkoutPlannerTheme {
        Surface {
            WorkoutHomeScreenContentVariant(Workout(0, "Chest", 120, listOf(), DaysInWeek.Monday))
        }
    }
}