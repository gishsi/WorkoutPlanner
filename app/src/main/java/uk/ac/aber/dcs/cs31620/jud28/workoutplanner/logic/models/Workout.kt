package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models

/**
 *  Represents a workout.
 *
 *  @author Julia Drozdz
 */
class Workout(val name: String = "", val durationInMinutes: Int, val exercises: List<Exercise>) {
}