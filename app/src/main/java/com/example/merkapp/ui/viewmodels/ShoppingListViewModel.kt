package com.example.merkapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.merkapp.data.ShoppingList
import com.example.merkapp.data.ShoppingItem
import com.example.merkapp.data.ShoppingListPreferences
import com.example.merkapp.ui.screens.ProductState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShoppingListViewModel(application: Application) : AndroidViewModel(application) {
    private val shoppingListPreferences = ShoppingListPreferences(application.applicationContext)
    private val _shoppingLists = MutableStateFlow<List<ShoppingList>>(emptyList())
    val shoppingLists: StateFlow<List<ShoppingList>> = _shoppingLists

    init {
        loadShoppingLists()
    }

    private fun loadShoppingLists() {
        viewModelScope.launch {
            _shoppingLists.value = shoppingListPreferences.getShoppingLists()
        }
    }

    fun saveNewList(items: Map<String, Pair<Boolean, String>>, productStates: Map<String, ProductState>) {
        viewModelScope.launch {
            val shoppingItems = items.mapValues { (key, pair) ->
                val state = productStates[key] ?: ProductState()
                ShoppingItem(
                    quantity = pair.second,
                    isFound = state.isFound,
                    isNotFound = state.isNotFound
                )
            }

            val newList = ShoppingList(
                items = shoppingItems,
                completed = true
            )

            shoppingListPreferences.saveShoppingList(newList)
            loadShoppingLists()
        }
    }

    fun clearLists() {
        viewModelScope.launch {
            shoppingListPreferences.clearShoppingLists()
            loadShoppingLists()
        }
    }

    fun deleteList(list: ShoppingList) {
        viewModelScope.launch {
            val lists = shoppingListPreferences.getShoppingLists().toMutableList()
            lists.removeAll { it.id == list.id }
            shoppingListPreferences.clearShoppingLists()
            lists.forEach { shoppingListPreferences.saveShoppingList(it) }
            loadShoppingLists()
        }
    }
} 