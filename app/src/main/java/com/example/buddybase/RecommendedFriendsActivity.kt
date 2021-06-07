package com.example.buddybase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Button
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.buddybase.model.UserInfo
import com.facebook.drawee.backends.pipeline.Fresco
import com.yuyakaido.android.cardstackview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendedFriendsActivity : AppCompatActivity(), CardStackListener {

    private val adapter = ProfilesAdapter()
    private lateinit var layoutManager: CardStackLayoutManager
    lateinit var stack_view: CardStackView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fresco.initialize(this)
        setContentView(R.layout.activity_recommended_friends)

        stack_view = findViewById<CardStackView>(R.id.stack_view)


        layoutManager = CardStackLayoutManager(this, this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        stack_view.layoutManager = layoutManager
        stack_view.adapter = adapter
        stack_view.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

        TinderAPI().getProfiles().enqueue(object : Callback<List<UserInfo>> {
            override fun onFailure(call: Call<List<UserInfo>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<UserInfo>>, response: Response<List<UserInfo>>) {
                response.body()?.let {
                    adapter.setProfiles(it)
                }
            }
        })
    }

    override fun onCardDisappeared(view: View?, position: Int) {

    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {

    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardRewound() {

    }
}
