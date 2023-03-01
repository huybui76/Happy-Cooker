package com.example.happycook.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.happycook.models.FoodRecipe
import com.example.happycook.util.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}