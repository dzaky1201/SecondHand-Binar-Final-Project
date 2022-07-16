package com.binar.secondhand.core.utils

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

@SuppressLint("SimpleDateFormat")
object DateTimeFormat {

    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    private val monthListIn = listOf("","Januari", "Februari", "Maret", "April", "Mei","Juni","Juli","Agustus","September","Oktober","November","Desember")
    private val monthListIn3Char = listOf("","Jan", "Feb", "Mar", "April", "Mei","Jun","Jul","Agu","Sep","Okt","Nov","Des")

    private val monthListEn = listOf("","January","February","March","April","May","June","July","August","September","October","November","December")
    private val monthListEn3Char = listOf("","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")

    fun dateFormatFromString(stringDate : String, pattern : String, stringTime: String = "") : String {
        var result = "-"
        if(stringDate.isNotEmpty()) {
            //val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            //val pattern ="yyyy-MM-dd HH:mm:ss"
            var optStr: String = stringDate.trim()
            if (stringDate.length < 16) {
                if(stringTime == "") {
                    optStr = "$stringDate 00:00:00"
                } else {
                    optStr = "$stringDate $stringTime"
                }
            }

            //Log.d("get string",optStr)
            val dateFormat = SimpleDateFormat(pattern)  //date Date
            //val date = Date()
            getDateFromString(optStr)?.let { result = dateFormat.format(it) }
        }
        return result
    }

