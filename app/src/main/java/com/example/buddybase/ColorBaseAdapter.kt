package com.example.buddybase

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

var selectedItemsMap = mutableMapOf<Int, MutableList<String>>()
var itemsList = mutableListOf<String>()

class ColorBaseAdapter : BaseAdapter(){
    private var currentQuestionIndex: Int = 0
    private val choices = choicesPerQuestion()
    private val firstQChoices = firstQChoices()
    lateinit var card: CardView
    var oldCardo: CardView? = null

    var curQuestion = firstQChoices

    val choiceOptionIndex = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?):View{
        // Inflate the custom view
        val inflater = parent?.context?.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.questions, null)

        // Get the custom view widgets reference
        val tvName = view.findViewById<TextView>(R.id.tvName)
        card = view.findViewById<CardView>(R.id.card_view)

        // Display color name on text view
        tvName.text = curQuestion[position]
        Log.i("position", position.toString())


        // Set a click listener for card view
        card.setOnClickListener {
            // Show selected color in a toast message
//            Toast.makeText(parent.context,
//                    "Clicked : ${curQuestion[position]}", Toast.LENGTH_SHORT).show()

            var cardo = it as CardView

            if (!selectedItemsMap.containsKey(currentQuestionIndex)) {
                itemsList = mutableListOf()
            }

            var picked = curQuestion[position]
            if (itemsList.contains(picked)) {
                itemsList.remove(picked)
                cardo.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
            } else {
                if (currentQuestionIndex == 0 || currentQuestionIndex == 2 || currentQuestionIndex == 4) {
                    if (itemsList.size == 0) {
                        itemsList.add(picked)
                        cardo.setCardBackgroundColor(Color.parseColor("#898F9C"))
                    } else {
                        itemsList.removeAt(0)
                        oldCardo?.setCardBackgroundColor(Color.parseColor("#FFFFFF"))
                        itemsList.add(picked)
                        cardo.setCardBackgroundColor(Color.parseColor("#898F9C"))
                    }
                } else {
                    itemsList.add(picked)
                    cardo.setCardBackgroundColor(Color.parseColor("#898F9C"))
                }
            }
            selectedItemsMap[currentQuestionIndex] = itemsList
            oldCardo = cardo

            // Get the activity reference from parent
            val activity  = parent.context as Activity
        }
        // Finally, return the view
        return view
    }

    fun moveToNextQuestion() {
        currentQuestionIndex += 1
    }

    fun getCurrentQuestionIndex(): Int{
        return currentQuestionIndex
    }

//TODO: color btn toggling.
//Doesn't work
//            Log.i("color1", card.background.toString())
//            if (!card.background.equals(Color.parseColor("#40fd779"))) {
//                card.setBackgroundColor(Color.parseColor("#3B5998"))
//                Log.i("inside", card.background.toString())
//            } else {
//                card.setBackgroundColor(Color.parseColor("#FFFFFF"))
//            }

    override fun getItem(position: Int): Any? {
        return curQuestion[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // Count the items
    override fun getCount(): Int {
        return curQuestion.size
    }

    fun updateQuestionOptions() {
        curQuestion = when(currentQuestionIndex) {
            0 -> firstQChoices()
            1 -> secondQChoices()
            2 -> thirdQChoices()
            3 -> fourthQChoices()
            4 -> fifthQChoices()
            5 -> sixthQChoices()
            else -> firstQChoices()
        }
        notifyDataSetChanged()
    }

    private fun firstQChoices():List<String> {
        return listOf("Doggo", "Cat")
    }

    private fun secondQChoices():List<String> {
        return listOf(
                "Hip-Hop", "Rock", "Jazz", "Classical", "Pop", "K-pop")
    }

    private fun thirdQChoices():List<String> {
        return listOf("Scary Movie", "Romance", "Action", "Documentary", "Comedy")
    }

    private fun fourthQChoices():List<String> {
        return listOf("Calm", "Cool", "Energetic", "Quiet", "Outgoing", "Organized", "Confident", "Enthusiastic")
    }

    private fun fifthQChoices():List<String> {
        return listOf("Savory", "Sweet")
    }

    private fun sixthQChoices():List<String> {
        return listOf("Calm", "Cool", "Energetic", "Quiet", "Outgoing", "Organized", "Confident", "Enthusiastic")
    }

    private fun choicesPerQuestion():Map<Number, List<String>>{
        val optionsList: Map<Number, List<String>> = mutableMapOf()
        optionsList.plus(0 to listOf("Doggo", "Cat"))
        optionsList.plus(1 to listOf("Hip-Hop", "Rock", "Jazz", "Classical", "Pop", "K-pop"))
        optionsList.plus(2 to listOf("Scary Movie", "Romance", "Action", "Documentary", "Comedy"))
        optionsList.plus(3 to listOf("Calm", "Cool", "Energetic", "Quiet", "Outgoing", "Organized", "Confident", "Enthusiastic"))
        optionsList.plus(4 to listOf("Savory", "Sweet"))
        optionsList.plus(5 to listOf("Calm", "Cool", "Energetic", "Quiet", "Outgoing", "Organized", "Confident", "Enthusiastic"))
        return optionsList
    }
}