package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R

/**
 * Top level scaffold for all pages.
 *
 * @author Julia Drozdz
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(onClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(stringResource(id = R.string.app_name))
        },
        navigationIcon = {
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = stringResource(id = R.string.nav_drawer_menu)
                )
            }
        }
    )
}