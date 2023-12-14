package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.TempData
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Workout

/**
 *  View model for the workouts list view.
 *
 *  @author Julia Drozdz
 */
class WorkoutsViewModel(application: Application) : AndroidViewModel(application) {
    val allWorkouts: List<Workout> = TempData.getAllWorkouts()
}