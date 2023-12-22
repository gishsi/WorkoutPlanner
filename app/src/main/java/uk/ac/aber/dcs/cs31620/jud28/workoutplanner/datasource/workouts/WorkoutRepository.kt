package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts

import androidx.lifecycle.LiveData
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
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

    fun getWorkoutForDay(day: DaysInWeek) : LiveData<Workout> {
        return workoutDao.getWorkoutForDay(day)
    }

    fun getWorkoutsForEachDay() : LiveData<List<Workout>> {
        return workoutDao.getWorkoutsForEachDay()
    }

    suspend fun clearTable() {
        workoutDao.clearTable()
    }
}