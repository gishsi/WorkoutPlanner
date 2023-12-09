package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.model

import androidx.annotation.NonNull
import androidx.room.PrimaryKey

/**
 *  Represents an exercise
 *
 *  @author Julia Drozdz
 */
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    var id: Int = 0,
    val name: String = "",
    val numberOfSets:
    Int,
    val numberOfRepetitions: Int,
    val weightInKilos: Float,
    val image: String,
    val dropset: Dropset? = null) {
}