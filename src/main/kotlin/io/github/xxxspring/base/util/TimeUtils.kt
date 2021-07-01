package io.github.xxxspring.base.util

import org.joda.time.DateTime
import java.text.ParseException
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class TimeUtils {

    companion object{

        //当前时间的 字符串 "yyyy-MM-dd HH:mm:ss"
        fun stringDate(): String{
            val currentTime = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return formatter.format(currentTime)
        }

        //当前时间的 短字符串 "yyyy-MM-dd"
        fun stringDateShort(): String{
            val currentTime = Date()
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            return formatter.format(currentTime)
        }

        //将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
        fun dateToStrLong(dataDate: Date): String{
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val dateString = formatter.format(dataDate)
            return dateString
        }

        //将某个时间 转换为 短字符串
        fun dateToStrShort(dataDate: Date): String{
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val dateString = formatter.format(dataDate)
            return dateString
        }

        //时间戳 转换成 长字符串
        fun timeToLongStr(time: Long): String{
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return formatter.format(DateTime(time).toDate())
        }

        //时间戳 转换成 短字符串
        fun timeToShortStr(time: Long): String{
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            return formatter.format(DateTime(time).toDate())
        }

        //时间戳转换成 日期格式
        fun timeToDate(time: Long): Date{
            val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            System.out.println("vvvvvvv" +DateTime(time))
            return DateTime(time).toDate()
        }

        // 是否是月末
        fun isMonthEnd(date: Date): Boolean{
            var calendar = Calendar.getInstance()
            calendar.setTime(date)
            var getDay = calendar.get(Calendar.DAY_OF_MONTH);
            return getDay == calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        }

        // 获取本月最后一天 的日期
        fun getMonthEndDate(date: Date): Date {
            val calendar = Calendar.getInstance()
            calendar.setTime(date)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            return calendar.time
        }

        // 获取本月最后一天 的 天数
        fun getMonthEndDateDay(date: Date): Int {
            val calendar = Calendar.getInstance()
            calendar.setTime(date)
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
            return calendar.get(Calendar.DAY_OF_MONTH)
        }

    }

}
fun main(args: Array<String>){
    val t0 = Date()
    System.out.println("t0: $t0")

    val t1 = Date().time
    System.out.println("t1: $t1")

    val t2 = TimeUtils.timeToDate(t1)
    System.out.println("t2: $t2")

    val t3 = TimeUtils.timeToShortStr(t1)
    System.out.println("t3: $t3")

    val t4 = TimeUtils.timeToLongStr(t1)
    System.out.println("t4: $t4")
}
