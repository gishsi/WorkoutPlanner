package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.exercises

import androidx.lifecycle.LiveData
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise

/**
 *  Repository for the exercises
 *
 *  @author Julia Drozdz [jud28]
 */
class ExercisesRepository(private val exerciseDao: ExerciseDao) {
    val readAllData: LiveData<List<Exercise>> = exerciseDao.getAll()

    suspend fun addExercise(exercise: Exercise) {
        exerciseDao.insert(exercise)
    }

    suspend fun deleteExercise(exercise: Exercise) {
        exerciseDao.delete(exercise)
    }

    suspend fun updateExercise(exercise: Exercise) {
        exerciseDao.update(exercise)
    }

    fun getExercise(id: Int): LiveData<Exercise> {
        return exerciseDao.getExercise(id);
    }
}