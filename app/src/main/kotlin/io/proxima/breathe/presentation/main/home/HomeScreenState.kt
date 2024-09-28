package io.proxima.breathe.presentation.main.home

import androidx.compose.runtime.Stable
import io.proxima.breathe.domain.model.QuoteModel

@Stable
data class HomeScreenState(
    val quote: QuoteModel = QuoteModel.Empty,
    val isQuotesDialogShown: Boolean = false
)