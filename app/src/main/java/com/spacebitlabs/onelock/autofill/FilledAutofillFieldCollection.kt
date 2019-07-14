package com.spacebitlabs.onelock.autofill

import android.service.autofill.Dataset
import android.view.View
import android.view.autofill.AutofillValue
import timber.log.Timber

/**
 * FilledAutofillFieldCollection is the model that represents all of the form data on a client app's page, plus the
 * dataset name associated with it.
 */
class FilledAutofillFieldCollection @JvmOverloads constructor(
        var datasetName: String? = null,
        private val hintMap: HashMap<String, FilledAutofillField> = HashMap()
) {

    /**
     * Sets values for a list of autofillHints.
     */
    fun add(autofillField: FilledAutofillField) {
        autofillField.autofillHints.forEach { autofillHint ->
            hintMap[autofillHint] = autofillField
        }
    }

    /**
     * Populates a [Dataset.Builder] with appropriate values for each [AutofillId]
     * in a `AutofillFieldMetadataCollection`. In other words, it builds an Autofill dataset
     * by applying saved values (from this `FilledAutofillFieldCollection`) to Views specified
     * in a `AutofillFieldMetadataCollection`, which represents the current page the user is
     * on.
     */
    fun applyToFields(autofillFieldMetadataCollection: AutofillFieldMetadataCollection,
            datasetBuilder: Dataset.Builder): Boolean {
        var setValueAtLeastOnce = false
        for (hint in autofillFieldMetadataCollection.allAutofillHints) {
            val autofillFields = autofillFieldMetadataCollection.getFieldsForHint(hint) ?: continue
            for (autofillField in autofillFields) {
                val autofillId = autofillField.autofillId
                val autofillType = autofillField.autofillType
                val savedAutofillValue = hintMap[hint]
                when (autofillType) {
                    View.AUTOFILL_TYPE_LIST -> {
                        savedAutofillValue?.textValue?.let {
                            val index = autofillField.getAutofillOptionIndex(it)
                            if (index != -1) {
                                datasetBuilder.setValue(autofillId, AutofillValue.forList(index))
                                setValueAtLeastOnce = true
                            }
                        }
                    }
                    View.AUTOFILL_TYPE_DATE -> {
                        savedAutofillValue?.dateValue?.let { date ->
                            datasetBuilder.setValue(autofillId, AutofillValue.forDate(date))
                            setValueAtLeastOnce = true
                        }
                    }
                    View.AUTOFILL_TYPE_TEXT -> {
                        savedAutofillValue?.textValue?.let { text ->
                            datasetBuilder.setValue(autofillId, AutofillValue.forText(text))
                            setValueAtLeastOnce = true
                        }
                    }
                    View.AUTOFILL_TYPE_TOGGLE -> {
                        savedAutofillValue?.toggleValue?.let { toggle ->
                            datasetBuilder.setValue(autofillId, AutofillValue.forToggle(toggle))
                            setValueAtLeastOnce = true
                        }
                    }
                    else -> Timber.w("Invalid autofill type - $autofillType")
                }
            }
        }
        return setValueAtLeastOnce
    }

    /**
     * @param autofillHints List of autofill hints, usually associated with a View or set of Views.
     * @return whether any of the filled fields on the page have at least 1 autofillHint that is
     * in the provided autofillHints.
     */
    fun helpsWithHints(autofillHints: List<String>): Boolean {
        for (autofillHint in autofillHints) {
            hintMap[autofillHint]?.let { savedAutofillValue ->
                if (!savedAutofillValue.isNull()) {
                    return true
                }
            }
        }
        return false
    }
}