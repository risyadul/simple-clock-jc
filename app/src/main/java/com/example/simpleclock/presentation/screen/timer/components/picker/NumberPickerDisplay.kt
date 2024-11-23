import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Displays the current and adjacent numbers in the number picker
 */
@Composable
fun NumberPickerDisplay(
    value: Int,
    range: IntRange,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NumberPickerValue(
                value = getAdjacentValue(value, range, -2),
                alpha = 0.1f,
                fontSize = 20.sp
            )
            NumberPickerValue(
                value = getAdjacentValue(value, range, -1),
                alpha = 0.2f,
                fontSize = 24.sp
            )
            NumberPickerValue(
                value = value,
                alpha = 1f,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
            NumberPickerValue(
                value = getAdjacentValue(value, range, 1),
                alpha = 0.2f,
                fontSize = 24.sp
            )
            NumberPickerValue(
                value = getAdjacentValue(value, range, 2),
                alpha = 0.1f,
                fontSize = 20.sp
            )
        }
    }
} 