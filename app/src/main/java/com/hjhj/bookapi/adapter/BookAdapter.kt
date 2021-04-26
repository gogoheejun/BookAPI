package com.hjhj.bookapi.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hjhj.bookapi.databinding.ItemBookBinding
import com.hjhj.bookapi.model.Book

class BookAdapter(private val myItemClickListener:(Book)-> Unit):ListAdapter<Book, BookAdapter.BookItemViewHolder>(diffUtil) {

    //※1
    inner class BookItemViewHolder(private val binding: ItemBookBinding): RecyclerView.ViewHolder(binding.root){

        //ItemBookBinding이란 애는 이름을 xml layout이랑 맞춰서 item_book 만드삼
        //※3
        fun bind(bookModel:Book){
            binding.titleTextView.text = bookModel.title
            binding.descriptionTextView.text = bookModel.description

            binding.root.setOnClickListener {
                myItemClickListener(bookModel)
                Log.e("BookAdapter",bookModel.title.toString())
            }

            Glide
                .with(binding.coverImageView.context)
                .load(bookModel.coverSmallUrl)
                .into(binding.coverImageView)
        }
    }

    //※4
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        return BookItemViewHolder(ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        //※2
        holder.bind(currentList[position]) //리스트어댑터는 데이터가 currentlist에 이미 다 저장되어있음
    }

    //※5..리사이클러뷰에 똑같은애 연속으로 올라오면 굳이 안바꿔도 되니까..그걸 판단해주는애임
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<Book>(){
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.id == newItem.id
            }

        }
    }

}