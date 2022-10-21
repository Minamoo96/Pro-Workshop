
package com.fury.labs.professionalworkshop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fury.labs.professionalworkshop.models.ScreenUI
import com.fury.labs.professionalworkshop.models.onBoardPages
import com.fury.labs.professionalworkshop.prefs.SliderPref
import com.fury.labs.professionalworkshop.ui.theme.ProfessionalWorkshopTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


class OnBoarding : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfessionalWorkshopTheme {
                Scaffold {
                    // A surface container using the 'background' color from the theme
                    OnBoardingUI()
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingUI() {
    val pagerState = rememberPagerState(pageCount = 4)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val dataShot = SliderPref(context)
    val activity = (LocalContext.current as? Activity)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Skip", modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp)
            .clickable {
                scope.launch {
                    dataShot.setScroll("Yes")
                }
                context.startActivity(
                    Intent(
                        context,
                        PostLaunch::class.java
                    )
                )
                activity?.finish()
            })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            ScreenUI(page = onBoardPages[page])
        }

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally),
            activeColor = colorResource(id = R.color.purple_700)
        )
        this@Column.AnimatedVisibility(visible = pagerState.currentPage == 3) {
            OutlinedButton(
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .width(200.dp)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                onClick = {
                    scope.launch {
                        dataShot.setScroll("Yes")
                    }
                    context.startActivity(
                        Intent(context,
                            PostLaunch::class.java)
                    )
                    activity?.finish()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFFFFFFF),
                    contentColor = Color(0xFFFFFFFF)
                )
            ) {
                Text(
                    text = "Let's Get Started",
                    color = colorResource(id = R.color.purple_700),
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    ProfessionalWorkshopTheme {
        Scaffold {
            OnBoardingUI()
        }
    }
}