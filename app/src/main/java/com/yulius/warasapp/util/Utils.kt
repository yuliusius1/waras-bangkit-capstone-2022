package com.yulius.warasapp.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*

private const val FILENAME_FORMAT = "dd-MMM-yyyy"
const val DEFAULT_QUERY_ARTICLES = "covid"
const val DEFAULT_SORT_BY_ARTICLES = "publishedAt"
const val ARTICLE_API_KEY = "2a81a09b7fae49ba817399a2fc9cb666"

val timeStamp: String = SimpleDateFormat(
    FILENAME_FORMAT,
    Locale.US
).format(System.currentTimeMillis())

fun getAges(dateOfBirth : String): Int{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val dateOfBirthParse = dateFormat.parse(dateOfBirth)
    val today = dateFormat.format(Date())
    val todayParse = dateFormat.parse(today)
    var numberOfDays = 0L
    if(todayParse != null && dateOfBirthParse != null){
        numberOfDays = (todayParse.time - dateOfBirthParse.time)/(1000*60*60*24)
    }

    return numberOfDays.div(365).toInt()
}

fun changeTimeFormatCreatedAt(date: String) : String{
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateParse = dateFormat.parse(date)
    val dateFormat2 = SimpleDateFormat("dd LLLL yyyy", Locale.getDefault())
    return dateFormat2.format(dateParse)
}

fun changeTimeFormat(date: String? = "2022/01/01") : String{
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    val dateParse = dateFormat.parse(date)
    val dateFormat2 = SimpleDateFormat("dd LLLL yyyy", Locale.getDefault())
    return dateFormat2.format(dateParse)
}

fun addTime(time: Int): String{
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())

    val cal = Calendar.getInstance()
    cal.time = dateFormat.parse(dateFormat.format(Date()))
    cal.add(Calendar.DAY_OF_MONTH , time)

    return dateFormat.format(cal.time)
}

interface ResponseCallback {
    fun getCallback(msg:String,status: Boolean)
}

fun dateFormat(dateString: String?): String {
    return "${dateString?.substring(0,10)} ${dateString?.substring(12,19)}"
}

fun createTempFile(context: Context): File {
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    return File.createTempFile(timeStamp, ".jpg", storageDir)
}

fun uriToFile(selectedImg: Uri, context: Context): File {
    val contentResolver: ContentResolver = context.contentResolver
    val myFile = createTempFile(context)

    val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
    val outputStream: OutputStream = FileOutputStream(myFile)
    val buf = ByteArray(1024)
    var len: Int
    while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
    outputStream.close()
    inputStream.close()

    return myFile
}
