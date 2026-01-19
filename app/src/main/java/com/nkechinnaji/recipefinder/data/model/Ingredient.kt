package com.nkechinnaji.recipefinder.data.model

import com.google.gson.annotations.SerializedName

data class IngredientsResponse(
    @SerializedName("meals")
    val ingredients: List<Ingredient>?
)

data class Ingredient(
    @SerializedName("idIngredient")
    val id: String,
    @SerializedName("strIngredient")
    val name: String,
    @SerializedName("strDescription")
    val description: String?,
    @SerializedName("strType")
    val type: String?
)
