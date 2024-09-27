package io.proxima.breathe.mappers

import io.proxima.breathe.data.remote.dto.QuoteResponseDto
import io.proxima.breathe.domain.model.QuoteModel

fun QuoteResponseDto.toModel() = QuoteModel(
    author = author,
    quote = quote
)