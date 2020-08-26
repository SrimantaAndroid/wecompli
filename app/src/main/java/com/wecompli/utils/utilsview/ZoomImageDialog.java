package com.wecompli.utils.utilsview;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rts.commonutils_2_0.deviceinfo.DeviceResolution;
import com.wecompli.R;


public class ZoomImageDialog extends AppCompatDialog {
    DeviceResolution deviceResolution;
    Activity checkedItemActivity;
    String url,name;
    ImageView image,cross_imagedialog;
    TextView tv_name_form;
    public ZoomImageDialog(Activity checkedItemActivity, String name, String url) {
        super(checkedItemActivity);
        deviceResolution=new DeviceResolution(checkedItemActivity);
        this.url=url;
        this.checkedItemActivity=checkedItemActivity;
        this.name=name;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = checkedItemActivity.getLayoutInflater().inflate(R.layout.zoomimage_layout, null);
        setContentView(view);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT ));
        deviceResolution=new DeviceResolution(checkedItemActivity);
        WindowManager.LayoutParams wmlp = getWindow().getAttributes();
        wmlp.width = deviceResolution.getWidth(0.9);
        wmlp.height =deviceResolution.getWidth(1);
        getWindow().setAttributes(wmlp);
        init(view);
        setimage();
    }

    private void setimage() {
         ImageLoader imageLoader= ImageLoader.getInstance(); // Get singleton instance
         imageLoader.init(ImageLoaderConfiguration.createDefault(checkedItemActivity));
      //   tv_name_form.setText(name);
         imageLoader.displayImage(url, image);
    }

    private void init(View view) {
         image=view.findViewById(R.id.image);
        cross_imagedialog=view.findViewById(R.id.cross_imagedialog);
        //tv_name_form=view.findViewById(R.id.tv_name_form);
       // tv_name_form.setTypeface(deviceResolution.getbebas(checkedItemActivity));
        cross_imagedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
