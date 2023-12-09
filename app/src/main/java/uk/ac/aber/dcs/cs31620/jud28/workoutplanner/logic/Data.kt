package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic

import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Workout

/**
 *  Temporary data
 *
 *  @author Julia Drozdz
 */

var bicepCurl = Exercise("Bicep curl", 3, 10, 10F, "BC")
var benchPress = Exercise("Bench press", 4, 8,  50F, "BP")
var deadLift = Exercise("Dead lift", 3, 10, 70F, "DL")
var squat = Exercise("Squat", 3, 10, 60F, "S")

var exercises = mutableMapOf<String, Exercise>(
    "Bicep curl" to bicepCurl,
    "Bench press" to benchPress,
    "Dead lift" to deadLift,
    "Squat" to squat,
)

/**
 * Add a new exercise
 */
fun addExercise(new: Exercise) {
    exercises.put(new.name, new)
}

/**
 * Remove an exercise from the data
 */
fun removeExercise(key: String) {
    exercises.remove(key)
}

/**
 *  Get all exercises - immutable
 */
fun getAllExercises(): List<Exercise> {
    return exercises.values.toList()
}

// ************************** workouts ***********************

val upperBody = Workout("Upper body", 60,
    listOf(exercises["Bicep curl"], exercises["Bench press"]) as List<Exercise>
)
val legsDay = Workout("Legs day", 50,
    listOf(exercises["Dead lift"], exercises["Squat"]) as List<Exercise>
)

var workouts = mapOf<String, Workout>(
    "Upper body" to upperBody,
    "Legs day" to legsDay,
)

/**
 * Add a new workout
 */
fun addWorkout(new: Workout): Map<String, Workout> {
    return workouts.toMutableMap().apply {
        put("Chest", new)
    }.toMap()
}

/**
 * Remove an workout from the data
 */
fun removeWorkout(name: String): Map<String, Workout> {
    return workouts.toMutableMap().apply {
        remove(name)
    }.toMap()
}
/**
 *  Get all workouts - immutable
 */
fun getAllWorkouts(): List<Workout> {
    return workouts.values.toList()
}