    fun currentDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }

    fun currentDateFormat(pattern: String) : String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format( Date())
    }

    fun getTimeAgo(date: Date): String {
        var time = date.time
        if (time < 1000000000000L) {
            time *= 1000
        }

        val now = currentDate().time
        if (time > now || time <= 0) {
            return "in the future"
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> "a moments ago"
            diff < 2 * MINUTE_MILLIS -> "a minute ago"
            diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS} Minutes ago"
            diff < 2 * HOUR_MILLIS -> "an hour ago"
            diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS} Hours ago"
            diff < 48 * HOUR_MILLIS -> "Yesterday"
            else -> getStringFromDate(date, "dd MMM yyyy") //"${diff / DAY_MILLIS} days ago"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun localdateToDateFormat(date : LocalDate, format : String) : String {
        //val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        //val dateFormat = SimpleDateFormat(format)
        //val date = Date()
        //return dateFormat.format(date)

        //LocalDate today = LocalDate.now();

        return date.format(DateTimeFormatter.ofPattern(format.trim()))

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stringDateToLocalDate(inputDate : String) : LocalDate {
        //Locale.setDefault(Locale.US)
        val formatter : DateTimeFormatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val dt : LocalDate  = LocalDate.parse(inputDate.trim(), formatter)
        return dt
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateToLocalDate(date: Date) : LocalDate {
        return Instant.ofEpochMilli(date.time).atZone(ZoneId.systemDefault()).toLocalDate()
    }

    fun getDateFromString(string : String) : Date? {
        var resultIme : Date? = Date()
        if(string.isNotEmpty()) {
            //val dtStart = "2010-10-15T09:27:37Z"
            var newString = string.trim() //.replace(" ","")
            if (string.trim().length < 16)
                newString = "$string 00:00:00"

            val pattern = "yyyy-MM-dd HH:mm:ss"
            val format = SimpleDateFormat(pattern) //date Date
            resultIme = format.parse(newString)
        }
        return resultIme
    }

    fun getStringFromDate(string : Date, pattern : String) : String {
        //val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        //val pattern ="yyyy-MM-dd HH:mm:ss"
        val dateFormat = SimpleDateFormat(pattern.trim())  //date Date
        //val date = Date()
        return dateFormat.format(string)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayNumberFromDate(date : LocalDate) : String {
        val pattern="dd"
        //val dateFormat = SimpleDateFormat(pattern)
        return localdateToDateFormat(
            date,
            pattern
        ) //dateFormat.format(date)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayNameIndoFromDate(date : LocalDate): String {
        val pattern="EE"
        //val dateFormat = SimpleDateFormat(pattern)
        var day=
            localdateToDateFormat(date, pattern) //dateFormat.format(date)
        if(day.toLowerCase() == "sen" || day.toLowerCase() == "mon")
            day="Senin"
        else if(day.toLowerCase() == "sel" || day.toLowerCase() == "tue")
            day="Selasa"
        else if(day.toLowerCase() == "rab" || day.toLowerCase() == "wed")
            day="Rabu"
        else if(day.toLowerCase() == "kam" || day.toLowerCase() == "thu")
            day="Kamis"
        else if(day.toLowerCase() == "jum" || day.toLowerCase() == "fri")
            day="Jum'at"
        else if(day.toLowerCase() == "sab" || day.toLowerCase() == "sat")
            day="Sabtu"
        else if(day.toLowerCase() == "min" || day.toLowerCase() == "sun")
            day="Ahad"
        return day
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthNumberFromDate(date : LocalDate) : String {
        val pattern="MM"
        //val dateFormat = SimpleDateFormat(pattern)
        return localdateToDateFormat(
            date,
            pattern
        ) //dateFormat.format(date) // -> date Date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMonthNameFromDate(date : LocalDate) : String {
        val pattern="MMMM"
        //val dateFormat = SimpleDateFormat(pattern)
        return localdateToDateFormat(
            date,
            pattern
        ) //dateFormat.format(date) // -> date Date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getYearFromDate(date : LocalDate) : String {
        val pattern="yyyy"
        //val dateFormat = SimpleDateFormat(pattern)
        return localdateToDateFormat(
            date,
            pattern
        ) //dateFormat.format(date) // -> date Date , if type is Date
    }

    fun getMonthName(month : Int, lang : String ="en") : String {
        val monthSelected : List<String>
        if(lang=="in") {
            monthSelected= monthListIn
        } else {
            monthSelected= monthListEn
        }
        return monthSelected[month]
    }

    fun getMonthName3Char(month : Int, lang : String ="en") : String {
        val monthSelected : List<String>
        if(lang=="in") {
            monthSelected= monthListIn3Char
        } else {
            monthSelected= monthListEn3Char
        }
        return monthSelected[month]
    }

    fun getDateDifferenceBetweenTwoDateTime(startDateTime : String ,endDateTime : String ) : HashMap<String, Long> {
        val result = HashMap<String, Long>()
        //val startDateTime = "2014-06-18 12:56:50"
        //val endDateTime = "2014-06-18 12:56:50"
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        try {
            val oldDate = dateFormat.parse(startDateTime.trim())
            println(oldDate)

            val newDate = dateFormat.parse(endDateTime.trim())
            println(newDate)

            //val currentDate = Date() //newDate

            val diff = newDate.time - oldDate.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            val days = hours / 24

            result["milis"] = diff
            result["seconds"] = seconds
            result["minutes"] = minutes
            result["hours"] = hours
            result["days"] = days

            if (oldDate.before(newDate)) {
                Log.e("oldDate", "is previous date")
                Log.e("Difference: ", " seconds: $seconds minutes: $minutes hours: $hours days: $days")
            }
            // Log.e("toyBornTime", "" + toyBornTime);
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }

    fun getDaysBetweenDates(dateStart: String, dateEnd: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)
        val startDate: Date
        val endDate: Date
        var numberOfDays: Long = 0
        try {
            startDate = dateFormat.parse(dateStart)!!
            endDate = dateFormat.parse(dateEnd)!!
            numberOfDays = getUnitBetweenDates(startDate, endDate, TimeUnit.DAYS)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return numberOfDays
    }

    private fun getUnitBetweenDates(startDate: Date, endDate: Date, unit: TimeUnit): Long {
        val timeDiff = endDate.time - startDate.time
        return unit.convert(timeDiff, TimeUnit.MILLISECONDS)
    }

    fun addMinutesFromDateTimeString(dateTime: String, minutesToAdd: Int): Date? {
        var result: Date? = null

        //val realMinutesToAdd = minutesToAdd + 1

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.parse(dateTime) //Date()
        println("Current Date " + dateFormat.format(date))

        // Convert Date to Calendar
        val c = Calendar.getInstance()
        c.time = date

        // Perform addition/subtraction
        //c.add(Calendar.YEAR, 0)
        //c.add(Calendar.MONTH, 0)
        //c.add(Calendar.DATE, 0)
        //c.add(Calendar.HOUR, 0)
        c.add(Calendar.MINUTE, minutesToAdd)
        //c.add(Calendar.SECOND, 0)

        // Convert calendar back to Date
        val dateTimeAfterAdded = c.time

        val resultDateTime = dateFormat.format(dateTimeAfterAdded)

        return dateTimeAfterAdded
    }

    fun addHoursFromDateTimeString(dateTime: String, hoursToAdd: Int): Date? {
        var result: Date? = null

        //val realMinutesToAdd = minutesToAdd + 1

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.parse(dateTime) //Date()
        println("Current Date " + dateFormat.format(date))

        // Convert Date to Calendar
        val c = Calendar.getInstance()
        c.time = date

        // Perform addition/subtraction
        //c.add(Calendar.YEAR, 0)
        //c.add(Calendar.MONTH, 0)
        //c.add(Calendar.DATE, 0)
        c.add(Calendar.HOUR, hoursToAdd)
        //c.add(Calendar.MINUTE, 0)
        //c.add(Calendar.SECOND, 0)

        // Convert calendar back to Date
        val dateTimeAfterAdded = c.time

        val resultDateTime = dateFormat.format(dateTimeAfterAdded)

        return dateTimeAfterAdded
    }

    fun addDaysFromDateTimeString(dateTime: String, daysToAdd: Int): Date? {
        var result: Date? = null

        //val realMinutesToAdd = minutesToAdd + 1

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.parse(dateTime) //Date()
        println("Current Date " + dateFormat.format(date))

        // Convert Date to Calendar
        val c = Calendar.getInstance()
        c.time = date

        // Perform addition/subtraction
        //c.add(Calendar.YEAR, 0)
        //c.add(Calendar.MONTH, 0)
        c.add(Calendar.DATE, daysToAdd)
        //c.add(Calendar.HOUR, 0)
        //c.add(Calendar.MINUTE, 0)
        //c.add(Calendar.SECOND, 0)

        // Convert calendar back to Date
        val dateTimeAfterAdded = c.time

        val resultDateTime = dateFormat.format(dateTimeAfterAdded)

        return dateTimeAfterAdded
    }

    fun addDaysFromDateTimeStringToString(dateTime: String, daysToAdd: Int): String {
        var result: Date? = null

        //val realMinutesToAdd = minutesToAdd + 1
        var dateTimeComplete = dateTime
        if (dateTime.length < 16)
            dateTimeComplete = "$dateTime 00:00:00"

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.parse(dateTimeComplete) //Date()
        println("Current Date " + dateFormat.format(date))

        // Convert Date to Calendar
        val c = Calendar.getInstance()
        c.time = date

        // Perform addition/subtraction
        //c.add(Calendar.YEAR, 0)
        //c.add(Calendar.MONTH, 0)
        c.add(Calendar.DATE, daysToAdd)
        //c.add(Calendar.HOUR, 0)
        //c.add(Calendar.MINUTE, 0)
        //c.add(Calendar.SECOND, 0)

        // Convert calendar back to Date
        val dateTimeAfterAdded = c.time

        val resultDateTime = dateFormat.format(dateTimeAfterAdded)

        return resultDateTime //dateTimeAfterAdded
    }

    fun addMonthFromDateTimeString(dateTime: String, monthToAdd: Int): Date? {
        var result: Date? = null

        //val realMinutesToAdd = minutesToAdd + 1

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = dateFormat.parse(dateTime) //Date()
        println("Current Date " + dateFormat.format(date))

        // Convert Date to Calendar
        val c = Calendar.getInstance()
        c.time = date

        // Perform addition/subtraction
        //c.add(Calendar.YEAR, 0)
        c.add(Calendar.MONTH, monthToAdd)
        //c.add(Calendar.DATE, 0)
        //c.add(Calendar.HOUR, 0)
        //c.add(Calendar.MINUTE, 0)
        //c.add(Calendar.SECOND, 0)

        // Convert calendar back to Date
        val dateTimeAfterAdded = c.time

        val resultDateTime = dateFormat.format(dateTimeAfterAdded)

        return dateTimeAfterAdded
    }

    fun getCurrentDayOfWeekFromDate(date: Date): Int {
        var currentDayOfWeek = Calendar.SUNDAY

        //val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        //val date = dateFormat.parse(dateTime) //Date()
        //println("Current Date " + dateFormat.format(date))

        // Convert Date to Calendar
        val calendar = Calendar.getInstance()
        calendar.time = date

        currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        return currentDayOfWeek
    }

    fun getCurrentDayOfWeekFromDateString(dateTime: String): Int {
        var currentDayOfWeek = Calendar.SUNDAY



        val dateFormat = if (dateTime.trim().length < 16)
            SimpleDateFormat("yyyy-MM-dd")
        else
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val date = dateFormat.parse(dateTime) //Date()
        //println("Current Date " + dateFormat.format(date))

        // Convert Date to Calendar
        val calendar = Calendar.getInstance()
        calendar.time = date

        currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        return currentDayOfWeek
    }

    fun getCurrentDayOfWeek(): Int {
        val calendar = Calendar.getInstance()
        val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        return currentDayOfWeek
        //return 1=sunday, 2=monday, ..... 7= saturday
    }

    fun getCountDayInMonth(): Int {
        var result = 31

        val calendar = Calendar.getInstance()
        //calendar.set(Calendar.YEAR, year)
        //calendar.set(Calendar.MONTH, month)
        result = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        return result
    }

    fun getStartDateAndEndDateOfWeek(myDate :String) : ArrayList<String> {
        val dates: ArrayList<String> = arrayListOf()
        val cal = Calendar.getInstance()
        cal.time = SimpleDateFormat("yyyy-MM-dd").parse(myDate)
        Log.e("==== ","== My Date  : ${SimpleDateFormat("EEE,dd MMM yyyy").format(SimpleDateFormat("yyyy-MM-dd").parse(myDate))} ")
        Log.e("== ","Day of Week : ${cal.get(Calendar.DAY_OF_WEEK)}")

        var startDate =""
        var endDate =""

        when(cal.get(Calendar.DAY_OF_WEEK)){
            1 ->{
                val date = getOldDate(6,cal.time)
                startDate = SimpleDateFormat("EEE,dd MMM yyyy").format(date)
                endDate = SimpleDateFormat("EEE,dd MMM yyyy").format(cal.time)

                Log.e("======","----------------------------------------------------")
                Log.e("======","----------------User Date is Sunday------------------")
                Log.e("======","----------------------------------------------------")

                Log.e("======","Start Date : $startDate")
                Log.e("======","End Date : $endDate")

                Log.e("======","----------------------------------------------------")
                dates.add(startDate)
                dates.add(endDate)
            }
            2 ->{
                val sdate = getOldDate(0,cal.time)
                val edate = getOldDate(-6,cal.time)
                startDate = SimpleDateFormat("EEE,dd MMM yyyy").format(sdate)
                endDate = SimpleDateFormat("EEE,dd MMM yyyy").format(edate)

                Log.e("======","----------------------------------------------------")
                Log.e("======","----------------User Date is Monday------------------")
                Log.e("======","----------------------------------------------------")

                Log.e("======","Start Date : $startDate")
                Log.e("======","End Date : $endDate")

                Log.e("======","----------------------------------------------------")
                dates.add(startDate)
                dates.add(endDate)
            }
            3 ->{
                val sdate = getOldDate(1,cal.time)
                val edate = getOldDate(-5,cal.time)
                startDate = SimpleDateFormat("EEE,dd MMM yyyy").format(sdate)
                endDate = SimpleDateFormat("EEE,dd MMM yyyy").format(edate)

                Log.e("======","----------------------------------------------------")
                Log.e("======","----------------User Date is Tuesday------------------")
                Log.e("======","----------------------------------------------------")

                Log.e("======","Start Date : $startDate")
                Log.e("======","End Date : $endDate")

                Log.e("======","----------------------------------------------------")
                dates.add(startDate)
                dates.add(endDate)
            }
            4 ->{
                val sdate = getOldDate(2,cal.time)
                val edate = getOldDate(-4,cal.time)
                startDate = SimpleDateFormat("EEE,dd MMM yyyy").format(sdate)
                endDate = SimpleDateFormat("EEE,dd MMM yyyy").format(edate)

                Log.e("======","----------------------------------------------------")
                Log.e("======","----------------User Date is Wednesday------------------")
                Log.e("======","----------------------------------------------------")

                Log.e("======","Start Date : $startDate")
                Log.e("======","End Date : $endDate")

                Log.e("======","----------------------------------------------------")
                dates.add(startDate)
                dates.add(endDate)
            }
            5 ->{
                val sdate = getOldDate(3,cal.time)
                val edate = getOldDate(-3,cal.time)
                startDate = SimpleDateFormat("EEE,dd MMM yyyy").format(sdate)
                endDate = SimpleDateFormat("EEE,dd MMM yyyy").format(edate)

                Log.e("======","----------------------------------------------------")
                Log.e("======","----------------User Date is Thursday------------------")
                Log.e("======","----------------------------------------------------")

                Log.e("======","Start Date : $startDate")
                Log.e("======","End Date : $endDate")

                Log.e("======","----------------------------------------------------")
                dates.add(startDate)
                dates.add(endDate)
            }
            6 ->{
                val sdate = getOldDate(4,cal.time)
                val edate = getOldDate(-2,cal.time)
                startDate = SimpleDateFormat("EEE,dd MMM yyyy").format(sdate)
                endDate = SimpleDateFormat("EEE,dd MMM yyyy").format(edate)
                Log.e("======","----------------------------------------------------")
                Log.e("======","----------------User Date is Friday------------------")
                Log.e("======","----------------------------------------------------")

                Log.e("======","Start Date : $startDate")
                Log.e("======","End Date : $endDate")

                Log.e("======","----------------------------------------------------")
                dates.add(startDate)
                dates.add(endDate)
            }
            7 ->{
                val sdate = getOldDate(5,cal.time)
                val edate = getOldDate(-1,cal.time)
                startDate = SimpleDateFormat("EEE,dd MMM yyyy").format(sdate)
                endDate = SimpleDateFormat("EEE,dd MMM yyyy").format(edate)

                Log.e("======","----------------------------------------------------")
                Log.e("======","----------------User Date is Saturday------------------")
                Log.e("======","----------------------------------------------------")

                Log.e("======","Start Date : $startDate")
                Log.e("======","End Date : $endDate")

                Log.e("======","----------------------------------------------------")
                dates.add(startDate)
                dates.add(endDate)
            }
        }

        return dates
        //getStartEndOFWeek(cal.get(Calendar.WEEK_OF_YEAR),cal.get(Calendar.YEAR),myDate)
    }


    fun getOldDate(daysAgo: Int,myDate: Date): Date {
        val calendar = Calendar.getInstance()
        /*calendar.set(Calendar.MONTH, APRIL)
        calendar.set(Calendar.DAY_OF_MONTH,6)
        calendar.set(Calendar.YEAR,2020)
        Log.e("==== ","=====  Date  : ${calendar.time}")*/
        calendar.add(Calendar.DAY_OF_YEAR, -daysAgo)
        //Log.e("==== ","=====  Date $daysAgo ago : ${calendar.time}")
        return calendar.time
    }

    fun isDateBeforeToday(date: Date): Boolean {
        //var result = false
        val today = Date()
        today.hours = 0
        today.minutes = 0
        today.seconds = 0

        return date.before(today)
    }

    fun isDateAfterToday(date: Date): Boolean {
        //var result = false
        val today = Date()
        today.hours = 0
        today.minutes = 0
        today.seconds = 0

        return date.after(today)
    }

}