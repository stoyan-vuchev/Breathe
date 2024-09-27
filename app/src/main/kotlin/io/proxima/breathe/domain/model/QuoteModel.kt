package io.proxima.breathe.domain.model

data class QuoteModel(
    val author: String,
    val quote: String
) {

    companion object {

        val Default = QuoteModel(
            author = "Maya Angelov",
            quote = "You will face many defeats in life, but never let yourself be defeated."
        )

    }

}