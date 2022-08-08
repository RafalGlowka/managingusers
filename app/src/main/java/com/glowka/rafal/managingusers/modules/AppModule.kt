package com.glowka.rafal.managingusers.modules

import com.glowka.rafal.managingusers.domain.service.SnackBarService
import com.glowka.rafal.managingusers.presentation.service.ToastServiceImpl
import com.glowka.rafal.managingusers.domain.service.ToastService
import com.glowka.rafal.managingusers.domain.utils.CoroutineErrorHandler
import com.glowka.rafal.managingusers.domain.utils.StringResolver
import com.glowka.rafal.managingusers.presentation.R
import com.glowka.rafal.managingusers.presentation.architecture.FragmentActivityAttachment
import com.glowka.rafal.managingusers.presentation.architecture.FragmentNavigatorImpl
import com.glowka.rafal.managingusers.presentation.architecture.ScreenNavigator
import com.glowka.rafal.managingusers.presentation.service.SnackBarServiceImpl
import com.glowka.rafal.managingusers.presentation.utils.StringResolverImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.koin.dsl.binds

val appModule = module {

  single<StringResolver> {
    StringResolverImpl(
      context = androidContext()
    )
  }

  single<ToastService> {
    ToastServiceImpl(
      context = androidContext()
    )
  }

  single<SnackBarService> {
    SnackBarServiceImpl()
  }

  single<CoroutineErrorHandler> {
    CoroutineErrorHandler(toastService = get())
  }

  single {
    FragmentNavigatorImpl(
      containerId = R.id.fragment_container
    )
  } binds(arrayOf(
    FragmentActivityAttachment::class,
    ScreenNavigator::class
  ))
}