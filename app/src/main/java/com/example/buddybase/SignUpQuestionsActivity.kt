package com.example.buddybase
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.buddybase.databinding.ActivitySignupQuestionsBinding



class SignUpQuestionsActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySignupQuestionsBinding
    private lateinit var questions: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivitySignupQuestionsBinding.inflate(layoutInflater).apply { setContentView(root) }
        val adapter = ColorBaseAdapter()
        questions = questions()
        // Set the grid view adapter
        binding.gridView.adapter = adapter
        // Configure the grid view
        binding.gridView.numColumns = 2
        binding.gridView.horizontalSpacing = 15
        binding.gridView.verticalSpacing = 15
        binding.gridView.stretchMode = GridView.STRETCH_COLUMN_WIDTH

        var curQuestionIndex = adapter.getCurrentQuestionIndex()

        //initial first question
        binding.question.text = questions[curQuestionIndex]



        binding.questionSubmitBtn.setOnClickListener {
            if (adapter.getCurrentQuestionIndex() < TOTAL_NUM_QUESTIONS) {
                adapter.moveToNextQuestion()
                adapter.updateQuestionOptions()
                Log.i("currentQuestionIndex", adapter.getCurrentQuestionIndex().toString())

                curQuestionIndex = adapter.getCurrentQuestionIndex()
                binding.question.text = questions[curQuestionIndex]
            } else {
                //launch new activity -> show finish icon
                Log.i("over", "all question completed")
            }

        }
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

    companion object {
        private const val TOTAL_NUM_QUESTIONS = 4
    }
}