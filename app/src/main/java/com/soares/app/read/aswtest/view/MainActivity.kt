package com.soares.app.read.aswtest.view


import android.app.DownloadManager
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soares.app.read.aswtest.*
import com.soares.app.read.aswtest.model.PostalCode
import com.soares.app.read.aswtest.presenter.IListPostalCodePresenter
import com.soares.app.read.aswtest.presenter.ListPostalCodePresenter
import com.soares.app.read.aswtest.view.adapter.PostalCodeListAdapter


class MainActivity : AppCompatActivity(), IMainView, SearchView.OnQueryTextListener {
    private lateinit var presenter: IListPostalCodePresenter
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PostalCodeListAdapter
    lateinit var layoutManager: LinearLayoutManager
    private var listPostalCode: ArrayList<PostalCode> = ArrayList()
    private lateinit var llLoading: LinearLayout
    private var minQuery: Int = 1
    private var maxQuery: Int = 100
    private lateinit var searchView: SearchView
    private var descFilter = ""
    private var postalCodeFilter = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = ListPostalCodePresenter(this, this, (application as Application).repository)
        recyclerView = findViewById(R.id.recyclerview)
        llLoading = findViewById(R.id.llLoading)

        runOnUiThread {
            if (!presenter.isExistDatabaseApp()) {
                registerReceiver(
                    presenter.broadcastReceiverView(),
                    IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
                )
                presenter.executeDownloadExcel()
            } else {
                showListPostalCode(presenter.getList(minQuery, maxQuery))
                try {
                    presenter.setValuesInDatabaseContinue()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.addOnScrollListener(setupRecyclerView())
    }


    private fun setupRecyclerView(): RecyclerView.OnScrollListener {
        return (object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (layoutManager.findLastCompletelyVisibleItemPosition() == listPostalCode.size - 1 && !searchView.hasFocus()) {
                    minQuery += 100
                    maxQuery += 101

                    runOnUiThread {
                        recyclerView.post {
                            adapter.notifyItemChanged(
                                listPostalCode.size - 1,
                                listPostalCode.addAll(presenter.getList(minQuery, maxQuery))
                            )
                        }
                    }
                }

            }
        })
    }

    override fun showListPostalCode(list: List<PostalCode>) {
        runOnUiThread {
            listPostalCode.addAll(list)
            adapter = PostalCodeListAdapter(listPostalCode)
            recyclerView.adapter = adapter
            layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
            adapter.notifyItemInserted(listPostalCode.size - 1)
            llLoading.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.action_search)
        searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus){
                minQuery = 1
                maxQuery = 100
                showListPostalCode(presenter.getList(minQuery, maxQuery))
            }
        }
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        runOnUiThread {
            llLoading.visibility = View.VISIBLE
            listPostalCode.clear()
            listPostalCode.addAll(presenter.getListFilter("$postalCodeFilter", "$descFilter"))
            adapter = PostalCodeListAdapter(listPostalCode)
            recyclerView.adapter = adapter
            Toast.makeText(this, getString(R.string.messageShowFilter, listPostalCode.size.toString()), Toast.LENGTH_LONG).show()
            llLoading.visibility = View.GONE
        }

        if (listPostalCode.size in 1..10) {
            recyclerView.postDelayed({
                recyclerView.smoothScrollToPosition(
                    listPostalCode.size - 1
                )
            }, 100)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText!!.isEmpty()) {
            minQuery = 1
            maxQuery = 100
            listPostalCode.clear()
            showListPostalCode(presenter.getList(minQuery, maxQuery))
        } else if (newText!!.length == 1) {
            if (newText.toIntOrNull() != null) {
                postalCodeFilter = "%$newText"
            } else {
                descFilter = "%$newText"
            }
        } else {
            if (newText!!.substring(newText.length - 1, newText.length).toIntOrNull() != null) {
                postalCodeFilter = "$postalCodeFilter%${newText!!.substring(newText.length - 1, newText.length)}%"
            } else {
                descFilter = "$descFilter%${newText!!.substring(newText.length - 1, newText.length)}%"
            }
        }
        return false
    }
}