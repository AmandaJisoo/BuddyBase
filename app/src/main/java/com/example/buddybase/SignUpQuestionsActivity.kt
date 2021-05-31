package com.example.buddybase
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import com.example.buddybase.databinding.ActivitySignupQuestionsBinding



class SignUpQuestionsActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySignupQuestionsBinding
    private lateinit var questons: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.binding = ActivitySignupQuestionsBinding.inflate(layoutInflater).apply { setContentView(root) }
        val adapter = ColorBaseAdapter()
        questons = questions()
        // Set the grid view adapter
        binding.gridView.adapter = adapter
        // Configure the grid view
        binding.gridView.numColumns = 2
        binding.gridView.horizontalSpacing = 15
        binding.gridView.verticalSpacing = 15
        binding.gridView.stretchMode = GridView.STRETCH_COLUMN_WIDTH



        binding.question.text = questons[currentQuestionIndex as Int]
        Log.i("AmandaSignUp", "SignUpQuestionsActivity")

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
}