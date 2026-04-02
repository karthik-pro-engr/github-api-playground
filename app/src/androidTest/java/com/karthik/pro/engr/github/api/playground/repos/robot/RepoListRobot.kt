package com.karthik.pro.engr.github.api.playground.repos.robot

import androidx.compose.ui.test.SemanticsNodeInteractionCollection
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import com.karthik.pro.engr.github.api.domain.constants.PaginationConstants.DEFAULT_PAGE_SIZE
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubRepoListTestTags.APPEND_ERROR
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubRepoListTestTags.APPEND_LOADER
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubRepoListTestTags.FULL_SCREEN_ERROR
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubRepoListTestTags.FULL_SCREEN_LOADER
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubRepoListTestTags.REPO_ITEM
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubRepoListTestTags.REPO_LIST
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubRepoListTestTags.RETRY_BUTTON
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubRepoListTestTags.SEARCH_BUTTON
import com.karthik.pro.engr.github.api.playground.presentation.repos.GithubRepoListTestTags.SEARCH_INPUT

class RepoListRobot(
    private val composeTestRule: ComposeTestRule
) {


    fun enterUsername(text: String) {
        composeTestRule
            .onNodeWithTag(SEARCH_INPUT)
            .assertExists()
            .performTextInput(text)
    }

    fun tapSearch() {
        composeTestRule
            .onNodeWithTag(SEARCH_BUTTON)
            .assertExists()
            .performClick()
    }

    fun scrollToLoadMore() {
        composeTestRule
            .onNodeWithTag(REPO_LIST)
            .assertExists()
            .performScrollToIndex(DEFAULT_PAGE_SIZE + 2)
    }

    fun tapRetry() {
        composeTestRule
            .onNodeWithTag(RETRY_BUTTON)
            .assertExists()
            .performClick()
    }


    fun assertRepoListDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag(REPO_ITEM)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule
            .onAllNodesWithTag(REPO_ITEM)
            .assertCountGreaterThan(0)
    }

    fun assertAppendLoaderDisplayed() {
        composeTestRule
            .onNodeWithTag(APPEND_LOADER)
            .assertExists()
            .assertIsDisplayed()
    }

    fun assertAppendErrorDisplayed() {
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag(APPEND_ERROR)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeTestRule
            .onNodeWithTag(APPEND_ERROR)
            .assertIsDisplayed()
    }

    fun assertMoreItemsLoaded() {
        composeTestRule.waitUntil {
            composeTestRule
                .onAllNodesWithTag(REPO_ITEM)
                .fetchSemanticsNodes().size > DEFAULT_PAGE_SIZE
        }

        composeTestRule
            .onAllNodesWithTag(REPO_ITEM)
            .assertCountGreaterThan(DEFAULT_PAGE_SIZE)
    }

    fun assertFullScreenLoaderDisplayed() {
        composeTestRule
            .onNodeWithTag(FULL_SCREEN_LOADER)
            .assertIsDisplayed()
    }

    fun assertFullScreenErrorDisplayed() {
        composeTestRule
            .onNodeWithTag(FULL_SCREEN_ERROR)
            .assertIsDisplayed()
    }

    fun SemanticsNodeInteractionCollection.assertCountGreaterThan(
        expected: Int
    ) {
        val count = fetchSemanticsNodes().size
        assert(count > expected) {
            "Expected more than $expected nodes, but found $count"
        }
    }
}

fun repoListRobot(
    composeTestRule: ComposeTestRule,
    block: RepoListRobot.() -> Unit
) {
    RepoListRobot(composeTestRule).block()
}