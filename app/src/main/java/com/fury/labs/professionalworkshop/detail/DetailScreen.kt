package com.fury.labs.professionalworkshop.detail

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fury.labs.professionalworkshop.R
import com.fury.labs.professionalworkshop.ui.theme.ProfessionalWorkshopTheme
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailScreen(
    detailViewModel: DetailViewModel?,
    productId: String,
    onNavigate: () -> Unit
) {
    val detailUiState = detailViewModel?.detailUiState ?: DetailUiState()
    val isFormNotBlank = detailUiState.title.isNotBlank() && detailUiState.image.toString().isNotBlank() &&
            detailUiState.description.isNotBlank() && detailUiState.phone.isNotBlank()

    val isProductIdNotBlank = productId.isNotBlank()

    LaunchedEffect(key1 = Unit){
        if (isProductIdNotBlank){
            detailViewModel?.getSingleProduct(productId)
        }else{
            detailViewModel?.resetState()
        }
    }

    val scope = rememberCoroutineScope()

    val scaffoldState = rememberScaffoldState()
    
    Scaffold(
        scaffoldState = scaffoldState
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (detailUiState.orderAdded) {
                scope.launch {
                    scaffoldState.snackbarHostState
                        .showSnackbar("Order Placed Successfully, You'll Be Contacted Very Soon")
                    detailViewModel?.resetState()
                    onNavigate.invoke()
                }
            }

            if (detailUiState.orderUpdated) {
                scope.launch {
                    scaffoldState.snackbarHostState
                        .showSnackbar("Your order Was Successfully Updated, You'll Contacted Soon")
                    detailViewModel?.resetState()
                    onNavigate.invoke()
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Order Details", color = Color.LightGray)

            OutlinedTextField(value = detailUiState.title, onValueChange = {
                detailViewModel?.onTitleChanged(it)
            }, label = { Text(text = "Title", color = colorResource(id = R.color.purple_500)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 24.dp)
            )

            OutlinedTextField(value = detailUiState.description, onValueChange = {
                detailViewModel?.onDescriptionChanged(it)
            }, label = { Text(text = "Description", color = colorResource(id = R.color.purple_500)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "User Data", color = Color.LightGray)
            Spacer(modifier = Modifier.height(4.dp))

            OutlinedTextField(value = detailUiState.username, onValueChange = {
                detailViewModel?.onUserameChanged(it)
            }, label = { Text(text = "Username", color = colorResource(id = R.color.purple_500)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 24.dp)
            )

            OutlinedTextField(value = detailUiState.phone, onValueChange = {
                detailViewModel?.onPhoneChanged(it)
            }, label = { Text(text = "Phone Number", color = colorResource(id = R.color.purple_500)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 24.dp)
            )

            OutlinedTextField(value = detailUiState.address, onValueChange = {
                detailViewModel?.onAddressChanged(it)
            }, label = { Text(text = "Address", color = colorResource(id = R.color.purple_500)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp, horizontal = 24.dp)
            )

            SimpleRadioButtonComponent(value = detailUiState.payment, onValueChange = {
                detailViewModel?.onPaymentChanged(it)
            })

            Spacer(modifier = Modifier.height(20.dp))

            if (isFormNotBlank) {
                if (isProductIdNotBlank) {
                    Button(
                        onClick = { detailViewModel?.updateOrder(productId) },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.purple_500)
                        ),
                        modifier = Modifier
                            .size(width = 180.dp, height = 60.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Update Order",
                            fontSize = 16.sp
                        )
                    }
                }else {
                    Button(
                        onClick = { detailViewModel?.makeOrder() },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.purple_500)
                        ),
                        modifier = Modifier
                            .size(width = 180.dp, height = 60.dp)
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = "Make Order",
                            fontSize = 16.sp
                        )
                    }
                }
            }

        }


//        OutlinedTextField(value = detailUiState.title, onValueChange = {})
//
//        OutlinedTextField(value = detailUiState.title, onValueChange = {})

    }

}

@Composable
fun SimpleRadioButtonComponent(value: String, onValueChange: (option: String) -> Unit) {
    val radioOptions = listOf("Cash", "MBok")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    Column(

        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column {
            // below line is use to set data to
            // each radio button in columns.
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                            }
                        )
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val context = LocalContext.current
                    RadioButton(
                        selected = (text == selectedOption),
                        modifier = Modifier
                            .padding(all = Dp(value = 8F)
                            ),
                        onClick = {
                            onOptionSelected(text)
                            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
                            value == text
                            onValueChange.invoke(value)
                        }
                    )
                    Text(
                        text = text,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PrevDetailScreen() {
    ProfessionalWorkshopTheme {
        Scaffold {
            DetailScreen(detailViewModel = null, productId = "") {
                
            }
        }
    }
}