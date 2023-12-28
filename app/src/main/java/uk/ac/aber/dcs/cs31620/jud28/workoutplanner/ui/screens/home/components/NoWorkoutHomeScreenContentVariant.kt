package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components.AssignWorkoutDialog
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

// Variants
@Composable
fun NoWorkoutHomeScreenContentVariant(
    weekName: DaysInWeek,
    allWorkouts: List<Workout>,
    onWorkoutAssign: (Workout) -> Unit
) {
    var showAssignWorkoutDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No workout for today",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
        )
        Text(
            text = "Check your calendar or add one",
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        Image(
            painter = painterResource(R.drawable.undraw_working_out_re_nhkg),
            contentDescription = stringResource(id = R.string.vec_graphics_no_workout),
            modifier = Modifier.padding(horizontal = 64.dp, vertical = 0.dp)
        )

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = { showAssignWorkoutDialog = true },
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 32.dp, vertical = 16.dp),
        ) {
            Text(
                text = "Add a workout",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
        }

        if (showAssignWorkoutDialog) {
            AssignWorkoutDialog(
                weekName,
                workouts = allWorkouts,
                onClose = { showAssignWorkoutDialog = false },
                onAddAction = onWorkoutAssign
            )
        }
    }
}

@Composable
@Preview
fun NoWorkoutHomeScreenContentVariantPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        NoWorkoutHomeScreenContentVariant(
            DaysInWeek.Sunday,
            listOf(
                Workout(
                    0,
                    "Chest",
                    120,
                    listOf(Exercise(0, "Crunches", 3, 20, 0.0F, R.drawable.crunches.toString()))
                )
            )
        ) {}
    }
}