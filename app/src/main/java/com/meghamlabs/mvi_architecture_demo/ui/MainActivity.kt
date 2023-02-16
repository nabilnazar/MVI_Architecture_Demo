package com.meghamlabs.mvi_architecture_demo.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meghamlabs.mvi_architecture_demo.R
import com.meghamlabs.mvi_architecture_demo.data.api.ApiHelper
import com.meghamlabs.mvi_architecture_demo.data.api.ApiHelperImpl
import com.meghamlabs.mvi_architecture_demo.data.api.RetrofitBuilder
import com.meghamlabs.mvi_architecture_demo.data.model.User
import com.meghamlabs.mvi_architecture_demo.ui.adapter.MainAdapter
import com.meghamlabs.mvi_architecture_demo.ui.intent.MainIntent
import com.meghamlabs.mvi_architecture_demo.ui.viewmodel.MainViewModel
import com.meghamlabs.mvi_architecture_demo.ui.viewstate.MainState
import com.meghamlabs.mvi_architecture_demo.util.ViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {



    private lateinit var mainViewModel: MainViewModel
    private var adapter = MainAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        val buttonFetchUser = findViewById<Button>(R.id.buttonFetchUser)
        buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }
    }


    private fun setupUI() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.run {
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
        }
        recyclerView.adapter = adapter
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(
                    RetrofitBuilder.apiService
                )
            )
        )[MainViewModel::class.java]
    }





    private fun observeViewModel() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val buttonFetchUser = findViewById<Button>(R.id.buttonFetchUser)
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        buttonFetchUser!!.visibility = View.GONE
                        progressBar!!.visibility = View.VISIBLE
                    }

                    is MainState.Users -> {
                        progressBar!!.visibility = View.GONE
                        buttonFetchUser!!.visibility = View.GONE
                        renderList(it.user)
                    }
                    is MainState.Error -> {
                        progressBar!!.visibility = View.GONE
                        buttonFetchUser!!.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.visibility = View.VISIBLE
        users.let { listOfUsers -> listOfUsers.let { adapter.addData(it) } }
        adapter.notifyDataSetChanged()
    }
}