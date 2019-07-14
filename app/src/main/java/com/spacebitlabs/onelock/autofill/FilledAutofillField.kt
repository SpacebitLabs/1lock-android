package com.spacebitlabs.onelock.autofill

import android.app.assist.AssistStructure

/**
 * JSON serializable data class containing the same data as an [AutofillValue].
 */
class FilledAutofillField(viewNode: AssistStructure.ViewNode) {
    var textValue: String? = null

    var dateValue: Long? = null

    var toggleValue: Boolean? = null

    val autofillHints = viewNode.autofillHints.filter(AutofillHelper::isValidHint).toTypedArray()

    init {
        viewNode.autofillValue?.let {
            if (it.isList) {
                val index = it.listValue
                viewNode.autofillOptions?.let { autofillOptions ->
                    if (autofillOptions.size > index) {
                        textValue = autofillOptions[index].toString()
                    }
                }
            } else if (it.isDate) {
                dateValue = it.dateValue
            } else if (it.isText) {
                // Using toString of AutofillValue.getTextValue in order to save it to
                // SharedPreferences.
                textValue = it.textValue.toString()
            } else {
            }
        }
    }

    fun isNull(): Boolean {
        return textValue == null && dateValue == null && toggleValue == null
    }
}