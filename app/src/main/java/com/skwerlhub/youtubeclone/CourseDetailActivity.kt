package com.skwerlhub.youtubeclone

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.course_lesson_row.view.*
import okhttp3.*
import java.io.IOException

class CourseDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)

        // change nav bar title
        val navBarTitle = intent.getStringExtra(CustomViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = navBarTitle

        fetchJSON()
    }

    private  fun fetchJSON() {
        val videoId = intent.getIntExtra(CustomViewHolder.VIDEO_ID_KEY, -1)
        val courseDetailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=" + videoId

        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailUrl).build()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()
                val courseLessons = gson.fromJson(body, Array<CourseLesson>::class.java)
                runOnUiThread {
                    recyclerView_main.adapter = CourseDetailAdapter(courseLessons)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
            }
        })
    }

    private class CourseDetailAdapter(
        val courseLessons: Array<CourseLesson>
    ): RecyclerView.Adapter<CourseLessonViewHolder>() {
        override fun getItemCount(): Int {
            return courseLessons.size
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CourseLessonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val customView =  layoutInflater.inflate(
                R.layout.course_lesson_row,
                parent,
                false
            )

            return CourseLessonViewHolder(customView)
        }

        override fun onBindViewHolder(holder: CourseLessonViewHolder, position: Int) {
            val courseLesson = courseLessons.get(position)

            holder.customView.textView_course_lesson_title?.text = courseLesson.name
            holder.customView.textView_duration?.text = courseLesson.duration

            val imageView = holder.customView.imageView_course_lesson_thumbnail
            Picasso.get().load(courseLesson.imageUrl).into(imageView)

            holder.courseLesson = courseLesson
        }
    }

    class CourseLessonViewHolder(
        val customView: View,
        var courseLesson: CourseLesson? = null
    ): RecyclerView.ViewHolder(customView) {
        companion object {
            val COURSE_LESSON_LINK_KEY = "COURSE_LESSON_LINK"
        }

        init {
            customView.setOnClickListener {
                println("Attempt to load webview somehow")

                val intent = Intent(customView.context, CourseLessonActivity::class.java)

                intent.putExtra(COURSE_LESSON_LINK_KEY, courseLesson?.link)

                customView.context.startActivity(intent)
            }
        }
    }
}
