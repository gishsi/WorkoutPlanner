package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
 *  Screen for editing a workout
 *
 *  @author Julia Drozdz
 */
@Composable
fun WorkoutEditScreen(
    navController: NavHostController,
    workoutEditViewModel: WorkoutEditViewModel = viewModel(),
    id: String = "0",
) {
    val coroutineScope = rememberCoroutineScope()
    // workoutToEdit = viewModel.getWorkout(id)

    ApplicationScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarLabel = stringResource(id = R.string.edit_workout_top_bar_label)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
            ) {
               WorkoutEditScreenContent(navController)
            }
        }
    }
}

@Composable
fun WorkoutEditScreenContent(navController: NavHostController) {
    val workoutName by remember { mutableStateOf("Name") }
    val duration by remember { mutableIntStateOf(120) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = workoutName,
            onValueChange = { /* todo */ },
            label = { Text("Name") })
        OutlinedTextField(
            value = duration.toString(),
            onValueChange = { /* todo */ },
            label = { Text("Workout duration") }
        )


        Text(text = "Exercises added:")

        Text(text = "Nothing yet")
        Text(text = "Press “Add” to add more exercises")

        var addingExercisesCancelRequired by rememberSaveable { mutableStateOf(false) }


        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = { addingExercisesCancelRequired = true }) {
            Text(text = "Add")
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }

        if (addingExercisesCancelRequired) {
            AddExercisesDialog(
                onClose = { addingExercisesCancelRequired = false },
            )
        }


        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(Screen.Workouts.route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }) {
            Text(text = "Confirm")
            Icon(imageVector = Icons.Default.Create, contentDescription = "Edit workout")
        }
    }
}

@Preview
@Composable
fun WorkoutEditContentPreview() {
    val navController = rememberNavController()
    WorkoutPlannerTheme(dynamicColor = false) {
        WorkoutEditScreenContent(navController)
    }
}