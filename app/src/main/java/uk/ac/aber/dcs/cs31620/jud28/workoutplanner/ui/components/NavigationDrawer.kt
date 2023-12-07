package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

/**
 * Navigation drawer for pages
 *
 * @author Julia Drozdz
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    navController: NavHostController,
    drawerState: DrawerState,
    closeDrawer: () -> Unit,
    content: @Composable () -> Unit
) {
    TODO("Not yet implemented")
}