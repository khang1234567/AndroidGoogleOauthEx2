package jp.co.actier.sfa.activity

import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import jp.co.actier.sfa.R
import jp.co.actier.sfa.timeline.init.MenuSettings

open class BaseMenuAppCompatActivity : AppCompatActivity() {

    private var flagShowMenu = false
    private var widthScreen: Float? = 0F
    private var xMove: Int = 0
    private var xMoveCacheOneStep = 0
    private var xUp: Int = 0
    private var yDown: Int = 0
    private var xDown: Int = 0
    private var yUp: Int = 0
    private var viewMain: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        flagShowMenu = false
        widthScreen = getWidthScreen(this)
    }

    override fun onStart() {
        super.onStart()
        viewMain = findViewById(R.id.view_main)
        findViewById(R.id.menu_info_img).setOnClickListener() {
            menuCollapse()
        }
        MenuSettings(this)
    }

    override fun onResume() {
        super.onResume()
        viewMain!!.animate().translationX(0F)
        flagShowMenu = false
    }

    private fun getWidthScreen(activity: AppCompatActivity): Float {
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x.toFloat()
    }

    private fun menuCollapse() {
        flagShowMenu = if (flagShowMenu) {
            viewMain!!.animate().translationX(0F)
            false
        } else {
            viewMain!!.animate().translationX(-1 * (widthScreen!!.times(9) / 11 - resources.getDimension(R.dimen.dp_m)))
            true
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                xMove = 0
                xUp = 0
                xDown = event.rawX.toInt()
                yDown = event.rawY.toInt()
                // logic
                val v = currentFocus
                if (v is EditText) {
                    val outRect = Rect()
                    v.getGlobalVisibleRect(outRect)
                    if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                        v.clearFocus()
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(v.windowToken, 0)
                    }
                }
            }

            MotionEvent.ACTION_MOVE -> {
                val eventX = event.rawX.toInt()
                if (xMoveCacheOneStep != event.rawX.toInt()) {
                    xMove = xMoveCacheOneStep
                }
                xMoveCacheOneStep = eventX
            }

            MotionEvent.ACTION_UP -> {
                yUp = event.rawY.toInt()
                xUp = event.rawX.toInt()
                if (Math.abs(yUp - yDown) < Math.abs(xUp - xDown) && 20 < Math.abs(xUp - xDown)) {
                    flagShowMenu = if (xUp - xMove > 0) {
                        viewMain!!.animate().translationX(0F)
                        false
                    } else {
                        viewMain!!.animate().translationX(-1 * (widthScreen!!.times(9) / 11 - resources.getDimension(R.dimen.dp_m)))
                        true
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


}