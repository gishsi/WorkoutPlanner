package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

var photos = listOf<Int>(
    R.drawable.dips,
    R.drawable.bench_press,
    R.drawable.crunches,
    R.drawable.kettlebell_swing,
    R.drawable.lungees,
    R.drawable.push_up,
    R.drawable.squat,
    R.drawable.bicep_curl,
    R.drawable.mountain_climber,
)

@Composable
fun ImageChoices(onImageSelect: (Int) -> Unit) {
    val rows = photos.chunked(5)

    var selectedIndex by remember{mutableStateOf(photos[0])}

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
                                .size(64.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .graphicsLayer {
                                    if (selectedIndex == photo) {
                                        alpha = 0.5F
                                    }
                                }
                                .selectable(selected = false, onClick = {
                                    selectedIndex = photo
                                    onImageSelect(selectedIndex)
                                }),
                            contentScale = ContentScale.FillHeight,
                            colorFilter = BlendModeColorFilter(color =
                                (if (selectedIndex == photo) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    Color.DarkGray
                                }) as Color, BlendMode.Softlight
                            ),
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
            ImageChoices({})
        }
    }
}