package com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Common

import com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Retrofit.IMenuRequest
import com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Retrofit.RetrofitClient.getClient
import retrofit2.create

object Common {
    val menuRequest: IMenuRequest
    get() = getClient("https://api.androidhive.info/")!!.create(IMenuRequest::class.java)
}