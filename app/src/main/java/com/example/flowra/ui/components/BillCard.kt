package com.example.flowra.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowra.domain.model.Bill
import com.example.flowra.domain.model.BillingCycle
import com.example.flowra.domain.model.Category
import com.example.flowra.domain.model.getCategoryIcon
import com.example.flowra.ui.theme.CyanGlow
import com.example.flowra.ui.theme.FlowraTheme
import com.example.flowra.ui.theme.PrimaryText
import com.example.flowra.ui.theme.SecondaryText
import com.example.flowra.ui.theme.boxShadow
import com.example.flowra.ui.theme.flowraGlass
import com.example.flowra.ui.utils.formatExpenseDate
import kotlin.text.uppercase

@Composable
fun BillCard(bill: Bill) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
            .boxShadow(
                color = CyanGlow.copy(alpha = 0.4f),
                borderRadius = 8.dp,
                blurRadius = 4.dp,
                spreadRadius = 1.dp,
                offsetX = 0.dp,
                offsetY = 0.dp
            )
            .flowraGlass()
            .clip(RoundedCornerShape(8.dp))
            .padding(24.dp, 16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(CyanGlow.copy(alpha = 0.1f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(getCategoryIcon(bill.category)),
                        contentDescription = bill.category,
                        modifier = Modifier.size(18.dp),
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = bill.title,
                        color = SecondaryText,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp,
                        letterSpacing = 1.2.sp
                    )
                    Text(
                        text = bill.daysUntilDue.toString(),
                        color = SecondaryText,
                        fontSize = 12.sp
                    )

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BillCardPreview() {
    FlowraTheme {
        BillCard(
            bill =
                Bill(
                    id = 1,
                    title = "Netflix",
                    category = Category.ENTERTAINMENT,
                    amount = 1200,
                    dueDate = 18,
                    billingCycle = BillingCycle.MONTHLY,
                    isActive = true,
                    reminderEnabled = true
                )
        )
    }
}