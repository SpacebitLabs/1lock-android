package com.spacebitlabs.onelock

import android.app.assist.AssistStructure
import com.spacebitlabs.onelock.autofill.AutofillFieldMetadata
import com.spacebitlabs.onelock.autofill.AutofillFieldMetadataCollection
import com.spacebitlabs.onelock.autofill.FilledAutofillField
import com.spacebitlabs.onelock.autofill.FilledAutofillFieldCollection
import timber.log.Timber

/**
 * Parser for an AssistStructure object. This is invoked when the Autofill Service receives an
 * AssistStructure from the client Activity, representing its View hierarchy. In this sample, it
 * parses the hierarchy and collects autofill metadata from {@link ViewNode}s along the way.
 */
internal class StructureParser(private val autofillStructure: AssistStructure) {
    val autofillFields = AutofillFieldMetadataCollection()
    var filledAutofillFieldCollection: FilledAutofillFieldCollection = FilledAutofillFieldCollection()
        private set

    fun parseForFill() {
        parse(true)
    }

    fun parseForSave() {
        parse(false)
    }

    /**
     * Traverse AssistStructure and add ViewNode metadata to a flat list.
     */
    private fun parse(forFill: Boolean) {
        Timber.d("Parsing structure for " + autofillStructure.activityComponent)
        val nodes = autofillStructure.windowNodeCount
        filledAutofillFieldCollection = FilledAutofillFieldCollection()
        for (i in 0 until nodes) {
            parseLocked(forFill, autofillStructure.getWindowNodeAt(i).rootViewNode)
        }
    }

    private fun parseLocked(forFill: Boolean, viewNode: AssistStructure.ViewNode) {
        viewNode.autofillHints?.let { autofillHints ->
            if (autofillHints.isNotEmpty()) {
                if (forFill) {
                    autofillFields.add(AutofillFieldMetadata(viewNode))
                } else {
                    filledAutofillFieldCollection.add(FilledAutofillField(viewNode))
                }
            }
        }
        val childrenSize = viewNode.childCount
        for (i in 0 until childrenSize) {
            parseLocked(forFill, viewNode.getChildAt(i))

        }
    }
}