package com.wecompli.screeen.intropages

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.wecompli.R
import com.wecompli.screeen.login.LoginActivity
import com.wecompli.utils.ApplicationConstant
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent
import kotlinx.android.synthetic.main.activity_intro.*
import java.util.*

class InterPagesActivity:AppCompatActivity() {
    private var currentPage = 0
    private var NUM_PAGES = 0
    private val IMAGES = arrayOf(R.drawable.intro1, R.drawable.intro2, R.drawable.intro3, R.drawable.intro4, R.drawable.intro5, R.drawable.intro6)
    internal lateinit var appSheardPreference: AppSheardPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        btn_skip.setOnClickListener {
            AppSheardPreference(this).setvalue_in_preference(PreferenceConstent.showintropage,
                ApplicationConstant.ISINTROPAGESHOW)
            val intent=Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        setuppager()
    }

    private fun setuppager() {
        mPager.adapter=SliderImageAdapter(this,IMAGES)
        indicator.setViewPager(mPager)
        val density = resources.displayMetrics.density
        indicator.radius = 5 * density

        val handler = Handler()
        val Update = Runnable {
            if (currentPage == NUM_PAGES) {
                currentPage = 0
            }
            mPager.setCurrentItem(currentPage++, true)
        }

        val swipeTimer = Timer()
        swipeTimer.schedule(object : TimerTask() {
            override fun run() {
                handler.post(Update)
            }
        }, 2000, 2000)

        indicator.setOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageSelected(position: Int) {
                currentPage = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
        })
    }
}