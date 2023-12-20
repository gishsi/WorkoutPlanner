package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation

/**
 *  Singletons for screens
 *
 *  @author Julia Drozdz
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Weekly : Screen("weekly")
    object ExercisesList : Screen("exercises")
    object ExerciseAdd : Screen("exercises/add")
    object ExerciseEdit : Screen("exercises/edit/{exercise}")
    object Workouts : Screen("workouts")
    object WorkoutAdd : Screen("workouts/add")
    object WorkoutEdit : Screen("workouts/edit/{workout}")
}

val screensInBottomBar = listOf(
    Screen.Home,
    Screen.Weekly,
    Screen.ExercisesList,
)