package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts

import androidx.lifecycle.LiveData
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout

class WorkoutRepository(private val workoutDao: WorkoutDao) {
    val readAllData: LiveData<List<Workout>> = workoutDao.getAll()

    suspend fun addWorkout(workout: Workout) {
        workoutDao.insert(workout)
    }

    suspend fun deleteWorkout(workout: Workout) {
        workoutDao.delete(workout)
    }

    suspend fun updateWorkout(workout: Workout) {
        workoutDao.update(workout)
    }

    fun getWorkout(id: Int): LiveData<Workout> {
        return workoutDao.getWorkout(id)
    }
}