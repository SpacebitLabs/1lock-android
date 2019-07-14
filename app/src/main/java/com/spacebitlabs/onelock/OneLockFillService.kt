package com.spacebitlabs.onelock

import android.os.CancellationSignal
import android.service.autofill.*
import android.view.autofill.AutofillId
import timber.log.Timber

class OneLockFillService : AutofillService() {
    override fun onFillRequest(request: FillRequest, cancellationSignal: CancellationSignal, callback: FillCallback) {
        Timber.d("FillRequest")

        val fillContexts = request.fillContexts
        val structure = fillContexts[fillContexts.size - 1].structure
        val callingActivity = structure.activityComponent
        val numNodes = structure.windowNodeCount

        val structureParser = StructureParser(structure).also { it.parseForFill() }
        structureParser.autofillFields


        Timber.d("structure: ${structureParser.autofillFields}")
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    data class ParsedStructure(var usernameId: AutofillId, var passwordId: AutofillId)

    data class UserData(var username: String, var password: String)
}
