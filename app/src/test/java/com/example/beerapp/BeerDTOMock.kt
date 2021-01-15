package com.example.beerapp

import com.example.beerapp.model.api.BeerDTO

object BeerDTOMock {

    val beerDTOList = listOf<BeerDTO>(
        BeerDTO(
            123,
            "Alpha Dog",
            "08/2014",
            "The levels of hops vary throughout the range. We love hops, so all four beers are big, bitter ",
            "https://images.punkapi.com/v2/18.png",
            7.3,
            70
        ),
        BeerDTO(
            7654,
            "Russian Doll",
            "09/2017",
            "The levels of hops vary throughout the range. We love hops, so all four beers are big, bitter ",
            "https://images.punkapi.com/v2/30.png",
            6.3,
            60
        ),
        BeerDTO(
            9485,
            "Single hop",
            "09/2013",
            "The levels of hops vary throughout the range. We love hops, so all four beers are big, bitter ",
            "https://images.punkapi.com/v2/30.png",
            6.7,
            70
        )
    )

    val beerDTO = BeerDTO(
        9485,
        "Single hop",
        "09/2013",
        "The levels of hops vary throughout the range. We love hops, so all four beers are big, bitter ",
        "https://images.punkapi.com/v2/30.png",
        6.7,
        70
    )

    val beerDTOById = listOf<BeerDTO>(
        BeerDTO(
            9485,
            "Single hop",
            "09/2013",
            "The levels of hops vary throughout the range. We love hops, so all four beers are big, bitter ",
            "https://images.punkapi.com/v2/30.png",
            6.7,
            70
        )
    )

}