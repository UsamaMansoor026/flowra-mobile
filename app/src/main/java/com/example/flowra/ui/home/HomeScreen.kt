package com.example.flowra.ui.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.flowra.MainViewModel
import com.example.flowra.R
import com.example.flowra.domain.model.Expense
import com.example.flowra.ui.components.BudgetCircularProgress
import com.example.flowra.ui.theme.CyanGlow
import com.example.flowra.ui.theme.FlowraTheme
import com.example.flowra.ui.theme.PrimaryBackground
import com.example.flowra.ui.theme.PrimaryText
import com.example.flowra.ui.theme.SecondaryText
import com.example.flowra.ui.theme.flowraGlass
import com.example.flowra.ui.utils.formatExpenseDate

@Composable
fun HomeScreen(navController: NavHostController, viewModel: MainViewModel) {
    val expenses = viewModel.expenses.value
    HomeScreenContent(
        navController = navController,
        expenses = expenses
    )
}

@Composable
fun HomeScreenContent(
    navController: NavHostController,
    expenses: List<Expense>
) {
    val listState = rememberLazyListState()
    val showTopFade by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0 }
    }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground)
            .padding(top = 20.dp, bottom = 6.dp, start = 16.dp, end = 16.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                focusManager.clearFocus()
                keyboardController?.hide()
            },
        contentAlignment = Alignment.TopCenter
    ) {
        // Ambient glow blur
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(300.dp)
                .blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(CyanGlow.copy(alpha = 0.2f), CircleShape)
        )

        Column(modifier = Modifier.fillMaxSize()) {
            BalanceAndBudgetCard()

            Spacer(modifier = Modifier.height(28.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent Spending",
                    color = SecondaryText,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "View All",
                    color = SecondaryText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                )
            }

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
                        ExpenseItem(expense)
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

            // Add Expense
            AddExpense(
                onTrackClick = { inputString ->
                    Log.d("Flowramaan", "User wants to track: $inputString")
                },
                onClearFocus = {
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }
            )
        }
    }
}

@Composable
private fun BalanceAndBudgetCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .flowraGlass()
            .border(1.dp, CyanGlow.copy(alpha = 0.3f), RoundedCornerShape(32.dp))
            .padding(32.dp)
    ) {
        Column {
            Text(
                text = "Available Balance".uppercase(),
                color = SecondaryText,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                letterSpacing = 1.2.sp
            )
            Text(
                text = "Rs 20,000",
                color = CyanGlow.copy(0.7f),
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Monthly Spending".uppercase(),
                        fontSize = 12.sp,
                        color = SecondaryText,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = "Rs 10,000",
                        color = CyanGlow.copy(0.7f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Monthly Bills".uppercase(),
                        fontSize = 12.sp,
                        color = SecondaryText,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        text = "Rs 5,000",
                        color = CyanGlow.copy(0.7f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    )
                }

                Column {
                    Text(
                        text = "Budget Spend".uppercase(),
                        fontSize = 12.sp,
                        color = SecondaryText,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    BudgetCircularProgress(
                        totalBudget = 20000.0,
                        monthlySpending = 10000.0
                    )
                }
            }
        }
    }
}

@Composable
private fun ExpenseItem(expense: Expense) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .flowraGlass()
            .clip(RoundedCornerShape(8.dp))
            .border(1.dp, CyanGlow.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(24.dp, 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = expense.title,
                    color = SecondaryText,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    letterSpacing = 1.2.sp
                )
                Text(
                    text = expense.category.uppercase(),
                    color = SecondaryText,
                    fontSize = 12.sp
                )
                Text(
                    text = formatExpenseDate(expense.addedDate),
                    fontSize = 10.sp,
                    color = SecondaryText.copy(alpha = 0.6f),
                    fontWeight = FontWeight.Normal
                )
            }

            Text(
                text = "-Rs ${expense.amount}",
                color = CyanGlow.copy(alpha = 0.6f),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
private fun AddExpense(
    onTrackClick: (String) -> Unit,
    onClearFocus: () -> Unit,
    modifier: Modifier = Modifier
) {
    var textState by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .height(72.dp)
            .clip(CircleShape)
            .flowraGlass()
            .border(1.dp, CyanGlow.copy(alpha = 0.2f), CircleShape)
            .padding(top = 8.dp, bottom = 8.dp, start = 24.dp, end = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicTextField(
                value = textState,
                onValueChange = { textState = it },
                textStyle = TextStyle(
                    color = PrimaryText,
                    fontSize = 16.sp
                ),
                modifier = Modifier.weight(1f),
                cursorBrush = SolidColor(CyanGlow),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (textState.isNotBlank()) {
                            onTrackClick(textState)
                            textState = ""
                        }
                        onClearFocus()
                    }
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (textState.isEmpty()) {
                            Text(
                                text = "e.g. 500 coffee",
                                color = SecondaryText.copy(alpha = 0.5f),
                                fontSize = 16.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                CyanGlow.copy(alpha = 0.4f),
                                Color.White.copy(alpha = 0.1f)
                            )
                        )
                    )
                    .clickable {
                        if (textState.isNotBlank()) {
                            onTrackClick(textState)
                            textState = ""
                        }
                        onClearFocus()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = "Send",
                    modifier = Modifier.size(20.dp)
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
fun HomeScreenPreview() {
    FlowraTheme {
        HomeScreenContent(
            navController = rememberNavController(),
            expenses = fakeExpenses
        )
    }
}