package com.example.merkapp.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

data class ShoppingList(
    val id: String = UUID.randomUUID().toString(),
    val date: String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date()),
    val items: Map<String, ShoppingItem>,
    val completed: Boolean = false
)

data class ShoppingItem(
    val quantity: String,
    val isFound: Boolean = false,
    val isNotFound: Boolean = false,
    val cost: String = ""
)

class ShoppingListPreferences(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveShoppingList(list: ShoppingList) {
        val lists = getShoppingLists().toMutableList()
        
        // Mantener solo las últimas 5 listas
        if (lists.size >= 5) {
            lists.removeAt(0)
        }
        
        lists.add(list)
        
        preferences.edit().apply {
            putString(KEY_SHOPPING_LISTS, gson.toJson(lists))
            apply()
        }
    }

    fun getShoppingLists(): List<ShoppingList> {
        val json = preferences.getString(KEY_SHOPPING_LISTS, "[]")
        val type = object : TypeToken<List<ShoppingList>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    fun updateShoppingList(list: ShoppingList) {
        val lists = getShoppingLists().toMutableList()
        val index = lists.indexOfFirst { it.id == list.id }
        if (index != -1) {
            lists[index] = list
            preferences.edit().apply {
                putString(KEY_SHOPPING_LISTS, gson.toJson(lists))
                apply()
            }
        }
    }

    fun clearShoppingLists() {
        preferences.edit().remove(KEY_SHOPPING_LISTS).apply()
    }

    companion object {
        private const val PREF_NAME = "shopping_list_preferences"
        private const val KEY_SHOPPING_LISTS = "shopping_lists"
    }
} 