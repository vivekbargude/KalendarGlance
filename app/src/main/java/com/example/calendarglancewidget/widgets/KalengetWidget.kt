package com.example.calendarglancewidget.widgets

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.*
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.updateAll
import androidx.glance.color.ColorProvider
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import com.example.calendarglancewidget.utils.DateTimeUtils
import com.example.calendarglancewidget.utils.DateTimeUtils.FULL_DATE_FORMAT
import java.util.Calendar
import java.util.Locale

class KalengetWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = KalengetWidget()

    suspend fun updateAllWidget(context: Context) {
        glanceAppWidget.updateAll(context = context)
    }
}

class KalengetWidget : GlanceAppWidget() {
    override val sizeMode: SizeMode
        get() = SizeMode.Exact


    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {

            val milliSecond = remember {
                mutableLongStateOf(System.currentTimeMillis())
            }

            val calendar = DateTimeUtils.getCalendar(milliSecond.longValue)
            KalengetContent(
                calendar = calendar,
                context = context
            )

        }
    }
}

@Composable
fun KalengetContent(
    calendar: Calendar,
    context: Context
) {
    Box(
        modifier = GlanceModifier
            .cornerRadius(30.dp)
            .fillMaxSize()
            .background(
                    day = Color(0XFF8390B2),
                    night = Color(0XFF343C51)
            )
            .padding(16.dp).clickable {
                Toast.makeText(
                    context,
                    "Today is ${
                        DateTimeUtils.formatDateTime(calendar.timeInMillis, FULL_DATE_FORMAT)
                    }",
                    Toast.LENGTH_SHORT
                ).show()
            },
        contentAlignment = Alignment.Center
    ) {

        Row(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = calendar.get(Calendar.DATE).toString(),
                style = TextStyle(
                    color = ColorProvider(
                        day = Color(0XFF3A3F4E),
                        night = Color(0XFFCDD5EA)
                    ),
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Bold
                ),
            )

            Column(
                modifier = GlanceModifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = calendar.getDisplayName(
                        Calendar.DAY_OF_WEEK,
                        Calendar.LONG,
                        Locale.getDefault()
                    ) ?: "SUNDAY",
                    style = TextStyle(
                        color = ColorProvider(
                            day = Color(0XFF4F566B),
                            night = Color(0XFF9AA2B7)
                        ),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "${
                        calendar.getDisplayName(
                            Calendar.MONTH,
                            Calendar.LONG,
                            Locale.getDefault()
                        )
                    }, ${calendar.get(Calendar.YEAR)}",
                    style = TextStyle(
                        color = ColorProvider(
                            day = Color(0XFF4F566B),
                            night = Color(0XFF9AA2B7)
                        ),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

    }
}