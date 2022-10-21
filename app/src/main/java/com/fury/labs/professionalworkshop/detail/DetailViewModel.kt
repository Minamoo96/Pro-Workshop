package com.fury.labs.professionalworkshop.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.fury.labs.professionalworkshop.models.Products
import com.fury.labs.professionalworkshop.repos.StorageRepository
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseUser

class DetailViewModel(
    val repository: StorageRepository = StorageRepository()
): ViewModel() {
    var detailUiState by mutableStateOf(DetailUiState())
        private set

    private val hasUser: Boolean
        get() = repository.hasUser()

    private val user: FirebaseUser?
        get() = repository.user()

    fun onUserameChanged(username: String){
        detailUiState = detailUiState.copy(username = username)
    }

    fun onPhoneChanged(phone: String){
        detailUiState = detailUiState.copy(phone = phone)
    }

    fun onAddressChanged(address: String){
        detailUiState = detailUiState.copy(address = address)
    }

    fun onPaymentChanged(payment: String){
        detailUiState = detailUiState.copy(payment = payment)
    }

    fun onImageChanged(image: Int){
        detailUiState = detailUiState.copy(image = image)
    }

    fun onTitleChanged(title: String){
        detailUiState = detailUiState.copy(title = title)
    }

    fun onDescriptionChanged(description: String){
        detailUiState = detailUiState.copy(description = description)
    }

    fun makeOrder(){
        if (hasUser){
            repository.addOrder(
                userId = user!!.uid,
                title = detailUiState.title,
                username = detailUiState.username,
                phone = detailUiState.phone,
                image = detailUiState.image,
                description = detailUiState.description,
                timestamp = Timestamp.now(),
                address = detailUiState.address,
                payment = detailUiState.payment
            ){
                detailUiState = detailUiState.copy(orderAdded = it)
            }
        }
    }

    fun setEditFields(products: Products){
        detailUiState = detailUiState.copy(
            title = products.title,
            description = products.description,
            price = products.price
        )
    }

    fun getSingleProduct(productId: String){
        repository.getOneProduct(
            productId = productId,
            onError = {}
        ){
            detailUiState = detailUiState.copy(selectedProduct = it)
            detailUiState.selectedProduct?.let { it1 -> setEditFields(it1) }
        }
    }

    fun updateOrder(
        orderId: String
    ){
        repository.updateOrder(
            orderId = orderId,
            phone = detailUiState.phone,
            username = detailUiState.username
        ){
            detailUiState = detailUiState.copy(orderUpdated = it)
        }
    }

    fun resetOrderAddedStatus(){
        detailUiState = detailUiState.copy(orderAdded = false, orderUpdated = false)
    }

    fun resetState(){
        detailUiState = DetailUiState()
    }

}

data class DetailUiState(
    val username: String = "",
    val phone: String = "",
    val image: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val address: String = "",
    val payment: String = "",
    val orderAdded: Boolean = false,
    val orderUpdated: Boolean = false,
    val selectedProduct: Products? = null
)