@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.fury.labs.professionalworkshop.orders

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fury.labs.professionalworkshop.models.Orders
import com.fury.labs.professionalworkshop.repos.Resources
import com.fury.labs.professionalworkshop.ui.theme.ProfessionalWorkshopTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrdersScreen(
    ordersViewModel: OrdersViewModel?,
    onOrderClicked: (id:String) -> Unit,
    navToDetailScreen: () -> Unit,
    navToLoginScreen:() -> Unit
) {
    val orderUiState = ordersViewModel?.orderUiState ?: OrderUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }

    var selectedProduct: Orders? by remember {
        mutableStateOf(null)
    }

    val userId = ordersViewModel?.user?.uid

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = Unit){
        if (userId != null) {
            ordersViewModel.getUserOrders(userId = userId)
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navToDetailScreen.invoke()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }, topBar = {
            TopAppBar(
                title = {
                    Text(text = "Professional Workshop")
                },
                navigationIcon = {},
                actions = {
                    IconButton(onClick = {
                        ordersViewModel?.signOut()
                        navToLoginScreen.invoke()
                    }) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                    }
                }
            )
        }
    ){ padding ->
        Column(
            modifier = Modifier
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when(orderUiState.ordersList) {
                is Resources.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(align = Alignment.Center)
                    )
                }

                is Resources.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LazyVerticalGrid(
                            cells = GridCells.Fixed(1),
                            contentPadding = PaddingValues(16.dp)
                        ) {
                            items(orderUiState.ordersList.data ?: emptyList()){
                                OrderItems(orders = it, onLongClick = {
                                    openDialog = true
                                    selectedProduct = it
                                }) {
                                    onOrderClicked.invoke(it.documentId)
                                }
                            }
                        }
                    }
                }

                else -> {
                    Text(
                        text = orderUiState.ordersList.throwable?.localizedMessage
                            ?: "Unknown Error Happened",
                        color = Color.Red
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = ordersViewModel?.hasUser){
        if (ordersViewModel?.hasUser == null){
            navToLoginScreen.invoke()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OrderItems(
    orders: Orders,
    onLongClick: () -> Unit,
    onClick: () -> Unit

) {
    Card(modifier = Modifier
        .combinedClickable(
            onClick = { onClick.invoke() },
            onLongClick = { onLongClick.invoke() }
        )
        .padding(8.dp)
        .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column {

            Text(
                text = orders.title,
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Clip,
                modifier = Modifier.padding(4.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            CompositionLocalProvider(
                LocalContentAlpha provides ContentAlpha.disabled
            ) {
                Text(
                    text = orders.description,
                    style = MaterialTheme.typography.body1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(4.dp),
                    maxLines = 4
                )
            }
        }
    }
}

@Preview
@Composable
fun PrevOrderScreen() {
    ProfessionalWorkshopTheme {
        OrdersScreen(
            ordersViewModel = null,
            onOrderClicked = {},
            navToDetailScreen = {  }) {

        }
    }
}
