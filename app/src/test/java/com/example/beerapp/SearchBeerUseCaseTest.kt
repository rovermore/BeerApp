package com.example.beerapp

import com.example.beerapp.repository.RepositoryImpl
import com.example.beerapp.usecase.SearchBeerUseCase
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchBeerUseCaseTest {

    lateinit var repository: RepositoryImpl
    lateinit var searchBeerUseCaseTest: SearchBeerUseCase

    private val beerList = BeerMock.beerList

    private val query = "lager"
    private val page  = 1

    private val pair = Pair(query, page)

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        repository = Mockito.mock(RepositoryImpl::class.java)
        whenever(repository.searchBeer(query, page)).thenReturn(this@SearchBeerUseCaseTest.beerList)
        searchBeerUseCaseTest = SearchBeerUseCase(repository)
    }

    @Test
    fun `if Repository return beerList then SearchBeerUseCase returns same beerList`() = runBlockingTest {
        val beerListFromUseCase = searchBeerUseCaseTest.requestWithParameter(pair)
        Assert.assertEquals(beerListFromUseCase, this@SearchBeerUseCaseTest.beerList)
        Assert.assertEquals(beerListFromUseCase!![0].id, this@SearchBeerUseCaseTest.beerList[0].id)
        Assert.assertEquals(beerListFromUseCase[0].name, this@SearchBeerUseCaseTest.beerList[0].name)
        Assert.assertEquals(beerListFromUseCase[0].date, this@SearchBeerUseCaseTest.beerList[0].date)
        Assert.assertEquals(beerListFromUseCase[0].description, this@SearchBeerUseCaseTest.beerList[0].description)
        Assert.assertEquals(beerListFromUseCase[0].image, this@SearchBeerUseCaseTest.beerList[0].image)
        Assert.assertEquals(beerListFromUseCase[0].graduation, this@SearchBeerUseCaseTest.beerList[0].graduation)
        Assert.assertEquals(beerListFromUseCase[0].ibu, this@SearchBeerUseCaseTest.beerList[0].ibu)

    }
}