package com.example.tvapp.ui

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.tvapp.R
import com.example.tvapp.model.AppInfo


class AppAdapter(private val context: Context, private val apps: List<AppInfo>) : BaseAdapter() {
    override fun getCount(): Int = apps.size

    override fun getItem(position: Int): Any = apps[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item, parent, false)
        }

        val appName = view!!.findViewById<TextView>(R.id.Name)
        val appIcon = view.findViewById<ImageView>(R.id.appIcon)

        val app = apps[position]
        appName.text = app.appName
        try {
            val icon = context.packageManager.getApplicationIcon(app.packageName)
            appIcon.setImageDrawable(icon)
        } catch (e: Exception) {
            appIcon.setImageResource(android.R.drawable.sym_def_app_icon)
            Log.e("ICON_ERROR", "Failed to load icon for ${app.packageName}", e)
        }

        view.setOnClickListener {
            val launchIntent = context.packageManager.getLaunchIntentForPackage(app.packageName)
            if (launchIntent != null) {
                context.startActivity(launchIntent)
            }
        }
        return view
    }
}
