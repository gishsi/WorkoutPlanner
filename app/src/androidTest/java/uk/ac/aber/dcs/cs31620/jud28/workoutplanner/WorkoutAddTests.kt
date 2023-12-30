package uk.ac.aber.dcs.cs31620.jud28.workoutplanner

import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDialog
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExerciseAddScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExercisesListScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutAddScreen

/**
 * Tests for adding exercises form
 *
 * @author Julia Drozdz [jud28]
 */
@RunWith(AndroidJUnit4::class)
class WorkoutAddTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupWorkoutPlannerNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            WorkoutAddScreen(navController = navController)
        }
    }

    @Test
    fun formWithoutDropset_Displayed() {
        composeTestRule.onNode(hasText("Name")).assertExists()
        composeTestRule.onNode(hasText("Workout duration (in minutes)")).assertExists()
        composeTestRule.onNode(hasText("Nothing yet.\n" +
                "Press “Add” to add more exercises")).assertExists()
    }

    @Test
    fun addClicked_addExerciseDialogDisplays() {
        composeTestRule.onNodeWithText("Add").performClick()

        composeTestRule.onNode(isDialog()).assertExists()
    }
}