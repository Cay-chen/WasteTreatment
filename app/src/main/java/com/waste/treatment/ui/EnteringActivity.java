package com.waste.treatment.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.emergency.EmergencyNumber;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.waste.treatment.R;
import com.waste.treatment.WasteTreatmentApplication;
import com.waste.treatment.databinding.ActivityEnteringBinding;
import com.waste.treatment.util.PrintDialog;
import com.waste.treatment.util.Utils;
import com.wildma.pictureselector.PictureSelector;

import java.util.Arrays;

public class EnteringActivity extends AppCompatActivity {
    ActivityEnteringBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        Utils.makeStatusBarTransparent(this);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_entering);
        mBinding.title.ivBack.setVisibility(View.VISIBLE);
        mBinding.title.tvTitle.setText(getResources().getString(R.string.entering_title));
        mBinding.spinnerZl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(WasteTreatmentApplication.TAG,"position:"+position+"  ..id:"+id+"-----"+getResources().getStringArray(R.array.zhonglei)[position]);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mBinding.igJiaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(EnteringActivity.this,PictureSelector.SELECT_REQUEST_CODE).selectPicture(false, 200, 200, 1, 1);;

            }
        });
        mBinding.igDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.igDelBtn.setVisibility(View.INVISIBLE);
                mBinding.igJiaBtn.setVisibility(View.VISIBLE);
                mBinding.imgRecycle.setImageBitmap(null);

            }
        });
        mBinding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PrintDialog(EnteringActivity.this).show();
            }
        });
        mBinding.title.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE){
            if (data!=null){
                String picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
                RequestOptions requestOptions = RequestOptions
                        .circleCropTransform()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true);
               // Glide.with(this).load(picturePath).apply(requestOptions).into(mBinding.imgRecycle);
                Glide.with(this).load(picturePath).into(mBinding.imgRecycle);
                mBinding.igJiaBtn.setVisibility(View.INVISIBLE);
                mBinding.igDelBtn.setVisibility(View.VISIBLE);
            }
        }
    }
}
