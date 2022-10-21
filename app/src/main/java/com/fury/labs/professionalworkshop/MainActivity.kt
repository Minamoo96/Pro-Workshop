package com.fury.labs.professionalworkshop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.fury.labs.professionalworkshop.prefs.SliderPref
import com.fury.labs.professionalworkshop.repos.AuthRepository
import com.fury.labs.professionalworkshop.ui.theme.ProfessionalWorkshopTheme
import kotlinx.coroutines.Runnable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfessionalWorkshopTheme {
                Modifier.fillMaxWidth()
                Modifier.fillMaxHeight()
                Scaffold {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.White
                    ) {
                        StartSetup()
                    }
                }
            }
        }
    }
}

@Composable
fun StartSetup() {
    val context = LocalContext.current
    val getScroll = SliderPref(context).getScrolled.collectAsState(initial = "")
    val activity = (LocalContext.current as? Activity)
    val repository : AuthRepository = AuthRepository()
    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Imager()
        val handler = Handler()
        handler.postDelayed(Runnable {
            if (getScroll.value == "Yes") {
                context.startActivity(Intent(context, PostLaunch::class.java))
                activity?.finish()
            } else{
                context.startActivity(Intent(context, OnBoarding::class.java))
                activity?.finish()
            } }, 2000)
    }
}

@Composable
fun Imager() {
    Image(painterResource(id = R.drawable.prowork), contentDescription = "")
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    ProfessionalWorkshopTheme {
        Scaffold {
            StartSetup()
        }
    }
}