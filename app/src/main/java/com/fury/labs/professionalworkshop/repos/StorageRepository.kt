package com.fury.labs.professionalworkshop.repos

import com.fury.labs.professionalworkshop.models.Orders
import com.fury.labs.professionalworkshop.models.Products
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val PRODUCTS_REF = "products"

const val ORDERS_REF = "orders"

class StorageRepository {

    fun user() = Firebase.auth.currentUser

    fun hasUser():Boolean = Firebase.auth.currentUser != null

    fun getUserId():String = Firebase.auth.currentUser?.uid.orEmpty()

    private val productsRef: CollectionReference = Firebase
        .firestore.collection(PRODUCTS_REF)

    private val ordersRef: CollectionReference = Firebase
        .firestore.collection(ORDERS_REF)

    fun getProducts(
    ):Flow<Resources<List<Products>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {

            snapshotStateListener = productsRef
                .orderBy("timestamp")
                .addSnapshotListener { snapshot, error ->
                    val response = if (snapshot != null){
                        val products = snapshot.toObjects(Products::class.java)
                        Resources.Success(data = products)
                    }else{
                        Resources.Error(throwable = error?.cause)
                    }
                    trySend(response)
                }
        }catch (e: Exception){
            trySend(Resources.Error(e.cause))
            e.printStackTrace()
        }
        awaitClose{
            snapshotStateListener?.remove()
        }

    }

    fun getUserProducts(
        userId: String
    ):Flow<Resources<List<Products>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {

            snapshotStateListener = productsRef
                .orderBy("timestamp")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, error ->
                    val response = if (snapshot != null){
                        val products = snapshot.toObjects(Products::class.java)
                        Resources.Success(data = products)
                    }else{
                        Resources.Error(throwable = error?.cause)
                    }
                    trySend(response)
                }
        }catch (e: Exception){
            trySend(Resources.Error(e.cause))
            e.printStackTrace()
        }
        awaitClose{
            snapshotStateListener?.remove()
        }

    }

    fun getUserOrders(
        userId: String
    ):Flow<Resources<List<Orders>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {

            snapshotStateListener = ordersRef
                .orderBy("timestamp")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, error ->
                    val response = if (snapshot != null){
                        val orders = snapshot.toObjects(Orders::class.java)
                        Resources.Success(data = orders)
                    }else{
                        Resources.Error(throwable = error?.cause)
                    }
                    trySend(response)
                }
        }catch (e: Exception){
            trySend(Resources.Error(e.cause))
            e.printStackTrace()
        }
        awaitClose{
            snapshotStateListener?.remove()
        }

    }

    fun getOneProduct(
    productId: String,
    onError: (Throwable?) -> Unit,
    onSuccess: (Products?) -> Unit
    ){
        productsRef
            .document(productId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Products::class.java))
            }
            .addOnFailureListener { error ->
                onError.invoke(error.cause)
            }
    }

    fun getOneOrder(
        orderId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (Orders?) -> Unit
    ){
        ordersRef
            .document(orderId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Orders::class.java))
            }
            .addOnFailureListener { error ->
                onError.invoke(error.cause)
            }
    }

    fun addOrder(
        userId: String,
        username: String,
        phone: String,
        image: Int,
        title: String,
        description: String,
        address: String,
        payment: String,
        timestamp: Timestamp,
        onComplete: (Boolean) -> Unit
    ){
        val documentId = productsRef.document().id
        val order = Orders(
            userId, username, phone, documentId, image, title, description, timestamp
        )

        ordersRef.document(documentId)
            .set(order)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }

    }

    fun deleteOrder(
        orderId: String,
        onComplete: (Boolean) -> Unit
    ){
        ordersRef.document(orderId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun updateOrder(
        orderId: String,
        username: String,
        phone: String,
        onResult: (Boolean) -> Unit
    ){
        val updateData = hashMapOf<String, Any>(
            "username" to username,
            "phone" to phone
        )
        ordersRef.document(orderId)
            .update(updateData)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }

    }

    fun signOut() = Firebase.auth.signOut()

}

sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null
){
    class Loading<T>: Resources<T>()
    class Success<T>(data: T?): Resources<T>(data = data)
    class Error<T>(throwable: Throwable?): Resources<T>(throwable = throwable)
}
