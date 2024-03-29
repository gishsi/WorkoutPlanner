package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R

/**
 * Top level scaffold for all pages.
 *
 * @author Julia Drozdz [jud28]
 */
@Composable
fun ApplicationScaffold(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    topBarLabel: String = stringResource(id = R.string.app_name),
    pageContent: @Composable (innerPadding: PaddingValues) -> Unit = {},
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    NavigationDrawer(
        navController,
        drawerState = drawerState,
        closeDrawer = {
            coroutineScope.launch {
                drawerState.close()
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    onClick = {
                        coroutineScope.launch {
                            if (drawerState.isOpen) {
                                drawerState.close()
                            } else {
                                drawerState.open()
                            }
                        }
                    },
                    label = topBarLabel
                )
            },
            bottomBar = {
                NavigationBar(navController)
            },
            content = { innerPadding ->
                pageContent(innerPadding)
            }
        )
    }
}