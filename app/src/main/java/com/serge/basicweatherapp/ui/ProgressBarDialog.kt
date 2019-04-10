package com.serge.basicweatherapp.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import com.serge.basicweatherapp.R

class ProgressBarDialog : androidx.fragment.app.DialogFragment() {

    companion object {
        const val PROGRESS_BAR_TAG = "PROGRESS_BAR_TAG"

        fun newInstance(): ProgressBarDialog {
            val progressBarDialog = ProgressBarDialog()
            val bundle = Bundle()
            progressBarDialog.arguments = bundle
            return progressBarDialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        isCancelable = false
        val dialog = Dialog(activity!!, R.style.ProgressDialogTheme)
        @SuppressLint("InflateParams") val view =
            activity!!.layoutInflater.inflate(R.layout.dialog_progress_circular, null, false)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }
}
