package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 * Top level scaffold for all pages.
 *
 * @author Julia Drozdz
 */
@Composable
fun TopAppBar(
    onClick: () -> Unit = {},
    label: String = stringResource(id = R.string.app_name),
) {
    CenterAlignedTopAppBar(
        title = {
            Text(label)
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

@Preview
@Composable
private fun MainPageTopAppBarPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        TopAppBar(label = "Preview")
    }
}