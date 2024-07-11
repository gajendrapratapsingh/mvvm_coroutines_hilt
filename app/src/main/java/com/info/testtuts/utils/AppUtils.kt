package com.info.testtuts.utils

import android.annotation.SuppressLint
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

class AppUtils private constructor(private val rootView: View){

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: AppUtils? = null

        fun getInstance(rootView: View): AppUtils {
            if (instance == null) {
                instance = AppUtils(rootView)
            }
            return instance!!
        }
    }

    fun showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(rootView.context, message, duration).show()
    }

    fun showToast(@StringRes messageRes: Int, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(rootView.context, messageRes, duration).show()
    }

    fun showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT, rootView: View) {
        Snackbar.make(rootView, message, duration).show()
    }

    fun showSnackbar(@StringRes messageRes: Int, duration: Int = Snackbar.LENGTH_SHORT) {
        Snackbar.make(rootView, messageRes, duration).show()
    }
}