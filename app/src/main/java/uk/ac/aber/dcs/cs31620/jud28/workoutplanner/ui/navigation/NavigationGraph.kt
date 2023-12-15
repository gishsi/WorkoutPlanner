package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercisesList.ExercisesListScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.HomeScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.ItemEntryDestination
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.ItemEntryScreen
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
        startDestination = ItemEntryDestination.route
    ) {
        composable(route = ItemEntryDestination.route) {
            ItemEntryScreen()
        }
        composable(Screen.ExercisesList.route) { ExercisesListScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Weekly.route) { WeeklyScreen(navController) }
    }
}