package com.fury.labs.professionalworkshop.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fury.labs.professionalworkshop.R
import com.fury.labs.professionalworkshop.ui.theme.ProfessionalWorkshopTheme

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToSignUpPage: () -> Unit
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.loginError != null
    val context = LocalContext.current

    LaunchedEffect(key1 = loginViewModel?.hasUser){
        if (loginViewModel?.hasUser == true){
            onNavToHomePage.invoke()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Image(
            modifier = Modifier
                .size(230.dp),
            painter = painterResource(id = R.drawable.prowork),
            contentDescription = "")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Login User",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.purple_500))
        if (isError){
            Text(text = loginUiState?.loginError?: "Unknown Error",
                color = Color.Red,
                textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 24.dp),
            value = loginUiState?.username?: "",
            onValueChange = { loginViewModel?.onUsernameChange(it) },
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = colorResource(id = R.color.purple_500)
                )},
            label = { Text(text = "Email Address", color = colorResource(id = R.color.purple_500))},
            isError = isError
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 24.dp),
            value = loginUiState?.password?:"",
            onValueChange = {loginViewModel?.onPasswordChange(it)},
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    tint = colorResource(id = R.color.purple_500)
                )},
            label = { Text(text = "Password", color = colorResource(id = R.color.purple_500))},
            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )
        Button(
            onClick = { loginViewModel?.loginUser(context) },
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 36.dp)
                .size(width = 120.dp, height = 45.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.purple_500),
                contentColor = colorResource(id = R.color.purple_500)
            )
        ) {
            Text(text = "Login", color = colorResource(id = R.color.white), fontSize = 18.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "New Or Don't Have Account? ", color = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = { onNavToSignUpPage.invoke() }){
                Text(text = "SIGN UP", color = colorResource(id = R.color.purple_500))
            }
        }
    }

    if (loginUiState?.isLoading == true){
        Card(
            modifier = Modifier
                .size(80.dp),
            elevation = 2.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }
    }

}

@Composable
fun SignUpScreen(
    loginViewModel: LoginViewModel? = null,
    onNavToHomePage: () -> Unit,
    onNavToLoginPage: () -> Unit
) {
    val loginUiState = loginViewModel?.loginUiState
    val isError = loginUiState?.signUpError != null
    val context = LocalContext.current

    LaunchedEffect(key1 = loginViewModel?.hasUser){
        if (loginViewModel?.hasUser == true){
            onNavToHomePage.invoke()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {
        Image(
            modifier = Modifier
                .size(140.dp),
            painter = painterResource(id = R.drawable.ic_person),
            contentDescription = "")
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Create User Account",
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.purple_500))
        if (isError){
            Text(text = loginUiState?.signUpError?: "Unknown Error",
                color = Color.Red,
                textAlign = TextAlign.Center)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 24.dp),
            value = loginUiState?.usernameSignUp?: "",
            onValueChange = { loginViewModel?.onUsernameSignUpChange(it) },
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Person,
                    contentDescription = null,
                    tint = colorResource(id = R.color.purple_500)
                )},
            label = { Text(text = "Email Address",
                color = colorResource(id = R.color.purple_500))},
            isError = isError
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 24.dp),
            value = loginUiState?.passwordSignUp?:"",
            onValueChange = {loginViewModel?.onPasswordSignUpChange(it)},
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    tint = colorResource(id = R.color.purple_500)
                )},
            label = { Text(text = "Password", color = colorResource(id = R.color.purple_500))},
            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 24.dp),
            value = loginUiState?.confirmPasswordSignUp?:"",
            onValueChange = {loginViewModel?.onConfirmPasswordSignUpChange(it)},
            trailingIcon = {
                Icon(imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                    tint = colorResource(id = R.color.purple_500)
                )},
            label = { Text(text = "Confirm Password", color = colorResource(id = R.color.purple_500))},
            visualTransformation = PasswordVisualTransformation(),
            isError = isError
        )

        Button(
            onClick = { loginViewModel?.createUser(context) },
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 36.dp)
                .size(width = 120.dp, height = 45.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.purple_500),
                contentColor = colorResource(id = R.color.purple_500)
            )
        ) {
            Text(text = "Login", color = colorResource(id = R.color.white), fontSize = 18.sp)
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Already Have Account? ", color = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            TextButton(onClick = { onNavToLoginPage.invoke() }){
                Text(text = "SIGN In", color = colorResource(id = R.color.purple_500))
            }
        }
    }
    if (loginUiState?.isLoading == true){
        Card(
            modifier = Modifier
                .size(80.dp),
            elevation = 2.dp
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun LoginUI() {

}

@Composable
fun SignUpUI() {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PrevSignUpScreen() {
    ProfessionalWorkshopTheme {
        Scaffold {
            SignUpScreen(onNavToHomePage = { /*TODO*/ }) {
                
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PrevLoginScreen() {
    ProfessionalWorkshopTheme {
        Scaffold {
            LoginScreen(onNavToHomePage = { /*TODO*/ }) {

            }
        }
    }
}