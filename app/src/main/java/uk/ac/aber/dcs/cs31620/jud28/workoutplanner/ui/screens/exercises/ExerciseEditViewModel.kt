package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.TempData
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise

class ExerciseEditViewModel(application: Application) : AndroidViewModel(application) {
    // repository here
    // private val repository: ExercisesRepository = ExercisesRepository(application)

    public fun editExercise(exercise: Exercise) {
        TempData.editExercise(exercise)
    }
}