package com.whiska.pocketweather.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import com.whiska.pocketweather.R
import java.util.Calendar

class ComposeMainActivity : ComponentActivity() {

    private val calendar by lazy { Calendar.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            MainScreen()
        }
    }

    private fun isNightNow(): Boolean {
        return calendar.get(Calendar.HOUR_OF_DAY) >= 18
    }

    @Composable
    fun MainScreen() {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (bgImg, weatherView, searchCity, cityTxt, progressBar, dataDetailLayout, forecastBlurView) = createRefs()
            val drawable = if(isNightNow()) R.drawable.night_bg
            else {
                retrieveBackgroundByIcon(icon)
            }
            Image(
                painter = painterResource(id = R.drawable.bg_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(bgImg) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            // Placeholder for custom WeatherView, replace with actual implementation
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(weatherView) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            Image(
                painter = painterResource(id = R.drawable.baseline_search_24), // Replace with your drawable
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 24.dp, end = 24.dp)
                    .constrainAs(searchCity) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )

            BasicText(
                text = "-",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    shadow = Shadow(
                        color = Color.Black,
                        blurRadius = 3f
                    )
                ),
                modifier = Modifier
                    .padding(top = 64.dp)
                    .constrainAs(cityTxt) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )

            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBar) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .constrainAs(dataDetailLayout) {
                        top.linkTo(cityTxt.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                DetailItem(
                    iconResId = R.drawable.wind, // Replace with your drawable
                    value = "-Km",
                    label = "Wind"
                )
                DetailItem(
                    iconResId = null,
                    value = "-",
                    label = "",
                    isLargeText = true,
                    isCenterItem = true
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                    ) {
                        BasicText(
                            text = "-",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 16.sp
                            )
                        )
                        BasicText(
                            text = "0",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 66.sp,
                                shadow = Shadow(
                                    color = Color.Black,
                                    blurRadius = 3f
                                )
                            )
                        )
                        Row(
                            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.arrow_up), // Replace with your drawable
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 8.dp)
                            )
                            BasicText(
                                text = "0",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 18.sp
                                ),
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            BasicText(
                                text = "0",
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 18.sp
                                )
                            )
                            Image(
                                painter = painterResource(id = R.drawable.arrow_down), // Replace with your drawable
                                contentDescription = null,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
                DetailItem(
                    iconResId = R.drawable.humidity, // Replace with your drawable
                    value = "-0%",
                    label = "Humidity"
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(143.dp)
                    .background(Color(0x10ffffff))
                    .padding(start = 24.dp, end = 24.dp, bottom = 48.dp)
                    .constrainAs(forecastBlurView) {
                        bottom.linkTo(bgImg.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Add your forecast list items here
                }
            }
        }
    }

    @Composable
    fun DetailItem(
        iconResId: Int?,
        value: String,
        label: String,
        isLargeText: Boolean = false,
        isCenterItem: Boolean = false,
        content: @Composable () -> Unit = {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = if (isCenterItem) androidx.compose.ui.Alignment.CenterHorizontally else androidx.compose.ui.Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                if (iconResId != null) {
                    Image(
                        painter = painterResource(id = iconResId),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                BasicText(
                    text = value,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = if (isLargeText) 66.sp else 18.sp,
                        textAlign = if (isCenterItem) androidx.compose.ui.text.style.TextAlign.Center else androidx.compose.ui.text.style.TextAlign.Start
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                BasicText(
                    text = label,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 12.sp,
                        textAlign = if (isCenterItem) androidx.compose.ui.text.style.TextAlign.Center else androidx.compose.ui.text.style.TextAlign.Start
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}