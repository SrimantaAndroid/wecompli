package com.wecompli.utils.customalert;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.rts.commonutils_2_0.deviceinfo.DeviceResolution;
import com.wecompli.R;
import com.wecompli.screeen.accidentreport.AccidentReportActivity;
import com.wecompli.utils.sheardpreference.PreferenceConstent;
import com.wecompli.utils.utilsview.DrawingView;

public class TaptoSignSubmitAccidentReport extends AppCompatDialog implements View.OnClickListener {

    AccidentReportActivity activity;
    TextView tv_done, tv_signhere, tv_cancel, tv_claer;
    DrawingView signDrawView;
    DeviceResolution deviceResolution;
    ImageView img_signbehlf;
    TextView ttextview;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = activity.getLayoutInflater().inflate(R.layout.tap_to_sign_dialog_layout, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        deviceResolution = new DeviceResolution(activity);
        WindowManager.LayoutParams wmlp = getWindow().getAttributes();

        wmlp.width = deviceResolution.getWidth(0.9);
        wmlp.height = deviceResolution.getWidth(1);
        getWindow().setAttributes(wmlp);
//        wmlp.gravity = Gravity.BOTTOM;
//        wmlp.windowAnimations = R.style.DialogAnimation;
        initView(view);
    }

    private void initView(View view) {
        tv_done = view.findViewById(R.id.tv_done);
        tv_signhere = view.findViewById(R.id.tv_signhere);
        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_claer = view.findViewById(R.id.tv_claer);
        signDrawView = view.findViewById(R.id.sign);
        tv_done.setOnClickListener(this);
        tv_signhere.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        tv_claer.setOnClickListener(this);
        //settypeface
        tv_done.setTypeface(deviceResolution.getbebas(activity));
        tv_signhere.setTypeface(deviceResolution.getbebas(activity));
        tv_cancel.setTypeface(deviceResolution.getbebas(activity));
        tv_claer.setTypeface(deviceResolution.getbebas(activity));
        //  signDrawView.setImageBitmap();
    }

    public TaptoSignSubmitAccidentReport(AccidentReportActivity activity, ImageView img_signbehlf, TextView ttextview,String type) {
        super(activity);
        this.activity = activity;
        this.img_signbehlf=img_signbehlf;
        this.ttextview=ttextview;
        this.type=type;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_done:
                //    signDrawView.clear();
                Bitmap bitmap = signDrawView.getBitmap();

                //if (PreferenceConstent.signdraw == true) {
                    //  if (bitmap!=null || !bitmap.equals("")) {
                   // PreferenceConstent.signdraw = false;
                    ttextview.setText("");
                    img_signbehlf.setImageBitmap(bitmap);
                    if (type.equals("signbehalf")){
                     activity.cretefileforsignbehalf();
                    }
                   else if (type.equals("signbywitness1")){
                       activity.cretefileforwitness1sign();

                   }
                    else if (type.equals("signbywitness2")){
                       activity.cretefileforwitness2sign();
                    }
                    else if (type.equals("SignCompletedby")){
                        activity.cretefileforsignfromcompleted();

                    }
                    else if (type.equals("Signbyinjuredperson")){
                        activity.cretefileforsignfromcompletedinjuredperson();
                    }
              //  }
                dismiss();
                break;
            case R.id.tv_signhere:
                //  Bitmap bmp=signDrawView.getBitmap();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_claer:
                signDrawView.clear();
                //  Bitmap bmp=signDrawView.getBitmap();
                break;
        }
    }

    public Bitmap getcanvasbitmap() {
        Bitmap bmp = null;
        signDrawView.setDrawingCacheEnabled(true);
        Bitmap bitmap = signDrawView.getDrawingCache();
        return bitmap;
    }
    /*private Bitmap convertImageViewToBitmap(AppCompatImageView v){
        Bitmap bm=((BitmapDrawable)v.getDrawable()).getBitmap();
        return bm;
    }*/

}
