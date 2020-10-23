package com.sergio.ikeveo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sergio.ikeveo.models.Gafa
import com.sergio.ikeveo.service.IkeveoServiceImpl


class IkeveoListActivity : AppCompatActivity() {
    private lateinit var gafas: ArrayList<Gafa>
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: IkeveoAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gafa_list)

        gafas = ArrayList<Gafa>()

        viewManager = LinearLayoutManager(this)

        viewAdapter = IkeveoAdapter(gafas, this)


        recyclerView = findViewById<RecyclerView>(R.id.recyclerViewGafas)
        // use a linear layout manager
        recyclerView.layoutManager = viewManager

        // specify an viewAdapter (see also next example)
        recyclerView.adapter = viewAdapter

        getAllGafas()

        val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
        fab.setOnClickListener{
            val intent = Intent(this, IkeveoDetailActivity::class.java)
            intent.putExtra("state", "Adding")
            startActivity(intent)
        }
    }

    private fun getAllGafas() {

        val ikeveoServiceImpl = IkeveoServiceImpl()
        ikeveoServiceImpl.getAll(this) { response ->
            run {
                if (response != null) {
                    viewAdapter.gafaList = response
                }
                viewAdapter.notifyDataSetChanged()
            }
        }
    }
}