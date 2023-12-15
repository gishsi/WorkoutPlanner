package uk.ac.aber.dcs.cs31620.jud28.workoutplanner

import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.AppContainer
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.datasource.AppDataContainer

import android.app.Application

class InventoryApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
