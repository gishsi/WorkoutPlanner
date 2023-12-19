package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var name: String = "",
    val numberOfSets: Int = 0,
    val numberOfRepetitions: Int = 0,
    val weightInKilos: Float = 0F,
    val image: String = "",
    // todo: type converter: Dropset? = null
    // val dropset: Int = 0
)