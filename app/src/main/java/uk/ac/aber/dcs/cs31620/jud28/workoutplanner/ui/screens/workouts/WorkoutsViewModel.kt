package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts.WorkoutDatabase
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts.WorkoutRepository
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout

/**
 *  View model for the workouts list view.
 *
 *  @author Julia Drozdz
 */
class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    val allData: LiveData<List<Workout>>
    private val repository: WorkoutRepository

    init {
        val dao = WorkoutDatabase.getInstance(application).workoutDao()
        repository = WorkoutRepository(dao)
        allData = repository.readAllData
    }

    fun getWorkout(id: Int): LiveData<Workout> {
        return repository.getWorkout(id)
    }

    fun getWorkoutForDay(day: DaysInWeek): LiveData<Workout> {
        return repository.getWorkoutForDay(day)
    }

    fun getWorkoutsForEachDay() : LiveData<List<Workout>> {
        return repository.getWorkoutsForEachDay()
    }

    fun addWorkout(workout: Workout) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addWorkout(workout)
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWorkout(workout)
        }
    }

    fun updateWorkout(workout: Workout) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateWorkout(workout)
        }
    }

    fun clearTable() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearTable()
        }
    }
}