package io.duckcat.d.presentation.main.home

import androidx.compose.runtime.Stable
import io.duckcat.d.domain.model.QuoteModel

@Stable
data class HomeScreenState(
    val quote: QuoteModel = QuoteModel.Default,
    val isQuotesDialogShown: Boolean = true
)