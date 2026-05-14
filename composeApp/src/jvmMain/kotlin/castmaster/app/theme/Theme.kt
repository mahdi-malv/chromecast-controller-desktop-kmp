package castmaster.app.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF9FCBFF),
    onPrimary = Color(0xFF003256),
    primaryContainer = Color(0xFF00497A),
    onPrimaryContainer = Color(0xFFD2E4FF),
    secondary = Color(0xFF8CDBC5),
    onSecondary = Color(0xFF00382D),
    secondaryContainer = Color(0xFF005141),
    onSecondaryContainer = Color(0xFFA8F8E0),
    tertiary = Color(0xFFFFB69C),
    onTertiary = Color(0xFF5A1F00),
    tertiaryContainer = Color(0xFF7E320A),
    onTertiaryContainer = Color(0xFFFFDBCF),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF0F141A),
    onBackground = Color(0xFFE1E8EF),
    surface = Color(0xFF151B22),
    onSurface = Color(0xFFE1E8EF),
    surfaceVariant = Color(0xFF2B333D),
    onSurfaceVariant = Color(0xFFB9C5D1),
    outline = Color(0xFF8793A0),
    outlineVariant = Color(0xFF414B56),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006494),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFD0E4FF),
    onPrimaryContainer = Color(0xFF001D32),
    secondary = Color(0xFF006B59),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFA8F2DF),
    onSecondaryContainer = Color(0xFF002019),
    tertiary = Color(0xFF99461F),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFFFDBCF),
    onTertiaryContainer = Color(0xFF351000),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFF6F8FB),
    onBackground = Color(0xFF171C22),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF171C22),
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = Color(0xFF414C58),
    outline = Color(0xFF717D8A),
    outlineVariant = Color(0xFFC1CAD4),
)

@Immutable
data class CastMasterExtendedColors(
    val panel: Color,
    val panelBorder: Color,
    val control: Color,
    val controlEmphasis: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,
)

private val DarkExtendedColors = CastMasterExtendedColors(
    panel = Color(0xFF1A212A),
    panelBorder = Color(0xFF313C48),
    control = Color(0xFF24303C),
    controlEmphasis = Color(0xFF35516C),
    successContainer = Color(0xFF123726),
    onSuccessContainer = Color(0xFFA9F0C8),
)

private val LightExtendedColors = CastMasterExtendedColors(
    panel = Color(0xFFFFFFFF),
    panelBorder = Color(0xFFD3DEE9),
    control = Color(0xFFEAF2FB),
    controlEmphasis = Color(0xFFD4E6FF),
    successContainer = Color(0xFFC6F1D6),
    onSuccessContainer = Color(0xFF002111),
)

private val LocalCastMasterExtendedColors = staticCompositionLocalOf { DarkExtendedColors }

@Composable
@ReadOnlyComposable
fun castMasterColors(): CastMasterExtendedColors = LocalCastMasterExtendedColors.current

private val ExpressiveShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(6.dp),
    large = RoundedCornerShape(8.dp),
    extraLarge = RoundedCornerShape(10.dp),
)

@Composable
fun CastMasterTheme(
    darkTheme: Boolean? = null,
    content: @Composable () -> Unit,
) {
    val useDarkTheme = darkTheme ?: isSystemInDarkTheme()
    val colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme
    val extendedColors = if (useDarkTheme) DarkExtendedColors else LightExtendedColors
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography(),
        shapes = ExpressiveShapes,
    ) {
        androidx.compose.runtime.CompositionLocalProvider(
            LocalCastMasterExtendedColors provides extendedColors,
            content = content,
        )
    }
}
