package com.logicsystems.appsofom.datos

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.logicsystems.appsofom.R
import java.net.URI


open class Reference {
    private var useDefaultCredentialsSetExplicitly: Boolean = false
    open var Url = ""
    var UseDefaultCredentials: Boolean = false
    open var StrProblema = ""
    fun WSAppSofom() {
        this.Url = "https://cib.logicsystems.com.mx/APPCOBRO/WSAppSofom.asmx"
        if (this.IsLocalFileSystemWebService(this.Url)) {
            this.UseDefaultCredentials = true
            this.useDefaultCredentialsSetExplicitly = false
        } else {
            this.useDefaultCredentialsSetExplicitly = true
        }
    }
    fun IsLocalFileSystemWebService(url: String): Boolean{
        var BlnReturn = false
        if (url == ""){
            BlnReturn = false
        }
        val wsUri = URI(url)
        if ((wsUri.port >= 1024)){
            BlnReturn = true
        }
        return BlnReturn
    }
    open fun alertasError(_context: Context, StrProblema: String){
        AlertDialog.Builder(_context).apply {
            setTitle("Mensaje del sistema")
            setMessage(StrProblema)
            setCancelable(false)
            setPositiveButton("Aceptar", null)
        }.show()
    }

    open fun alertasError(_context: Context) : AlertDialog.Builder{
        return AlertDialog.Builder(_context).apply {
            setTitle("Mensaje del sistema")
            setCancelable(false)
        }
    }

    open fun alertasSuccess(_context: Context, StrProblema: String){
        AlertDialog.Builder(_context).apply {
            setTitle("Mensaje del sistema")
            setMessage(StrProblema)
            setCancelable(false)
            setPositiveButton("Aceptar", null)
        }.show()
    }
}
class ProgressDialog {
    companion object {
        fun progressBarCIB(_context: Context) : AlertDialog{
            val builder = AlertDialog.Builder(_context)
            builder.setCancelable(false)
            builder.setView(R.layout.layout_loading_dialog)
            return builder.create()
        }
        fun progressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.layout_loading_dialog, null)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            return dialog
        }
        fun setProgressDialog(context:Context, message:String):AlertDialog {
            val llPadding = 30
            val ll = LinearLayout(context)
            ll.orientation = LinearLayout.HORIZONTAL
            ll.setPadding(llPadding, llPadding, llPadding, llPadding)
            ll.gravity = Gravity.CENTER
            var llParam = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            llParam.gravity = Gravity.CENTER
            ll.layoutParams = llParam

            val progressBar = ProgressBar(context)
            progressBar.isIndeterminate = true
            progressBar.setPadding(0, 0, llPadding, 0)
            progressBar.layoutParams = llParam

            llParam = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            llParam.gravity = Gravity.CENTER
            val tvText = TextView(context)
            tvText.text = message
            tvText.setTextColor(Color.parseColor("#000000"))
            tvText.textSize = 20.toFloat()
            tvText.layoutParams = llParam

            ll.addView(progressBar)
            ll.addView(tvText)

            val builder = AlertDialog.Builder(context)
            builder.setCancelable(true)
            builder.setView(ll)

            val dialog = builder.create()
            val window = dialog.window
            if (window != null) {
                val layoutParams = WindowManager.LayoutParams()
                layoutParams.copyFrom(dialog.window?.attributes)
                layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
                layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
                dialog.window?.attributes = layoutParams
            }
            return dialog
        }
    }
}