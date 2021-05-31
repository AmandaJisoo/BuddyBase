package com.example.buddybase

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView

class ColorBaseAdapter : BaseAdapter(){
    private val list = colors()

    override fun getView(position:Int, convertView: View?, parent: ViewGroup?):View{
        // Inflate the custom view
        val inflater = parent?.context?.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_first_question,null)

        // Get the custom view widgets reference
        val tv = view.findViewById<TextView>(R.id.tv_name)
        val card = view.findViewById<CardView>(R.id.card_view)

        // Display color name on text view
        tv.text = list[position].first

        // Set background color for card view
        card.setCardBackgroundColor(list[position].second)

        // Set a click listener for card view
        card.setOnClickListener{
            // Show selected color in a toast message
            Toast.makeText(parent.context,
                "Clicked : ${list[position].first}",Toast.LENGTH_SHORT).show()

            // Get the activity reference from parent
            val activity  = parent.context as Activity

            // Get the activity root view
            val viewGroup = activity.findViewById<ViewGroup>(android.R.id.content)
                .getChildAt(0)

            // Change the root layout background color
            viewGroup.setBackgroundColor(list[position].second)
        }

        // Finally, return the view
        return view
    }



    /*
        **** reference source developer.android.com ***

        Object getItem (int position)
            Get the data item associated with the specified position in the data set.

        Parameters
            position int : Position of the item whose data we want within the adapter's data set.
        Returns
            Object : The data at the specified position.
    */
    override fun getItem(position: Int): Any? {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    // Count the items
    override fun getCount(): Int {
        return list.size
    }




    // Custom method to generate list of color name value pair
    private fun colors():List<Pair<String,Int>>{
        return listOf(
            Pair("INDIANRED", Color.parseColor("#CD5C5C")),
            Pair("LIGHTCORAL",Color.parseColor("#F08080")),
            Pair("SALMON",Color.parseColor("#FA8072")),
            Pair("DARKSALMON",Color.parseColor("#E9967A")),
            Pair("LIGHTSALMON",Color.parseColor("#FFA07A")),
            Pair("CRIMSON",Color.parseColor("#DC143C")),
            Pair("RED",Color.parseColor("#FF0000")),
            Pair("FIREBRICK",Color.parseColor("#B22222")),
            Pair("DARKRED",Color.parseColor("#8B0000")),

            Pair("PINK",Color.parseColor("#FFC0CB")),
            Pair("LIGHTPINK",Color.parseColor("#FFB6C1")),
            Pair("HOTPINK",Color.parseColor("#FF69B4")),
            Pair("DEEPPINK",Color.parseColor("#FF1493")),
            Pair("MEDIUMVIOLETRED",Color.parseColor("#C71585")),
            Pair("PALEVIOLETRED",Color.parseColor("#DB7093"))
        )
    }
}