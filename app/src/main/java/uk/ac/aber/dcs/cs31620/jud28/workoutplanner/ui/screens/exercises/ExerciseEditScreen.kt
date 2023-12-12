package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 * Screen used to edit exercises.
 *
 * @author Julia Drozdz
 */
@Composable
fun ExerciseEditScreen(
    navController: NavHostController,
    id: String = "0",
) {
    val coroutineScope = rememberCoroutineScope()

    ApplicationScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarLabel = stringResource(id = R.string.edit_exercise_top_bar_label)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ExerciseEditContent(navController, id)
        }
    }
}

/**
 * Edit an exercise form. Drop set option enables three additional input fields that represent weight in kilograms for consequent sets of an exercise.
 */
@Composable
fun ExerciseEditContent(navController: NavHostController, id: String) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val exerciseName by remember { mutableStateOf(id) }
        val numOfSets by remember { mutableIntStateOf(0) }
        val numOfRepetitions by remember { mutableIntStateOf(0)  }
        var isDropset by remember { mutableStateOf(false) }

        IconButton(onClick = {  navController.navigate(Screen.ExercisesList.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                inclusive = true
            }
            launchSingleTop = true
            restoreState = true
        } }) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close edit")
        }

        TextField(
            value = exerciseName,
            onValueChange = { /* todo */ },
            label = { Text("Exercise name") })
        TextField(
            value = numOfSets.toString(),
            onValueChange = { /* todo */ },
            label = { Text("Number of sets") })
        TextField(
            value = numOfRepetitions.toString(),
            onValueChange = { /* todo */ },
            label = { Text("Number of repetitions") })

        if (!isDropset) {
            val weight by remember { mutableFloatStateOf(0.0F) }

            TextField(
                value = weight.toString(),
                onValueChange = { /* todo */ },
                label = { Text("Weight") })
        }

        Checkbox(checked = isDropset, onCheckedChange = { isDropset = !isDropset })

        if (isDropset) {
            val weightFirst by remember { mutableFloatStateOf(0.0F) }
            val weightSecond by remember { mutableFloatStateOf(0.0F) }
            val weightThird by remember { mutableFloatStateOf(0.0F) }

            TextField(
                value = weightFirst.toString(),
                onValueChange = { /* todo */ },
                label = { Text("Weight (First)") })
            TextField(
                value = weightSecond.toString(),
                onValueChange = { /* todo */ },
                label = { Text("Weight (Second)") })
            TextField(
                value = weightThird.toString(),
                onValueChange = { /* todo */ },
                label = { Text("Weight (Third)") })
        }

        Button(onClick = {
            navController.navigate(Screen.ExercisesList.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }) {
            Text(text = "Edit")
            Icon(imageVector = Icons.Default.Create, contentDescription = "Edit")
        }
    }
}


@Preview
@Composable
fun ExerciseEditScreenPreview() {
    val navController = rememberNavController()

    WorkoutPlannerTheme {
        ExerciseEditScreen(navController)
    }
}