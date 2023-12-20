package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.components.NoWorkoutHomeScreenContentVariant
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.components.WorkoutHomeScreenContentVariant
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**
 * Home screen. User can see or add a workout for the day here.
 *
 * @author Julia Drozdz
 */
@Composable
fun HomeScreen(
    navController: NavHostController,
    exercisesViewModel: ExerciseViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val day = getCurrentDayOfWeek()

    // todo: get the guy from the view model

    ApplicationScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarLabel = stringResource(id = R.string.home_top_bar_label)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            HomeScreenContent()
        }
    }
}

@Composable
fun getCurrentDayOfWeek(): String {
    val currentDayOfWeek = LocalDate.now().dayOfWeek
    return currentDayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
}

@Composable
fun HomeScreenContent() {
    // todo: remove later
    var switchBetweenVariants  by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = getCurrentDayOfWeek(),
        )

        Checkbox(
            checked = switchBetweenVariants,
            onCheckedChange = { switchBetweenVariants = !switchBetweenVariants },
        )

        if(switchBetweenVariants) {
            NoWorkoutHomeScreenContentVariant()
        } else {
            WorkoutHomeScreenContentVariant()
        }
    }

}

/*************** Previews ***************/

@Composable
@Preview
fun HomeScreenContentPreview() {
    WorkoutPlannerTheme {
        Surface {
            HomeScreenContent()
        }
    }
}