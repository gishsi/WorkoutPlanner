package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Top level scaffold for all pages.
 *
 * @author Julia Drozdz
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationScaffold(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    pageContent: @Composable (innerPadding: PaddingValues) -> Unit = {}
) {
    var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    NavigationDrawer(
        navController,
        drawerState = drawerState,
        closeDrawer = {
            coroutineScope.launch {
                // We know it will be open
                drawerState.close()
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(onClick = {
                    coroutineScope.launch {
                        if (drawerState.isOpen) {
                            drawerState.close()
                        } else {
                            drawerState.open()
                        }
                    }
                })
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