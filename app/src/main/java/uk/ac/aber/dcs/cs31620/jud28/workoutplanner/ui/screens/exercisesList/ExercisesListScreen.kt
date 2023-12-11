package uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercisesList

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.R
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.bicepCurl
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.exercises
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.removeExercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.components.ApplicationScaffold
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme

/**
 * Screen for viewing all exercises
 *
 * @author Julia Drozdz
 */
@Composable
fun ExercisesListScreen(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val exercisesList = exercises.values.toList()


    ApplicationScaffold(
        navController = navController,
        coroutineScope = coroutineScope,
        topBarLabel = stringResource(id = R.string.exercises_list_top_bar_label)
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(exercisesList) { exercise ->
                    ExerciseCard(
                        modifier = Modifier.padding(4.dp),
                        exercise = exercise,
                        editAction = {
                            Log.d("EXE_LIST", "Edtitng an exercise [${it.name}]")

                            // todo: edit
                        },
                        deleteAction = {
                            Log.d("EXE_LIST", "Deleting an exercse [${it.name}]")

                            removeExercise(it.name)
                        })
                }
            }
        }
    }
}
@Composable
fun ExerciseCard(
    modifier: Modifier = Modifier,
    exercise: Exercise,
    editAction: (Exercise) -> Unit = {},
    deleteAction: (Exercise) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxSize()
    ) {


        // todo: use constraint layout
        Row {
            Column {
                Image(painter = painterResource(id = R.drawable.picture), contentDescription = "Image of the [${exercise.name}] exercise")
            }
            Column {
                Text(text = "${exercise.name}")
                Text(text = "${exercise.numberOfSets} x ${exercise.numberOfRepetitions}")
                Text(text = "${exercise.weightInKilos} kg")
            }

            Column {
                Row {
                    IconButton(onClick = { editAction(exercise) }) {
                        Icon(imageVector = Icons.Filled.Create, contentDescription = "Edit an exercise todo: resource")
                    }
                    IconButton(onClick = { deleteAction(exercise) }) {
                        Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete an exercise todo: resource")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ExerciseListScreenPreview() {
    var navHostController = rememberNavController()
    WorkoutPlannerTheme(dynamicColor = false) {
        ExercisesListScreen(navHostController)
    }

}

@Preview
@Composable
fun ExerciseCardPreview() {
    WorkoutPlannerTheme(dynamicColor = false) {
        ExerciseCard(exercise = bicepCurl)
    }
}