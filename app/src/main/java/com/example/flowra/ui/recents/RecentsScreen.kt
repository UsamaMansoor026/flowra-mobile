package com.example.flowra.ui.recents

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flowra.MainViewModel
import com.example.flowra.R
import com.example.flowra.domain.model.Expense
import com.example.flowra.ui.components.ExpenseCard
import com.example.flowra.ui.theme.FlowraTheme
import com.example.flowra.ui.theme.PrimaryBackground
import com.example.flowra.ui.theme.PrimaryText

@Composable
fun RecentsScreen(navController: NavHostController, viewModel: MainViewModel) {
    val expenses = viewModel.expenses.value
    RecentsScreenContent(
        navController = navController,
        expenses = expenses
    )
}

@Composable
private fun RecentsScreenContent(navController: NavHostController, expenses: List<Expense>) {
    val listState = rememberLazyListState()
    val showTopFade by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0 }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground)
            .padding(top = 10.dp, bottom = 6.dp, start = 16.dp, end = 16.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Screen Heading and Back Button
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(0.dp, 12.dp)
                    .clickable(
                        onClick = {
                            navController.popBackStack()
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically,

                ) {
                Image(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Recents",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText
                )
            }

            // Recents Spending Data
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {
                    items(expenses) { expense ->
                        ExpenseCard(expense)
                    }
                }

                // Top fade
                this@Column.AnimatedVisibility(
                    visible = showTopFade,
                    enter = fadeIn(animationSpec = tween(200)),
                    exit = fadeOut(animationSpec = tween(200)),
                    modifier = Modifier.align(Alignment.TopCenter)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        PrimaryBackground.copy(alpha = 0.95f),
                                        PrimaryBackground.copy(alpha = 0.6f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                }

                // Bottom fade
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    PrimaryBackground.copy(alpha = 0.8f)
                                )
                            )
                        )
                )
            }
        }
    }
}

private val fakeExpenses = listOf(
    Expense(1, "Fuel", 500, "Transport"),
    Expense(2, "Coffee", 120, "Food"),
    Expense(3, "Internet", 2500, "Bills"),
    Expense(4, "Groceries", 3200, "Food")
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RecentsScreenPreview() {
    FlowraTheme {
        RecentsScreenContent(
            navController = rememberNavController(),
            expenses = fakeExpenses
        )
    }
}