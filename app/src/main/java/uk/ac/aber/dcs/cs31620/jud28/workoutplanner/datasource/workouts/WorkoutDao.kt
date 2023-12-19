package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout

@Dao
interface WorkoutDao {
    @Insert
    suspend fun insert(workout: Workout)

    @Update
    suspend fun update(workout: Workout)

    @Delete
    suspend fun delete(workout: Workout)

    @Query("SELECT * from workouts WHERE id = :id")
    fun getWorkout(id: Int): LiveData<Workout>

    @Query("SELECT * FROM workouts")
    fun getAll(): LiveData<List<Workout>>
}