package com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Retrofit

import com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Model.Item
import retrofit2.Call
import retrofit2.http.GET

interface IMenuRequest {
    @GET("json/menu.json")
    fun getMenuList(): Call<MutableList<Item>>
}