package com.themaker.fshmo.klassikaplus.presentation.common

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.DecelerateInterpolator
import android.widget.FrameLayout
/**
 *
 * @author jagger on 31.05.18.
 *
 * ViewGroup that shows menu on swipe.
 * The first child should represent a menu.
 * The second child should represent a swipable item.
 */
class SwipeMenuContainer @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    var onMenuShownListener: ((Boolean) -> Unit)? = null
    var isMenuShown: Boolean
        get() = isMenuOpen
        set(value) {
            isMenuOpen = value
            currentAnim?.cancel()
            contentView.translationX = if (isMenuOpen) -menuWidth else 0F
        }
    private val contentView: View by lazy { getChildAt(1) }
    private val menuView: View by lazy { getChildAt(0) }
    private val touchSlope = ViewConfiguration.get(context).scaledTouchSlop
    private val menuWidth: Float
        get() = menuView.width.toFloat()
    private var initialX = 0F
    private var initialY = 0F
    private var initialTranslation = 0F
    private var isMenuOpen = false
    private var ignoreTouchEvents = false
    private var currentAnim: ObjectAnimator? = null
    private var isDragging = false
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (ignoreTouchEvents) return true
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                isDragging = true
                val translation = initialTranslation + event.x - initialX
                if (translation <= 0) {
                    contentView.translationX = Math.max(translation, -menuWidth)
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                isDragging = false
                if (!isMenuOpen) {
                    showMenuAnimated()
                } else {
                    hideMenuAnimated()
                }
            }
        }
        return true
    }
    override fun onDetachedFromWindow() {
        currentAnim?.cancel()
        super.onDetachedFromWindow()
    }
    fun showMenuAnimated() = runTranslationAnimation(
            targetTranslation = -menuWidth,
            onAnimationEnd = {
                isMenuOpen = true
                post { onMenuShownListener?.invoke(true) }
            }
    )
    fun hideMenuAnimated() = runTranslationAnimation(
            targetTranslation = 0F,
            onAnimationEnd = {
                isMenuOpen = false
                post { onMenuShownListener?.invoke(false) }
            }
    )
    private fun runTranslationAnimation(targetTranslation: Float, onAnimationEnd: ()->Unit) {
        ignoreTouchEvents = true
        val anim = ObjectAnimator
                .ofFloat(contentView, "translationX", targetTranslation)
                .apply {
                    duration = ANIM_DURATION
                    interpolator = DecelerateInterpolator()
                    onAnimationTerminated {
                        ignoreTouchEvents = false
                        onAnimationEnd.invoke()
                    }
                }
        currentAnim?.cancel()
        anim.start()
        currentAnim = anim
    }
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                initialTranslation = contentView.translationX
                initialX = ev.x
                initialY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dy = Math.abs(ev.y - initialY)
                val dx = Math.abs(ev.x - initialX)
                if (isDragging || (dx > dy && dx > touchSlope)) {
                    // Don't allow parent to intercept touch events
                    parent.requestDisallowInterceptTouchEvent(true)
                    return true
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }
    private fun ObjectAnimator.onAnimationTerminated(block: () -> Unit) {
        this.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) { block.invoke() }
            override fun onAnimationCancel(animation: Animator?) { block.invoke() }
            override fun onAnimationStart(animation: Animator?) {}
        })
    }
    companion object {
        private const val ANIM_DURATION = 200L
    }
}