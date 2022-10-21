package com.fury.labs.professionalworkshop.home

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
import com.fury.labs.professionalworkshop.models.Products
import com.fury.labs.professionalworkshop.repos.Resources
import com.fury.labs.professionalworkshop.ui.theme.ProfessionalWorkshopTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Home(
    homeViewModel: HomeViewModel?,
    onProductClicked: (id:String) -> Unit,
    navToDetailScreen:() -> Unit,
    navToLoginScreen:() -> Unit,
    navToOrderScreen:() -> Unit
) {
    val homeUiState = homeViewModel?.homeUiState ?: HomeUiState()

    var openDialog by remember {
        mutableStateOf(false)
    }

    var selectedProduct:Products? by remember {
        mutableStateOf(null)
    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = Unit){
        homeViewModel?.loadProducts()
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
                navigationIcon = {},
                actions = {
                          IconButton(onClick = {
                              homeViewModel?.signOut()
                              navToLoginScreen.invoke()
                          }) {
                              Icon(imageVector = Icons.Default.ExitToApp, contentDescription = null)
                          }
                },
                title = {
                    Text(text = "Professional Workshop")
                }
            )
        }
    ) { padding ->
        
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(homeUiState.productsList){
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
                            cells = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp)
                        ){
                            items(homeUiState.productsList.data?: emptyList()){ product ->
                                ProductItem(products = product) {
                                    onProductClicked.invoke(product.documentId)
                                }
                            }
                        }
                        OutlinedButton(
                            onClick = { navToOrderScreen.invoke() },
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .align(Alignment.End)

                        ) {
                            Text(text = "Check My Orders")
                        }
                    }
                }
                else -> {
                    Text(
                        text = homeUiState.productsList.throwable?.localizedMessage
                            ?: "Unknown Error Happened",
                        color = Color.Red
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = homeViewModel?.hasUser){
        if (homeViewModel?.hasUser == null){
            navToLoginScreen.invoke()
        }
    }
    
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductItem(
    products: Products,
    onClick: () -> Unit
) {
    Card(modifier = Modifier
        .combinedClickable(
            onClick = { onClick.invoke() }
        )
        .padding(8.dp)
        .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Column {

            Text(
                text = products.title,
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
                    text = products.description,
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
fun PrevHomeScreen() {
    ProfessionalWorkshopTheme {
        Home(
            homeViewModel = null,
            onProductClicked = {},
            navToDetailScreen = { /*TODO*/ },
            navToLoginScreen = { /*TODO*/ }) {

        }
    }
}