package com.example.beerapp.model.api

import com.google.gson.annotations.SerializedName

data class BeerDTO(
    val id: Int?,
    val name: String?,
    @SerializedName("first_brewed")
    val date: String?,
    val description: String?,
    @SerializedName("image_url")
    val image: String?,
    @SerializedName("abv")
    val graduation: Double?,
    val ibu: Int?
) {
}