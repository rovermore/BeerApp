package com.example.beerapp

import com.example.beerapp.model.toBeer
import junit.framework.TestCase
import org.junit.Test

class DataMappersTest {

    private val beerDTO = BeerDTOMock.beerDTO
    private val beer = BeerMock.beer

    @Test
    fun `movieApiDTO to movie`() {
        val beer = beerDTO.toBeer()
        TestCase.assertEquals(beer, this.beer)
    }
}