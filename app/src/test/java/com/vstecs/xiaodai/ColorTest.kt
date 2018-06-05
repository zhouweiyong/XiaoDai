package com.vstecs.xiaodai

import android.graphics.Color
import junit.framework.Assert.assertEquals
import org.junit.Test

class ColorTest {
    @Test fun addColor() {
        val color = Color.parseColor("#AEB4C7")
        println("color: $color")
        println(Color.parseColor("#14AEB4C7"))
        println(0x14 and color)
        assertEquals(0x14 and color, Color.parseColor("#14AEB4C7"))
    }
}