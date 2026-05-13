package com.example.flowra.ui.navigation


import com.example.flowra.R

sealed class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: Int
) {
    object Home: BottomNavItem(Screen.Home, "Home", R.drawable.ic_home)
    object Insights: BottomNavItem(Screen.Insights, "Insights", R.drawable.ic_insights)
    object Recurring: BottomNavItem(Screen.Recurring, "Bills", R.drawable.ic_calender)
    object Setting: BottomNavItem(Screen.Setting, "Setting", R.drawable.ic_profile)

    companion object {
        val items = listOf(Home, Insights, Recurring, Setting)
    }
}