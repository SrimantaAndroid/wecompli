package com.wecompli.screeen.checkinputnote

import android.view.View
import com.wecompli.R
import com.wecompli.utils.customalert.Alert
import kotlinx.android.synthetic.main.activity_check_input_note.view.*

class CheckInputNoteOnClick(
    var checkInputNoteActivity: CheckInputNoteActivity,
    var checkInputNoteViewBind: CheckInputNoteViewBind) : View.OnClickListener{
    init {
        checkInputNoteViewBind.btn_submit_input!!.setOnClickListener(this)
        checkInputNoteViewBind.rl_back_temptails!!.setOnClickListener(this)
    }
    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.btn_submit_input->{
                if (!checkInputNoteViewBind.et_input!!.text.toString().equals(""))
                checkInputNoteActivity.callApiforinputnote()
                else
                    Alert.showalert(checkInputNoteActivity,checkInputNoteActivity.getResources().getString(R.string.inputsomevalue))

            }
            R.id.rl_back_temptails->{
                checkInputNoteActivity.finish()
            }
        }

    }

}