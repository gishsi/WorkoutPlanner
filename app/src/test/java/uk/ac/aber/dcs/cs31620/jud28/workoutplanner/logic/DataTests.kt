package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic

import org.junit.Assert.*
import org.junit.Test
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Workout

/**
 * Unit tests for the basic domain logic operations: adding, removing, editing workouts and exercises.
 */
class DataTests {
    // ************* exercises ********************
    @Test
    fun getAll_returns() {
        var result = getAllExercises()
        assertEquals(4, result.count())
    }

    @Test
    fun addExercise_countIs5() {
        var newExercise = Exercise("Triceps extension", 4, 8, 20F, "TE");
        addExercise(newExercise)
        var result = getAllExercises()
        assertEquals(5, result.count())
    }

    @Test
    fun addExercise_ResultNotNull() {
        var newExercise = Exercise("Triceps extension", 4, 8, 20F, "TE");

        addExercise(newExercise)
        var result = getAllExercises()

        assertNotNull(result)
    }

    @Test
    fun removeExercise_countIs3() {
        removeExercise("Bicep curl")

        var result = getAllExercises()

        assertEquals(3, result.count())
    }

    // ************* workouts ********************
    @Test
    fun getAll_returnsTwo() {
        var result = getAllWorkouts()

        assertEquals(2, result.count())
    }

    @Test
    fun addWorkout_countIsThree() {
        var newWorkout = Workout("Chest", 50, listOf())
        var result = addWorkout(newWorkout)

        assertEquals(3, result.count())
    }

    @Test
    fun addWorkout_ResultNotNull() {
        var newWorkout = Workout("Chest", 50, listOf())

        var result = addWorkout(newWorkout)["Chest"]
        assertNotNull(result)
    }

    @Test
    fun removeWorkout_countIsOne() {
        var result = removeWorkout("Upper body")
        assertEquals(1, result.count())
    }
}