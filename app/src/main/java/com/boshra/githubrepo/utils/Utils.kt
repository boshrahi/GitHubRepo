package com.boshra.githubrepo.utils

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.net.ConnectivityManager
import com.boshra.githubrepo.dataModel.Repo
import com.boshra.githubrepo.database.Repository


class Utils {

    fun showAlertDialog(title : String ,message : String,context : Context){
        var alertDialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Try Again", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()

            }).show()
    }
    fun networkAvailable(context: Context): Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        return cm!!.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }
    fun createArrayOfData(list: ArrayList<Repo>): Array<Repository?> {
        val size = list.size
        val array = arrayOfNulls<Repository>(size)
        for (i in 0 until size) {
            val repo = list.get(i)
            array[i] = Repository(
                i.toString(), repo.name, repo.full_name, repo.description
            )
        }
        return array
    }

    fun resultModifier(result: List<Repository>): ArrayList<Repo>? {
        val modified = ArrayList<Repo>()
        for (i in 0 until result.size) {
            val repository = result.get(i)
            modified.add(
                Repo(
                    repository.id.toInt(),
                    repository.name,
                    repository.full_name,
                    repository.description
                )
            )
        }
        return modified
    }
}