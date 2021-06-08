package com.example.buddybase
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.buddybase.databinding.ActivitySignupQuestionsBinding
import com.example.buddybase.manager.UserManager
import com.google.firebase.firestore.FirebaseFirestore


class SignUpQuestionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupQuestionsBinding
    private lateinit var questions: List<String>
    private lateinit var adapter: ColorBaseAdapter
    private val userApp: UserApplication by lazy { application as UserApplication }
    private lateinit var manager: UserManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.manager = userApp.userManager

        this.binding = ActivitySignupQuestionsBinding.inflate(layoutInflater).apply { setContentView(root) }

        adapter = ColorBaseAdapter()
        questions = questions()
        // Set the grid view adapter
        binding.gridView.adapter = adapter
        // Configure the grid view
        binding.gridView.numColumns = 2
        binding.gridView.horizontalSpacing = 25
        binding.gridView.verticalSpacing = 25
        binding.gridView.stretchMode = GridView.STRETCH_COLUMN_WIDTH

        var curQuestionIndex = adapter.getCurrentQuestionIndex()

        //initial first question
        binding.question.text = questions[curQuestionIndex]
        updateImage()

        Log.i("currentQuestionIndex", adapter.getCurrentQuestionIndex().toString())

//        binding.gridView.onItemClickListener = OnItemClickListener { parent, v, position, id ->
//            val selectedIndex = adapter.getItem(position)
//            Log.i("peanute", "$selectedIndex")
//            Toast.makeText(parent.context,
//                    "Clicked : $selectedIndex", Toast.LENGTH_SHORT).show()
//        }


        binding.questionSubmitBtn.setOnClickListener {
            if (adapter.getCurrentQuestionIndex() < TOTAL_NUM_QUESTIONS) {
                binding.processBar.visibility = View.GONE;
                adapter.moveToNextQuestion()
                adapter.updateQuestionOptions()
                updateImage()
                binding.processBar.visibility = View.VISIBLE;

                curQuestionIndex = adapter.getCurrentQuestionIndex()

                binding.question.text = questions[curQuestionIndex]
            } else {
                //launch new activity -> show finish icon
                Log.i("over", "all question completed")
                Log.i("peanute", "$selectedItemsMap")

                //TODO: set up matched and send matched AND survey data to firestore
                val userDetails = hashMapOf<String, Any>()
                val keyList = listOf<String>("Q_Pet", "Q_Music", "Q_Show", "Q_Personality", "Q_Taste", "Q_FriendType")
                for (item in keyList) {
                    var value: Any
                    if (keyList.indexOf(item) == 0 || keyList.indexOf(item) == 2 || keyList.indexOf(item) == 4) {
                        value = selectedItemsMap.getValue(keyList.indexOf(item))[0]
                    } else {
                        value = selectedItemsMap.getValue(keyList.indexOf(item))
                    }
                    userDetails[item] = value
                }

                val firestore = FirebaseFirestore.getInstance()
                val matchedList = mutableListOf<String>()
                val matchedMap = mutableMapOf<String, Any>()
                val docRef1 = firestore.collection("Users")
                docRef1.get()
                        .addOnSuccessListener { result ->
                            for (document in result) {
                                if (document.data.size > 8) {
                                    matchedList.add(document.id)
                                    matchedMap[document.data["FullName"] as String] = document.data
                                }
                            }
                            userDetails["Matched"] = matchedList
                            userDetails["uid"] = manager.uid.toString()
                            manager.setMatchedUids(matchedList)
                            manager.setMatchedUsers(matchedMap)
                            val docRef = manager.uid?.let { it1 -> firestore.collection("Users").document(it1) }
                            if (docRef != null) {
                                docRef.update(userDetails)
                                        .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                                        .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }
                            }
                            startActivity(Intent(this@SignUpQuestionsActivity, SignUpConfirmActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { exception ->
                            Log.d("rawr", "get failed with ", exception)
                        }
            }
        }
    }

    private fun questions():List<String>{
        return listOf(
                "Pick One",
                "Pick Your Favorite Music Genre(s)",
                "What Do You Like To Watch",
                "Pick Your Personality Type(s)",
                "Pick One",
                "Pick Personality Trait(s) You Look For In A Friend"
        )
    }

    //TODO: correctly get the file. It looks too messed stuff
    private fun updateImage(){
        Log.i("updateImage", adapter.getCurrentQuestionIndex().toString())
        when(adapter.getCurrentQuestionIndex()) {
            0 -> binding.processBar.setImageResource(R.drawable.sign_up_2)
            1 -> binding.processBar.setImageResource(R.drawable.sign_up_3)
            2 -> binding.processBar.setImageResource(R.drawable.sign_up_4)
            3 -> binding.processBar.setImageResource(R.drawable.sign_up_5)
            4 -> binding.processBar.setImageResource(R.drawable.sign_up_6)
            5 -> binding.processBar.setImageResource(R.drawable.sign_up_7)
        }
    }

    companion object {
        private const val TOTAL_NUM_QUESTIONS = 5
    }
}