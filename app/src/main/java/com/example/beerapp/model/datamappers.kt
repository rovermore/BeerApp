package com.example.beerapp.model

import com.example.beerapp.model.api.BeerDTO
import com.example.beerapp.model.canon.Beer

fun BeerDTO.toBeer() = Beer(
    id,
    name,
    date,
    description,
    image,
    graduation,
    ibu
)