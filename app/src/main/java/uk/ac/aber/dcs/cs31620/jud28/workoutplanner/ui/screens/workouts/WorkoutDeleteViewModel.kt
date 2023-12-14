package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.TempData

/**
 *  View model for the editing a workout
 *
 *  @author Julia Drozdz
 */
class WorkoutDeleteViewModel(application: Application) : AndroidViewModel(application) {
    public fun removeWorkout(id: Int) {
        TempData.removeWorkout("Chest")
    }
}