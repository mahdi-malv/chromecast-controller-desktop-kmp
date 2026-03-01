package castmaster.app.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// Night City — Cyberpunk dark theme
// Primary: neon cyan  |  Secondary: electric magenta  |  Tertiary: toxic yellow
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00F5FF),          // neon cyan
    onPrimary = Color(0xFF001A1C),
    primaryContainer = Color(0xFF003B40),
    onPrimaryContainer = Color(0xFF80FAFF),
    secondary = Color(0xFFFF2D9B),        // electric magenta
    onSecondary = Color(0xFF1A0010),
    secondaryContainer = Color(0xFF5C0035),
    onSecondaryContainer = Color(0xFFFF9DD0),
    tertiary = Color(0xFFE8FF00),         // toxic yellow
    onTertiary = Color(0xFF1A1A00),
    tertiaryContainer = Color(0xFF3D3D00),
    onTertiaryContainer = Color(0xFFF5FF80),
    error = Color(0xFFFF4444),
    onError = Color(0xFF1A0000),
    errorContainer = Color(0xFF5C0000),
    onErrorContainer = Color(0xFFFFAAAA),
    background = Color(0xFF08090D),       // near-black with a blue tint
    onBackground = Color(0xFFD0E8F0),
    surface = Color(0xFF0D0F18),          // deep navy-black
    onSurface = Color(0xFFD0E8F0),
    surfaceVariant = Color(0xFF1A1E2E),   // dark slate
    onSurfaceVariant = Color(0xFF8BAABB),
    outline = Color(0xFF00C8D4),          // dim cyan
    outlineVariant = Color(0xFF1E2A30),
)

// Keep light scheme as a fallback (still uses cyberpunk palette but lighter)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006B75),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFB2F0F5),
    onPrimaryContainer = Color(0xFF001F23),
    secondary = Color(0xFF8B005C),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFD8EC),
    onSecondaryContainer = Color(0xFF38001E),
    tertiary = Color(0xFF5A5A00),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFF5FF80),
    onTertiaryContainer = Color(0xFF1A1A00),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFF4FBFC),
    onBackground = Color(0xFF0D1E21),
    surface = Color(0xFFF4FBFC),
    onSurface = Color(0xFF0D1E21),
    surfaceVariant = Color(0xFFD8EEF2),
    onSurfaceVariant = Color(0xFF3C5558),
    outline = Color(0xFF006B75),
    outlineVariant = Color(0xFFB2D4D8),
)

private val ExpressiveShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(6.dp),
    large = RoundedCornerShape(8.dp),
    extraLarge = RoundedCornerShape(10.dp),
)

@Composable
fun CastMasterTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        shapes = ExpressiveShapes,
        content = content,
    )
}
