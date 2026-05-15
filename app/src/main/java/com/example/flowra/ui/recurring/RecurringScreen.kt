package com.example.flowra.ui.recurring

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flowra.MainViewModel
import com.example.flowra.domain.model.Bill
import com.example.flowra.domain.model.BillStatus
import com.example.flowra.domain.model.BillWithStatus
import com.example.flowra.domain.model.BillingCycle
import com.example.flowra.domain.model.Category
import com.example.flowra.domain.model.Expense
import com.example.flowra.domain.model.getCategoryIcon
import com.example.flowra.ui.components.BillCard
import com.example.flowra.ui.theme.CyanGlow
import com.example.flowra.ui.theme.ErrorRed
import com.example.flowra.ui.theme.FlowraTheme
import com.example.flowra.ui.theme.PrimaryBackground
import com.example.flowra.ui.theme.PrimaryText
import com.example.flowra.ui.theme.SecondaryText
import com.example.flowra.ui.theme.boxShadow
import com.example.flowra.ui.theme.flowraGlass

@Composable
fun RecurringScreen(navController: NavHostController, viewModel: MainViewModel) {
    val userBills by viewModel.bills.collectAsStateWithLifecycle()
    RecurringScreenContent(
        navController = navController,
        userBills = userBills
    )
}

@Composable
private fun RecurringScreenContent(
    navController: NavHostController,
    userBills: List<Bill>
) {
    val listState = rememberLazyListState()
    val showTopFade by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0 }
    }

    val unpaidBills = remember(userBills) {
        userBills.filter { !it.isPaid && it.isActive }.sortedBy { it.daysUntilDue }
    }

    val paidBills = remember(userBills) {
        userBills.filter { it.isPaid && it.isActive }.sortedBy { it.daysUntilDue }
    }

    val nextDueBill = remember(userBills) {
        userBills.filter { it.isActive }.minByOrNull { it.daysUntilDue }
    }

    val monthlyTotal = remember(userBills) {
        userBills.filter { it.isActive && it.billingCycle == BillingCycle.MONTHLY }.sumOf { it.amount }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground)
            .padding(top = 20.dp, bottom = 6.dp, start = 16.dp, end = 16.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Monthly Total Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp)
                    .boxShadow(
                        color = CyanGlow.copy(alpha = 0.4f),
                        borderRadius = 16.dp,
                        blurRadius = 8.dp,
                        spreadRadius = 2.dp,
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .flowraGlass()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Monthly Total".uppercase(),
                        color = SecondaryText,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        letterSpacing = 1.2.sp,
                    )
                    Text(
                        text = "Rs $monthlyTotal",
                        color = CyanGlow.copy(0.6f),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 32.sp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Next Payment Card
            nextDueBill?.let { bill ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .flowraGlass()
                        .border(1.dp, CyanGlow.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            text = "Next Payment".uppercase(),
                            color = SecondaryText,
                            fontSize = 11.sp,
                            letterSpacing = 1.2.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(52.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(CyanGlow.copy(alpha = 0.12f)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Image(
                                        painter = painterResource(getCategoryIcon(bill.category)),
                                        contentDescription = bill.category,
                                        modifier = Modifier.size(26.dp),
                                        colorFilter = ColorFilter.tint(CyanGlow.copy(alpha = 0.9f))
                                    )
                                }
                                Column {
                                    Text(
                                        text = bill.title,
                                        color = PrimaryText,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = bill.dueDateLabel,
                                            color = when (bill.status) {
                                                BillStatus.OVERDUE  -> ErrorRed.copy(alpha = 0.9f)
                                                BillStatus.DUE_SOON -> CyanGlow.copy(alpha = 0.9f)
                                                BillStatus.UPCOMING -> SecondaryText
                                            },
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                        Text(text = "•", color = SecondaryText, fontSize = 12.sp)
                                        Text(
                                            text = "Rs ${bill.amount}",
                                            color = CyanGlow.copy(0.8f),
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Scrollable list
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    // Due Payments section
                    if (unpaidBills.isNotEmpty()) {
                        item {
                            Text(
                                text = "Due Payments",
                                color = PrimaryText,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        items(unpaidBills) { bill ->
                            BillCard(bill = bill, showDueLabel = true)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                        item { Spacer(modifier = Modifier.height(8.dp)) }
                    }

                    // Active Flow section
                    if (userBills.isNotEmpty()) {
                        item {
                            Text(
                                text = "Active Flow",
                                color = PrimaryText,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        item {
                            Text(
                                text = "You have ${userBills.size} active bills",
                                color = SecondaryText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                        items(userBills) { bill ->
                            BillCard(bill = bill, showDueLabel = false)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
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

private val fakeBills = listOf(
    Bill(1, "Netflix",    Category.ENTERTAINMENT, 1200,  3,  BillingCycle.MONTHLY, isPaid = false),
    Bill(2, "House Rent", Category.BILLS,         25000, 5,  BillingCycle.MONTHLY, isPaid = false),
    Bill(3, "Spotify",    Category.ENTERTAINMENT, 350,   18, BillingCycle.MONTHLY, isPaid = true),
    Bill(4, "Internet",   Category.BILLS,         2500,  20, BillingCycle.MONTHLY, isPaid = true),
)

@Preview(showBackground = true)
@Composable
private fun BillsScreenPreview() {
    FlowraTheme {
        RecurringScreenContent(
            navController = rememberNavController(),
            userBills = fakeBills
        )
    }
}