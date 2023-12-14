package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 * Navigation drawer for pages
 *
 * @author Julia Drozdz
 */
@Composable
fun NavigationDrawer(
    navController: NavController,
    drawerState: DrawerState,
    closeDrawer: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    val items = listOf(
        NavigationDrawerItem(
            Icons.Default.Home ,
            stringResource(id = R.string.nav_home),
            Screen.Home.route
        ),
        NavigationDrawerItem(
            Icons.Default.CalendarMonth,
            stringResource(id = R.string.nav_weekly),
            Screen.Weekly.route,
        ),
        NavigationDrawerItem(
            Icons.Default.SportsGymnastics,
            stringResource(id = R.string.nav_exercises_list),
            Screen.ExercisesList.route
        ),
        NavigationDrawerItem(
            Icons.Default.Add,
            stringResource(id = R.string.nav_exercise_add),
            Screen.ExerciseAdd.route
        ),
        NavigationDrawerItem(
            Icons.Default.FitnessCenter,
            stringResource(id = R.string.nav_workouts_list),
            Screen.Workouts.route
        ),
        NavigationDrawerItem(
            Icons.Default.Add,
            stringResource(id = R.string.nav_add_workout),
            Screen.WorkoutAdd.route
        ),
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            val selectedItem = rememberSaveable { mutableStateOf(0) }

            ModalDrawerSheet  {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            selected = index == selectedItem.value,
                            onClick = {
                                selectedItem.value = index
                                closeDrawer()
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            })
                    }
                }
            }
        },
        content = content,
    )
}

/**
 *  Used to associate an icon and a label with a route.
 */
data class NavigationDrawerItem(val icon: ImageVector, val label: String, val route: String)

@Preview
@Composable
private fun NavigationDrawerPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Open)
        NavigationDrawer(navController, drawerState)
    }
}
