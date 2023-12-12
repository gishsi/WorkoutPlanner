package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseAddScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseEditScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExercisesListScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.HomeScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.WeeklyScreen

/**
 * Build a navigation graph for the application
 *
 * @see Screen
 *
 * @author Julia Drozdz
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Weekly.route) { WeeklyScreen(navController) }
        composable(Screen.ExercisesList.route) { ExercisesListScreen(navController) }
        composable(Screen.ExerciseAdd.route) { ExerciseAddScreen(navController) }
        composable(Screen.ExerciseEdit.route) { backStackEntry ->
            backStackEntry.arguments?.getString("exercise_id")
                ?.let { ExerciseEditScreen(navController, it) }
        }
    }
}