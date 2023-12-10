package com.example.android_project_3133142


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import kotlin.math.roundToInt

@Composable
fun LineChartScreen(pointsData: List<Point>){

    val steps = 5

    var maxValue = 0f

    for (point in pointsData){
        if (point.y > maxValue)
            maxValue = point.y
    }

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(colorResource(id = R.color.boxBackground))
        .steps(pointsData.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(14.dp)
        .axisLineColor(colorResource(id = R.color.tagBackground))
        .axisLabelColor(colorResource(id = R.color.tagBackground))
        .build()

    val yAxisData = AxisData.Builder()
        .steps(steps)
        .backgroundColor(colorResource(id = R.color.boxBackground))
        .labelAndAxisLinePadding(30.dp)
        .labelData { i ->
            val yScale = maxValue.roundToInt() / steps
            (i * yScale).toString()
        }
        .axisLineColor(colorResource(id = R.color.tagBackground))
        .axisLabelColor(colorResource(id = R.color.tagBackground))
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsData,
                    LineStyle(
                        color = colorResource(id = R.color.tagBackground),
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(
                        color = colorResource(id = R.color.purple_500)
                    ),
                    SelectionHighlightPoint(color = MaterialTheme.colorScheme.primary),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = R.color.tagBackground),
                                Color.Blue
                            )
                        )
                    ),
                    SelectionHighlightPopUp(
                        backgroundColor = colorResource(id = R.color.boxBackground)
                    )
                )
            ),
        ),
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        isZoomAllowed = true,
        gridLines = GridLines(color = MaterialTheme.colorScheme.outlineVariant),
        backgroundColor = colorResource(id = R.color.boxBackground),
        paddingRight = 0.dp,
        containerPaddingEnd = 20.dp,
        bottomPadding = 0.dp

    )

    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
        ,
        lineChartData = lineChartData
    )
}