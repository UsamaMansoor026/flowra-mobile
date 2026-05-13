package com.example.flowra.domain.model

import androidx.annotation.DrawableRes
import com.example.flowra.R

@DrawableRes
fun getCategoryIcon(category: String): Int {
    return when (category) {
        Category.TRANSPORT -> R.drawable.ic_transport
        Category.FOOD -> R.drawable.ic_food
        Category.BILLS -> R.drawable.ic_shopping
        Category.SHOPPING -> R.drawable.ic_shopping
        Category.ENTERTAINMENT -> R.drawable.ic_entertainment
        Category.HEALTH -> R.drawable.ic_health
        else -> R.drawable.ic_app_icon
    }
}