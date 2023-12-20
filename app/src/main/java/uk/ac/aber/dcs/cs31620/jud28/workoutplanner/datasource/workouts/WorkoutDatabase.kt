package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts.converters.ExerciseListConverter
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Workout

@Database(entities = [Workout::class], version = 1, exportSchema = false)
@TypeConverters(ExerciseListConverter::class)
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
