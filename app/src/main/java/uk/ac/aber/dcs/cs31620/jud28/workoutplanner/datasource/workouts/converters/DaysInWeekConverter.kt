package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.workouts.converters;

import androidx.room.TypeConverter;
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models.DaysInWeek

class DaysInWeekConverter {
    @TypeConverter
    fun fromDaysInWeek(dayInWeek: DaysInWeek): String {
        return dayInWeek.name
    }

    @TypeConverter
    fun toExerciseList(day: String): DaysInWeek {
        return enumValueOf(day)
    }
}