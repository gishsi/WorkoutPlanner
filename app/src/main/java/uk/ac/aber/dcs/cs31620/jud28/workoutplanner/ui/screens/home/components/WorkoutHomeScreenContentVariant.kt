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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseCard
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components.photos
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

@Composable
fun WorkoutHomeScreenContentVariant() {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
            ) {
                Text(text = "Focus for today")
                Text(
                    text = "Chest",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
            Column(
                modifier = Modifier
            ) {
                Icon(imageVector = Icons.Outlined.WatchLater, contentDescription = "Clock icon")
                Text(text = "1 h 40 min")
            }
        }
        ExerciseCard(
            exercise = Exercise(0, "Bicep curl", 3, 10, 10F, photos[0].toString()),
            imageWidth = 96.dp,
            imageHeight = 96.dp,
        )

        ExerciseCard(
            exercise = Exercise(0, "Bicep curl", 3, 10, 10F, photos[0].toString()),
            imageWidth = 96.dp,
            imageHeight = 96.dp,
        )

        ExerciseCard(
            exercise = Exercise(0, "Bicep curl", 3, 10, 10F, photos[0].toString()),
            imageWidth = 96.dp,
            imageHeight = 96.dp,
        )
    }
}

@Composable
@Preview
fun WorkoutHomeScreenContentVariantPreview() {
    WorkoutPlannerTheme {
        Surface {
            WorkoutHomeScreenContentVariant()
        }
    }
}