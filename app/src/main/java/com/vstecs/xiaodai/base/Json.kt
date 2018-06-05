package com.vstecs.xiaodai.base

import com.google.gson.Gson
import java.lang.reflect.Type

object Json {
    private val gson: Gson by lazy { Gson() }

    inline fun <reified T> parse(json: String): T {
        return Json.parse(json, T::class.java)
    }

    fun <T> parse(json: String, type: Type): T {
        return gson.fromJson(json, type)
    }

    fun <T> stringify(t: T): String {
        return gson.toJson(t)
    }
}
