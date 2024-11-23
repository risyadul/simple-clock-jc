/**
 * Calculates the adjacent value for the number picker considering the range wrap-around
 * 
 * @param current Current value
 * @param range Valid range for the picker
 * @param offset Offset from the current value
 * @return The adjacent value considering wrap-around
 */
internal fun getAdjacentValue(current: Int, range: IntRange, offset: Int): Int {
    return when {
        current + offset > range.last -> 
            range.first + ((current + offset - range.last - 1) % (range.last - range.first + 1))
        current + offset < range.first -> 
            range.last - ((range.first - (current + offset) - 1) % (range.last - range.first + 1))
        else -> current + offset
    }
} 