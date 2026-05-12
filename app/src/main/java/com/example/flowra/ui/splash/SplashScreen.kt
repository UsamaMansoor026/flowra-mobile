package com.example.flowra.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flowra.R
import com.example.flowra.ui.components.CircularLoader
import com.example.flowra.ui.theme.CyanGlow
import com.example.flowra.ui.theme.FlowraTheme
import com.example.flowra.ui.theme.GlassSurface
import com.example.flowra.ui.theme.GreenGlow
import com.example.flowra.ui.theme.IndigoGlow
import com.example.flowra.ui.theme.PoppinsFontFamily
import com.example.flowra.ui.theme.PrimaryBackground
import com.example.flowra.ui.theme.PrimaryText
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onNavigationToHome: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000L)
        onNavigationToHome()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryBackground)
    ) {
        // Blur One
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(400.dp)
                .offset((-40).dp, (-60).dp)
                .blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(
                    CyanGlow.copy(alpha = 0.2f), CircleShape
                )
        )

        // Blur Two
        Box(
            modifier = Modifier
                .width(120.dp)
                .height(260.dp)
                .offset(210.dp, 300.dp)
                .blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(
                    GreenGlow.copy(alpha = 0.2f), CircleShape
                ),
        )

        // Blur Three
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(450.dp)
                .offset(210.dp, 600.dp)
                .blur(80.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded)
                .background(
                    IndigoGlow.copy(alpha = 0.2f), CircleShape
                ),
        )

        // Content
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp, 60.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(65.dp)
                        .background(GlassSurface, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_app_icon),
                        contentDescription = "App Icon",
                        modifier = Modifier
                            .size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // App Title
                Text(
                    text = "Flowra",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFontFamily,
                    color = CyanGlow.copy(0.8f),
                )

                Spacer(modifier = Modifier.weight(0.5f))

                // App Headline
                Text(
                    text = "Expense Tracker For Lazy People",
                    fontSize = 48.sp,
                    lineHeight = 56.sp,
                    fontFamily = PoppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = PrimaryText,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.weight(1f))

                CircularLoader(
                    size = 40.dp,
                    strokeWidth = 3.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    FlowraTheme {
        SplashScreen(onNavigationToHome = {})
    }
}