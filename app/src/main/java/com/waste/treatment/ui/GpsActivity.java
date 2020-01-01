package com.waste.treatment.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.waste.treatment.R;
import com.waste.treatment.databinding.ActivityGpsBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GpsActivity extends AppCompatActivity {
    private ActivityGpsBinding mBinding;
    private Geocoder geocoder;
    private List<Address> addressList;
    private StringBuilder sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_gps);
        mBinding.getGpsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mBinding.gpsAddressBtn.setText("获取位置");
               // mBinding.gpsAddressBtn.setText("获取位置");
               // mBinding.gpsAddressBtn.setText("获取位置");

                initData();
            }
        });

    }
    private void initData() {
        mBinding.gpsAddressBtn.setText("开始");

        // 获取经纬度坐标
        // 1 获取位置管理者对象
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // 2 通过lm获得经纬调度坐标
        // (参数： provider（定位方式 提供者 通过 LocationManager静态调用），
        // minTime（获取经纬度间隔的最小时间 时时刻刻获得传参数0），
        // minDistance（移动的最小间距 时时刻刻传0），LocationListener（监听）)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Toast.makeText(GpsActivity.this,"没权限",Toast.LENGTH_LONG).show();
             mBinding.gpsAddressBtn.setText("没权限");

            return;
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 获取经纬度主要方法
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Toast.makeText(GpsActivity.this,"精度"+latitude+","+longitude,Toast.LENGTH_LONG).show();
                mBinding.gpsJwBtn.setText(latitude+","+longitude);
                sb = new StringBuilder();
                geocoder = new Geocoder(GpsActivity.this);
                addressList = new ArrayList<Address>();

                try {
                    // 返回集合对象泛型address
                    addressList= geocoder.getFromLocation(latitude,longitude,1);


                    if (addressList.size() > 0) {
                        Address address = addressList.get(0);
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        sb.append(address.getFeatureName());//周边地址
                    }
                    Toast.makeText(GpsActivity.this,"位置："+sb.toString(),Toast.LENGTH_LONG).show();

                    mBinding.gpsAddressBtn.setText("当前位置"+sb.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                mBinding.gpsJwBtn.setText("onStatusChanged："+s);

                //状态发生改变监听
            }

            @Override
            public void onProviderEnabled(String s) {
                mBinding.gpsJwBtn.setText("onProviderEnabled："+s);
                // GPS 开启的事件监听
            }

            @Override
            public void onProviderDisabled(String s) {
                mBinding.gpsJwBtn.setText("onProviderDisabled："+s);
                // GPS 关闭的事件监听
            }
        });
    }
}
