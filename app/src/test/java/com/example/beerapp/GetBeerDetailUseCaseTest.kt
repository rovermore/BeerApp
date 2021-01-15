package com.example.beerapp

import com.example.beerapp.repository.RepositoryImpl
import com.example.beerapp.usecase.GetBeerDetailUseCase
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetBeerDetailUseCaseTest {

    lateinit var repository: RepositoryImpl
    lateinit var getBeerDetailUseCase: GetBeerDetailUseCase

    private val beer = BeerMock.beer

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        repository = Mockito.mock(RepositoryImpl::class.java)
        whenever(repository.getBeerById(1)).thenReturn(this@GetBeerDetailUseCaseTest.beer)
        getBeerDetailUseCase = GetBeerDetailUseCase(repository)
    }

    @Test
    fun `if Repository return beer then GetBeerDetailUseCase returns same beer`() = runBlockingTest {
        val beerFromUseCase = getBeerDetailUseCase.requestWithParameter(1)
        Assert.assertEquals(beerFromUseCase, this@GetBeerDetailUseCaseTest.beer)
        Assert.assertEquals(beerFromUseCase!!.id, this@GetBeerDetailUseCaseTest.beer.id)
        Assert.assertEquals(beerFromUseCase.name, this@GetBeerDetailUseCaseTest.beer.name)
        Assert.assertEquals(beerFromUseCase.date, this@GetBeerDetailUseCaseTest.beer.date)
        Assert.assertEquals(beerFromUseCase.description, this@GetBeerDetailUseCaseTest.beer.description)
        Assert.assertEquals(beerFromUseCase.image, this@GetBeerDetailUseCaseTest.beer.image)
        Assert.assertEquals(beerFromUseCase.graduation, this@GetBeerDetailUseCaseTest.beer.graduation)
        Assert.assertEquals(beerFromUseCase.ibu, this@GetBeerDetailUseCaseTest.beer.ibu)
    }
}