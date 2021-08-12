package io.github.thang86.weathertest.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import dagger.hilt.android.AndroidEntryPoint
import io.github.thang86.weathertest.R
import io.github.thang86.weathertest.databinding.WeatherFragmentBinding
import io.github.thang86.weathertest.extensions.getCurrentDate
import io.github.thang86.weathertest.ui.viewmodel.WeatherViewModel
import io.github.thang86.weathertest.ui.adapter.CalendarAdapter
import kotlinx.android.synthetic.main.weather_fragment.*
import java.util.*
import kotlin.collections.ArrayList

/**
 *
 *    WeatherFragment.kt
 *
 *    Created by ThangTX on 10/08/2021
 *
 */

@AndroidEntryPoint
class WeatherFragment : Fragment() {

    lateinit var binding: WeatherFragmentBinding


    private val mainViewModel: WeatherViewModel by viewModels()

    private val lastDayInCalendar = Calendar.getInstance(Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)

    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentYear = currentDate[Calendar.YEAR]

    private var selectedDay: Int = currentDay
    private var selectedMonth: Int = currentMonth
    private var selectedYear: Int = currentYear

    private val dates = ArrayList<Date>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel.fetchWeather(Calendar.getInstance().getCurrentDate())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.weather_fragment,
            container,
            false
        )
        binding.apply {
            viewModel = mainViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        /**
         * Adding SnapHelper here, but it is not needed. I add it just to looks better.
         */
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(calendar_recycler_view)

        /**
         * This is the maximum month that the calendar will display.
         * I set it for 6 months, but you can increase or decrease as much you want.
         */
        lastDayInCalendar.add(Calendar.MONTH, 6)

        setUpCalendar()
        return binding.root

    }

    /**
     * @param changeMonth I am using it only if next or previous month is not the current month
     */
    private fun setUpCalendar(changeMonth: Calendar? = null) {
        val monthCalendar = cal.clone() as Calendar
        val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        selectedDay =
            when {
                changeMonth != null -> changeMonth.getActualMinimum(Calendar.DAY_OF_MONTH)
                else -> currentDay
            }
        selectedMonth =
            when {
                changeMonth != null -> changeMonth[Calendar.MONTH]
                else -> currentMonth
            }
        selectedYear =
            when {
                changeMonth != null -> changeMonth[Calendar.YEAR]
                else -> currentYear
            }

        var currentPosition = 0
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        /*
         * Fill dates with days and set currentPosition.
         * currentPosition is the position of first selected day.
         */
        while (dates.size < maxDaysInMonth) {
            // get position of selected day
            if (monthCalendar[Calendar.DAY_OF_MONTH] == selectedDay)
                currentPosition = dates.size
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val calendarAdapter = CalendarAdapter(requireContext(), dates, currentDate, changeMonth)
        binding.calendarRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = calendarAdapter
        }

        /*
         * If you start the application, it centers the current day, but only if the current day
         */
        when {
            currentPosition > 2 -> binding.calendarRecyclerView.scrollToPosition(currentPosition - 3)
            maxDaysInMonth - currentPosition < 2 -> calendar_recycler_view!!.scrollToPosition(
                currentPosition
            )
            else -> calendar_recycler_view!!.scrollToPosition(currentPosition)
        }


        /*
         * After calling up the OnClickListener, the text of the current month and year is changed.
         * Then change the selected day.
         */
        calendarAdapter.setOnItemClickListener(object : CalendarAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val clickCalendar = Calendar.getInstance()
                clickCalendar.time = dates[position]
                selectedDay = clickCalendar[Calendar.DAY_OF_MONTH]

                val newDate =
                    "" + clickCalendar[Calendar.YEAR] + "/" + (clickCalendar[Calendar.MONTH] + 1) + "/" + clickCalendar[Calendar.DAY_OF_MONTH]
                mainViewModel.fetchWeather(newDate)
            }
        })
    }

//    fun consumeEvent(calendar: Calendar, myFunc: () -> Int, currentDate: Int): Int {
//        return when {
//            calendar != null -> myFunc()
//            else -> currentDate
//        }
////        selectedDay =
////            when {
////                changeMonth != null -> changeMonth.getActualMinimum(Calendar.DAY_OF_MONTH)
////                else -> currentDay
////            }
////        selectedMonth =
////            when {
////                changeMonth != null -> changeMonth[Calendar.MONTH]
////                else -> currentMonth
////            }
////        selectedYear =
////            when {
////                chang/**/eMonth != null -> changeMonth[Calendar.YEAR]
////                else -> currentYear
////            }
//
//    }
}
