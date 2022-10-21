package com.fury.labs.professionalworkshop.home

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.fury.labs.professionalworkshop.detail.DetailScreen
import com.fury.labs.professionalworkshop.detail.DetailViewModel
import com.fury.labs.professionalworkshop.login.LoginScreen
import com.fury.labs.professionalworkshop.login.LoginViewModel
import com.fury.labs.professionalworkshop.login.SignUpScreen
import com.fury.labs.professionalworkshop.orders.OrdersScreen
import com.fury.labs.professionalworkshop.orders.OrdersViewModel
import com.fury.labs.professionalworkshop.repos.AuthRepository

enum class LoginRoutes{
    SignUp,
    SignIn
}

enum class HomeRoutes{
    Home,
    Detail,
    Orders
}

enum class NestedRoutes{
    Main,
    Login
}
@Composable
fun Navigation(
    navController: NavHostController = rememberNavController(),
    repo : AuthRepository = AuthRepository(),
    loginViewModel: LoginViewModel,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel,
    ordersViewModel: OrdersViewModel
) {
    NavHost(
        navController = navController,
        startDestination = if (repo.hasUser()) NestedRoutes.Main.name else NestedRoutes.Login.name
    ) {
        authGraph(navController, loginViewModel)
        homeGraph(
            navController = navController,
            detailViewModel = detailViewModel,
            homeViewModel = homeViewModel,
            ordersViewModel = ordersViewModel
        )
    }

}

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel
){
    navigation(startDestination = LoginRoutes.SignUp.name,
    route = NestedRoutes.Login.name){

        composable(route = LoginRoutes.SignIn.name) {
            LoginScreen(
                onNavToHomePage = {
                    navController.navigate(NestedRoutes.Main.name) {
                        launchSingleTop = true
                        popUpTo(route = LoginRoutes.SignIn.name) {
                            inclusive = true
                        }
                    }
                },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignUp.name) {
                    launchSingleTop = true
                    popUpTo(LoginRoutes.SignIn.name) {
                        inclusive = true
                    }
                }
            }
        }

        composable(route = LoginRoutes.SignUp.name) {
            SignUpScreen(
                onNavToHomePage = {
                    navController.navigate(NestedRoutes.Main.name) {
                        launchSingleTop = true
                        popUpTo(route = LoginRoutes.SignUp.name) {
                            inclusive = true
                        }
                    }
                },
                loginViewModel = loginViewModel
            ) {
                navController.navigate(LoginRoutes.SignIn.name)
            }
        }
    }
}

fun NavGraphBuilder.homeGraph(
    navController: NavHostController,
    detailViewModel: DetailViewModel,
    homeViewModel: HomeViewModel,
    ordersViewModel: OrdersViewModel
){
    navigation(startDestination = HomeRoutes.Home.name,
    route = NestedRoutes.Main.name){


        composable(HomeRoutes.Home.name){

            Home(
                homeViewModel = homeViewModel,
                onProductClicked = {
                    navController.navigate(HomeRoutes.Detail.name + "?id=$it"){
                        launchSingleTop = true
                    }
                },
                navToDetailScreen = {
                    navController.navigate(HomeRoutes.Detail.name){}
                },
                navToLoginScreen = {
                    navController.navigate(NestedRoutes.Login.name){
                        launchSingleTop = true
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                }, navToOrderScreen = {
                    navController.navigate(HomeRoutes.Orders.name){
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(
            route = HomeRoutes.Detail.name + "?id={id}",
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
                defaultValue = ""
            })
        ){ entry ->
            DetailScreen(detailViewModel = detailViewModel, productId = entry.arguments?.getString("id") as String) {
                navController.navigateUp()
            }
        }

        composable(
            route = HomeRoutes.Orders.name
        ){
            OrdersScreen(
                ordersViewModel = ordersViewModel,
                onOrderClicked = {
                    navController.navigate(HomeRoutes.Detail.name + "?id=$it"){
                        launchSingleTop = true
                    }
                },
                navToDetailScreen = {
                    navController.navigate(HomeRoutes.Detail.name){

                    }
                }, navToLoginScreen = {
                    navController.navigate(NestedRoutes.Login.name){
                        launchSingleTop = true
                        popUpTo(0){
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}






















