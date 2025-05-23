package ru.itmo.se.mad.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val SFProDisplay = FontFamily(
    Font(R.font.sf_pro_display_ultra_light, FontWeight.Thin, FontStyle.Normal),
    Font(R.font.sf_pro_display_ultra_light_italic, FontWeight.Thin, FontStyle.Italic),

    Font(R.font.sf_pro_display_thin, FontWeight.ExtraLight, FontStyle.Normal),
    Font(R.font.sf_pro_display_thin_italic, FontWeight.ExtraLight, FontStyle.Italic),

    Font(R.font.sf_pro_display_light, FontWeight.Light, FontStyle.Normal),
    Font(R.font.sf_pro_display_light_italic, FontWeight.Light, FontStyle.Italic),

    Font(R.font.sf_pro_display_regular, FontWeight.Normal, FontStyle.Normal),
    Font(R.font.sf_pro_display_regular_italic, FontWeight.Normal, FontStyle.Italic),

    Font(R.font.sf_pro_display_medium, FontWeight.Medium, FontStyle.Normal),
    Font(R.font.sf_pro_display_medium_italic, FontWeight.Medium, FontStyle.Italic),

    Font(R.font.sf_pro_display_semibold, FontWeight.SemiBold, FontStyle.Normal),
    Font(R.font.sf_pro_display_semibold_italic, FontWeight.SemiBold, FontStyle.Italic),

    Font(R.font.sf_pro_display_bold, FontWeight.Bold, FontStyle.Normal),
    Font(R.font.sf_pro_display_bold_italic, FontWeight.Bold, FontStyle.Italic),

    Font(R.font.sf_pro_display_heavy, FontWeight.ExtraBold, FontStyle.Normal),
    Font(R.font.sf_pro_display_heavy_italic, FontWeight.ExtraBold, FontStyle.Italic),

    Font(R.font.sf_pro_display_black, FontWeight.Black, FontStyle.Normal),
    Font(R.font.sf_pro_display_black_italic, FontWeight.Black, FontStyle.Italic),
)