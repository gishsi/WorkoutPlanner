package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 * Screen used to add exercises.
 *
 * @author Julia Drozdz
 */
@Composable
fun ExerciseAddScreen(
    navController: NavHostController,
    exerciseAddViewModel: ExerciseAddViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    ApplicationScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarLabel = stringResource(id = R.string.exercises_list_top_bar_label)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            ExerciseAddContent(navController)
        }
    }
}

/**
 * Add an exercise form. Drop set option enables three additional input fields that represent weight in kilograms for consequent sets of an exercise.
 */
@Composable
fun ExerciseAddContent(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val exerciseName by remember { mutableStateOf("Bicep curl") }
        val numOfSets by remember { mutableStateOf(1) }
        val numOfRepetitions by remember { mutableStateOf(1) }
        var isDropset by remember { mutableStateOf(false) }

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
            val weight by remember { mutableStateOf(1) }

            TextField(
                value = weight.toString(),
                onValueChange = { /* todo */ },
                label = { Text("Weight") })
        }

        Checkbox(checked = isDropset, onCheckedChange = { isDropset = !isDropset })

        if (isDropset) {
            val weightFirst by remember { mutableStateOf(1) }
            val weightSecond by remember { mutableStateOf(1) }
            val weightThird by remember { mutableStateOf(1) }

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
            Text(text = "Add")
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
}


@Preview
@Composable
fun ExerciseAddScreenPreview() {
    val navController = rememberNavController()

    WorkoutPlannerTheme {
        ExerciseAddScreen(navController)
    }
}