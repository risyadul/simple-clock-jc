import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Displays a single value in the number picker
 */
@Composable
fun NumberPickerValue(
    value: Int,
    alpha: Float,
    fontSize: TextUnit,
    fontWeight: FontWeight = FontWeight.Normal
) {
    Text(
        text = value.toString().padStart(2, '0'),
        color = Color.White.copy(alpha = alpha),
        fontSize = fontSize,
        fontWeight = fontWeight
    )
}

/**
 * Displays the label below the number picker
 */
@Composable
fun NumberPickerLabel(
    label: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = label,
        color = Color.White.copy(alpha = 0.5f),
        fontSize = 14.sp,
        modifier = modifier
    )
} 