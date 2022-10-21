package com.fury.labs.professionalworkshop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fury.labs.professionalworkshop.detail.DetailViewModel
import com.fury.labs.professionalworkshop.home.HomeViewModel
import com.fury.labs.professionalworkshop.home.Navigation
import com.fury.labs.professionalworkshop.login.LoginViewModel
import com.fury.labs.professionalworkshop.orders.OrdersViewModel
import com.fury.labs.professionalworkshop.ui.theme.ProfessionalWorkshopTheme

class PostLaunch : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
            val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
            val detailViewModel = viewModel(modelClass = DetailViewModel::class.java)
            val ordersViewModel = viewModel(modelClass = OrdersViewModel::class.java)

            ProfessionalWorkshopTheme {
                Scaffold {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        Navigation(
                            loginViewModel = loginViewModel,
                            detailViewModel = detailViewModel,
                            homeViewModel = homeViewModel,
                            ordersViewModel = ordersViewModel
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    val loginViewModel = viewModel(modelClass = LoginViewModel::class.java)
    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
    val detailViewModel = viewModel(modelClass = DetailViewModel::class.java)
    val ordersViewModel = viewModel(modelClass = OrdersViewModel::class.java)
    ProfessionalWorkshopTheme {
        Scaffold {
            Navigation(
                loginViewModel = loginViewModel,
                detailViewModel = detailViewModel,
                homeViewModel = homeViewModel,
                ordersViewModel = ordersViewModel
            )
        }
    }
}