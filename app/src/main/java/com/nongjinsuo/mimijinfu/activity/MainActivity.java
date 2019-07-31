package com.nongjinsuo.mimijinfu.activity;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;
import com.nongjinsuo.mimijinfu.fragment.P2pAndProjectFragment2;
import com.umeng.analytics.MobclickAgent;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.config.SharedPreferenceUtil;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.ActivityDialog;
import com.nongjinsuo.mimijinfu.fragment.FindFragment;
import com.nongjinsuo.mimijinfu.fragment.HomePageFragment;
import com.nongjinsuo.mimijinfu.fragment.P2pAndProjectFragment;
import com.nongjinsuo.mimijinfu.fragment.PropertyFragment;
import com.nongjinsuo.mimijinfu.httpmodel.ClientModel;
import com.nongjinsuo.mimijinfu.httpmodel.VersionModel;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.MobileInfoUtils;
import com.nongjinsuo.mimijinfu.util.StatusBarUtil;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.Util;
import com.nongjinsuo.mimijinfu.util.update.UpdateVersionService;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import de.greenrobot.event.EventBus;

public class MainActivity extends AppCompatActivity implements IBase, OnPermissionCallback {
    @BindView(R.id.rbHomePage)
    RadioButton rbHomePage;
    @BindView(R.id.rbProject)
    RadioButton rbProject;
    @BindView(R.id.rbProperty)
    RadioButton rbProperty;
    @BindView(R.id.rbFind)
    RadioButton rbFind;
    @BindView(R.id.rgBottomView)
    RadioGroup rgBottomView;
    private Fragment mCurrentFragment;
    private HomePageFragment homePageFragment;
    private P2pAndProjectFragment2 projectFragment;
    private FindFragment findFragment;
    private PropertyFragment propertyFragment;
    private long mExitTime;
    private int checkId = R.id.rbHomePage;
    private static MainActivity mainActivity;


    public static MainActivity getActivity() {
        return mainActivity;
    }

    public LocationClient mLocationClient = null;
    private PermissionHelper permissionHelper;
    public BDLocationListener myListener = new MyLocationListener();
    private boolean projectSwitchFlag = false;
    private final static String[] MULTI_PERMISSIONS = new String[]{
//            Manifest.permission.CALL_PHONE,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Constants.userid = SharedPreferenceUtil.getUserId(this);
        // 获取屏幕的宽高
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        Constants.WINDOW_WIDTH = defaultDisplay.getWidth();
        Constants.WINDOW_HEIGHT = defaultDisplay.getHeight();
        EventBus.getDefault().register(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Util.isMIUI()) {
                StatusBarUtil.setTransparent(this, Color.TRANSPARENT);
                StatusBarUtil.setStatusBarDarkMode(true, this);
            } else if (Util.isFlyme()) {
                StatusBarUtil.setTransparent(this, Color.TRANSPARENT);
                StatusBarUtil.setStatusBarDarkIcon(getWindow(), true);
            } else {
                StatusBarUtil.setTransparent(this, Color.BLACK);
            }
        }
        Bundle bundle = getIntent().getBundleExtra(Constants.EXTRA_BUNDLE);
        if (bundle != null) {
            String classId = bundle.getString(Constants.CLASSID);
            String projectBm = bundle.getString(Constants.PROJECTBM);

            String productBm = bundle.getString(Constants.PRODUCTBM);
            String productId = bundle.getString(Constants.PRODUCTID);

            String url = bundle.getString(Constants.URL);
            if (classId.equals(Constants.PROJECT)) {
                Intent detailIntent = new Intent(this, ProjectDetilsActivity.class);
                detailIntent.putExtra(Constants.PROJECTBM, projectBm);
                startActivity(detailIntent);
            } else if (classId.equals(Constants.URL)) {
                Intent detailIntent = new Intent(this, WebViewActivity.class);
                detailIntent.putExtra(WebViewActivity.URL, url);
                startActivity(detailIntent);
            } else if (classId.equals(Constants.INTER_URL)) {
                Intent detailIntent = new Intent(this, WebViewAndJsActivity.class);
                detailIntent.putExtra(WebViewAndJsActivity.URL, url);
                startActivity(detailIntent);
            }else if (classId.equals(Constants.PRODUCT)) {
                Intent detailIntent= new Intent(this, P2pDetilsActivity.class);
                detailIntent.putExtra(Constants.PROJECTBM, productBm);
                detailIntent.putExtra(Constants.ID,productId);
            }
        }
        String openAdUrl = getIntent().getStringExtra("openAdUrl");
        if (TextUtil.IsNotEmpty(openAdUrl)) {
            Intent intent = new Intent(MainActivity.this, WebViewAndJsActivity.class);
            intent.putExtra(WebViewActivity.URL, openAdUrl);
            startActivity(intent);
        }

