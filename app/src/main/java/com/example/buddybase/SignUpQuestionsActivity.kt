package com.example.buddybase
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.buddybase.databinding.ActivitySignupQuestionsBinding



class SignUpQuestionsActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySignupQuestionsBinding
    private lateinit var questions: List<String>
    private lateinit var adapter: ColorBaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivitySignupQuestionsBinding.inflate(layoutInflater).apply { setContentView(root) }
        adapter = ColorBaseAdapter()
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
        updateImage()



        binding.questionSubmitBtn.setOnClickListener {
            if (adapter.getCurrentQuestionIndex() < TOTAL_NUM_QUESTIONS) {
                binding.processBar.setVisibility(View.GONE);
                adapter.updateQuestionOptions()
                adapter.moveToNextQuestion()
                updateImage()
                binding.processBar.setVisibility(View.VISIBLE);
                Log.i("currentQuestionIndex", adapter.getCurrentQuestionIndex().toString())

                curQuestionIndex = adapter.getCurrentQuestionIndex()
                binding.question.text = questions[curQuestionIndex]
            } else {
                //launch new activity -> show finish icon
                Log.i("over", "all question completed")
                startActivity(Intent(this@SignUpQuestionsActivity, SignUpConfirmActivity::class.java))

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

    //TODO: correctly get the file. It looks too messed stuff
    private fun updateImage(){
        Log.i("updateImage", adapter.getCurrentQuestionIndex().toString())
        when(adapter.getCurrentQuestionIndex()) {
            0 -> binding.processBar.setBackgroundResource(R.drawable.process2)
            1 -> binding.processBar.setBackgroundResource(R.drawable.process3)
            2 -> binding.processBar.setBackgroundResource(R.drawable.process4)
            3 ->  binding.processBar.setBackgroundResource(R.drawable.process6)

        }
    }

    companion object {
        private const val TOTAL_NUM_QUESTIONS = 4
    }


}