package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseAddScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseAddViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseEditScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseEditViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExercisesDeleteViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExercisesListScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExercisesListViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.HomeScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.HomeViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.WeeklyScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutAddScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutAddViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutDeleteViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutEditScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutEditViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutsScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutsViewModel

/**
 * Build a navigation graph for the application
 *
 * @see Screen
 *
 * @author Julia Drozdz
 */
@Composable
fun NavigationGraph(
    homeViewModel: HomeViewModel = viewModel(),
    exercisesListViewModel: ExercisesListViewModel = viewModel(),
    exercisesDeleteViewModel: ExercisesDeleteViewModel = viewModel(),
    exerciseAddViewModel: ExerciseAddViewModel = viewModel(),
    exerciseEditViewModel: ExerciseEditViewModel = viewModel(),
    workoutsViewModel: WorkoutsViewModel = viewModel(),
    workoutAddViewModel: WorkoutAddViewModel = viewModel(),
    workoutEditViewModel: WorkoutEditViewModel = viewModel(),
    workoutDeleteViewModel: WorkoutDeleteViewModel = viewModel(),
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(navController, homeViewModel) }
        composable(Screen.Weekly.route) { WeeklyScreen(navController) }
        composable(Screen.ExercisesList.route) {
            ExercisesListScreen(
                navController,
                exercisesListViewModel,
                exercisesDeleteViewModel
            )
        }
        composable(Screen.ExerciseAdd.route) {
            ExerciseAddScreen(
                navController,
                exerciseAddViewModel
            )
        }
        composable(Screen.ExerciseEdit.route) { backStackEntry ->
            backStackEntry.arguments?.getString("exercise_id")
                ?.let { ExerciseEditScreen(navController, exerciseEditViewModel, it) }
        }
        composable(Screen.Workouts.route) {
            WorkoutsScreen(navController = navController, workoutsViewModel, workoutDeleteViewModel)
        }
        composable(Screen.WorkoutAdd.route) {
            WorkoutAddScreen(navController = navController, workoutAddViewModel)
        }
        composable(Screen.WorkoutEdit.route) { backStackEntry ->
            backStackEntry.arguments?.getString("workout_id")
                ?.let { WorkoutEditScreen(navController, workoutEditViewModel, it) }
        }
        composable(Screen.Weekly.route) { WeeklyScreen(navController) }
    }
}