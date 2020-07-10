package com.wecompli.screeen.bodymap

import android.content.Intent
import android.view.View
import com.wecompli.R

class BodyMapOnClick(
   val activityBodyMap: ActivityBodyMap,
    val bodyMapViewBind: BodyMapViewBind) : View.OnClickListener {

    init {
        bodyMapViewBind.rl_back_bodymap!!.setOnClickListener(this)
        bodyMapViewBind.tv_backandfrontview!!.setOnClickListener(this)
        bodyMapViewBind.tv_forehead!!.setOnClickListener(this)
        bodyMapViewBind.tv_jaw!!.setOnClickListener(this)
        bodyMapViewBind.tv_tooth!!.setOnClickListener(this)
        bodyMapViewBind.tv_chest!!.setOnClickListener(this)
        bodyMapViewBind.tv_left_elbow!!.setOnClickListener(this)
        bodyMapViewBind.tv_right_elbow!!.setOnClickListener(this)
        bodyMapViewBind.tv_adomen!!.setOnClickListener(this)
        bodyMapViewBind.tv_right_wrist!!.setOnClickListener(this)
        bodyMapViewBind.tv_left_wrist!!.setOnClickListener(this)
        bodyMapViewBind.tv_hip!!.setOnClickListener(this)
        bodyMapViewBind.tv_fingure!!.setOnClickListener(this)
        bodyMapViewBind.tv_right_thing!!.setOnClickListener(this)
        bodyMapViewBind.tv_left_thing!!.setOnClickListener(this)
        bodyMapViewBind.tv_right_knee!!.setOnClickListener(this)
        bodyMapViewBind.tv_left_knee!!.setOnClickListener(this)
        bodyMapViewBind.tv_right_shin!!.setOnClickListener(this)
        bodyMapViewBind.tv_left_shin!!.setOnClickListener(this)
        bodyMapViewBind.tv_right_foot!!.setOnClickListener(this)
        bodyMapViewBind.tv_left_foot!!.setOnClickListener(this)

        bodyMapViewBind.tv_leftear!!.setOnClickListener(this)
        bodyMapViewBind.tv_rightear!!.setOnClickListener(this)
        bodyMapViewBind.tv_nose!!.setOnClickListener(this)
        bodyMapViewBind.tv_chin!!.setOnClickListener(this)
        bodyMapViewBind.tv_left_shoulder!!.setOnClickListener(this)
        bodyMapViewBind.tv_right_shoulder!!.setOnClickListener(this)
        bodyMapViewBind.tv_upperback!!.setOnClickListener(this)
        bodyMapViewBind.tv_lowerback!!.setOnClickListener(this)
        bodyMapViewBind.tv_left_leg!!.setOnClickListener(this)
        bodyMapViewBind.tv_right_leg!!.setOnClickListener(this)
        bodyMapViewBind.tv_leftcalf!!.setOnClickListener(this)
        bodyMapViewBind.tv_rightcalf!!.setOnClickListener(this)
        bodyMapViewBind.tv_leftankle!!.setOnClickListener(this)
        bodyMapViewBind.tv_rightankle!!.setOnClickListener(this)
        bodyMapViewBind.tv_lefttoe!!.setOnClickListener(this)
        bodyMapViewBind.tv_righttoe!!.setOnClickListener(this)
        bodyMapViewBind.btn_continue!!.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id) {
            R.id.btn_continue -> {
                if (activityBodyMap.bodyitemlist.size > 0) {
                    for (i in 0 until activityBodyMap.bodyitemlist.size) {
                        activityBodyMap.str_body =
                            activityBodyMap.str_body + "," + activityBodyMap.bodyitemlist[i]
                    }
                }

                val intent = Intent()
                intent.putExtra("body", activityBodyMap.str_body)
                activityBodyMap.setResult(500, intent)
                activityBodyMap.finish()
            }
            R.id.rl_back_bodymap1-> {
                activityBodyMap.finish()
            }
            R.id.tv_backandfrontview-> {
                if (bodyMapViewBind.tv_backandfrontview!!.text.toString().equals(activityBodyMap.resources.getString(R.string.backbody))
                ) {
                    bodyMapViewBind.tv_backandfrontview!!.text =
                        activityBodyMap.resources.getString(R.string.frontbody)
                    bodyMapViewBind.rl_front_body_map!!.visibility = View.GONE
                    bodyMapViewBind.rl_backbodymap_view!!.visibility = View.VISIBLE
                    bodyMapViewBind.tv_viewbody!!.text =
                        activityBodyMap.resources.getString(R.string.backbody)
                } else {
                    bodyMapViewBind.tv_backandfrontview!!.text =
                        activityBodyMap.resources.getString(R.string.backbody)
                    bodyMapViewBind.rl_backbodymap_view!!.visibility = View.GONE
                    bodyMapViewBind.rl_front_body_map!!.visibility = View.VISIBLE
                    bodyMapViewBind.tv_viewbody!!.text =
                        activityBodyMap.resources.getString(R.string.frontbody)
                }
            }
            R.id.tv_forehead->{
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_forehead!!)
            }
           R.id.tv_jaw->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_jaw!!)
            R.id.tv_tooth->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_tooth!!)
            R.id.tv_left_elbow->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_left_elbow!!)
            R.id.tv_chest->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_chest!!)
            R.id.tv_right_elbow->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_right_elbow!!)
            R.id.tv_adomen->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_adomen!!)
            R.id.tv_right_wrist->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_right_wrist!!)
            R.id.tv_left_wrist->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_left_wrist!!)
            R.id.tv_hip->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_hip!!)
            R.id.tv_fingure->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_fingure!!)
            R.id.tv_right_thing->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_right_thing!!)
            R.id.tv_left_thing->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_left_thing!!)
            R.id.tv_right_knee->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_right_knee!!)
            R.id.tv_left_knee->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_left_knee!!)
            R.id.tv_right_shin->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_right_shin!!)
            R.id.tv_left_shin->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_left_shin!!)
            R.id.tv_right_foot->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_right_foot!!)
            R.id.tv_left_foot->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_left_foot!!)
            R.id.tv_leftear->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_leftear!!)
            R.id.tv_rightear->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_rightear!!)
            R.id.tv_nose->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_nose!!)
            R.id.tv_chin->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_chin!!)
            R.id.tv_left_shoulder->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_left_shoulder!!)
            R.id.tv_right_shoulder->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_right_shoulder!!)
            R.id.tv_upperback->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_upperback!!)
            R.id.tv_lowerback->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_lowerback!!)
            R.id.tv_left_leg->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_left_leg!!)
            R.id.tv_right_leg->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_right_leg!!)
            R.id.tv_leftcalf->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_leftcalf!!)
            R.id.tv_rightcalf->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_rightcalf!!)
            R.id.tv_leftankle->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_leftankle!!)
            R.id.tv_rightankle->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_rightankle!!)
            R.id.tv_lefttoe->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_lefttoe!!)
            R.id.tv_righttoe->
                activityBodyMap.addselectebodymaplist(bodyMapViewBind.tv_righttoe!!)

        }

    }
}