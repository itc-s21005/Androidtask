package jp.ac.it_college.std.s21005.androidtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import jp.ac.it_college.std.s21005.androidtask.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.URL


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val url = "https://api.moemoe.tokyo/anime/v1/master/"

        val btn2021: Button = findViewById(R.id.bt2021)
        val btn2022: Button = findViewById(R.id.bt2022)


        btn2022.setOnClickListener {
            val animeUrl = "${url}2022"
            animeTask(animeUrl)
        }

        btn2021.setOnClickListener {
            val animeUrl = "${url}2021"

            animeTask(animeUrl)
        }
    }
    private fun animeTask(animeUrl:String){
        lifecycleScope.launch{
            val result = animeBackgroundTask(animeUrl)

            animeJsonTask(result)
        }

    }
    private suspend fun animeBackgroundTask(animeUrl: String):String{
        val response = withContext(Dispatchers.IO){
            var  httpResult = ""
            try {
                val urlObj = URL(animeUrl)

                val br = BufferedReader(InputStreamReader(urlObj.openStream()))
                httpResult = br.readText()
            }catch (e:IOException){
                e.printStackTrace()
            }catch (e:JSONException){
                e.printStackTrace()
            }
            return@withContext httpResult
            //return@withContext
        }

        return response
    }
    private fun animeJsonTask(result:String){
        val txanimename: TextView = findViewById(R.id.animename)
        val jsonObj = JSONArray(result)
        //val jsonObj = JSONObject(result)
        //val jsonObj = JSONObject(result)

        //.split("id", "title","{","}","[","]",":",")
        //.replace("  ", " ")
        val random = (0..30).random()
        val animeName = jsonObj.getString(random).split("id", "title","{","}","[","]",":",",")

        //val animeName = jsonObj.getString("title")
        txanimename.text = animeName.toString()
    }
}