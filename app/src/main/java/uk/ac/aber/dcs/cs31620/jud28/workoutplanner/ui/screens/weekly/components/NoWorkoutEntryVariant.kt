package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 *  Shown if there is no workout assigned to the given day
 *
 *  @author Julia Drozdz [jud28]
 */
@Composable
fun NoWorkoutEntryVariant(
    allWorkouts: List<Workout>,
    weekDay: DaysInWeek,
    onWorkoutAssign: (Workout) -> Unit
) {
    var showAssignWorkoutDialog by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .fillMaxWidth(),
    ) {
        Text(
            text = stringResource(R.string.no_workout_in_weekly),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontStyle = FontStyle.Italic,
        )

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { showAssignWorkoutDialog = true }) {
            Text(text = "Add a workout")
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add to weekly")
        }

        if (showAssignWorkoutDialog) {
            AssignWorkoutDialog(
                weekDay,
                workouts = allWorkouts,
                onClose = { showAssignWorkoutDialog = false },
                onAddAction = onWorkoutAssign,
            )
        }

    }
}

@Preview
@Composable
fun NoWorkoutEntryVariantPreview() {
    WorkoutPlannerTheme {
        NoWorkoutEntryVariant(listOf(), DaysInWeek.Monday, onWorkoutAssign = {})
    }
}