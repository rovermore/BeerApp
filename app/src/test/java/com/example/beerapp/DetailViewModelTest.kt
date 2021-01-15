package com.example.beerapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.beerapp.screen.detail.DetailViewModel
import com.example.beerapp.usecase.GetBeerDetailUseCase
import com.example.beerapp.utils.ViewState
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var getBeerDetailUseCase: GetBeerDetailUseCase
    private lateinit var detailViewModel: DetailViewModel

    private val beer = BeerMock.beer

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        getBeerDetailUseCase = Mockito.mock(GetBeerDetailUseCase::class.java)
        detailViewModel = DetailViewModel(getBeerDetailUseCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `checks viewState is not empty when request is not null`() = runBlocking {
        whenever(getBeerDetailUseCase.requestWithParameter(1)).thenReturn(beer)
        detailViewModel.initialize(1)
        Truth.assertThat(detailViewModel.viewState.value).isEqualTo(ViewState.Content(beer))
    }

    @Test
    fun `checks viewState is error when request is null`() = runBlocking {
        whenever(getBeerDetailUseCase.requestWithParameter(1)).thenReturn(null)
        detailViewModel.initialize(1)
        Truth.assertThat(detailViewModel.viewState.value).isEqualTo(ViewState.Error)
    }
}