package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.exercises.ExercisesDatabase
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.exercises.ExercisesRepository
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    val allData: LiveData<List<Exercise>>
    private val repository: ExercisesRepository

    init {
        val dao = ExercisesDatabase.getInstance(application).exercisesDao()
        repository = ExercisesRepository(dao)
        allData = repository.readAllData
    }

    fun getExercise(id: Int): LiveData<Exercise> {
        return repository.getExercise(id)
    }

    fun addExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addExercise(exercise)
        }
    }

    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteExercise(exercise)
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateExercise(exercise)
        }
    }
}