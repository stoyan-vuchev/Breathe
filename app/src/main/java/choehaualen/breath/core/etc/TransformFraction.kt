package choehaualen.breath.core.etc

fun transformFraction(
    value: Float,
    startX: Float = 0f,
    endX: Float = 1f,
    startY: Float,
    endY: Float
): Float {

    // Check if startX is less than endX
    val newStartX = if (startX <= endX) startX else endX
    val newEndX = if (startX <= endX) endX else startX

    // Check if startY is less than endY
    val newStartY = if (startY <= endY) startY else endY
    val newEndY = if (startY <= endY) endY else startY

    // Transform the value to the new range
    return ((value - newStartX) / (newEndX - newStartX)) * (newEndY - newStartY) + newStartY

}