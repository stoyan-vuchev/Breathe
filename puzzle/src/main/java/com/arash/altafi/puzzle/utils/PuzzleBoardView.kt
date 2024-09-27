package com.arash.altafi.puzzle.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.util.Log
import android.view.MotionEvent
import android.view.View

@SuppressLint("ViewConstructor")
class PuzzleBoardView(
    context: Context,
    private val n: Int,
    private val solve: ((Boolean) -> Unit)
) : View(context) {

    private val paint = Paint()
    private var containerWidth: Int = 0
    private var size = 0
    private val mat = Array(n) { Array(n) { PuzzleBlock(context, 0, 0F, 0F, 0F) } }
    private var emptyBlockIndex = Point(n - 1, n - 1)

    init {
        paint.isAntiAlias = true
    }

    fun initGame() {
        emptyBlockIndex = Point(n - 1, n - 1)
        size = containerWidth / n
        var x = 0
        var y = 0
        var id = 1
        for (element in mat) {
            for (j in 0.until(mat[0].size)) {
                element[j] = PuzzleBlock(context, id, x.toFloat(), y.toFloat(), size.toFloat())
                id++
                id %= n * n
                x += size
            }
            x = 0
            y += size
        }
        shuffleMat()
    }

    private fun shuffleMat() {
        val iteration = 100
        for (i in 0 until iteration) {
            val options = mutableListOf<Point>()
            if (emptyBlockIndex.x + 1 < n) {
                options.add(Point(emptyBlockIndex.x + 1, emptyBlockIndex.y))
            }
            if (emptyBlockIndex.x - 1 >= 0) {
                options.add(Point(emptyBlockIndex.x - 1, emptyBlockIndex.y))
            }
            if (emptyBlockIndex.y + 1 < n) {
                options.add(Point(emptyBlockIndex.x, emptyBlockIndex.y + 1))
            }
            if (emptyBlockIndex.y - 1 >= 0) {
                options.add(Point(emptyBlockIndex.x, emptyBlockIndex.y - 1))
            }
            options.shuffle()
            val selectedIndex = options[0]
            swapBlock(selectedIndex.x, selectedIndex.y)
        }
    }

    private fun isSolution(): Boolean {
        var count = 1
        for (element in mat) {
            for (j in 0 until mat[0].size) {
                if (element[j].ID != count && count != n * n) {
                    return false
                }
                count++
            }
        }
        return true
    }

    private fun swapBlock(i: Int, j: Int) {
        val id = mat[i][j].ID
        mat[i][j].ID = 0
        mat[emptyBlockIndex.x][emptyBlockIndex.y].ID = id
        emptyBlockIndex = Point(i, j)
    }

    private fun makeMove(i: Int, j: Int) {
        swapBlock(i, j)
        invalidate()
        solve.invoke(isSolution())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (element in mat) {
            for (j in 0 until mat[0].size) {
                element[j].onDraw(canvas, paint)
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        containerWidth = measuredWidth

        if (containerWidth == 0) {
            return
        }

        initGame()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event!!.action) {

            MotionEvent.ACTION_DOWN -> {
                Log.d("event down: ", event.x.toString() + ":" + event.y.toString())
                return true
            }

            MotionEvent.ACTION_UP -> {
                if (size == 0) {
                    return false
                }

                val i = (event.y / size).toInt()
                val j = (event.x / size).toInt()

                if (i + 1 < n && i + 1 == emptyBlockIndex.x && j == emptyBlockIndex.y) {
                    makeMove(i, j)
                } else if (i - 1 >= 0 && i - 1 == emptyBlockIndex.x && j == emptyBlockIndex.y) {
                    makeMove(i, j)
                } else if (j + 1 < n && i == emptyBlockIndex.x && j + 1 == emptyBlockIndex.y) {
                    makeMove(i, j)
                } else if (j - 1 >= 0 && i == emptyBlockIndex.x && j - 1 == emptyBlockIndex.y) {
                    makeMove(i, j)
                }

                Log.d("event up: ", event.x.toString() + ":" + event.y.toString())
            }
        }

        return super.onTouchEvent(event)
    }
}
