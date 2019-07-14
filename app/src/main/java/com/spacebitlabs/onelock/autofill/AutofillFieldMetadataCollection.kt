package com.spacebitlabs.onelock.autofill

import android.view.autofill.AutofillId

/**
 * Data structure that stores a collection of `AutofillFieldMetadata`s. Contains all of the client's `View`
 * hierarchy autofill-relevant metadata.
 */
data class AutofillFieldMetadataCollection @JvmOverloads constructor(
    val autofillIds: ArrayList<AutofillId> = ArrayList(),
    val allAutofillHints: ArrayList<String> = ArrayList(),
    val focusedAutofillHints: ArrayList<String> = ArrayList()
) {

    private val autofillHintsToFieldsMap = HashMap<String, MutableList<AutofillFieldMetadata>>()
    var saveType = 0
        private set

    fun add(autofillFieldMetadata: AutofillFieldMetadata) {
        saveType = saveType or autofillFieldMetadata.saveType
        autofillIds.add(autofillFieldMetadata.autofillId)
        val hintsList = autofillFieldMetadata.autofillHints
        allAutofillHints.addAll(hintsList)
        if (autofillFieldMetadata.isFocused) {
            focusedAutofillHints.addAll(hintsList)
        }
        autofillFieldMetadata.autofillHints.forEach {
            val fields = autofillHintsToFieldsMap[it] ?: ArrayList()
            autofillHintsToFieldsMap[it] = fields
            fields.add(autofillFieldMetadata)
        }
    }

    fun getFieldsForHint(hint: String): MutableList<AutofillFieldMetadata>? {
        return autofillHintsToFieldsMap[hint]
    }
}