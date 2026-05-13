package com.example.flowra.ui.theme

import android.graphics.BlurMaskFilter
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.flowraGlass(): Modifier = this
    .background(Color.White.copy(alpha = 0.06f))


@RequiresApi(Build.VERSION_CODES.O)
fun Modifier.boxShadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    spreadRadius: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
): Modifier = this.drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()

        frameworkPaint.isAntiAlias = true

        if (blurRadius != 0.dp) {
            frameworkPaint.maskFilter = BlurMaskFilter(
                blurRadius.toPx(),
                BlurMaskFilter.Blur.NORMAL
            )
        }

        frameworkPaint.color = color.toArgb()

        val spread  = spreadRadius.toPx()
        val left    = -spread + offsetX.toPx()
        val top     = -spread + offsetY.toPx()
        val right   = size.width  + spread + offsetX.toPx()
        val bottom  = size.height + spread + offsetY.toPx()
        val radius  = borderRadius.toPx()

        // Save layer then clip OUT the box area so shadow only shows outside
        canvas.save()
        canvas.nativeCanvas.clipOutPath(
            android.graphics.Path().apply {
                addRoundRect(
                    android.graphics.RectF(0f, 0f, size.width, size.height),
                    radius,
                    radius,
                    android.graphics.Path.Direction.CW
                )
            }
        )

        canvas.drawRoundRect(
            left    = left,
            top     = top,
            right   = right,
            bottom  = bottom,
            radiusX = radius,
            radiusY = radius,
            paint   = paint
        )

        canvas.restore()
    }
}