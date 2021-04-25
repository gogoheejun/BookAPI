package com.hjhj.bookapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hjhj.bookapi.databinding.ItemHistoryBinding
import com.hjhj.bookapi.model.History

class HistoryAdapter(val historDeleteClickedListener:(String)->Unit): ListAdapter<History, HistoryAdapter.HistoryItemViewHolder>(diffUtil) {

    //※1
    inner class HistoryItemViewHolder(private val binding: ItemHistoryBinding): RecyclerView.ViewHolder(binding.root){

        //ItemBookBinding이란 애는 이름을 xml layout이랑 맞춰서 item_book 만드삼
        //※3
        fun bind(historyModel: History){
            binding.historyKeywordTextView.text = historyModel.keyword

            binding.historyKeywordDeleteButton.setOnClickListener {
                historDeleteClickedListener(historyModel.keyword.orEmpty())
            }
        }
    }

    //※4
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        return HistoryItemViewHolder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        //※2
        holder.bind(currentList[position]) //리스트어댑터는 데이터가 currentlist에 이미 다 저장되어있음
    }

    //※5..리사이클러뷰에 똑같은애 연속으로 올라오면 굳이 안바꿔도 되니까..그걸 판단해주는애임
    companion object{
        val diffUtil = object : DiffUtil.ItemCallback<History>(){
            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.keyword == newItem.keyword
            }

        }
    }

}