package com.example.jsonparsing

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var courseNameTV: TextView
    private lateinit var courseDescTV: TextView
    private lateinit var courseReqTV: TextView
    private lateinit var courseIV: ImageView
    private lateinit var visitCourseBtn: Button
    private lateinit var loadingPB: ProgressBar
    private var url = "https://jsonkeeper.com/b/8RFY"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        courseNameTV = findViewById(R.id.idTVCourseName)
        courseDescTV = findViewById(R.id.idTVDesc)
        courseReqTV = findViewById(R.id.idTVPreq)
        courseIV = findViewById(R.id.idIVCourse)
        visitCourseBtn = findViewById(R.id.idBtnVisitCourse)
        loadingPB = findViewById(R.id.idLoadingPB)

        val requestqueue: RequestQueue = Volley.newRequestQueue(applicationContext)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            loadingPB.visibility = View.GONE
            try {
                val courseName: String = response.getString("courseName")
                val courseLink: String = response.getString("courseLink")
                val courseImg: String = response.getString("courseimg")
                val courseDesc: String = response.getString("courseDesc")
                val Prerequisites: String = response.getString("Prerequisites")
                courseReqTV.text = Prerequisites
                courseDescTV.text = courseDesc
                courseNameTV.text = courseName
                Picasso.get().load(courseImg).into(courseIV)
                visitCourseBtn.visibility = View.VISIBLE
                visitCourseBtn.setOnClickListener {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(courseLink)
                    startActivity(i)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }, { error ->
            Log.e("TAG", "RESPONSE IS $error")
            Toast.makeText(this@MainActivity, "Failed to get a response", Toast.LENGTH_SHORT)
                .show()
        })
        requestqueue.add(jsonObjectRequest)
    }}


