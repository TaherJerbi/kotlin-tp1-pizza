package com.example.tp1_pizza

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView

class OrderPreview : AppCompatActivity() {

    private lateinit var firstNameText: TextView
    private lateinit var lastNameText: TextView
    private lateinit var addressText: TextView
    private lateinit var sizeText: TextView
    private lateinit var ingredientsText: TextView
    private lateinit var totalText: TextView

    private lateinit var sendButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_preview)

        firstNameText = findViewById(R.id.preview_firstName)
        lastNameText = findViewById(R.id.preview_lastName)
        addressText = findViewById(R.id.preview_Address)
        sizeText = findViewById(R.id.preview_size)
        ingredientsText = findViewById(R.id.preview_ingredients)
        totalText = findViewById(R.id.preview_total)
        sendButton = findViewById(R.id.button_email)
        
        val order: Order? = intent.getSerializableExtra("order") as Order?
        if (order != null) {
            firstNameText.text = order.firstName
            lastNameText.text = order.lastName
            addressText.text = order.address
            sizeText.text = "Size : ${order.size}"
            val ingredients = StringBuffer()
            for (ingredient in order.ingredients) {
                ingredients.append("${ingredient.name} : ${ingredient.price}DT \n")
            }
            ingredientsText.text = ingredients.toString()

            totalText.text = "Total: ${order.getPrice().toString()}DT"

            sendButton.setOnClickListener {
                val intentEmail = Intent(Intent.ACTION_SEND, Uri.parse("mailto:"))
                intentEmail.putExtra(Intent.EXTRA_EMAIL, "pizza@example.com")
                intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Delivery for ${order.firstName}")
                intentEmail.putExtra(
                    Intent.EXTRA_TEXT, "" +
                            "First Name: ${order.firstName}" +
                            "\nLast Name: ${order.lastName}" +
                            "\nSize : ${order.size}" +
                            "\nIngredients : \n" +
                            ingredients.toString() +
                            "\nTotal: ${order.getPrice()}DT"
                )
                intentEmail.setType("text/plain")
                startActivity(Intent.createChooser(intentEmail, "Choose an email client"))
            }
        }
    }
}