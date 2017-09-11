package jp.co.oauth.exgoole.androidgoogleoauthex2

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

/**
 * Created by kandodin on 2017/09/11.
 */
class ConfirmDialogFragment : DialogFragment() {

    val FRAGMENT_TAG = this.javaClass.simpleName

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val title = arguments.getString(ConfirmDialogFragment.EXTRA_TITLE)
        val message = arguments.getString(ConfirmDialogFragment.EXTRA_MESSAGE)

        val builder = AlertDialog.Builder(activity)
        if (title.isNullOrBlank()) builder.setTitle(title)
        return builder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(activity.getString(R.string.btn_positive), { dialog, witch ->
                    if (activity is ConfirmationDialogListener) {
                        (activity as ConfirmationDialogListener).onPositiveButtonClicked()
                    }
                })
                .setNegativeButton(activity.getString(R.string.btn_negative), { dialog, witch ->
                    if (activity is ConfirmationDialogListener) {
                        (activity as ConfirmationDialogListener).onNegativeButtonClicked()
                    }
                })
                .create()
    }

    interface ConfirmationDialogListener {
        fun onPositiveButtonClicked()
        fun onNegativeButtonClicked()
    }

    companion object {
        val EXTRA_TITLE = "${this.javaClass.name}.EXTRA_TITLE"

        val EXTRA_MESSAGE = "${this.javaClass.name}.EXTRA_MESSAGE"

        fun newInstance(title: String?, message: String): ConfirmDialogFragment {
            val fragment = ConfirmDialogFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_TITLE, title)
            bundle.putString(EXTRA_MESSAGE, message)
            fragment.arguments = bundle
            return fragment
        }
    }
}