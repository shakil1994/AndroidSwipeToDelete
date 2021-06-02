package com.blackbirds.shakil.kotlinswipetodeleterecycleritem

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.*
import com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Adapter.CartListAdapter
import com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Common.Common
import com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Helper.RecyclerItemTouchHelper
import com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Helper.RecyclerItemTouchHelperListener
import com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Model.Item
import com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Retrofit.IMenuRequest
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), RecyclerItemTouchHelperListener {

    var recyclerView: RecyclerView? = null
    var itemList: MutableList<Item> = ArrayList()
    var rootLayout: CoordinatorLayout? = null
    var adapter: CartListAdapter? = null
    var toolbar: Toolbar? = null

    var mService: IMenuRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Cart Item"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mService = Common.menuRequest

        recyclerView = findViewById(R.id.recycler_view)
        rootLayout = findViewById(R.id.rootLayout)

        adapter = CartListAdapter(this, itemList)

        val  layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.setHasFixedSize(true)
        recyclerView?.itemAnimator = DefaultItemAnimator()
        recyclerView?.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView?.adapter = adapter

        val itemTouchHelperCallback: ItemTouchHelper.SimpleCallback =
            RecyclerItemTouchHelper(0,
            ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)

        prepareCart()
    }

    private fun prepareCart() {
        mService?.getMenuList()?.enqueue(object : Callback<MutableList<Item>>{
            override fun onResponse(
                call: Call<MutableList<Item>>,
                response: Response<MutableList<Item>>
            ) {
                itemList.clear()
                itemList.addAll(response.body() as MutableList<Item>)
                adapter?.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<MutableList<Item>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        if (viewHolder is CartListAdapter.MyViewHolder){
            val name: String = itemList[viewHolder.adapterPosition].name.toString()

            val deletedItem = itemList[viewHolder.adapterPosition]
            val deletedIndex = viewHolder.adapterPosition

            adapter?.removeItem(deletedIndex)

            val snackbar = rootLayout?.let {
                Snackbar.make(it, "$name removed form cart!", Snackbar.LENGTH_LONG)
            }
            snackbar?.setAction("UNDO") {view: View ->
                adapter?.restoreItem(deletedItem, deletedIndex)
            }
            snackbar?.setActionTextColor(Color.YELLOW)
            snackbar?.show()

        }
    }
}