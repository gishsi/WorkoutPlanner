package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  Represents a workout.
 *
 *  @author Julia Drozdz
 */

@Entity(tableName = "workouts")
class Workout(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val name: String = "",
    val durationInMinutes: Int = 0,
    val exercises: List<Exercise> = listOf(),
    val assignedToWeek: DaysInWeek = DaysInWeek.NotAssigned,
)

enum class DaysInWeek {
    NotAssigned,
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
    Sunday,
}