package io.duckcat.d.mappers

import io.duckcat.d.data.local.entity.QuoteEntity
import io.duckcat.d.data.remote.dto.QuoteResponseDto
import io.duckcat.d.domain.model.QuoteModel

fun QuoteResponseDto.toEntity() = QuoteEntity(
    id = System.currentTimeMillis(),
    author = author,
    quote = quote
)

fun QuoteEntity.toModel() = QuoteModel(
    author = author,
    quote = quote
)