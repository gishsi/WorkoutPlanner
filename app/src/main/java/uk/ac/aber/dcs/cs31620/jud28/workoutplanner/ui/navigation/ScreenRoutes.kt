package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation

/**
 *  Singletons for screens
 *
 *  @author Julia Drozdz
 */
sealed class Screen(val route: String) {
    object Home: Screen("Home")
    object ExercisesList : Screen("ExercisesList")
    object Weekly : Screen("Weekly")
    object AddExercise : Screen("AddExercise")
    object WorkoutsList : Screen("WorkoutsList")
    object AddWorkout : Screen("AddWorkout")
}

val screensInBottomBar = listOf(
    Screen.Home,
    Screen.Weekly,
    Screen.ExercisesList,
)