package uk.ac.aber.dcs.cs31620.jud28.workoutplanner

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.exercises.ExercisesListScreen
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.ui.screens.workouts.WorkoutsScreen

/**
 * Tests for the exercises list.
 *
 * @author Julia Drozdz [jud28]
 */
@RunWith(AndroidJUnit4::class)
class WorkoutsListTests {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupWorkoutPlannerNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            WorkoutsScreen(navController = navController)
        }
    }

    @Test
    fun noWorkoutsVariant_Displayed() {
        composeTestRule.onNode(hasText("No workouts yet")).assertExists()
    }
}