package com.glowka.rafal.managingusers.domain.utils

interface StringResolver {
  operator fun invoke(resId: Int): String
  operator fun invoke(resId: Int, vararg args: Any): String
}