package uk.ac.aber.dcs.cs31620.jud28.workoutplanner

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.compose.rememberNavController
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.exercises
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.getAllExercises
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Exercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.models.Workout
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.removeExercise
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.workouts
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.NavigationGraph
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.navigation.Screen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercisesList.ExercisesListScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.home.HomeScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.weekly.WeeklyScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.theme.WorkoutPlannerTheme
import java.lang.Exception

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutPlannerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationGraph()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnrememberedMutableState")
@Composable
fun Application() {
    var localExercises = remember  {
        val pairs = buildList {
            for (exercise in getAllExercises())
                add(Pair(exercise.name, exercise))
        }
        mutableStateMapOf(*pairs.toTypedArray())
    }


    Column(modifier = Modifier.fillMaxSize()) {
        // ****************** Form: add an exercise**********************
        var exerciseName by remember { mutableStateOf("Bicep curl")}
        var numOfSets by remember { mutableStateOf(1)}
        var numOfRepetitions by remember { mutableStateOf(1)}
        var isDropset by remember { mutableStateOf(false)}

        TextField(value = exerciseName , onValueChange = { /* todo */ }, label = { Text("Exercise name") })
        TextField(value = numOfSets.toString(), onValueChange = { /* todo */ }, label = { Text("Number of sets") })
        TextField(value = numOfRepetitions.toString(), onValueChange = { /* todo */ }, label = { Text("Number of repetitions") })

        if(!isDropset) {
            var weight by remember { mutableStateOf(1)}

            TextField(value = weight.toString(), onValueChange = { /* todo */ }, label = { Text("Weight") })
        }

        Checkbox(checked = isDropset, onCheckedChange = { isDropset = !isDropset} )

        if(isDropset) {
            var weightFirst by remember { mutableStateOf(1)}
            var weightSecond by remember { mutableStateOf(1)}
            var weightThird by remember { mutableStateOf(1)}

            TextField(value = weightFirst.toString(), onValueChange = { /* todo */ }, label = { Text("Weight (First)") })
            TextField(value = weightSecond.toString(), onValueChange = { /* todo */ }, label = { Text("Weight (Second)") })
            TextField(value = weightThird.toString(), onValueChange = { /* todo */ }, label = { Text("Weight (Third)") })
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Add")
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }

        // ************************* list of exercises ******************
        Text(text = "Exercise")
        Column(modifier = Modifier.padding(4.dp)) {
            for (exerciseEntry in localExercises) {
                ExerciseCard(exerciseEntry.value)
            }
        }


        Text(text = "Workouts")
        // ******************* list of workouts ************************
        Column(modifier = Modifier.padding(4.dp)) {
            for (workout in workouts.values) {
                WorkoutCard(workout)
            }
        }
    }
}


@Composable
fun ExerciseCard(exercise: Exercise) {
    Row(modifier = Modifier
        .background(Color.Gray)
        .padding(4.dp)) {
        Column {
            Text(exercise.name)
            Text(exercise.numberOfSets.toString())
            Text(exercise.numberOfRepetitions.toString())
            Row {
                OutlinedButton(onClick = { /* todo */ }) {
                    Icon(imageVector = Icons.Default.Create, contentDescription = "Edit", modifier = Modifier.size(24.dp))
                }
                OutlinedButton(onClick = { removeExercise(exercise.name)}) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Remove", modifier = Modifier.size(24.dp))
                }
            }
        }
    }
}

@Composable
fun WorkoutCard(workout: Workout) {
    Row(modifier = Modifier
        .background(Color.Green)
        .padding(4.dp))  {
        Column {
            Text(workout.name)
            Text(workout.durationInMinutes.toString())
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun MainActivityPreview() {
    WorkoutPlannerTheme {
        NavigationGraph()
    }
}