package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts.converters.DaysInWeekConverter
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts.converters.ExerciseListConverter
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout

/**
 *  Data access object for the workouts
 *
 *  @author Julia Drozdz [jud28]
 */
@Database(entities = [Workout::class], version = 2, exportSchema = false)
@TypeConverters(ExerciseListConverter::class, DaysInWeekConverter::class)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao

    companion object {
        private var INSTANCE: WorkoutDatabase? = null

        fun getInstance(context: Context): WorkoutDatabase {
            synchronized(this) {
                if (INSTANCE != null) {
                    return INSTANCE as WorkoutDatabase
                }

                return Room.databaseBuilder(
                    context.applicationContext,
                    WorkoutDatabase::class.java,
                    "workouts"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}

