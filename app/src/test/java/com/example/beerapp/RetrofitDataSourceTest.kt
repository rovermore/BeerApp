package com.example.beerapp

import com.example.beerapp.client.RetrofitApiClientImpl
import com.example.beerapp.client.RetrofitDataSource
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RetrofitDataSourceTest {

    private lateinit var retrofitApiClientImpl: RetrofitApiClientImpl
    private lateinit var retrofitDataSource: RetrofitDataSource

    private val beerApiListResponse = BeerDTOMock.beerDTOList
    private val beerApiResponse = BeerDTOMock.beerDTOById

    private val beerListResult = BeerMock.beerList
    private val beerResult = BeerMock.beer

    @Before
    fun setup() = runBlockingTest {
        MockitoAnnotations.initMocks(this)
        retrofitApiClientImpl = Mockito.mock(RetrofitApiClientImpl::class.java)
        whenever(retrofitApiClientImpl.searchBeer("lager", 1)).thenReturn(this@RetrofitDataSourceTest.beerApiListResponse)
        whenever(retrofitApiClientImpl.getBeerById(1)).thenReturn(this@RetrofitDataSourceTest.beerApiResponse)
        retrofitDataSource = RetrofitDataSource(retrofitApiClientImpl)
    }


    @Test
    fun `if RetrofitApiClientImpl return beerList then RetrofitDataSource calls searchBeer method returns same beerList`() = runBlockingTest {
        val postListFromClientImpl = retrofitDataSource.searchBeer("lager", 1)
        Assert.assertEquals(postListFromClientImpl, this@RetrofitDataSourceTest.beerListResult)
        assertEquals(postListFromClientImpl!![0].id, this@RetrofitDataSourceTest.beerListResult[0].id)
        assertEquals(postListFromClientImpl[0].name, this@RetrofitDataSourceTest.beerListResult[0].name)
        assertEquals(postListFromClientImpl[0].date, this@RetrofitDataSourceTest.beerListResult[0].date)
        assertEquals(postListFromClientImpl[0].description, this@RetrofitDataSourceTest.beerListResult[0].description)
        assertEquals(postListFromClientImpl[0].image, this@RetrofitDataSourceTest.beerListResult[0].image)
        assertEquals(postListFromClientImpl[0].graduation, this@RetrofitDataSourceTest.beerListResult[0].graduation)
        assertEquals(postListFromClientImpl[0].ibu, this@RetrofitDataSourceTest.beerListResult[0].ibu)
    }

    @Test
    fun `if RetrofitApiClientImpl return user then RetrofitDataSource calls getUserById method returns same user`() = runBlockingTest {
        val userFromClientImpl = retrofitDataSource.getBeerById(1)
        assertEquals(userFromClientImpl!!.id, this@RetrofitDataSourceTest.beerResult.id)
        assertEquals(userFromClientImpl.name, this@RetrofitDataSourceTest.beerResult.name)
        assertEquals(userFromClientImpl.date, this@RetrofitDataSourceTest.beerResult.date)
        assertEquals(userFromClientImpl.description, this@RetrofitDataSourceTest.beerResult.description)
        assertEquals(userFromClientImpl.image, this@RetrofitDataSourceTest.beerResult.image)
        assertEquals(userFromClientImpl.graduation, this@RetrofitDataSourceTest.beerResult.graduation)
        assertEquals(userFromClientImpl.ibu, this@RetrofitDataSourceTest.beerResult.ibu)
    }
}