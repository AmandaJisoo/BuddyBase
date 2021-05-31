package com.example.buddybase

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat

var currentQuestionIndex: Number = 0


class ColorBaseAdapter : BaseAdapter(){
    private val list = colors()


//    private val question = questions()
    private val choices = choicesPerQuestion()
    private val firstQChoices = firstQChoices()
    var curQuestion = firstQChoices

    val choiceOptionIndex = 0

    @SuppressLint("ViewHolder")
    override fun getView(position:Int, convertView: View?, parent: ViewGroup?):View{

        // Inflate the custom view
        val inflater = parent?.context?.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.activity_first_question,null)

        // Get the custom view widgets reference
        val tvName = view.findViewById<TextView>(R.id.tvName)
        val card = view.findViewById<CardView>(R.id.card_view)


        // Display color name on text view
        tvName.text = curQuestion[position]
        Log.i("position", position.toString())

        // Set a click listener for card view
        card.setOnClickListener{
            // Show selected color in a toast message
            Toast.makeText(parent.context,
                "Clicked : ${list[position].first}",Toast.LENGTH_SHORT).show()

            // Get the activity reference from parent
            val activity  = parent.context as Activity



        }
        // Finally, return the view
        return view
    }



//
//Doesn't work
//            Log.i("color1", card.background.toString())
//            if (!card.background.equals(Color.parseColor("#40fd779"))) {
//                card.setBackgroundColor(Color.parseColor("#3B5998"))
//                Log.i("inside", card.background.toString())
//            } else {
//                card.setBackgroundColor(Color.parseColor("#FFFFFF"))
//            }


    override fun getItem(position: Int): Any? {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }



    // Count the items
    override fun getCount(): Int {
        return curQuestion.size
    }


    fun updateQuestionOptions(curIndex: Number) {
        when(curIndex) {
            0 -> curQuestion = firstQChoices()
            1 -> curQuestion = secondQChoices()
            2 -> curQuestion = thirdQChoices()
            3 -> curQuestion = fourthQChoices()
        }

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

    private fun questions():List<String>{
        return listOf(
            "Pick a fav pet",
            "Pick a fav genre of music",
            "Pick a fav genre of movie",
            "Pick your personality",
            "Pick a fav friend personality"
        )
    }

    private fun firstQChoices():List<String> {
        return listOf("Doggo", "Cat")
    }

    private fun secondQChoices():List<String> {
        return listOf(
            "Hip-Hop", "Rock", "Jazz", "Classical", "Pop", "K-pop")
    }

    private fun thirdQChoices():List<String> {
        return listOf("Horror", "Romance", "Action", "Documentary", "Comedy")
    }

    private fun fourthQChoices():List<String> {
        return listOf("Very Introverted", "introverted", "extroverted")
    }



    private fun choicesPerQuestion():Map<Number, List<String>>{
        val optionsList: Map<Number, List<String>> = mutableMapOf()
        optionsList.plus(0 to listOf("Doggo", "Cat"))
        optionsList.plus(1 to listOf("Hip-Hop", "Rock", "Jazz", "Classical", "Pop", "K-pop"))
        optionsList.plus(2 to listOf("Horror", "Romance", "Action", "Documentary", "Comedy"))
        optionsList.plus(3 to listOf("Very Introverted", "introverted", "extroverted"))
        optionsList.plus(4 to listOf("Savory", "Sweet"))
        optionsList.plus(5 to listOf("Calm", "Energetic", "Athletic", "Outgoing", "Reserve"))
        return optionsList
    }
}