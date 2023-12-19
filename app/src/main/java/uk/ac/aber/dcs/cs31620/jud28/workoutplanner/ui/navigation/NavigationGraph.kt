package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseAddScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseEditScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseViewModelFactory
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExercisesListScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.HomeScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.WeeklyScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutAddScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutEditScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutViewModelFactory
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutsScreen

/**
 * Build a navigation graph for the application
 *
 * @see Screen
 *
 * @author Julia Drozdz
 */
@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val exerciseViewModel: ExerciseViewModel = viewModel(
        factory = ExerciseViewModelFactory(context.applicationContext as Application)
    )

    val workoutsViewModel: WorkoutViewModel = viewModel(
        factory = WorkoutViewModelFactory(context.applicationContext as Application)
    )


    NavHost(
        navController = navController, startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(navController, exerciseViewModel) }
        composable(Screen.Weekly.route) { WeeklyScreen(navController) }
        composable(Screen.ExercisesList.route) {
            ExercisesListScreen(navController, exerciseViewModel)
        }
        composable(Screen.ExerciseAdd.route) {
            ExerciseAddScreen(
                navController, exerciseViewModel,
            )
        }
        composable(Screen.ExerciseEdit.route) { backStackEntry ->
            backStackEntry.arguments?.getString("exercise_id")
                ?.let { ExerciseEditScreen(navController, exerciseViewModel, it.toInt()) }
        }
        composable(Screen.Workouts.route) {
            WorkoutsScreen(navController = navController, workoutsViewModel)
        }
        composable(Screen.WorkoutAdd.route) {
            WorkoutAddScreen(navController = navController, workoutsViewModel)
        }
        composable(Screen.WorkoutEdit.route) { backStackEntry ->
            backStackEntry.arguments?.getString("workout_id")
                ?.let { WorkoutEditScreen(navController, workoutsViewModel, it.toInt()) }
        }
        composable(Screen.Weekly.route) { WeeklyScreen(navController) }
    }
}