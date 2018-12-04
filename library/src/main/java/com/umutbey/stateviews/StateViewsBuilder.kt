package com.umutbey.stateviews

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.view.View
import java.util.*

/*
 * Created by medyo on 11/15/17.
 * Developed by kobeumut on 11/04/18
 */

class StateViewsBuilder private constructor() {
    private val TAG = "StateViewsBuilder"
    //    var context: Context
//        internal set
    private var mStates: MutableList<StateModel> = ArrayList()
    var textColor: Int? = null
        internal set
    var iconSize: Int? = null
        internal set
    var iconColor: Int? = null
        internal set
    var buttonBackgroundColor: Int? = null
        internal set
    var buttonTextColor: Int? = null
        internal set
    internal var mTitleColor: Int? = null
    internal var mDescriptionColor: Int? = null
    var font: Typeface? = null
        internal set

    val states: List<StateModel>
        get() = mStates

//    init {
//        this.context = context
//    }

    fun setTextColor(color: Int): StateViewsBuilder {
        this.textColor = color
        return this
    }

    fun setIconColor(iconColor: Int): StateViewsBuilder {
        this.iconColor = iconColor
        return this
    }

    fun setIconSize(iconSize: Int): StateViewsBuilder {
        this.iconSize = iconSize
        return this
    }


    fun addState(tagName: String, view: View): StateViewsBuilder {

        val state = StateModel()
        state.view = view
        state.tag = tagName
        state.custom = true

        mStates.add(state)
        return this
    }

    fun addState(tagName: String, layout: Int): StateViewsBuilder {

        val state = StateModel()
        state.layoutId = layout
        state.tag = tagName
        state.custom = true

        mStates.add(state)

        return this
    }

    private fun addingState(tag: String, title: String? = null, description: String? = null, icon: Drawable? = null, iconColor: Int? = null, buttonTitle: String? = null, clickListener: View.OnClickListener? = null): StateViewsBuilder {

        val state = StateModel()
        state.title = title
        state.description = description
        state.buttonTitle = buttonTitle
        state.clickListener = clickListener
        state.icon = icon
        state.iconColor = iconColor
        state.tag = tag
        state.custom = false

        mStates.add(state)

        return this
    }

    fun addState(tag: String, title: String? = null, description: String? = null, icon: Drawable? = null, iconColor: Int? = null, buttonTitle: String? = null, clickListener: View.OnClickListener? = null): StateViewsBuilder {
        addingState(tag, title, description, icon, iconColor, buttonTitle, clickListener)
        return this
    }

    fun setFontFace(font: Typeface): StateViewsBuilder {
        this.font = font
        return this
    }

    fun setButtonBackgroundColor(buttonBackgroundColor: Int): StateViewsBuilder {
        this.buttonBackgroundColor = buttonBackgroundColor
        return this
    }

    fun setButtonTextColor(textColor: Int): StateViewsBuilder {
        this.buttonTextColor = textColor
        return this
    }

    companion object {

        var instance: StateViewsBuilder? = null
            private set


        fun init(): StateViewsBuilder {
            if (instance == null) {
                instance = StateViewsBuilder()
            }

            return instance as StateViewsBuilder
        }
    }

}