        if (SharedPreferenceUtil.getString(this, SharedPreferenceUtil.GESTURE_PWD_TAG).length() > 0) {
            Intent intent = new Intent(this, LeftMenuSettingGesturePwdActivity.class);
            intent.putExtra(LeftMenuSettingGesturePwdActivity.INTENT_TYPE, LeftMenuSettingGesturePwdActivity.INTENT_MAIN);
            startActivity(intent);
        }
        mainActivity = this;
        //检测版本是否需要更新
        updateVersion();
        location();
        init();
        addListener();
        synchronizClientIdAndJPushRegId();

        //设置别名
        JPushInterface.setAlias(MainActivity.this, Constants.userid, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
            }
        });

    }


    /**
     * 定位
     */
    private void location() {
        permissionHelper = PermissionHelper.getInstance(this);
        permissionHelper
                .setForceAccepting(false) // default is false. its here so you know that it exists.
                .request(MULTI_PERMISSIONS);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        initLocation();
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(false);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public boolean isAppOnForeground() {
        // Returns a list of application processes that are running on the
        // device

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            // The name of the process that this object is associated with.
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

    public void onEventMainThread(EventBarEntity entity) {
        if (entity.getType() == EventBarEntity.UPDATE_LOGIN_VIEW) {
            rbProperty.setChecked(true);
        }
        if (entity.getType() == EventBarEntity.UPDATE_LOGOUT_VIEW) {
            rbHomePage.setChecked(true);
        }
        if (entity.getType() == EventBarEntity.UPDATE_PROJECT_VIEW) {
            rbProject.setChecked(true);
        }
        if (entity.getType() == EventBarEntity.PROJECT_ZC) {
            if (projectFragment == null) {
                projectSwitchFlag = true;
            }
            rbProject.setChecked(true);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        JPushInterface.stopPush(this);
        EventBus.getDefault().unregister(this);
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Constants.clientid = SharedPreferenceUtil.getString(this, SharedPreferenceUtil.CLIENTID);
    }

    private void updateVersion() {
        JacksonRequest<VersionModel> jacksonRequest = new JacksonRequest<>(RequestMap.getStartMap(), VersionModel.class, new Response.Listener<VersionModel>() {
            @Override
            public void onResponse(VersionModel baseVo) {
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    //判断服务器是否更新

                    //判断是否是强制升级
                    boolean flag = false;
                    if (baseVo.result.getAndroidForceUpdate().equals("1")) {
                        flag = true;
                    }
                    UpdateVersionService updateVersionService = new UpdateVersionService(MainActivity.this, baseVo.result.getAndroidVersionInfo(), flag, baseVo.result.getDownload());// 创建更新业务对象
                    updateVersionService.checkUpdate(baseVo.result.getAndroidVersionCode(), baseVo.result.getAndroidVersionName(), true);
                    if (Integer.parseInt(baseVo.result.getAndroidVersionCode()) <= UpdateVersionService.getVersionCode(MainActivity.this)) {
                        boolean ziqidong = SharedPreferenceUtil.getBoolean(getActivity(), SharedPreferenceUtil.ZIQIDONG);
//                        if (!ziqidong) {
//                            SharedPreferenceUtil.saveBoolean(getActivity(), SharedPreferenceUtil.ZIQIDONG, true);
//                            jumpStartInterface();
//                            return;
//                        }
                        if (baseVo.result.getJumpAdStatus() == 1) { //判断是否显示广告
                            //保存图片的路径
                            String imgUrl = baseVo.result.getJumpAdImage();
                            if (TextUtil.IsNotEmpty(imgUrl)) {
                                String fileName = imgUrl.substring(imgUrl.lastIndexOf("/"), imgUrl.length());
                                String orlImgUrl = SharedPreferenceUtil.getString(MainActivity.this, SharedPreferenceUtil.HB_IMG);
                                if (TextUtil.IsEmpty(orlImgUrl)) {
                                    orlImgUrl = "";
                                }
                                if (!orlImgUrl.equals(imgUrl)) {
                                    final File file = new File(getFilesDir().getAbsolutePath(), fileName);
                                    if (file.exists()) {
                                        SharedPreferenceUtil.saveString(MainActivity.this, SharedPreferenceUtil.HB_IMG, imgUrl);
                                        ActivityDialog activityDialog = new ActivityDialog(MainActivity.this);
                                        activityDialog.show();
                                        activityDialog.setGgBitmap(Util.getBitmap(file));
                                        activityDialog.setDjCkUrl(baseVo.result.getJumpAdUrl());
                                        LogUtil.d("img", "广告图片存在");
                                    } else {
                                        LogUtil.d("img", "广告图片不存在，去下载");
                                        downloadImg(imgUrl, file);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    private void downloadImg(String imgUrl, final File file) {
        ImageRequest imageRequest = new ImageRequest(Urls.PROJECT_URL + imgUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
//                                        ivNetImg.setImageBitmap(response);
                        //保存图片到内部存储
                        if (file.exists()) {
                            file.delete();
                        }
                        try {
                            FileOutputStream out = new FileOutputStream(file);
                            response.compress(Bitmap.CompressFormat.PNG, 90, out);
                            out.flush();
                            out.close();
                            LogUtil.d("img", "广告图片下载成功");
                        } catch (FileNotFoundException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        AiMiCrowdFundingApplication.context().addToRequestQueue(imageRequest, getClass().getName());
    }

    /**
     * Jump Start Interface
     * 提示是否跳转设置自启动界面
     */
    private void jumpStartInterface() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("如接收不到新标预告通知，请按照指引设置！");
            builder.setPositiveButton("如何设置",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
//                            MobileInfoUtils.jumpStartInterface(MainActivity.this);
                            Intent intent = new Intent(getActivity(), WebViewActivity.class);
                            intent.putExtra(WebViewActivity.URL, Urls.ZI_QI_DONG + MobileInfoUtils.getDeviceBrand());
                            startActivity(intent);
                        }
                    });
            builder.setNegativeButton("忽略",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.setCancelable(false);
            builder.create().show();
        } catch (Exception e) {
        }
    }

    /**
     * 同步clientId和JPushRegId
     */
    public void synchronizClientIdAndJPushRegId() {
        LogUtil.i("phone", "当前手机系统语言:" + MobileInfoUtils.getSystemLanguage() + "\n当前手机系统版本号:" + MobileInfoUtils.getSystemVersion() + "\n当前手机型号:" + MobileInfoUtils.getSystemModel() + "\n当前手机厂商:" + MobileInfoUtils.getDeviceBrand() + "\n制造商:" + MobileInfoUtils.getMobileType());
        LogUtil.d("RegistrationID:" + JPushInterface.getRegistrationID(this));
        JacksonRequest<ClientModel> jacksonRequest = new JacksonRequest<>(RequestMap.getSynchoroniza(this, JPushInterface.getRegistrationID(this)), ClientModel.class, new Response.Listener<ClientModel>() {
            @Override
            public void onResponse(ClientModel baseVo) {
                if (baseVo.getCode().equals(Constants.SUCCESS)) {
                    if (baseVo.result != null) {
                        if (!SharedPreferenceUtil.getString(MainActivity.this, SharedPreferenceUtil.CLIENTID).equals(baseVo.result.getClientId())) {
                            Constants.clientid = baseVo.result.getClientId();
                            SharedPreferenceUtil.saveString(MainActivity.this, SharedPreferenceUtil.CLIENTID, baseVo.result.getClientId());
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogUtil.e(volleyError.getMessage());
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    @Override
    public void init() {
        if (homePageFragment == null) {
            homePageFragment = HomePageFragment.newInstance();
        }
        switchContent(homePageFragment);
        rbProperty.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (SharedPreferenceUtil.isLogin(MainActivity.this)) {
                    return false;
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    return true;
                }
            }
        });
        rbProperty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public void addListener() {
        rgBottomView.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbHomePage://首页
                        StatusBarUtil.setStatusBar(false, MainActivity.this);
                        if (homePageFragment == null) {
                            homePageFragment = HomePageFragment.newInstance();
                        }
                        homePageFragment.startAuto();
                        checkId = R.id.rbHomePage;
                        switchContent(homePageFragment);
                        if (homePageFragment.baseVos == null) {
                            EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_HOMEPAGE_FIRST));
                        }
                        break;
                    case R.id.rbProject://项目
                        StatusBarUtil.setStatusBar(true, MainActivity.this);
                        if (projectFragment == null) {
                            projectFragment = P2pAndProjectFragment2.newInstance();
                        }
                        homePageFragment.stopAuto();
                        checkId = R.id.rbProject;
                        switchContent(projectFragment);
                        break;
                    case R.id.rbFind://发现
                        StatusBarUtil.setStatusBar(true, MainActivity.this);
                        if (findFragment == null) {
                            findFragment = FindFragment.newInstance();
                        }
                        homePageFragment.stopAuto();
                        checkId = R.id.rbFind;
                        switchContent(findFragment);
                        break;
                    case R.id.rbProperty://资产
                        StatusBarUtil.setStatusBar(false, MainActivity.this);
                        if (propertyFragment == null) {
                            propertyFragment = PropertyFragment.newInstance();
                        }
                        homePageFragment.stopAuto();
                        switchContent(propertyFragment);
                        if (SharedPreferenceUtil.isLogin(MainActivity.this)) {
                            EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROPERTY));
                        }
                        break;
                }
            }
        });
    }

    private void switchContent(Fragment fragment) {
        if (mCurrentFragment == null) {
            mCurrentFragment = fragment;
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.realtabcontent, mCurrentFragment);
            transaction.commitAllowingStateLoss();
        } else {
            if (mCurrentFragment != fragment) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!fragment.isAdded()) { // 先判断是否被add过
                    transaction.hide(mCurrentFragment).add(R.id.realtabcontent, fragment).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mCurrentFragment).show(fragment).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                }

                mCurrentFragment = fragment;
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出米米金服", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                return super.onKeyDown(keyCode, event);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        permissionHelper.onActivityForResult(requestCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {
        Log.i("onPermissionGranted", "Permission(s) " + Arrays.toString(permissionName) + " Granted");
        if (mLocationClient != null) {
            mLocationClient.start();
        }
    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {
        Log.i("onPermissionDeclined", "Permission(s) " + Arrays.toString(permissionName) + " Declined");
    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {
        Log.i("onPermissionPreGranted", "Permission( " + permissionsName + " ) preGranted");
    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {
        Log.i("NeedExplanation", "Permission( " + permissionName + " ) needs Explanation");
    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {
        Log.i("ReallyDeclined", "Permission " + permissionName + " can only be granted from settingsScreen");
        /** you can call  {@link PermissionHelper#openSettingsScreen(Context)} to open the settings screen */
    }

    @Override
    public void onNoPermissionNeeded() {
        Log.i("onNoPermissionNeeded", "Permission(s) not needed");
    }

    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            if (location.getLocType() == 161) {
                Constants.latitude = String.valueOf(location.getLatitude());
                Constants.longitude = String.valueOf(location.getLongitude());
                Constants.city = location.getCity();
                Constants.province = location.getProvince();
            }
            LogUtil.i("BaiduLocationApiDem", sb.toString());
            if (mLocationClient != null) {
                if (mLocationClient.isStarted()) {
                    mLocationClient.stop();
                }
            }
            synchronizClientIdAndJPushRegId();

        }

    }
}
