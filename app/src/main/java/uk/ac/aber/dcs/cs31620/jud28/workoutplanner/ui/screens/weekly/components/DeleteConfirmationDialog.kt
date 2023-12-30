package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R

/**
 *  Delete a workout from a weekly entry
 *
 *  @author Julia Drozdz [jud28]
 */
@Composable
fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        text = { Text("This action will remove the workout from this day. It will not affect the workout. Proceed?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = stringResource(R.string.remove))
            }
        })
}