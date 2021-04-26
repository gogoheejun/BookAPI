package com.hjhj.bookapi

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.bumptech.glide.Glide
import com.hjhj.bookapi.databinding.ActivityDetailBinding
import com.hjhj.bookapi.model.Book

class DetailActivity:AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_detail)
//        binding = ActivityDetailBinding.inflate(layoutInflater)
//        setContentView(binding.root)
        Log.e("BookAdapter","test")
//
//        db = Room.databaseBuilder(
//            applicationContext,
//            AppDatabase::class.java,
//            "BookSearchDB"
//        ).build()

//        val model = intent.getParcelableExtra<Book>("bookModel")
//        Log.e("BookAdapter",model?.title.orEmpty())
//        binding.titleTextView.text = model?.title.orEmpty() //안넘어왔을수도있으니까
//        binding.descriptionTextView.text = model?.description.orEmpty()
//
//
//
//        Glide.with(binding.coverImageView.context)
//            .load(model?.coverSmallUrl.orEmpty())
//            .into(binding.coverImageView)
    }
}