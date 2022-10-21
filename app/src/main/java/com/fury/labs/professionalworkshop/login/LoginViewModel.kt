package com.fury.labs.professionalworkshop.login

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fury.labs.professionalworkshop.repos.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
): ViewModel() {
    val currentUser = repository.currentUser
    val hasUser: Boolean
        get() = repository.hasUser()

    var loginUiState by mutableStateOf(LoginUiState())
        private set

    fun onUsernameChange(username: String){
        loginUiState = loginUiState.copy(username = username)
    }

    fun onPasswordChange(password: String){
        loginUiState = loginUiState.copy(password = password)
    }

    fun onUsernameSignUpChange(usernameSignUp: String){
        loginUiState = loginUiState.copy(usernameSignUp = usernameSignUp)
    }

    fun onPasswordSignUpChange(passwordSignUp: String){
        loginUiState = loginUiState.copy(passwordSignUp = passwordSignUp)
    }

    fun onConfirmPasswordSignUpChange(confirmPasswordSignUp: String){
        loginUiState = loginUiState.copy(confirmPasswordSignUp = confirmPasswordSignUp)
    }

    private fun validateLoginForm() =
        loginUiState.username.isNotBlank() && loginUiState.password.isNotBlank()

    private fun validateSignUpForm() =
        loginUiState.usernameSignUp.isNotBlank() &&
                loginUiState.passwordSignUp.isNotBlank() &&
                loginUiState.confirmPasswordSignUp.isNotBlank()


    fun createUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateSignUpForm()){
                throw IllegalArgumentException("Email and Password Can't Be Empty")
            }else{
                loginUiState = loginUiState.copy(isLoading = true)
                if (loginUiState.passwordSignUp != loginUiState.confirmPasswordSignUp){
                    throw IllegalArgumentException("Passwords Must Match")
                }
                loginUiState = loginUiState.copy(signUpError = null)

                repository.createUser(
                    email = loginUiState.usernameSignUp,
                    password = loginUiState.passwordSignUp
                ){ isSuccessful ->
                    if (isSuccessful){
                        Toast.makeText(context, "Account Created Successfully :)", Toast.LENGTH_SHORT).show()
                        loginUiState = loginUiState.copy(isSuccessLogin = true)
                    }else{
                        Toast.makeText(context, "Error Happened Creating Your Account", Toast.LENGTH_SHORT).show()
                        loginUiState = loginUiState.copy(isSuccessLogin = false)
                    }
                }
            }
        }catch (e: Exception){
            loginUiState = loginUiState.copy(signUpError = e.localizedMessage)
            e.printStackTrace()
        }
        finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

    fun loginUser(context: Context) = viewModelScope.launch {
        try {
            if (!validateLoginForm()){
                throw IllegalArgumentException("Email and Password Can't Be Empty")
            }else{
                loginUiState = loginUiState.copy(isLoading = true)

                loginUiState = loginUiState.copy(loginError = null)

                repository.loginUser(
                    email = loginUiState.username,
                    password = loginUiState.password
                ){ isSuccessful ->
                    if (isSuccessful){
                        Toast.makeText(context, "Logged In Successfully :)", Toast.LENGTH_SHORT).show()
                        loginUiState = loginUiState.copy(isSuccessLogin = true)
                    }else{
                        Toast.makeText(context, "Error Happened Logging In", Toast.LENGTH_SHORT).show()
                        loginUiState = loginUiState.copy(isSuccessLogin = false)
                    }
                }
            }
        }catch (e: Exception){
            loginUiState = loginUiState.copy(loginError = e.localizedMessage)
            e.printStackTrace()
        }
        finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }
    }

}

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val usernameSignUp: String = "",
    val passwordSignUp: String = "",
    val confirmPasswordSignUp: String = "",
    val isLoading: Boolean = false,
    val isSuccessLogin: Boolean = false,
    val signUpError: String? = null,
    val loginError: String? = null
)