package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0,
    val name: String,
    val price: Double,
    val quantity: Int
)