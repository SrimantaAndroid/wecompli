package com.wecompli.screeen.bodymap

import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution
import com.wecompli.R

class BodyMapViewBind(
   val activity: ActivityBodyMap,
   val view: View):DeviceResolution(activity) {
    var rl_back_bodymap: RelativeLayout? = null
    var rl_front_body_map:RelativeLayout? = null
    var rl_backbodymap_view:RelativeLayout? = null
    var tv_backandfrontview: TextView? = null
    var tv_viewbody:android.widget.TextView? = null
    var tv_forehead:android.widget.TextView? = null
    var tv_jaw:android.widget.TextView? = null
    var tv_tooth:android.widget.TextView? = null
    var tv_chest:android.widget.TextView? = null
    var tv_right_elbow:android.widget.TextView? = null
    var tv_left_elbow:android.widget.TextView? = null
    var tv_adomen:android.widget.TextView? = null
    var tv_right_wrist:android.widget.TextView? = null
    var tv_left_wrist: TextView? = null
    var tv_hip:android.widget.TextView? = null
    var tv_right_thing:android.widget.TextView? = null
    var tv_left_thing:android.widget.TextView? = null
    var tv_right_knee:android.widget.TextView? = null
    var tv_left_knee:android.widget.TextView? = null
    var tv_right_shin:android.widget.TextView? = null
    var tv_left_shin:android.widget.TextView? = null
    var tv_right_foot:android.widget.TextView? = null
    var tv_left_foot:android.widget.TextView? = null
    var tv_righttoe: TextView? = null
    var tv_lefttoe:android.widget.TextView? = null
    var tv_rightankle:android.widget.TextView? = null
    var tv_leftankle:android.widget.TextView? = null
    var tv_rightcalf:android.widget.TextView? = null
    var tv_leftcalf:android.widget.TextView? = null
    var tv_right_leg:android.widget.TextView? = null
    var tv_left_leg:android.widget.TextView? = null
    var tv_lowerback:android.widget.TextView? = null
    var tv_upperback:android.widget.TextView? = null
    var tv_right_shoulder:android.widget.TextView? = null
    var tv_left_shoulder: TextView? = null
    var tv_chin:android.widget.TextView? = null
    var tv_nose:android.widget.TextView? = null
    var tv_rightear:android.widget.TextView? = null
    var tv_leftear:android.widget.TextView? = null
    var tv_fingure:android.widget.TextView? = null

    var btn_continue: Button? = null
    init {
          viewbinds()
          settypeface()
    }

    private fun viewbinds() {
        rl_back_bodymap = view.findViewById(R.id.rl_back_bodymap1)
        tv_backandfrontview = view.findViewById(R.id.tv_backandfrontview)
        rl_front_body_map = view.findViewById(R.id.rl_front_body_map)
        rl_backbodymap_view = view.findViewById(R.id.rl_backbodymap)
        tv_viewbody = view.findViewById(R.id.tv_viewbody)
        btn_continue = view.findViewById(R.id.btn_continue)
        tv_forehead = view.findViewById(R.id.tv_forehead)
        tv_jaw = view.findViewById(R.id.tv_jaw)
        tv_tooth = view.findViewById(R.id.tv_tooth)
        tv_chest = view.findViewById(R.id.tv_chest)
        tv_right_elbow = view.findViewById(R.id.tv_right_elbow)
        tv_left_elbow = view.findViewById(R.id.tv_left_elbow)
        tv_adomen = view.findViewById(R.id.tv_adomen)
        tv_right_wrist = view.findViewById(R.id.tv_right_wrist)
        tv_left_wrist = view.findViewById(R.id.tv_left_wrist)
        tv_hip = view.findViewById(R.id.tv_hip)
        tv_right_thing = view.findViewById(R.id.tv_right_thing)
        tv_left_thing = view.findViewById(R.id.tv_left_thing)
        tv_right_knee = view.findViewById(R.id.tv_right_knee)
        tv_left_knee = view.findViewById(R.id.tv_left_knee)
        tv_right_shin = view.findViewById(R.id.tv_right_shin)
        tv_left_shin = view.findViewById(R.id.tv_left_shin)
        tv_right_foot = view.findViewById(R.id.tv_right_foot)
        tv_left_foot = view.findViewById(R.id.tv_left_foot)
        tv_leftear = view.findViewById(R.id.tv_leftear)
        tv_rightear = view.findViewById(R.id.tv_rightear)
        tv_nose = view.findViewById(R.id.tv_nose)
        tv_chin = view.findViewById(R.id.tv_chin)
        tv_left_shoulder = view.findViewById(R.id.tv_left_shoulder)
        tv_right_shoulder = view.findViewById(R.id.tv_right_shoulder)
        tv_upperback = view.findViewById(R.id.tv_upperback)
        tv_lowerback = view.findViewById(R.id.tv_lowerback)
        tv_left_leg = view.findViewById(R.id.tv_left_leg)
        tv_right_leg = view.findViewById(R.id.tv_right_leg)
        tv_leftcalf = view.findViewById(R.id.tv_leftcalf)
        tv_rightcalf = view.findViewById(R.id.tv_rightcalf)
        tv_leftankle = view.findViewById(R.id.tv_leftankle)
        tv_rightankle = view.findViewById(R.id.tv_rightankle)
        tv_lefttoe = view.findViewById(R.id.tv_lefttoe)
        tv_righttoe = view.findViewById(R.id.tv_righttoe)
        tv_fingure = view.findViewById(R.id.tv_fingure)
    }

    private fun settypeface() {
        tv_viewbody!!.setTypeface(getbebas(activity))
        tv_backandfrontview!!.setTypeface(getbebas(activity))
        btn_continue!!.setTypeface(getbebas(activity))
        tv_forehead!!.setTypeface(getgothmlight(activity))
        tv_jaw!!.setTypeface(getgothmlight(activity))
        tv_tooth!!.setTypeface(getgothmlight(activity))
        tv_chest!!.setTypeface(getgothmlight(activity))
        tv_right_elbow!!.setTypeface(getgothmlight(activity))
        tv_left_elbow!!.setTypeface(getgothmlight(activity))
        tv_adomen!!.setTypeface(getgothamthin(activity))
        tv_right_wrist!!.setTypeface(getgothmlight(activity))
        tv_left_wrist!!.setTypeface(getgothmlight(activity))
        tv_hip!!.setTypeface(getgothmlight(activity))
        tv_right_thing!!.setTypeface(getgothmlight(activity))
        tv_left_thing!!.setTypeface(getgothmlight(activity))
        tv_right_knee!!.setTypeface(getgothmlight(activity))
        tv_left_knee!!.setTypeface(getgothmlight(activity))
        tv_right_shin!!.setTypeface(getgothmlight(activity))
        tv_left_shin!!.setTypeface(getgothmlight(activity))
        tv_right_foot!!.setTypeface(getgothmlight(activity))
        tv_left_foot!!.setTypeface(getgothmlight(activity))
        tv_leftear!!.setTypeface(getgothmlight(activity))
        tv_rightear!!.setTypeface(getgothmlight(activity))
        tv_nose!!.setTypeface(getgothmlight(activity))
        tv_left_shoulder!!.setTypeface(getgothmlight(activity))
        tv_chin!!.setTypeface(getgothmlight(activity))
        tv_right_shoulder!!.setTypeface(getgothmlight(activity))
        tv_right_shoulder!!.setTypeface(getgothamthin(activity))
        tv_upperback!!.setTypeface(getgothmlight(activity))
        tv_lowerback!!.setTypeface(getgothmlight(activity))
        tv_left_leg!!.setTypeface(getgothmlight(activity))
        tv_right_leg!!.setTypeface(getgothmlight(activity))
        tv_leftcalf!!.setTypeface(getgothmlight(activity))
        tv_rightcalf!!.setTypeface(getgothmlight(activity))
        tv_leftankle!!.setTypeface(getgothmlight(activity))
        tv_rightankle!!.setTypeface(getgothmlight(activity))
        tv_lefttoe!!.setTypeface(getgothmlight(activity))
        tv_fingure!!.setTypeface(getgothmlight(activity))
    }
}