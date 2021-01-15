package com.example.beerapp

import com.example.beerapp.client.RetrofitDataSource
import com.example.beerapp.repository.RepositoryImpl
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RepositoryImplTest {

    private lateinit var retrofitDataSource: RetrofitDataSource
    private lateinit var repositoryImpl: RepositoryImpl

    private val beerListResult = BeerMock.beerList
    private val beerResult = BeerMock.beer

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        retrofitDataSource = Mockito.mock(RetrofitDataSource::class.java)
        whenever(retrofitDataSource.searchBeer("lager", 1)).thenReturn(this@RepositoryImplTest.beerListResult)
        whenever(retrofitDataSource.getBeerById(1)).thenReturn(this@RepositoryImplTest.beerResult)
        repositoryImpl = RepositoryImpl(retrofitDataSource)
    }

    @Test
    fun `if RetrofitDataSource return beerList then RepositoryImpl calls searchBeer method and returns beerList`() = runBlockingTest {
        val beerListFromDataSource = repositoryImpl.searchBeer("lager", 1)
        Assert.assertEquals(beerListFromDataSource, this@RepositoryImplTest.beerListResult)
        Assert.assertEquals(beerListFromDataSource!![0].id, this@RepositoryImplTest.beerListResult[0].id)
        Assert.assertEquals(beerListFromDataSource[0].name, this@RepositoryImplTest.beerListResult[0].name)
        Assert.assertEquals(beerListFromDataSource[0].date, this@RepositoryImplTest.beerListResult[0].date)
        Assert.assertEquals(beerListFromDataSource[0].description, this@RepositoryImplTest.beerListResult[0].description)
        Assert.assertEquals(beerListFromDataSource[0].image, this@RepositoryImplTest.beerListResult[0].image)
        Assert.assertEquals(beerListFromDataSource[0].graduation, this@RepositoryImplTest.beerListResult[0].graduation)
        Assert.assertEquals(beerListFromDataSource[0].ibu, this@RepositoryImplTest.beerListResult[0].ibu)
    }

    @Test
    fun `if RetrofitDataSource return beer then RepositoryImpl calls getBeerById method returns same beer`() = runBlockingTest {
        val beerFromDataSource = repositoryImpl.getBeerById(1)
        Assert.assertEquals(beerFromDataSource, this@RepositoryImplTest.beerResult)
        Assert.assertEquals(beerFromDataSource!!.id, this@RepositoryImplTest.beerResult.id)
        Assert.assertEquals(beerFromDataSource.name, this@RepositoryImplTest.beerResult.name)
        Assert.assertEquals(beerFromDataSource.date, this@RepositoryImplTest.beerResult.date)
        Assert.assertEquals(beerFromDataSource.description, this@RepositoryImplTest.beerResult.description)
        Assert.assertEquals(beerFromDataSource.image, this@RepositoryImplTest.beerResult.image)
        Assert.assertEquals(beerFromDataSource.graduation, this@RepositoryImplTest.beerResult.graduation)
        Assert.assertEquals(beerFromDataSource.ibu, this@RepositoryImplTest.beerResult.ibu)
    }
}