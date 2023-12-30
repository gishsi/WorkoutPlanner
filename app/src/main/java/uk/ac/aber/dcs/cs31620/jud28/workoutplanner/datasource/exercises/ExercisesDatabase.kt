package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.exercises

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise

/**
 *  Database for the exercises
 *
 *  @author Julia Drozdz [jud28]
 */
@Database(entities = [Exercise::class], version = 3, exportSchema = false)
abstract class ExercisesDatabase : RoomDatabase() {
    abstract fun exercisesDao(): ExerciseDao

    companion object {
        private var INSTANCE: ExercisesDatabase? = null

        fun getInstance(context: Context): ExercisesDatabase {
            synchronized(this) {
                if (INSTANCE != null) {
                    return INSTANCE as ExercisesDatabase
                }

                return Room.databaseBuilder(
                    context.applicationContext,
                    ExercisesDatabase::class.java,
                    "exercises"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}