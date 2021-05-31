package com.example.buddybase

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView




class ColorBaseAdapter : BaseAdapter(){
    private  var currentQuestionIndex: Int = 0
    private val choices = choicesPerQuestion()
    private val firstQChoices = firstQChoices()


    var curQuestion = firstQChoices


    val choiceOptionIndex = 0

    @SuppressLint("ViewHolder")
    override fun getView(position:Int, convertView: View?, parent: ViewGroup?):View{

        // Inflate the custom view
        val inflater = parent?.context?.
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.questions,null)

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
                "Clicked : ${curQuestion[position]}",Toast.LENGTH_SHORT).show()

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
        when(currentQuestionIndex) {
            0 -> curQuestion = firstQChoices()
            1 -> curQuestion = secondQChoices()
            2 -> curQuestion = thirdQChoices()
            3 -> curQuestion = fourthQChoices()
            4 -> curQuestion = fifthQChoices()
            else -> curQuestion = firstQChoices()
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
        return listOf("Horror", "Romance", "Action", "Documentary", "Comedy")
    }

    private fun fourthQChoices():List<String> {
        return listOf("Very Introverted", "introverted", "extroverted")
    }

    private fun fifthQChoices():List<String> {
        return listOf("Calm", "Energetic", "Athletic", "Outgoing", "Reserve")
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