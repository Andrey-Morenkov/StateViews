package com.umutbey.stateviews

import android.graphics.drawable.Drawable
import android.view.View

/*
 * Created by medyo on 11/15/17.
 * Developed by kobeumut on 11/04/18
 */

class StateModel {

    var tag: String? = null
    var title: String? = null
    var description: String? = null
    var icon: Drawable? = null
    var buttonTitle: String? = null
    var layoutId: Int? = null
    var view: View? = null
    var custom: Boolean? = false
    var clickListener: View.OnClickListener? = null
}
