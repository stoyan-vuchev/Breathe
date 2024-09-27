package io.proxima.breathe.mappers

import io.proxima.breathe.data.local.entity.QuoteEntity
import io.proxima.breathe.data.remote.dto.QuoteResponseDto
import io.proxima.breathe.domain.model.QuoteModel

fun QuoteResponseDto.toEntity() = QuoteEntity(
    id = System.currentTimeMillis(),
    author = author,
    quote = quote
)

fun QuoteEntity.toModel() = QuoteModel(
    author = author,
    quote = quote
)