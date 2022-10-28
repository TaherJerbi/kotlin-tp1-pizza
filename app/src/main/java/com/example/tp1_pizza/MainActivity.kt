package com.example.tp1_pizza

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var allIngredients: ArrayList<Ingredient>


    private lateinit var sizeGroup: RadioGroup
    private lateinit var size_s: RadioButton
    private lateinit var size_m: RadioButton
    private lateinit var size_l: RadioButton
    private lateinit var firstNameEdit: TextInputEditText
    private lateinit var lastNameEdit: TextInputEditText
    private lateinit var addressEdit: TextInputEditText
    private lateinit var buttonNext: MaterialButton

    private var order: Order = Order("", "", "", "", arrayListOf<Ingredient>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        allIngredients = arrayListOf<Ingredient>()
        getIngredients()

        sizeGroup = findViewById(R.id.form_size_group)
        size_s = findViewById(R.id.form_size_S)
        size_m = findViewById(R.id.form_size_M)
        size_l = findViewById(R.id.form_size_L)
        firstNameEdit = findViewById(R.id.form_firstName)
        lastNameEdit = findViewById(R.id.form_lastName)
        addressEdit = findViewById(R.id.form_Address)
        buttonNext = findViewById(R.id.button_next)

        sizeGroup.setOnCheckedChangeListener { _, _ ->
            if (size_s.isChecked) {
                order.size = "S"
            }
            if (size_m.isChecked) {
                order.size = "M"
            }
            if (size_l.isChecked) {
                order.size = "L"
            }
        }

        firstNameEdit.addTextChangedListener {
            order.firstName = firstNameEdit.text.toString()
        }
        lastNameEdit.addTextChangedListener {
            order.lastName = lastNameEdit.text.toString()
        }
        addressEdit.addTextChangedListener {
            order.address = addressEdit.text.toString()
        }

        buttonNext.setOnClickListener {
            if (validateOrder(order)) {
                val intent = Intent(it.context, OrderPreview::class.java)
                intent.putExtra("order", order as java.io.Serializable)
                startActivity(intent)
            } else {
                Toast.makeText(this@MainActivity, "Please complete the form", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun getIngredients() {
        allIngredients.add(Ingredient("Fromage", 2))
        allIngredients.add(Ingredient("Pepperoni", 4))
        allIngredients.add(Ingredient("Champignon", 2))
        allIngredients.add(Ingredient("Thon", 3))
        allIngredients.add(Ingredient("Jambon", 3))
        allIngredients.add(Ingredient("Ananas", 5))


        newRecyclerView = findViewById(R.id.ingredientsList)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        newRecyclerView.adapter =
            IngredientAdapter(
                allIngredients,
                onItemClicked = { position -> onItemClicked(position) })
    }

    private fun onItemClicked(position: Int) {
        Log.d("onItemClicked", position.toString())
        val added = order.ingredients.find { it.name == allIngredients[position].name }
        if (added == null) {
            Log.d("onItemClicked", "Add $position")
            order.ingredients.add(allIngredients[position])
            Log.d("onItemClicked", "Add Success $position")
            Toast.makeText(
                this@MainActivity,
                "Added ${allIngredients[position].name} to ingredients",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Log.d("onItemClicked", "Remove $position")
            order.ingredients.remove(allIngredients[position])
            Log.d("onItemClicked", "Remove Success $position")
            Toast.makeText(
                this@MainActivity,
                "Removed ${allIngredients[position].name} from ingredients",
                Toast.LENGTH_LONG
            ).show()
        }
        Log.d("onItemClicked", order.toString())
        for (ingredient: Ingredient in order.ingredients) {
            Log.d("onItemClicked", ingredient.name)
        }

    }

    private fun validateOrder(order: Order): Boolean {
        return order.size.isNotEmpty() && order.firstName.isNotEmpty() && order.lastName.isNotEmpty() && order.address.isNotEmpty()
    }

}