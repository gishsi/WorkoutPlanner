package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout
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
        navController = navController,
        startDestination = Screen.Home.route,
    ) {
        // Home
        composable(Screen.Home.route) { HomeScreen(navController, workoutsViewModel) }

        // Weekly
        composable(Screen.Weekly.route) { WeeklyScreen(navController) }

        // Exercises
        composable(Screen.ExercisesList.route) {
            ExercisesListScreen(navController, exerciseViewModel)
        }
        composable(Screen.ExerciseAdd.route) {
            ExerciseAddScreen(
                navController, exerciseViewModel,
            )
        }
        composable(
            Screen.ExerciseEdit.route,
            arguments = listOf(navArgument("exercise") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("exercise")
                ?.let {
                    val exercise = Gson().fromJson(it, Exercise::class.java)
                    ExerciseEditScreen(navController, exerciseViewModel, exercise)
                }
        }

        // Workouts
        composable(Screen.Workouts.route) {
            WorkoutsScreen(navController = navController, workoutsViewModel)
        }
        composable(Screen.WorkoutAdd.route) {
            WorkoutAddScreen(navController = navController, workoutsViewModel, exerciseViewModel)
        }
        composable(
            Screen.WorkoutEdit.route,
            arguments = listOf(navArgument("workout") { type = NavType.StringType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("workout")
                ?.let {
                    val workout = Gson().fromJson(it, Workout::class.java)
                    WorkoutEditScreen(navController, workoutsViewModel, exerciseViewModel, workout)
                }
        }
    }
}