package com.glowka.rafal.managingusers.presentation.style

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.glowka.rafal.managingusers.presentation.R

object Fonts {
  val NormalFont = FontFamily(
    Font(R.font.roboto_black)
  )
  val BoldFont = FontFamily(
    Font(R.font.roboto_bold),
  )
}

object FontSize {
  val small: TextUnit = 10.sp
  val base: TextUnit = 12.sp
  val big: TextUnit = 15.sp
  val large: TextUnit = 20.sp
}