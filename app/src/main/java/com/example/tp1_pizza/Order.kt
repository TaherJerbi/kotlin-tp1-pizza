package com.example.tp1_pizza

data class Order(
    var firstName: String,
    var lastName: String,
    var address: String,
    var size: String,
    var ingredients: ArrayList<Ingredient>
) : java.io.Serializable {
    fun getPrice(): Int {
        var sizePrice = 0
        if (size == "S") {
            sizePrice = 8
        } else if (size == "M") {
            sizePrice = 12
        } else if (size == "L") {
            sizePrice = 14
        }

        var ingredientPrice = 0
        for (ingredient in ingredients) {
            ingredientPrice += ingredient.price
        }
        return sizePrice + ingredientPrice
    }
}