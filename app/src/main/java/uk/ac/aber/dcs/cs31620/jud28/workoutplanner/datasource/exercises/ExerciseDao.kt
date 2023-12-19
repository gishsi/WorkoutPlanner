package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.exercises

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise

@Dao
interface ExerciseDao {
    @Insert
    suspend fun insert(exercise: Exercise)

    @Update
    suspend fun update(exercise: Exercise)

    @Delete
    suspend fun delete(exercise: Exercise)

    @Query("SELECT * from exercises WHERE id = :id")
    fun getExercise(id: Int): LiveData<Exercise>

    @Query("SELECT * FROM exercises")
    fun getAll(): LiveData<List<Exercise>>
}