package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout

/**
 *  Data access object for the workouts
 *
 *  @author Julia Drozdz [jud28]
 */
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

    /**
     *  Look for the day passed in the list serialized as string.
     */
    @Query("SELECT * FROM workouts WHERE assignedTo LIKE '%' || :day || '%'")
    fun getWorkoutForDay(day: DaysInWeek): LiveData<Workout>

    /**
     *  Get all workouts that are assigned.
     *  If the list is null or empty that means the workout is not assigned therefore should not be returned.
     */
    @Query("SELECT * FROM workouts WHERE assignedTo IS NOT NULL OR LENGTH(assignedTo) != 0")
    fun getWorkoutsForEachDay(): LiveData<List<Workout>>

    @Query("DELETE FROM workouts")
    suspend fun clearTable()
}