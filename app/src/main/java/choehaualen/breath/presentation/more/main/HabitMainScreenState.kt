package choehaualen.breath.presentation.more.main

data class HabitMainScreenState(
    val currentSegment: HabitMainScreenSegment? = null,
    val username: String = "",
    val name: String = "",
    val quote: String = "",
    val goalDuration: Int = 0,
    val currentProgress: Int = 0
)