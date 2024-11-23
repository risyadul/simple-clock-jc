/**
 * Represents the state of a timer
 * @property hours The hours value of the timer (0-23)
 * @property minutes The minutes value of the timer (0-59)
 * @property seconds The seconds value of the timer (0-59)
 * @property isRunning Indicates whether the timer is currently running
 */
data class TimerState(
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,
    val isRunning: Boolean = false
) {
    /**
     * Calculates the total number of seconds represented by this timer state
     */
    val totalSeconds: Int
        get() = hours * 3600 + minutes * 60 + seconds
} 