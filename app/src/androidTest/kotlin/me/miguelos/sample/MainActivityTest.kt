package me.miguelos.sample

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import me.miguelos.sample.presentation.ui.MainActivity
import me.miguelos.base.util.BaseRobot
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Tests to verify that the behavior of {@link MainActivity} is correct.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @Rule
    @JvmField
    var hiltEmulatorTestRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    private var robot = BaseRobot()

    @Before
    fun init() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun restartActivity() {
        activityRule.scenario.apply {
            moveToState(Lifecycle.State.RESUMED)
            activityRule.scenario.recreate()
        }
    }

    @Test
    fun mainActivity() {
        robot.assertOnView(withId(R.id.toolbar))
        robot.assertOnView(withId(R.id.beers_cl))
    }

    @Test
    fun searchList() {
        robot.turnOnInternetConnections()
        robot.fillEditTextAndApply(R.id.search_et, "vlad")
        robot.doOnView(withText("Hello My Name is Vladimir"), ViewActions.click())
        robot.assertOnView(withId(R.id.beer_cl))
    }


    /**
     * Check that without Internet the Ranking shows previous beers involved in battles
     */
    @Test
    fun offlineSearchList() {
        robot.turnOffInternetConnections()
        robot.fillEditTextAndApply(R.id.search_et, "vlad")
        robot.assertOnView(withText(R.string.dialog_offline))
        robot.turnOnInternetConnections()
    }
}
