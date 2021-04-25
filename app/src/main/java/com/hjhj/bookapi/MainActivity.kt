package com.hjhj.bookapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.hjhj.bookapi.adapter.BookAdapter
import com.hjhj.bookapi.adapter.HistoryAdapter
import com.hjhj.bookapi.api.BookService
import com.hjhj.bookapi.databinding.ActivityMainBinding
import com.hjhj.bookapi.model.BestSellerDTO
import com.hjhj.bookapi.model.History
import com.hjhj.bookapi.model.SearchBookDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter:BookAdapter
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var bookService: BookService

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //바인딩
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //어댑터 연걸
        initBookRecyclerView()
        initHistoryRecyclerView()

        //룸 만들기
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "BookSearchDB"
        ).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://book.interpark.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        bookService = retrofit.create(BookService::class.java)

        bookService.getBestSellerBooks(getString(R.string.interparkAPIKey))
            .enqueue(object: Callback<BestSellerDTO>{
                override fun onResponse(call: Call<BestSellerDTO>,  response: Response<BestSellerDTO>) {
                    if(response.isSuccessful.not()){
                        return
                    }
                    response.body()?.let{
                        Log.d(TAG,it.toString())

                        it.books.forEach { book ->
                            Log.d(TAG, book.toString())
                        }

                        adapter.submitList(it.books)
                    }
                }

                override fun onFailure(call: Call<BestSellerDTO>, t: Throwable) {
                     Log.d(TAG,t.toString())
                }
            })

        initSearchEditText()
    }

    private fun search(keyword:String){
        bookService.getBooksByName(getString(R.string.interparkAPIKey), keyword)
            .enqueue(object: Callback<SearchBookDTO>{
                override fun onResponse(call: Call<SearchBookDTO>,  response: Response<SearchBookDTO>) {
                    if(response.isSuccessful.not()){
                        Log.e(TAG, "not success!!")
                        return
                    }
                    //성공시
                    hideHistoryView()
                    saveSearchKeyword(keyword)//room에다가 저장

//                    response.body()?.let{
//                        adapter.submitList(it.books)
//                    } 이거를 더 간결하게 다음처럼..
                    adapter.submitList(response.body()?.books.orEmpty())//없으면 엠티

                }

                override fun onFailure(call: Call<SearchBookDTO>, t: Throwable) {
                    hideHistoryView()
                    Log.d(TAG,t.toString())
                }
            })
    }

    private fun initSearchEditText(){
        binding.searchEditText.setOnKeyListener { v, keyCode, event ->
            if(keyCode == KeyEvent.KEYCODE_ENTER && event.action == MotionEvent.ACTION_DOWN){
                search(binding.searchEditText.text.toString())
                return@setOnKeyListener true //실제로 이벤트처리했다는건 트루
            }
            return@setOnKeyListener false //실제 이벤트처리안됐다ㅠㅠ
        }
        binding.searchEditText.setOnTouchListener { v, event ->
            if(event.action == MotionEvent.ACTION_DOWN){
                showHistoryView()
            }
            return@setOnTouchListener false
        }
    }

    private fun initHistoryRecyclerView(){
        historyAdapter = HistoryAdapter(historDeleteClickedListener = {
            deleteSearchKeyword(it)
        })
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.historyRecyclerView.adapter = historyAdapter
    }
    private fun deleteSearchKeyword(keyword: String){
        Thread{
            db.historyDao().delete(keyword)
            //todo view갱신
            showHistoryView()
        }.start()
    }

    private fun saveSearchKeyword(keyword:String){
        Thread{
            db.historyDao().insertHistory(History(null, keyword))
        }.start()
    }

    private fun showHistoryView(){
        Thread{
            val keywords = db.historyDao().getAll().reversed()
            runOnUiThread{
                binding.historyRecyclerView.isVisible=true
                historyAdapter.submitList(keywords.orEmpty())
            }
        }.start()
        binding.historyRecyclerView.isVisible=true
    }
    private fun hideHistoryView(){
        binding.historyRecyclerView.isVisible=false
    }

    private  fun initBookRecyclerView(){
        adapter = BookAdapter()

        binding.bookRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.bookRecyclerView.adapter = adapter
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}