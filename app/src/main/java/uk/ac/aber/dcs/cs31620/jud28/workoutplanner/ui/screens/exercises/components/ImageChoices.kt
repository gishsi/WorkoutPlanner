package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

var photos = listOf<Int>(
    R.drawable.picture,
    R.drawable.picture,
    R.drawable.picture,
    R.drawable.picture,
    R.drawable.picture,
    R.drawable.picture
)

@Composable
fun ImageChoices() {
    // todo: selecting images polish
    var isOverlayVisible by remember { mutableStateOf(false) }


    val rows = photos.chunked(5) // Adjust the chunk size as needed

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        rows.forEach { rowImages ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowImages.forEach { photo ->
                    Image(
                        painter = painterResource(id = photo),
                        contentDescription = "",
                        modifier = Modifier
                            .height(64.dp)
                            .width(64.dp)
                            .graphicsLayer {
                                if (isOverlayVisible) {
                                    alpha = 0.5F
                                }
                            }
                            .clickable { isOverlayVisible = true }
                    )
                }
            }
        }
    }


}

@Preview
@Composable
fun ImageChoicesPreview() {
    WorkoutPlannerTheme {
        Surface {
            ImageChoices()
        }
    }
}