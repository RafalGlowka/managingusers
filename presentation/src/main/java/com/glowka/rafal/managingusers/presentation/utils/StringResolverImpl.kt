package com.glowka.rafal.managingusers.presentation.utils

import android.content.Context
import com.glowka.rafal.managingusers.domain.utils.StringResolver

class StringResolverImpl(
  val context: Context,
) : StringResolver {

  override fun invoke(resId: Int): String {
    return context.getString(resId)
  }

  override fun invoke(resId: Int, vararg args: Any): String {
    return context.getString(resId, *args)
  }
}