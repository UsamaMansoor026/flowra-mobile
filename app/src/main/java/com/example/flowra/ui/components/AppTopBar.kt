package com.example.flowra.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowra.R
import com.example.flowra.ui.theme.CyanGlow
import com.example.flowra.ui.theme.FlowraTheme
import com.example.flowra.ui.theme.GlassSurface
import com.example.flowra.ui.theme.PoppinsFontFamily
import com.example.flowra.ui.theme.PrimaryText
import com.example.flowra.ui.theme.SecondarySurface
import com.example.flowra.ui.theme.SecondaryText

@Composable
fun AppTopBar(currentRoute: String?) {
//    val title = when (currentRoute) {
//        Screen.Home.route      -> "Flowra"
//        Screen.Insights.route  -> "Insights"
//        Screen.Recurring.route -> "Recurring"
//        Screen.Setting.route   -> "Settings"
//        else -> ""
//    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(SecondarySurface)
            .drawBehind() {
                val strokeWidth = 1.dp.toPx()
                drawLine(
                    color = CyanGlow.copy(alpha = 0.3f),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = strokeWidth
                )
            },
    ) {
        Row(
            modifier = Modifier.matchParentSize().padding(18.dp, 0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_app_icon),
                contentDescription = "App Icon",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(18.dp)
            )
            Text(
                text = "Flowra",
                color = PrimaryText,
                fontFamily = PoppinsFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAppTopBar() {
    FlowraTheme {
        AppTopBar(currentRoute = "")
    }
}