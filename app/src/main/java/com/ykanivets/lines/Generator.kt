package com.ykanivets.lines

import android.view.View

data class Cell(
    val y: Int = 0,
    val x: Int = 0,
    val view: View? = null,
    var type: Int = 0
)

const val MAX_TYPE = 7
const val MIN_RESULT = 5
const val MULTIPLIER = 5

fun Array<Array<Cell>>.generateDots(numberOfDots: Int = 3) {
    val height = size - 1
    val width = this[0].size - 1

    var counter = numberOfDots

    while (counter != 0) {
        val y = (0..height).shuffled().first()
        val x = (0..width).shuffled().first()

        if (this[y][x].type == 0) {
            counter--
            this[y][x].type = (1..MAX_TYPE).shuffled().first()
        }
    }
}

fun Array<Array<Cell>>.checkResult(): Int {
    val height = size - 1
    val width = this[0].size - 1

    for (y in 0..height) {
        for (x in 0..width) {
            if (this[y][x].type != 0) {
                checkHorizontal(y, x)
            }
        }
    }

    return 0
}

fun Array<Array<Cell>>.checkHorizontal(y: Int, x: Int): Int {
    val width = this[0].size - 1

    var left = 0
    var right = 0

    for (p in x downTo 0) {
        if (this[y][p].type == this[y][x].type) left++
        else break
    }

    for (p in x..width) {
        if (this[y][p].type == this[y][x].type) right++
        else break
    }

    val total = left + right - 1

    return if (total >= MIN_RESULT) {
        if (x - 1 >= 0) {
            for (p in (x - 1) downTo 0) {
                if (this[y][p].type == this[y][x].type) this[y][p].type = 0
                else break
            }
        }

        if (x + 1 <= width) {
            for (p in (x + 1)..width) {
                if (this[y][p].type == this[y][x].type) this[y][p].type = 0
                else break
            }
        }

        this[y][x].type = 0

        MIN_RESULT + (total - MIN_RESULT) * MULTIPLIER
    } else {
        0
    }
}
