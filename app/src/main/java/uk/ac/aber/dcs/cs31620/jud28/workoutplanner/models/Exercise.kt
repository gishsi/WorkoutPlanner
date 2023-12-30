package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents an exercise
 *
 * @author Julia Drozdz [jud28]
 */
@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    val numberOfSets: Int = 0,
    val numberOfRepetitions: Int = 0,
    val weightInKilos: Float = 0F,
    val image: String = "",
    val dropSetEnabled: Boolean = false,
    val firstWeight: Float = 0F,
    val secondWeight: Float = 0F,
    val thirdWeight: Float = 0F,
)