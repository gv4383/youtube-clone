package com.skwerlhub.youtubeclone

import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class CourseDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = CourseDetailAdapter()
    }

    private class CourseDetailAdapter: RecyclerView.Adapter<CourseLessonViewHolder>() {
        override fun getItemCount(): Int {
            return 2
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CourseLessonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val customView =  layoutInflater.inflate(R.layout.course_lesson_row, parent, false)

//            val blueView = View(parent.context)
//            blueView.setBackgroundColor(Color.BLUE)
//            blueView.minimumHeight = 50

            return CourseLessonViewHolder(customView)
        }

        override fun onBindViewHolder(holder: CourseLessonViewHolder, position: Int) {
        }
    }

    private class CourseLessonViewHolder(val customView: View): RecyclerView.ViewHolder(customView) {

    }
}
