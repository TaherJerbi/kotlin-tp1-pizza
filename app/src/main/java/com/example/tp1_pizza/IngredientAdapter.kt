package com.example.tp1_pizza

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class IngredientAdapter(
    private val ingredientsList: ArrayList<Ingredient>,
    private val onItemClicked: (position: Int) -> Unit
) :
    RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return IngredientViewHolder(itemView, onItemClicked)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        val currentItem = ingredientsList[position]
        holder.name.text = currentItem.name
        holder.price.text = currentItem.price.toString() + "DT"
    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    class IngredientViewHolder(itemView: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val name: MaterialTextView = itemView.findViewById(R.id.ingredient_name)
        val price: MaterialTextView = itemView.findViewById(R.id.ingredient_price)
        val button: MaterialButton = itemView.findViewById(R.id.ingredient_button)
        private var selected = false

        init {
            button.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            selected = !selected
            if (selected) {
                button.text = "Remove"
            }
            if (!selected) {
                button.text = "Add"
            }
            onItemClicked(position)
        }


    }
}