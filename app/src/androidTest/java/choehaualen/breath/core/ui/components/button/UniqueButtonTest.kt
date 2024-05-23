package choehaualen.breath.core.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import choehaualen.breath.core.ui.theme.BreathTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UniqueButtonTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val isClicked = MutableStateFlow(false)
    private val isEnabled = MutableStateFlow(true)

    companion object {
        private const val BUTTON_TAG = "unique_button_tag"
        private const val BUTTON_LABEL = "Perform action"
    }

    @Before
    fun setContent() = composeTestRule.setContent {
        BreathTheme {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BreathTheme.colors.background),
                contentAlignment = Alignment.Center
            ) {

                val enabled by isEnabled.collectAsStateWithLifecycle()

                UniqueButton(
                    modifier = Modifier.testTag(BUTTON_TAG),
                    enabled = enabled,
                    onClick = { isClicked.update { true } },
                    onClickLabel = BUTTON_LABEL,
                    content = { Text(text = "Unique Button") }
                )

            }

        }
    }

    @After
    fun tearDown() {
        isClicked.update { false }
        isEnabled.update { true }
    }

    @Test
    fun the_button_is_displayed() = runTest {
        composeTestRule
            .onNodeWithTag(BUTTON_TAG, true)
            .assertIsDisplayed()
    }

    @Test
    fun the_button_is_enabled() = runTest {
        isEnabled.update { true }
        composeTestRule.awaitIdle()
        composeTestRule
            .onNodeWithTag(BUTTON_TAG, true)
            .assertIsEnabled()
            .also { assertThat(isEnabled.value).isTrue() }
    }

    @Test
    fun the_button_is_not_enabled() = runTest {
        isEnabled.update { false }
        composeTestRule.awaitIdle()
        composeTestRule
            .onNodeWithTag(BUTTON_TAG, true)
            .assertIsNotEnabled()
            .also { assertThat(isEnabled.value).isFalse(); isEnabled.update { true } }
    }

    @Test
    fun the_button_has_on_click_label() = runTest {
        composeTestRule.awaitIdle()
        composeTestRule
            .onNodeWithTag(BUTTON_TAG, true)
            .assertContentDescriptionEquals(BUTTON_LABEL)
    }

    @Test
    fun the_button_is_clicked() = runTest {
        composeTestRule
            .onNodeWithTag(BUTTON_TAG, true)
            .assertHasClickAction()
            .performClick()
            .also {
                if (isEnabled.value) assertThat(isClicked.value).isTrue()
                else assertThat(isClicked.value).isFalse()
            }
    }

}