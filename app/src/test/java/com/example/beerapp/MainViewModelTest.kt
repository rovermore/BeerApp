package com.example.beerapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.beerapp.screen.main.MainViewModel
import com.example.beerapp.usecase.SearchBeerUseCase
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
class MainViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var searchBeerUseCase: SearchBeerUseCase
    private lateinit var mainViewModel: MainViewModel

    private val beerList = BeerMock.beerList
    private val emptyBeerList = BeerMock.emptyBeerList

    private val query = "lager"
    private val page  = 1

    private val pair = Pair(query, page)

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        searchBeerUseCase = Mockito.mock(SearchBeerUseCase::class.java)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `checks viewState is error when request is null`() = runBlocking {
        whenever(searchBeerUseCase.requestWithParameter(pair)).thenReturn(null)
        mainViewModel = MainViewModel(searchBeerUseCase)
        mainViewModel.loadData(query, page)
        Truth.assertThat(mainViewModel.viewState.value).isEqualTo(ViewState.Error)
    }

    @Test
    fun `checks viewState is error when request is empty`() = runBlocking {
        whenever(searchBeerUseCase.requestWithParameter(pair)).thenReturn(emptyBeerList)
        mainViewModel = MainViewModel(searchBeerUseCase)
        mainViewModel.loadData(query, page)
        Truth.assertThat(mainViewModel.viewState.value).isEqualTo(ViewState.FinishLoading)
    }

    @Test
    fun `checks viewState is not empty when request is not empty`() = runBlocking {
        whenever(searchBeerUseCase.requestWithParameter(pair)).thenReturn(beerList)
        mainViewModel = MainViewModel(searchBeerUseCase)
        mainViewModel.loadData(query, page)
        Truth.assertThat(mainViewModel.viewState.value).isEqualTo(ViewState.Content(beerList))
    }

}