import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.abs

/**
 * A scrollable number picker component that allows users to select a numeric value
 * within a specified range.
 *
 * @param value Current selected value
 * @param onValueChange Callback when value changes
 * @param range Valid range for the picker
 * @param label Label displayed below the picker
 * @param modifier Modifier for the component
 */
@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange,
    label: String,
    modifier: Modifier = Modifier
) {
    var offset by remember { mutableFloatStateOf(0f) }
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .scrollable(
                orientation = Orientation.Vertical,
                state = rememberScrollableState { delta ->
                    offset += delta
                    if (abs(offset) >= 40) {
                        val newValue = if (offset > 0) {
                            if (value - 1 < range.first) range.last else value - 1
                        } else {
                            if (value + 1 > range.last) range.first else value + 1
                        }
                        onValueChange(newValue)
                        offset = 0f
                    }
                    delta
                }
            )
    ) {
        NumberPickerDisplay(
            value = value,
            range = range,
            modifier = Modifier.height(200.dp)
        )
        
        NumberPickerLabel(
            label = label,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
} 