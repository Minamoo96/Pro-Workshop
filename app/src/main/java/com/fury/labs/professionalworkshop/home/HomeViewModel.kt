package com.fury.labs.professionalworkshop.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fury.labs.professionalworkshop.models.Products
import com.fury.labs.professionalworkshop.repos.Resources
import com.fury.labs.professionalworkshop.repos.StorageRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: StorageRepository = StorageRepository()
) : ViewModel(){
    var homeUiState by mutableStateOf(HomeUiState())

    val user = repository.user()

    val hasUser: Boolean
        get() = repository.hasUser()

    private val userId: String
        get() = repository.getUserId()

    fun loadProducts(){
        if (hasUser) {
            if (userId.isNotBlank()) {
                getUserProducts(userId = userId)
            }
        }else{
            homeUiState = homeUiState.copy(productsList = Resources.Error(
                throwable = Throwable(message = "User Is Not Logged In")
            ))
        }
    }

    fun getUserProducts(userId: String) = viewModelScope.launch {
        repository.getUserProducts(userId).collect{
            homeUiState = homeUiState.copy(productsList = it)
        }
    }


    fun signOut() = repository.signOut()
}

data class HomeUiState(
    val productsList:Resources<List<Products>> = Resources.Loading(),
    val productAdded: Boolean = false,
    val productDeleted: Boolean = false
)