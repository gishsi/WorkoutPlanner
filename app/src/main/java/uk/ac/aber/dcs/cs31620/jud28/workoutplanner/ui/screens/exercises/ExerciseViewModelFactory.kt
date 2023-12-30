package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 *  Factory for the exercises view model.
 *  Reference: https://youtu.be/4fzbxnzIJsI?si=Ykck\_a-wgbdJKSgs
 *
 *  @author Julia Drozdz [jud28]
 */
class ExerciseViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ExerciseViewModel::class.java)) {
            return ExerciseViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}