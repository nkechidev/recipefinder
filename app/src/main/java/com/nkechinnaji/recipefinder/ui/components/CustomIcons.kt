package com.nkechinnaji.recipefinder.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CutleryIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    strokeWidth: Float = 2f
) {
    Canvas(modifier = modifier.size(24.dp)) {
        val width = size.width
        val height = size.height
        
        // Fork (left side)
        val forkX = width * 0.3f
        
        // Fork handle
        drawLine(
            color = color,
            start = Offset(forkX, height * 0.95f),
            end = Offset(forkX, height * 0.45f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        
        // Fork prongs
        val prongSpacing = width * 0.08f
        for (i in -1..1) {
            val x = forkX + (i * prongSpacing)
            drawLine(
                color = color,
                start = Offset(x, height * 0.45f),
                end = Offset(x, height * 0.15f),
                strokeWidth = strokeWidth,
                cap = StrokeCap.Round
            )
        }
        
        // Fork top curve
        drawLine(
            color = color,
            start = Offset(forkX - prongSpacing, height * 0.15f),
            end = Offset(forkX + prongSpacing, height * 0.15f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        
        // Knife (right side)
        val knifeX = width * 0.7f
        
        // Knife handle
        drawLine(
            color = color,
            start = Offset(knifeX, height * 0.95f),
            end = Offset(knifeX, height * 0.55f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
        
        // Knife blade
        val bladePath = Path().apply {
            moveTo(knifeX - width * 0.06f, height * 0.55f)
            lineTo(knifeX + width * 0.06f, height * 0.55f)
            lineTo(knifeX + width * 0.04f, height * 0.15f)
            lineTo(knifeX - width * 0.04f, height * 0.15f)
            close()
        }
        drawPath(
            path = bladePath,
            color = color,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
        
        // Cross line
        drawLine(
            color = color,
            start = Offset(width * 0.15f, height * 0.25f),
            end = Offset(width * 0.85f, height * 0.75f),
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )
    }
}

@Composable
fun DiceIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    strokeWidth: Float = 2f
) {
    Canvas(modifier = modifier.size(24.dp)) {
        val width = size.width
        val height = size.height
        val padding = width * 0.15f
        val cornerRadius = width * 0.1f
        
        // Draw rounded rectangle
        val rectPath = Path().apply {
            val left = padding
            val top = padding
            val right = width - padding
            val bottom = height - padding
            
            moveTo(left + cornerRadius, top)
            lineTo(right - cornerRadius, top)
            quadraticTo(right, top, right, top + cornerRadius)
            lineTo(right, bottom - cornerRadius)
            quadraticTo(right, bottom, right - cornerRadius, bottom)
            lineTo(left + cornerRadius, bottom)
            quadraticTo(left, bottom, left, bottom - cornerRadius)
            lineTo(left, top + cornerRadius)
            quadraticTo(left, top, left + cornerRadius, top)
            close()
        }
        
        drawPath(
            path = rectPath,
            color = color,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
        
        // Draw dots
        val dotRadius = width * 0.06f
        val centerX = width / 2
        val centerY = height / 2
        val offset = width * 0.18f
        
        // Center dot
        drawCircle(color = color, radius = dotRadius, center = Offset(centerX, centerY))
        
        // Top-left and bottom-right dots
        drawCircle(color = color, radius = dotRadius, center = Offset(centerX - offset, centerY - offset))
        drawCircle(color = color, radius = dotRadius, center = Offset(centerX + offset, centerY + offset))
        
        // Top-right and bottom-left dots
        drawCircle(color = color, radius = dotRadius, center = Offset(centerX + offset, centerY - offset))
        drawCircle(color = color, radius = dotRadius, center = Offset(centerX - offset, centerY + offset))
    }
}

@Composable
fun SparklesIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    strokeWidth: Float = 2f
) {
    Canvas(modifier = modifier.size(24.dp)) {
        val width = size.width
        val height = size.height
        
        // Main sparkle (center-right)
        val mainX = width * 0.6f
        val mainY = height * 0.5f
        val mainSize = width * 0.35f
        
        drawSparkle(color, mainX, mainY, mainSize, strokeWidth)
        
        // Small sparkle (top-left)
        val smallX = width * 0.25f
        val smallY = height * 0.25f
        val smallSize = width * 0.18f
        
        drawSparkle(color, smallX, smallY, smallSize, strokeWidth)
        
        // Tiny sparkle (bottom-left)
        val tinyX = width * 0.3f
        val tinyY = height * 0.75f
        val tinySize = width * 0.12f
        
        drawSparkle(color, tinyX, tinyY, tinySize, strokeWidth)
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawSparkle(
    color: Color,
    centerX: Float,
    centerY: Float,
    size: Float,
    strokeWidth: Float
) {
    val path = Path().apply {
        // Top point
        moveTo(centerX, centerY - size)
        // Right curve to center
        quadraticTo(centerX + size * 0.2f, centerY - size * 0.2f, centerX + size, centerY)
        // Bottom curve from center
        quadraticTo(centerX + size * 0.2f, centerY + size * 0.2f, centerX, centerY + size)
        // Left curve to center
        quadraticTo(centerX - size * 0.2f, centerY + size * 0.2f, centerX - size, centerY)
        // Top curve from center
        quadraticTo(centerX - size * 0.2f, centerY - size * 0.2f, centerX, centerY - size)
        close()
    }
    
    drawPath(
        path = path,
        color = color,
        style = Stroke(width = strokeWidth, cap = StrokeCap.Round, join = StrokeJoin.Round)
    )
}
