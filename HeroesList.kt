package com.example.mrtayyab.heroesapisapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class HeroesList(private val context: Activity, internal var hero: List<hero>) : ArrayAdapter<hero>(context, R.layout.hero_layout_view, hero) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val listViewItem = inflater.inflate(R.layout.hero_layout_view, null, true)

        val textViewName = listViewItem.findViewById(R.id.heroNameId) as TextView
        val textViewRole = listViewItem.findViewById(R.id.heroRoleId) as TextView

        val myhero = hero[position]
        textViewName.text = myhero.name
        textViewRole.text = myhero.role

        return listViewItem
    }
}