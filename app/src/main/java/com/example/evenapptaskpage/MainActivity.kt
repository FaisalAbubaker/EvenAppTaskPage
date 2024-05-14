package com.example.evenapptaskpage

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.evenapptaskpage.ui.theme.EvenAppTaskPageTheme
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.stream.Collectors
import java.util.stream.Stream
import com.foreverrafs.datepicker.DatePickerTimeline
import com.foreverrafs.datepicker.Orientation
import com.foreverrafs.datepicker.state.rememberDatePickerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EvenAppTaskPageTheme {
                TaskPage()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun TaskPage() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(15.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var query by rememberSaveable { mutableStateOf("") }
        var active by remember { mutableStateOf(false) }
        SearchBar(query = query, onQueryChange = { query = it }, onSearch = { active = false },
            active = active, onActiveChange = { active = it },
            placeholder = {
                Text(text = "Search for task")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "")
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "",
                    modifier = Modifier.clickable {
                        if (query != "") {
                            query = ""
                        }
                    })
            },
            modifier = Modifier.fillMaxWidth()
        ) {
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 30.dp)
        ) {
            Text(text = "Task", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            val sdf = SimpleDateFormat("MMMM yyyy")
            val currentMonthAndYear = sdf.format(Date())
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
            Text(
                text = currentMonthAndYear,
                modifier = Modifier.padding(start = 10.dp)
            )
        }
        var date by rememberSaveable {
            mutableStateOf("Today")
        }
        DatePickerTimeline(
            modifier = Modifier,
            onDateSelected = {selectedDate: LocalDate->
                date = selectedDate.toString()
            }
        )
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp)) {
            Text(text = date, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Timer()
        }
        val list = listOf(
            "7:00",
            "8:00",
            "9:00",
            "10:00",
            "11:00",
            "12:00",
        )
        LazyColumn(Modifier.padding(top = 10.dp)) {
            list.forEach {
                item {
                    Divider()
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(114.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = it)
                        Text(
                            text = "      You don't have any schedule   or  ",
                            color = Color.Gray
                        )
                        Text(text = "+ Add", color = Color.Blue)
                    }
                }

            }
        }
    }
}

@Composable
private fun Timer() {
    var time by remember { mutableStateOf("") }
    val sdf = remember { SimpleDateFormat("HH:mm", java.util.Locale.ROOT)}
    LaunchedEffect(key1 = Unit){
        while(isActive){
            time = sdf.format(Date())
            delay(1000)
        }
    }
    Text(text = time)
}
