package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.TempData

class ExercisesDeleteViewModel(application: Application) : AndroidViewModel(application) {
    // repository here
    // private val repository: ExercisesRepository = ExercisesRepository(application)

    // Suspend
    public fun removeExercise(id: Int) {
        TempData.removeExercise("Bicep curl")
    }
}