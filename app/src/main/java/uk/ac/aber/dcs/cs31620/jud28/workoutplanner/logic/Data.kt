package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic

import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Workout

/**
 *  Temporary data
 *
 *  @author Julia Drozdz
 */
object Exercises {
    var bicepCurl = Exercise("Bicep curl", 3, 10, 10F, "BC")
    var benchPress = Exercise("Bench press", 4, 8,  50F, "BP")
    var deadLift = Exercise("Dead lift", 3, 10, 70F, "DL")
    var squat = Exercise("Squat", 3, 10, 60F, "S")
}

var workouts = object {
    val upperBody = Workout("Upper body", 60, listOf(Exercises.bicepCurl, Exercises.benchPress))
}