package com.mumti.jetpacksubmission

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.mumti.mycore.utils.EspressoIdlingResources
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {

    private val dummyMovies = DataDummy.generateDummyMovies()
    private val dummyTvShows = DataDummy.generateDummyTvShows()

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResources.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResources.idlingResource)
    }

    @Test
    fun loadMovies() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyMovies.size))
    }

    @Test
    fun loadTvShows() {
        onView(withId(R.id.navigation_tv_show)).perform(click())

        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(dummyTvShows.size))
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.txt_title)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_title)).check(matches(withText(dummyMovies[0].title)))

        onView(withId(R.id.txt_popularity)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_popularity)).check(matches(withText(dummyMovies[0].type)))

        onView(withId(R.id.txt_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_duration)).check(matches(withText(dummyMovies[0].duration)))

        onView(withId(R.id.txt_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_overview)).check(matches(withText(dummyMovies[0].overview)))

        onView(withId(R.id.txt_slogan)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_slogan)).check(matches(withText(dummyMovies[0].slogan)))
    }

    @Test
    fun loadDetailTvShow() {
        onView(withId(R.id.navigation_tv_show)).perform(click())

        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.txt_title)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_title)).check(matches(withText(dummyTvShows[0].title)))

        onView(withId(R.id.txt_popularity)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_popularity)).check(matches(withText(dummyTvShows[0].type)))

        onView(withId(R.id.txt_duration)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_duration)).check(matches(withText(dummyTvShows[0].duration)))

        onView(withId(R.id.txt_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_overview)).check(matches(withText(dummyTvShows[0].overview)))

        onView(withId(R.id.txt_slogan)).check(matches(isDisplayed()))
        onView(withId(R.id.txt_slogan)).check(matches(withText(dummyTvShows[0].slogan)))
    }

    @Test
    fun loadFavMovies() {
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_fav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.txt_title)).check(matches(isDisplayed()))

        onView(withId(R.id.fab_fav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }

    @Test
    fun loadFavTvShows() {
        onView(withId(R.id.navigation_tv_show)).perform(click())
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.fab_fav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.navigation_favorite)).perform(click())
        onView(withText("TV Shows")).perform(click())

        onView(withId(R.id.rv_tvShow)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tvShow)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.txt_title)).check(matches(isDisplayed()))

        onView(withId(R.id.fab_fav)).perform(click())
        onView(isRoot()).perform(ViewActions.pressBack())
    }
}