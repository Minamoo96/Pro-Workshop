package com.fury.labs.professionalworkshop.orders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fury.labs.professionalworkshop.models.Orders
import com.fury.labs.professionalworkshop.repos.Resources
import com.fury.labs.professionalworkshop.repos.StorageRepository
import kotlinx.coroutines.launch

class OrdersViewModel(
    private val repository: StorageRepository = StorageRepository()
):ViewModel() {
    var orderUiState by mutableStateOf(OrderUiState())
        private set

    val user = repository.user()

    val hasUser: Boolean
        get() = repository.hasUser()

    private val userId: String
        get() = repository.getUserId()

    fun loadUserOrders(){
        if (hasUser){
            if (userId.isNotBlank()){
                getUserOrders(userId = userId)
            }else{
                orderUiState = orderUiState.copy(ordersList = Resources.Error(
                    throwable = Throwable(message = "User Is Not Logged In")
                ))
            }
        }
    }

    fun signOut() = repository.signOut()

    fun deleteOrder(orderId: String) = repository.deleteOrder(orderId){
        orderUiState = orderUiState.copy(orderUpdated = it)
    }

    fun getUserOrders(userId: String) = viewModelScope.launch {
        repository.getUserOrders(userId).collect{
            orderUiState = orderUiState.copy(ordersList = it)
        }
    }
}

data class OrderUiState(
    val ordersList: Resources<List<Orders>> = Resources.Loading(),
    val ordersAdded: Boolean = false,
    val orderUpdated: Boolean = false
)