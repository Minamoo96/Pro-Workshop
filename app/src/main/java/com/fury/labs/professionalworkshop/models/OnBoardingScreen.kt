package com.fury.labs.professionalworkshop.models

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.fury.labs.professionalworkshop.R

@Composable
fun ScreenUI(page: Page) {
    Column(horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = page.image),
            contentDescription = "",
            modifier = Modifier.size(200.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = page.title,
            color = colorResource(id = R.color.purple_700),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = page.body,
            color = colorResource(id = R.color.black),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}
