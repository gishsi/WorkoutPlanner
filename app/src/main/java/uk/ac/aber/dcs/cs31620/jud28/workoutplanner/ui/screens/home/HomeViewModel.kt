package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Workout

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    // repository here
    // private val repository: ExercisesRepository = ExercisesRepository(application)

    // should be LiveData
    val workoutForTheDay: Workout= getWorkoutOfTheDay()

    private fun getWorkoutOfTheDay(): Workout {
        return Workout("Workout from view model", 1,  listOf())
    }
}