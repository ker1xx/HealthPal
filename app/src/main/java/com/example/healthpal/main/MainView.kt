package com.example.healthpal.main

import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.createBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.Line
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.example.healthpal.localdb.DTO.workout.WorkoutDTO
import com.example.healthpal.localdb.HealthPalDatabase
import com.example.healthpal.ui.theme.Mint
import com.example.healthpal.ui.theme.Pink
import com.example.healthpal.ui.theme.PtSans
import com.example.healthpal.utility.backgroundSetter
import com.example.healthpal.ui.theme.PtSansCaption
import com.example.healthpal.ui.theme.Purple
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.emoji.Emoji
import org.kodein.emoji.light
import org.kodein.emoji.people_body.hand_fingers_open.WavingHand
import timber.log.Timber
import java.time.LocalDateTime
import java.util.Calendar
import kotlin.math.roundToInt

@Composable
fun MainDisplay(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {
        backgroundSetter.BackgroundAnimation()
        Column {
            val user = HealthPalDatabase
                .getDbInstance(LocalContext.current)
                .userDataDao()
                .getAllUserData()
            Timber.tag("Info").i("${user.name} ${user.image}")
            topSection(user.name!!, user.image)
            FillCards()
        }
    }
}

@Composable
fun topSection(
    name: String,
    image: Bitmap
) {
    Row {
        Column(
            modifier = Modifier
                .weight(3f)
                .padding(
                    start = 5.dp,
                    top = 10.dp
                )
        ) {
            Text(
                fontFamily = PtSansCaption,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                text = "Hello, $name! ${Emoji.WavingHand.light}"
            )
            Text(
                fontFamily = PtSansCaption,
                textAlign = TextAlign.Start,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                text = "This is your progress."
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = 5.dp,
                    top = 10.dp
                )
        ) {
            userPhoto(image = image)
        }
    }
}

