package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R

/**
 *  Dialog which is shown after the user tries to remove an exercise from the list
 *
 *  @author Julia Drozdz [jud28]
 */
@Composable
fun RemoveExerciseFromListDialog(
    name: String = "",
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    val styledText = buildAnnotatedString {
        withStyle(
            style = MaterialTheme.typography.bodyMedium.toSpanStyle()
                .copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    textDecoration = TextDecoration.Underline
                )
        ) {
            append(name)
        }
    }

    AlertDialog(
        onDismissRequest = { /* Do nothing */ },
        text = {
            Text(
                buildAnnotatedString {
                    append("You are about to remove an exercise called ")
                    append(styledText)
                    append(". Are you sure you want to proceed?")
                })
        },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = stringResource(R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(
                    text = stringResource(R.string.remove),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        containerColor = Color.White
    )
}