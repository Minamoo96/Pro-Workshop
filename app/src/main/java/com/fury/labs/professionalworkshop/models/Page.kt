package com.fury.labs.professionalworkshop.models

import androidx.annotation.DrawableRes
import com.fury.labs.professionalworkshop.R

data class Page(
    val title: String,
    val body: String,
    @DrawableRes val image: Int
    )
    val onBoardPages = listOf(
        Page(
            "A Carpenter At Your Hand",
            "Always choose the best at everything, and the Professional Workshop is your best choice",
            R.drawable.illustration_svg
        ),
        Page(
            "Choose Your Decor",
            "A giant library of high quality hand made furniture ready for you to explore",
            R.drawable.illustration_svg1
        ),
        Page(
            "Order as you want",
            "With the ability to order the furniture that you see applies your house mood",
            R.drawable.illustration_svg2
        ),
        Page(
            "Modern Day Styles",
            "A pieces of art that completes the view of your house",
            R.drawable.illustration_svg3
        )
    )