@Composable
fun userPhoto(image: Bitmap) {

    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
    ) {
        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = "UserIcon",
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun FillCards(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
    googleAccount: GoogleSignInAccount = GoogleSignIn.getAccountForExtension(
        LocalContext.current,
        viewModel.fitnessOptions
    )
) {
    val isLoadingFinished by remember {
        viewModel.isLoadingFinished
    }

    val context = LocalContext.current
    val database = HealthPalDatabase.getDbInstance(context)
    val steps by remember {
        viewModel.steps
    }
    LaunchedEffect(key1 = true) {
        GlobalScope.launch {
            Fitness
                .getHistoryClient(context, googleAccount)
                .readDailyTotal(DataType.TYPE_STEP_COUNT_DELTA)
                .addOnSuccessListener {
                    viewModel.onSuccess(it)
                }
            Timber.tag("Info").i("step received")
            Fitness
                .getHistoryClient(context, googleAccount)
                .readDailyTotal(DataType.TYPE_CALORIES_EXPENDED)
                .addOnSuccessListener {
                    viewModel.onSuccess(it)
                }
            Timber.tag("Info").i("step received")

            Fitness
                .getHistoryClient(context, googleAccount)
                .readDailyTotal(DataType.TYPE_DISTANCE_DELTA)
                .addOnSuccessListener { it ->
                    viewModel.onSuccess(it)
                }
            Timber.tag("Info").i("step received")
        }
    }
    Surface(
        modifier
    ) {
        Column {
            Row {
                StepsCard(
                    stepsCurrentValue = steps,
                    modifier = Modifier
                        .weight(1f)
                )
                Spacer(
                    modifier
                    = Modifier
                        .weight(0.1f)
                )
                TrainingCard(
                    trainingEntry = viewModel.generateExercise(
                        database
                    )
                )
            }
        }
        Spacer(
            modifier = Modifier
                .height(20.dp)
        )
        kilometersMonthStatCard()
    }
}

@Composable
fun StepsCard(
    stepsCurrentValue: Float = 6253f,
    stepsTargetValue: Float = 10000f,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(3.dp)
    ) {
        val currentValue = if (stepsCurrentValue < stepsTargetValue)
            stepsCurrentValue / stepsTargetValue
        else
            1f
        Column {
            Text(
                text = "Steps",
                fontFamily = PtSans,
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            var animationPlayed by remember {
                mutableStateOf(false)
            }
            val curPercentage = animateFloatAsState(
                targetValue = if (animationPlayed)
                    currentValue
                else 0f,
                animationSpec = tween(
                    durationMillis = 1000,
                    delayMillis = 100
                )
            )
            LaunchedEffect(key1 = true) {
                animationPlayed = true
            }
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize(0.5f)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize(0.5f)
                ) {
                    drawArc(
                        color = Mint,
                        startAngle = -90f,
                        sweepAngle = 360 * curPercentage.value,
                        useCenter = false,
                        style = Stroke(
                            8f,
                            cap = StrokeCap.Round
                        )
                    )
                }
                Column {
                    Text(
                        text = stepsCurrentValue.toString(),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = PtSans,
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp
                    )
                    Text(
                        text = "steps",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = PtSans,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun TrainingCard(
    modifier: Modifier = Modifier,
    trainingEntry: WorkoutDTO,
) {
    Column(
        modifier = Modifier
            .padding(
                top = 5.dp,
                bottom = 5.dp
            )
    ) {
        Text(
            text = "Next Training",
            fontFamily = PtSans,
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(
                id = trainingEntry.Id!!
            ),
            contentDescription = "Workout Icon",
            alignment = Alignment.Center
        )
        Text(
            text = trainingEntry.WorkOut,
            fontFamily = PtSans,
            textAlign = TextAlign.Start,
            fontSize = 18.sp
        )
    }
}

@Composable
fun kilometersMonthStatCard(
    viewModel: MainViewModel = hiltViewModel(),
    googleAccount: GoogleSignInAccount = GoogleSignIn.getAccountForExtension(
        LocalContext.current,
        viewModel.fitnessOptions
    )
) {
    val stepsValues by remember { viewModel.stepsForCertainDates }
    val calendar = Calendar.getInstance()
    val dateTime = LocalDateTime.now()
    val datesList = mutableListOf<String>()
    val pointsList = mutableListOf<Point>()
    for (index in 0..stepsValues.count()) {
        pointsList.add(Point(index.toFloat(), stepsValues[index]))
    }
    for (i in 0..30) {
        datesList.add("dateTime.dayOfMonth ${dateTime.month.name}")
        dateTime.minusDays(1)
    }
    val stepsValuesList = mutableListOf<String>()
    for (i in 1..stepsValues
        .maxOf {
            it / 1000
        }
        .roundToInt()
    ) {
        stepsValuesList.add("${i * 1000}")
    }
    calendar.set(
        dateTime.year,
        dateTime.monthValue,
        dateTime.dayOfMonth,
        0,
        0,
        0
    )
    viewModel.requestForHistory(
        context = LocalContext.current,
        startDateCalendar = calendar,
        googleAccount = googleAccount
    )
    val xAxisData = AxisData.Builder()
        .axisStepSize(10.dp)
        .backgroundColor(Color.Transparent)
        .steps(30)
        .labelData { i ->
            datesList[i]
        }
        .labelAndAxisLinePadding(8.dp)
        .axisLabelColor(MaterialTheme.colorScheme.secondary)
        .axisLineColor(MaterialTheme.colorScheme.secondary)
        .build()

    val yAxisData = AxisData.Builder()
        .axisStepSize(12.dp)
        .steps(stepsValues
            .maxOf {
                it / 1000
            }
            .roundToInt()
        )
        .backgroundColor(Color.Transparent)
        .labelAndAxisLinePadding(10.dp)
        .labelData { i ->
            stepsValuesList[i]
        }
        .axisLineColor(Color.Transparent)
        .axisLabelColor(MaterialTheme.colorScheme.secondary)
        .build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                Line(
                    dataPoints = pointsList,
                    LineStyle(
                        color = Mint,
                        lineType = LineType.SmoothCurve(isDotted = true)
                    ),
                    IntersectionPoint(
                        color = Pink
                    ),
                    SelectionHighlightPoint(
                        color = Purple
                    ),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.secondary,
                                Color.Transparent
                            )
                        )
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        backgroundColor = Color.Transparent,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = null
    )
    Box(
        modifier = Modifier
            .padding(
                top = 7.dp,
                start = 5.dp,
                end = 5.dp,
                bottom = 8.dp
            )
            .clip(RoundedCornerShape(5.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Row {
            Column {
                Text(
                    text = "Today",
                    fontFamily = PtSans,
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    Text(
                        text = "\u2022 ${viewModel.steps.floatValue}",
                        fontFamily = PtSans,
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Mint
                    )
                    Text(
                        text = "steps",
                        fontFamily = PtSans,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp
                    )
                }
            }
            Column {
                Text(
                    text = "Average",
                    fontFamily = PtSans,
                    textAlign = TextAlign.Start,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    Text(
                        text = "\u2022 ${
                            viewModel.stepsForCertainDates.value.average().roundToInt()
                        }",
                        fontFamily = PtSans,
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = "steps",
                        fontFamily = PtSans,
                        textAlign = TextAlign.Start,
                        fontSize = 14.sp
                    )
                }
            }
        }
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            lineChartData = lineChartData
        )
    }
}


@Composable
fun caloriesCard(
    modifier: Modifier = Modifier
) {

}