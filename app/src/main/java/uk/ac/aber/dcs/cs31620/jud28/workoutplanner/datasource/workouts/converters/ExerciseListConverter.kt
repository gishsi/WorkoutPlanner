package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.Exercise

class ExerciseListConverter {
    @TypeConverter
    fun fromExerciseList(exercises: List<Exercise>?): String? {
        return if (exercises == null) {
            null
        } else {
            Gson().toJson(exercises)
        }
    }

    @TypeConverter
    fun toExerciseList(exerciseListString: String?): List<Exercise>? {
        return if (exerciseListString.isNullOrBlank()) {
            null
        } else {
            val type = object : TypeToken<List<Exercise>>() {}.type
            Gson().fromJson(exerciseListString, type)
        }
    }
}