package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.bicepCurl
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercisesList.ExerciseCard
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 * Home screen. User can see or add a workout for the day here.
 *
 * @author Julia Drozdz
 */
@Composable
fun HomeScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()

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
fun HomeScreenContent() {
//    NoWorkoutHomeScreenContentVariant()
    WorkoutHomeScreenContentVariant()
}

// Variants
@Composable
fun NoWorkoutHomeScreenContentVariant() {
    Column {
        Text(text = "Tuesday")
        Text(text = "No workout for today")
        Text(text = "Check your calendar or add one")
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Add a workout")
            Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add a workout")
        }
    }
}

@Composable
fun WorkoutHomeScreenContentVariant() {
    Column {
        Row {
            Column {
                Text(text = "Tuesday")
                Text(text = "Focus for today")
                Text(text = "Chest")
            }
            Column {
                Icon(imageVector = Icons.Outlined.WatchLater, contentDescription = "Clock icon")
                Text(text = "1 h 40 min")
            }
        }
        ExerciseCard(
            modifier = Modifier.padding(4.dp),
            exercise = bicepCurl,
        )
        ExerciseCard(
            modifier = Modifier.padding(4.dp),
            exercise = bicepCurl,
        )
        ExerciseCard(
            modifier = Modifier.padding(4.dp),
            exercise = bicepCurl,
        )
    }
}

/*************** Previews ***************/
@Composable
@Preview
fun HomeScreenPreview() {
    val navController = rememberNavController()
    WorkoutPlannerTheme {
        HomeScreen(navController)
    }
}

@Composable
@Preview
fun HomeScreenContentPreview() {
    WorkoutPlannerTheme {
        HomeScreenContent()
    }
}

@Composable
@Preview
fun NoWorkoutHomeScreenContentVariantPreview() {
    WorkoutPlannerTheme {
        NoWorkoutHomeScreenContentVariant()
    }
}

@Composable
@Preview
fun WorkoutHomeScreenContentVariantPreview() {
    WorkoutPlannerTheme {
        WorkoutHomeScreenContentVariant()
    }
}