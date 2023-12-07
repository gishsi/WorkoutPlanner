package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models

/**
 *  Represents an exercise
 *
 *  @author Julia Drozdz
 */
class Exercise( val name: String = "", val numberOfSets: Int, val numberOfRepetitions: Int, val weightInKilos: Float, val image: String, val dropset: Dropset? = null) {
}