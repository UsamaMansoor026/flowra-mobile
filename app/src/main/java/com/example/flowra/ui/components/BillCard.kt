package com.example.flowra.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowra.domain.model.Bill
import com.example.flowra.domain.model.BillStatus
import com.example.flowra.domain.model.BillingCycle
import com.example.flowra.domain.model.Category
import com.example.flowra.domain.model.getCategoryIcon
import com.example.flowra.ui.theme.CyanGlow
import com.example.flowra.ui.theme.ErrorRed
import com.example.flowra.ui.theme.FlowraTheme
import com.example.flowra.ui.theme.GlassSurface
import com.example.flowra.ui.theme.IndigoGlow
import com.example.flowra.ui.theme.PrimaryText
import com.example.flowra.ui.theme.SecondarySurface
import com.example.flowra.ui.theme.SecondaryText
import com.example.flowra.ui.theme.boxShadow
import com.example.flowra.ui.theme.flowraGlass
import com.example.flowra.ui.utils.formatExpenseDate
import kotlin.text.uppercase

@Composable
fun BillCard(
    bill: Bill,
    showDueLabel: Boolean = true
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .flowraGlass()
            .border(1.dp, CyanGlow.copy(alpha = 0.15f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Category icon
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(GlassSurface),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(getCategoryIcon(bill.category)),
                        contentDescription = bill.category,
                        modifier = Modifier.size(26.dp),
                        colorFilter = ColorFilter.tint(CyanGlow.copy(alpha = 0.8f))
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy((-4).dp)) {
                    Text(
                        text = bill.title,
                        color = PrimaryText,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(
                        text = if (showDueLabel) bill.dueDateLabel
                        else bill.nextPaymentLabel,
                        color = if (showDueLabel) {
                            when (bill.status) {
                                BillStatus.OVERDUE  -> ErrorRed.copy(alpha = 0.9f)
                                BillStatus.DUE_SOON -> CyanGlow.copy(alpha = 0.9f)
                                BillStatus.UPCOMING -> SecondaryText
                            }
                        } else SecondaryText,
                        fontSize = 12.sp
                    )
                    Text(
                        text = bill.category.uppercase(),
                        color = IndigoGlow,
                        fontSize = 10.sp,
                        letterSpacing = 0.8.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Amount
            Text(
                text = "Rs ${bill.amount}",
                color = CyanGlow.copy(0.8f),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
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