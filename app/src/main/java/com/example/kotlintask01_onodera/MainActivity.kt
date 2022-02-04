package com.example.kotlintask01_onodera

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.Exception

data class Item(val imageUrl: String, val jname: String, val ename: String)

class MainActivity : AppCompatActivity(), CustomAdapter.ItemSelectionListener {

    private val TAG = MainActivity::class.java.simpleName
    private val items = mutableListOf<Item>()
    private var adapter: CustomAdapter? = null

    private val webApiManagerListener = object : WebApiManager.Listener<List<CountryEntity>> {
                                            override fun completed(entities: List<CountryEntity>) {
                                                items.clear()
                                                for (entity in entities) {
                                                    Log.d(TAG, "$entity")
                                                    items.add(Item(entity.imageUrl, entity.jname, entity.ename))
                                                }
                                                adapter?.notifyDataSetChanged()
                                            }

                                            override fun error(code: Int, description: String) {
                                                Log.d(TAG, "code : $code description : $description")
                                            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val view = findViewById<RecyclerView>(R.id.recyclerView)
        view.layoutManager = LinearLayoutManager(this)
        adapter = CustomAdapter(this, items, this)
        view.adapter = adapter
        setup()
    }

    private fun setup() {
        // サーバにリクエストを行う
        try {
            WebApiManager.getCountries(webApiManagerListener)
        } catch (e: Exception) {
            Log.e(TAG, "エラー: " + e.localizedMessage)
        }
    }

    override fun notifyItemSelected(item: Item) {
        Toast.makeText(this, "${item.jname}が選択されました。", Toast.LENGTH_SHORT).show()
    }
}






















