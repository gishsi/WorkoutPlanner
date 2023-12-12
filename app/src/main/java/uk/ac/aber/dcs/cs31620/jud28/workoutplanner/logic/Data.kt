package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic

import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Workout

/**
 *  Temporary data
 *
 *  @author Julia Drozdz
 */

object TempData {
    public var bicepCurl = Exercise("Bicep curl", 3, 10, 10F, "BC")
    public var benchPress = Exercise("Bench press", 4, 8,  50F, "BP")
    public var deadLift = Exercise("Dead lift", 3, 10, 70F, "DL")
    public var squat = Exercise("Squat", 3, 10, 60F, "S")

    public var exercises = mutableMapOf<String, Exercise>(
        "Bicep curl" to bicepCurl,
        "Bench press" to benchPress,
        "Dead lift" to deadLift,
        "Squat" to squat,
    )

    /**
     * Add a new exercise
     */
    public fun addExercise(new: Exercise) {
        exercises.put(new.name, new)
    }

    /**
     * Remove an exercise from the data
     */
    public fun removeExercise(key: String) {
        exercises.remove(key)
    }

    /**
     *  Get all exercises - immutable
     */
    public fun getAllExercises(): List<Exercise> {
        return exercises.values.toList()
    }

    /**
     * Edit an exercise
     */
    public fun editExercise(new: Exercise) {
        exercises.replace(new.name, new)
    }

// ************************** workouts ***********************

    public val upperBody = Workout("Upper body", 60,
        listOf(exercises["Bicep curl"], exercises["Bench press"]) as List<Exercise>
    )
    public val legsDay = Workout("Legs day", 50,
        listOf(exercises["Dead lift"], exercises["Squat"]) as List<Exercise>
    )

    public var workouts = mapOf<String, Workout>(
        "Upper body" to upperBody,
        "Legs day" to legsDay,
    )

    /**
     * Add a new workout
     */
    public fun addWorkout(new: Workout): Map<String, Workout> {
        return workouts.toMutableMap().apply {
            put("Chest", new)
        }.toMap()
    }

    /**
     * Remove an workout from the data
     */
    public fun removeWorkout(name: String): Map<String, Workout> {
        return workouts.toMutableMap().apply {
            remove(name)
        }.toMap()
    }
    /**
     *  Get all workouts - immutable
     */
    public fun getAllWorkouts(): List<Workout> {
        return workouts.values.toList()
    }

}


