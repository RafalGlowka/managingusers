package com.glowka.rafal.managingusers.presentation

import com.glowka.rafal.managingusers.domain.service.ToastService
import com.glowka.rafal.managingusers.domain.utils.CoroutineErrorHandler
import com.glowka.rafal.managingusers.domain.utils.StringResolver
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module

open class ViewModelTest {

  @MockK
  protected lateinit var toastService: ToastService

  @MockK
  protected lateinit var stringResolver: StringResolver

  fun initMocks() {
    MockKAnnotations.init(this)
    every { stringResolver.invoke(any()) } returns "Mocked string"
    every { stringResolver.invoke(any(), any()) } returns "Mocked string"
  }

  private fun initKoinForViewModelTest() {

    startKoin {
      modules(listOf<Module>(
        module {
          single<CoroutineErrorHandler> {
            CoroutineErrorHandler(toastService = get())
          }
          single<ToastService> {
            toastService
          }
        }
      ))
      createEagerInstances()
    }
  }

  private fun cleanUpKoin() {
    stopKoin()
  }

  fun runViewModelTest(
    testBody: suspend TestScope.() -> Unit
  ) = runTest {
    initKoinForViewModelTest()
    val testDispatcher = UnconfinedTestDispatcher(testScheduler)
    Dispatchers.setMain(testDispatcher)
    try {
      testBody()
    } finally {
      cleanUpKoin()
    }
  }

}


