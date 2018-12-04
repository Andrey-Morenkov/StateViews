package com.umutbey.stateviews

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import java.util.*

/*
 * Created by medyo on 11/15/17.
 * Developed by kobeumut on 11/04/18
 */
class StateView : ViewFlipper {

    private val TAG = "PageStatusView"
    /**
     * Data States
     */

    private var mBuilder: StateViewsBuilder? = null
    private lateinit var mInflater: LayoutInflater
    private var mOnClickListener: View.OnClickListener? = null
    private var showingState: String? = ""

    constructor(context: Context) : super(context) {
        setupView(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupView(attrs)
    }

    private fun setupView(attrs: AttributeSet?) {

        // TODO: Add support of dynamic styles

        if (isInEditMode) {
            return
        }

        handleStates()
    }

    private fun handleStates() {

        mInflater = LayoutInflater.from(context)

        /**
         * Append new state values
         */

        addView(getViewByLayoutId(R.layout.state_loading, STATES.LOADING.name), 0)

        if (hasPrivateConfiguration()) {
            applyCustomStyle(mBuilder, mBuilder!!.states)
        } else if (StateViewsBuilder.instance != null) {
            applyCustomStyle(StateViewsBuilder.instance, StateViewsBuilder.instance!!.states)
        }

        hideStates()

    }

    fun addCustomState(tag: String, title: String, description: String, icon: Drawable, buttonTitle: String) {

        var builder = mBuilder
        if (builder == null) {
            builder = StateViewsBuilder.instance
        }

        val view = mInflater.inflate(R.layout.state_custom, null, false) as LinearLayout

        setTitleValue(view, title, builder!!.textColor, builder.font)
        setDescriptionValue(view, description, builder.textColor, builder.font)
        setIcon(view, icon, builder.iconColor, builder.iconSize!!)
        setButtonStyle(view, buttonTitle, builder.font, builder.buttonBackgroundColor, builder.buttonTextColor)

        view.tag = tag
        addView(view, childCount)
    }

    private fun applyCustomStyle(builder: StateViewsBuilder?, states: List<StateModel>) {

        /*
         * Load Views
         */

        for (i in states.indices) {
            val state = states[i]
            val position = i + 1
            if (state.custom!!) {

                if (state.view != null) {
                    val view = state.view
                    view!!.tag = state.tag
                    addView(view, position)

                } else if (state.layoutId != null) {
                    val view = mInflater.inflate(state.layoutId!!, null, false)
                    view.tag = state.tag
                    addView(view, position)
                }
            } else {
                val view = mInflater.inflate(R.layout.state_custom, null, false) as LinearLayout

                setTitleValue(view, state.title, builder!!.textColor, builder.font)
                setDescriptionValue(view, state.description, builder.textColor, builder.font)
                setIcon(view, state.icon, builder.iconColor, builder.iconSize!!)
                setButtonStyle(view, state.buttonTitle, builder.font, builder.buttonBackgroundColor, builder.buttonTextColor)

                view.tag = state.tag
                addView(view, position)
            }
        }

    }

    fun applyGravity(gravity: Int?) {

        var builder = mBuilder
        StateViewsBuilder.instance?.let {
            builder = it
        }

        if (builder == null) {
            return
        }

        val states = builder!!.states
        val inlineStates = arrayOfNulls<String>(states.size)

        for (i in states.indices) {
            inlineStates[i] = states[i].tag
        }

        for (i in 0 until childCount) {
            val view = getChildAt(i)

            if (view.tag != null && Arrays.asList<String>(*inlineStates).contains(view.tag.toString())) {

                if (view is LinearLayout) {
                    view.gravity = gravity!!
                }

            }
        }


    }

    private fun hasPrivateConfiguration(): Boolean {
        return mBuilder != null
    }

    fun setPrivateConfiguration(builder: StateViewsBuilder) {
        this.mBuilder = builder
    }

    /**
     * Display the show progress View
     */
    fun displayLoadingState() {
        displayedChild = 0
    }
    fun isStateShowing():Boolean{
        return !showingState.isNullOrEmpty()
    }
    fun getShowingState():String?{
        return showingState?:"none"
    }
    fun displayState(tagName: String) {

        var found: Boolean? = false
        for (i in 0 until childCount) {
            val view = getChildAt(i)

            if (view.tag != null && view.tag.toString().equals(tagName, ignoreCase = true)) {
                displayedChild = i
                showingState = tagName
                found = true
            }
        }

        if (!(found)!!) {
            Log.e(TAG, "Tag name Incorrect or not found")
        }
    }

    fun hideStates() {

        for (i in 0 until childCount) {
            if (getChildAt(i).tag == null) {
                displayedChild = i
                showingState = null
            }

        }
    }

    /**
     * Return a view based on layout id
     *
     * @param layout Layout Id
     * @param tag    Layout Tag
     * @return View
     */
    private fun getViewByLayoutId(layout: Int, tag: String): View {

        val view = mInflater.inflate(layout, null, false)
        view.tag = tag
        view.visibility = View.GONE

        return view!!
    }

    private fun setTitleValue(view: View, value: String?, color: Int?, typeface: Typeface?) {
        val mTextView = view.findViewById<TextView>(R.id.state_title)
        mTextView.gravity = Gravity.CENTER_HORIZONTAL
        setTextColor(mTextView, color)
        setTextFont(mTextView, typeface)
        if (!TextUtils.isEmpty(value)) {
            mTextView.text = value
            mTextView.visibility = View.VISIBLE
        } else {
            mTextView.visibility = View.GONE
        }
    }

    private fun setTextFont(mTextView: TextView, typeface: Typeface?) {
        if (typeface != null) {
            mTextView.typeface = typeface
        }
    }

    private fun setTextColor(textView: TextView, color: Int?) {

        if (color != null) {
            textView.setTextColor(color)
        }
    }

    private fun setDescriptionValue(view: View, value: String?, color: Int?, typeface: Typeface?) {
        val mTextView = view.findViewById<TextView>(R.id.state_description)
        mTextView.gravity = Gravity.CENTER_HORIZONTAL
        setTextColor(mTextView, color)
        setTextFont(mTextView, typeface)
        if (!TextUtils.isEmpty(value)) {
            mTextView.text = value
            mTextView.visibility = View.VISIBLE
        } else {
            mTextView.visibility = View.GONE
        }
    }

    fun setOnStateButtonClicked(clickListener: View.OnClickListener) {
        mOnClickListener = clickListener

        for (i in 0 until childCount) {
            if (getChildAt(i).tag != null) {

                val view = getChildAt(i)

                if (view.findViewById<View>(R.id.state_button) != null) {
                    val button = view.findViewById<Button>(R.id.state_button)
                    button.tag = view.tag
                    button.setOnClickListener(mOnClickListener)
                }
            }


        }
    }

    private fun setButtonStyle(view: View, buttonTitle: String?, typeface: Typeface?, buttonBackgroundColor: Int?, buttonTextColor: Int?) {
        val button = view.findViewById<Button>(R.id.state_button)
        setButtonFont(button, typeface)

        if (buttonBackgroundColor != null) {
            button.background.setColorFilter(buttonBackgroundColor, PorterDuff.Mode.MULTIPLY)
        }

        if (buttonTextColor != null) {
            button.setTextColor(buttonTextColor)
        }

        if (!TextUtils.isEmpty(buttonTitle)) {
            button.text = buttonTitle
            button.visibility = View.VISIBLE
            button.setOnClickListener(mOnClickListener)
        } else {
            button.visibility = View.GONE
        }
    }

    private fun setButtonFont(button: Button, typeface: Typeface?) {
        if (typeface != null) {
            button.typeface = typeface
        }
    }

    private fun setIcon(view: View, icon: Drawable?, color: Int?, iconSize: Int) {
        val mImageView = view.findViewById<ImageView>(R.id.state_icon)

        if (color != null) {
            mImageView.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
        } else {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(R.attr.colorAccent, typedValue, true)
            mImageView.setColorFilter(typedValue.data, android.graphics.PorterDuff.Mode.SRC_IN)
        }

        if (iconSize > 0) {
            val params = LinearLayout.LayoutParams(iconSize, iconSize)
            mImageView.layoutParams = params
        }

        if (icon != null) {
            mImageView.setImageDrawable(icon)
            mImageView.visibility = View.VISIBLE
        } else {
            mImageView.visibility = View.GONE
        }
    }

    enum class STATES {
        LOADING
    }


}
