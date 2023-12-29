package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts.converters;

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek

class DaysInWeekConverter {
    @TypeConverter
    fun fromDaysInWeekList(daysInWeek: List<DaysInWeek>?): String? {
        return if (daysInWeek == null) {
            null
        } else {
            Gson().toJson(daysInWeek)
        }
    }

    @TypeConverter
    fun toDaysInWeekList(days: String?): List<DaysInWeek>? {
        return if (days.isNullOrBlank()) {
            null
        } else {
            val type = object : TypeToken<List<DaysInWeek>>() {}.type
            Gson().fromJson(days, type)
        }
    }
}