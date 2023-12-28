package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

@Composable
fun WeekEntry(
    weekDay: DaysInWeek,
    workouts: List<Workout>,
    allWorkouts: List<Workout>,
    onWorkoutAssign: (Workout) -> Unit,
    onWorkoutEntryDelete: (Workout) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = weekDay.toString(),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (workouts.isEmpty()) {
            NoWorkoutEntryVariant(allWorkouts, weekDay, onWorkoutAssign)
        } else {
            for (workout in workouts) {
                WorkoutEntry(
                    weekDay = weekDay,
                    workout = workout,
                    onWorkoutEntryDelete
                )
            }
        }
    }
}

@Preview
@Composable
fun WeekEntryPreview() {
    WorkoutPlannerTheme {
        WeekEntry(
            weekDay = DaysInWeek.Sunday,
            workouts = listOf(Workout(0, "Chest", 90)),
            onWorkoutAssign = {},
            allWorkouts = listOf(),
            onWorkoutEntryDelete = {},
        )
    }
}