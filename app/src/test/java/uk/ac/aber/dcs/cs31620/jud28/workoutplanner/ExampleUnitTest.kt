package uk.ac.aber.dcs.cs31620.jud28.workoutplanner

import org.junit.Test

import org.junit.Assert.*
import uk.ac.aber.dcs.cs31620.jud28.workoutplanner.logic.Uded

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        var test = Uded()
        assertEquals(4, test.Add(2, 2))
    }
}