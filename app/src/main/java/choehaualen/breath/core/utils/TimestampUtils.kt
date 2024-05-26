package choehaualen.breath.core.utils

object TimestampUtils {

    /**
     * A function to find the hours difference between two timestamp values.
     *
     * @param start The lower timestamp in milliseconds.
     * @param end The larger timestamp in milliseconds.
     */
    fun findHoursDifference(
        start: Long,
        end: Long
    ): Int {
        val differenceInMillis = end - start
        val differenceInHours = differenceInMillis / (1000 * 60 * 60)
        return differenceInHours.toInt()
    }

}