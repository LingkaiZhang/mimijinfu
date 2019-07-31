package com.nongjinsuo.mimijinfu.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bigkoo.pickerview.OptionsPickerView;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.beans.BankVo;
import com.nongjinsuo.mimijinfu.beans.ProvinceBean;
import com.nongjinsuo.mimijinfu.beans.YanZhengVo;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.config.Constants;
import com.nongjinsuo.mimijinfu.dao.http.JacksonRequest;
import com.nongjinsuo.mimijinfu.dao.http.RequestMap;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.httpmodel.JianCeBank;
import com.nongjinsuo.mimijinfu.httpmodel.UserverificationModel;
import com.nongjinsuo.mimijinfu.httpmodel.YanZhengModel;
import com.nongjinsuo.mimijinfu.util.JsonFileReader;
import com.nongjinsuo.mimijinfu.util.LogUtil;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.TextUtil;
import com.nongjinsuo.mimijinfu.util.ValidUtil;
import com.nongjinsuo.mimijinfu.widget.EventBarEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * @author czz
 * @Description: (账户设置)
 */
public class AccountSettingActivity extends AbstractActivity {

    //  省份
    ArrayList<ProvinceBean> provinceBeanList = new ArrayList<>();
    //  城市
    ArrayList<String> cities;
    ArrayList<List<String>> cityList = new ArrayList<>();
    //  区/县
    ArrayList<String> district;
    ArrayList<List<String>> districts;
    //    ArrayList<List<List<String>>> districtList = new ArrayList<>();
    OptionsPickerView pvOptions;
    @BindView(R.id.ivStatus)
    ImageView ivStatus;
    @BindView(R.id.llStatus)
    LinearLayout llStatus;
    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.tvNext)
    TextView tvNext;

    private View inflate;
    private TextView view1EtName;
    private TextView view1EtIDcard;

    private TextView view2TvName;
    private EditText view2EtBankCard;
    private TextView view2TvFkBank;
    private TextView view2TvKhd;
    private EditText view2EtPhone;
    private EditText view2EtCode;

    private TextView view3TvTishi;
    private int status;
    private boolean selectFkyh = false;
    private boolean selectKhd = false;
    private String province;
    private String city;
    private TextView tvHqCode;
    private View progressSmall;
    private int recLen = 60;
    public static final int SELECT_BANK = 3;
    public static final int UPDATE_SETPWD = 4;
    private BankVo bankVo;
    private YanZhengVo yanZheng;
    private UserverificationModel.Verification verification;
    public static final String IS_KAI_HU = "isKaiHu";
    private boolean isKaiHu;

    @Override
    public void init() {
        isKaiHu = getIntent().getBooleanExtra(IS_KAI_HU, true);
        if (isKaiHu) {
            llStatus.setVisibility(View.VISIBLE);
            titleView.setText("账户设置");
            templateTextViewRight.setText("联系客服");
            templateTextViewRight.setVisibility(View.VISIBLE);
        } else {
            titleView.setText("绑定银行卡");
            llStatus.setVisibility(View.GONE);
        }
        initCity();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AiMiCrowdFundingApplication.context().cancelPendingRequests(getClass().getName());
    }

    private void loadData() {
        showLoading();
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.getUserverification(1), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                cancleLoading();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    verification = baseVo.result;
                    status = baseVo.result.getVerificationstatus();
                    setStatusView(baseVo.result);
                } else {
                    showShortToastMessage(baseVo.getText());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                cancleLoading();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    private void initCity() {
        //  创建选项选择器
        pvOptions = new OptionsPickerView(this);
        //  获取json数据
        String province_data_json = JsonFileReader.getJson(this, "province_data.json");
        //  解析json数据
        parseJson(province_data_json);

        //  设置三级联动效果
        pvOptions.setPicker(provinceBeanList, cityList, true);


        //  设置选择的三级单位
        //pvOptions.setLabels("省", "市", "区");
        //pvOptions.setTitle("选择城市");

        //  设置是否循环滚动
        pvOptions.setCyclic(false, false, false);
        pvOptions.setCancelable(true);

        // 设置默认选中的三级项目
        pvOptions.setSelectOptions(0, 0, 0);
        //  监听确定选择按钮
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String cityStr = provinceBeanList.get(options1).getPickerViewText();
                String address;
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京市".equals(cityStr) || "上海市".equals(cityStr) || "天津市".equals(cityStr) || "重庆市".equals(cityStr) || "澳门".equals(cityStr) || "香港".equals(cityStr)) {
                    address = provinceBeanList.get(options1).getPickerViewText();
                    province = address;
                    city = address;
                } else {
                    address = provinceBeanList.get(options1).getPickerViewText()
                            + " " + cityList.get(options1).get(option2);

                    province = provinceBeanList.get(options1).getPickerViewText();
                    city = cityList.get(options1).get(option2);
                }
                selectKhd = true;
                view2TvKhd.setText(address);
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (pvOptions != null && pvOptions.isShowing()) {
                pvOptions.dismiss();
                return true;
            }
            if (isKaiHu) {
                showExitDialog();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setStatusView(UserverificationModel.Verification verification) {
        if (frameLayout != null) {
            frameLayout.removeAllViews();
            switch (verification.getVerificationstatus()) {
                case Constants.TYPE_SMRZ:
                    tvNext.setText("下一步");
                    ivStatus.setImageResource(R.drawable.img_aa);
                    inflate = View.inflate(AccountSettingActivity.this, R.layout.view_1_smrz, null);
                    frameLayout.addView(inflate);
                    view1EtName = (TextView) inflate.findViewById(R.id.view1_etName);
                    view1EtIDcard = (TextView) inflate.findViewById(R.id.view1_etIDcard);
                    break;
                case Constants.TYPE_BYHK:
                case Constants.TYPE_JBK:
                    if (isKaiHu) {
                        tvNext.setText("下一步");
                    } else {
                        tvNext.setText("绑定银行卡");
                    }
                    ivStatus.setImageResource(R.drawable.img_ab);
                    inflate = View.inflate(AccountSettingActivity.this, R.layout.view_2_byhk, null);
                    view2TvName = (TextView) inflate.findViewById(R.id.view2_tvName);
                    view2EtBankCard = (EditText) inflate.findViewById(R.id.view2_etBankCard);
                    view2TvFkBank = (TextView) inflate.findViewById(R.id.view2_tvFkBank);
                    view2TvKhd = (TextView) inflate.findViewById(R.id.view2_tvKhd);
                    view2EtPhone = (EditText) inflate.findViewById(R.id.view2_etPhone);
                    view2EtCode = (EditText) inflate.findViewById(R.id.view2_etCode);
                    tvHqCode = (TextView) inflate.findViewById(R.id.tvHqCode);
                    progressSmall = inflate.findViewById(R.id.progressSmall);
                    view2TvName.setText(verification.getIdentityName());
                    inflate.findViewById(R.id.view2_llFkyh).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String bankCard = view2EtBankCard.getText().toString();
                            if (bankCard.length() < 16) {
                                showShortToastMessage("请输入正确的银行卡号");
                                return;
                            }
                            Intent intent = new Intent(AccountSettingActivity.this, CanBindBankCardActivity.class);
                            startActivityForResult(intent, SELECT_BANK);
                        }
                    });
                    inflate.findViewById(R.id.llKhd).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //隐藏输入法
                            if (getCurrentFocus() != null && getCurrentFocus().getApplicationWindowToken() != null) {
                                InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            }
                            pvOptions.show();
                        }
                    });
                    view2EtBankCard.addTextChangedListener(new TextWatcher() {
                        int beforeTextLength = 0;
                        int onTextLength = 0;
                        boolean isChanged = false;

                        int location = 0;// 记录光标的位置
                        private char[] tempChar;
                        private StringBuffer buffer = new StringBuffer();
                        int konggeNumberB = 0;

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            onTextLength = s.length();
                            buffer.append(s.toString());
                            if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                                isChanged = false;
                                return;
                            }
                            isChanged = true;
                        }

                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            beforeTextLength = s.length();
                            if (buffer.length() > 0) {
                                buffer.delete(0, buffer.length());
                            }
                            konggeNumberB = 0;
                            for (int i = 0; i < s.length(); i++) {
                                if (s.charAt(i) == ' ') {
                                    konggeNumberB++;
                                }
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (isChanged) {
                                location = view2EtBankCard.getSelectionEnd();
                                int index = 0;
                                while (index < buffer.length()) {
                                    if (buffer.charAt(index) == ' ') {
                                        buffer.deleteCharAt(index);
                                    } else {
                                        index++;
                                    }
                                }

                                index = 0;
                                int konggeNumberC = 0;
                                while (index < buffer.length()) {
                                    // if (index % 5 == 4) {
                                    //      buffer.insert(index, ' ');
                                    //      konggeNumberC++;
                                    // }
                                    if (index == 4 || index == 9 || index == 14 || index == 19) {
                                        buffer.insert(index, ' ');
                                        konggeNumberC++;
                                    }
                                    index++;
                                }

                                if (konggeNumberC > konggeNumberB) {
                                    location += (konggeNumberC - konggeNumberB);
                                }

                                tempChar = new char[buffer.length()];
                                buffer.getChars(0, buffer.length(), tempChar, 0);
                                String str = buffer.toString();
                                if (location > str.length()) {
                                    location = str.length();
                                } else if (location < 0) {
                                    location = 0;
                                }

                                view2EtBankCard.setText(str);
                                Editable etable = view2EtBankCard.getText();
                                Selection.setSelection(etable, location);
                                isChanged = false;

                            }
                            String bankCard = s.toString().replaceAll(" ", "").trim();
                            if (bankCard.toString().length() > 5 && bankCard.toString().length() < 9) {
                                jianceBank(bankCard.toString());
                            } else if (bankCard.toString().length() < 6) {
                                view2TvFkBank.setText("");
                                selectFkyh = false;
                            }
                        }
                    });
                    tvHqCode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String bankCard = view2EtBankCard.getText().toString().replaceAll(" ", "").trim();
                            if (bankCard.length() < 16) {
                                showShortToastMessage("请输入正确的银行卡号");
                                return;
                            }
                            if (!selectFkyh) {
                                showShortToastMessage("请选择发卡银行");
                                return;
                            }
                            if (!selectKhd) {
                                showShortToastMessage("请选择开户地");
                                return;
                            }
                            String phone = view2EtPhone.getText().toString();
                            if (TextUtil.IsEmpty(phone)) {
                                showShortToastMessage("请输入银行预留手机号");
                                return;
                            }
                            String validPhone = ValidUtil.validPhone(phone);
                            if (TextUtil.IsNotEmpty(validPhone)) {
                                showShortToastMessage(validPhone);
                                return;
                            }
                            getCode(bankVo.getBankName(), province, city, bankCard, phone);
                        }
                    });
                    frameLayout.addView(inflate);
                    break;
                case Constants.TYPE_ZFMM:
                    ivStatus.setImageResource(R.drawable.img_ac);
                    tvNext.setText("前往开户");
                    inflate = View.inflate(AccountSettingActivity.this, R.layout.view_3_xltg, null);
                    frameLayout.addView(inflate);
                    view3TvTishi = (TextView) inflate.findViewById(R.id.view3_tvTishi);
                    view3TvTishi.setText("米米金服由新浪支付提供资金安全保障");
                    break;
                case Constants.TYPE_SYKF:
                    ivStatus.setImageResource(R.drawable.img_ac);
                    tvNext.setText("开启委托代扣");
                    inflate = View.inflate(AccountSettingActivity.this, R.layout.view_3_xltg, null);
                    frameLayout.addView(inflate);
                    view3TvTishi = (TextView) inflate.findViewById(R.id.view3_tvTishi);
                    view3TvTishi.setText("开启委托代扣使投资更快更简单!");
                    break;
                case Constants.TYPE_KHCG:
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROPERTY));//开户成功 更新我的页面
                    tvNext.setText("去投资");
                    ivStatus.setImageResource(R.drawable.img_ad);
                    inflate = View.inflate(AccountSettingActivity.this, R.layout.view_4_khcg, null);
                    frameLayout.addView(inflate);
                    break;
            }
        }
    }

    private void haveTime() {
        tvHqCode.setClickable(false);
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { // UI thread
                    public void run() {
                        recLen--;
                        progressSmall.setVisibility(View.GONE);
                        tvHqCode.setVisibility(View.VISIBLE);
                        tvHqCode.setText(recLen + "秒");
                        if (recLen < 1) {
                            timer.cancel();
                            tvHqCode.setClickable(true);
                            recLen = 60;
                            tvHqCode.setText("重新发送");
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    //  解析json填充集合
    public void parseJson(String str) {
        try {
            //  获取json中的数组
            JSONArray jsonArray = new JSONArray(str);
            //  遍历数据组
            for (int i = 0; i < jsonArray.length(); i++) {
                //  获取省份的对象
                JSONObject provinceObject = jsonArray.optJSONObject(i);
                //  获取省份名称放入集合
                String provinceName = provinceObject.getString("name");
                provinceBeanList.add(new ProvinceBean(provinceName));
                //  获取城市数组
                JSONArray cityArray = provinceObject.optJSONArray("city");
                cities = new ArrayList<>();//   声明存放城市的集合
                districts = new ArrayList<>();//声明存放区县集合的集合
                //  遍历城市数组
                for (int j = 0; j < cityArray.length(); j++) {
                    //  获取城市对象
                    JSONObject cityObject = cityArray.optJSONObject(j);
                    //  将城市放入集合
                    String cityName = cityObject.optString("name");
                    cities.add(cityName);
                    district = new ArrayList<>();// 声明存放区县的集合
                    //  获取区县的数组
                    JSONArray areaArray = cityObject.optJSONArray("area");
                    //  遍历区县数组，获取到区县名称并放入集合
                    for (int k = 0; k < areaArray.length(); k++) {
                        String areaName = areaArray.getString(k);
                        district.add(areaName);
                    }
                    //  将区县的集合放入集合
                    districts.add(district);
                }
                //  将存放区县集合的集合放入集合
//                districtList.add(districts);
                //  将存放城市的集合放入集合
                cityList.add(cities);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addListener() {
        templateTextViewRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountSettingActivity.this, WebViewAndJsActivity.class);
                intent.putExtra(WebViewAndJsActivity.URL, Urls.ZXKF);
                intent.putExtra(WebViewAndJsActivity.NOZXKF, false);
                startActivity(intent);
            }
        });
        templateButtonLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isKaiHu)
                    showExitDialog();
                else
                    finish();
            }
        });
    }

    private void showExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AccountSettingActivity.this);
        builder.setMessage("您正在开户流程中,是否退出");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        init();
        addListener();
    }

    @OnClick(R.id.tvNext)
    public void onClick() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showShortToastMessage(getString(R.string.str_no_network));
            return;
        }
        switch (status) {
            case Constants.TYPE_SMRZ:
                certification();
                break;
            case Constants.TYPE_BYHK:
            case Constants.TYPE_JBK:
                bindBank();
                break;
//            case Constants.TYPE_SYKF:
            case Constants.TYPE_ZFMM:
                if (verification != null) {
                    Intent intent = new Intent(AccountSettingActivity.this, SetSinaPwdWebViewActivity.class);
                    intent.putExtra(SetSinaPwdWebViewActivity.URL, verification.getRedirect_url());
                    startActivityForResult(intent, UPDATE_SETPWD);
                }
                break;
            case Constants.TYPE_KHCG:
                finish();
                EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_LOGOUT_VIEW));
                EventBus.getDefault().post(new EventBarEntity(EventBarEntity.FINISH_ACTIVITY));
                break;
        }
    }

    private void bindBank() {
        String bankCard = view2EtBankCard.getText().toString().replaceAll(" ", "").trim();
        if (bankCard.length() < 15) {
            showShortToastMessage("请输入正确的银行卡号");
            return;
        }
        if (!selectFkyh) {
            showShortToastMessage("请选择发卡银行");
            return;
        }
        if (!selectKhd) {
            showShortToastMessage("请选择开户地");
            return;
        }
        String phone = view2EtPhone.getText().toString();
        if (TextUtil.IsEmpty(phone)) {
            showShortToastMessage("请输入银行预留手机号");
            return;
        }
        String validPhone = ValidUtil.validPhone(phone);
        if (TextUtil.IsNotEmpty(validPhone)) {
            showShortToastMessage(validPhone);
            return;
        }
        String code = view2EtCode.getText().toString();
        if (code.length() < 6) {
            showShortToastMessage("请输入正确的验证码");
            return;
        }
        if (bankVo != null && yanZheng != null) {
            bindBank(bankVo.getBankName(), province, city, bankCard, phone, yanZheng.getTicket(), code);
        } else if (yanZheng == null) {
            showShortToastMessage("重新获取验证码验证绑卡人身份");
        } else {
            showShortToastMessage("银行卡输入有误");
        }
    }

    /**
     * 实名认证
     */
    private void certification() {
        if (view1EtName == null)
            return;
        String name = view1EtName.getText().toString().trim();
        if (TextUtil.IsEmpty(name)) {
            showShortToastMessage("请输入姓名");
            return;
        }
        String idCard = view1EtIDcard.getText().toString().trim();
        String validUserIdCode = ValidUtil.validUserIdCode(idCard);
        if (TextUtil.IsNotEmpty(validUserIdCode)) {
            showShortToastMessage(validUserIdCode);
            return;
        }
        idCard = idCard.replaceAll("x", "X");
        smrz(name, idCard);
    }

    /**
     * 实名认证
     *
     * @param name
     * @param idCard
     */
    private void smrz(String name, String idCard) {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(this, "实名认证");
        myProgressDialog.show();
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.getCertification(name, idCard), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                myProgressDialog.dismiss();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    verification = baseVo.result;
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_ANQUAN_SETTING));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.LOGIN_UPDATE_WEB));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROPERTY));
                    status = baseVo.result.getVerificationstatus();
                    setStatusView(baseVo.result);
                } else {
                    showShortToastMessage(baseVo.getText());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                myProgressDialog.dismiss();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    /**
     * 检测银行卡
     *
     * @param idCard
     */
    private void jianceBank(String idCard) {
        JacksonRequest<JianCeBank> jacksonRequest = new JacksonRequest<>(RequestMap.getBank(idCard), JianCeBank.class, new Response.Listener<JianCeBank>() {
            @Override
            public void onResponse(JianCeBank baseVo) {
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    bankVo = baseVo.result;
                    selectFkyh = true;
                    view2TvFkBank.setText(baseVo.result.getBankName());
                } else {
                    view2TvFkBank.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    /**
     * 验证绑卡人身份
     */
    public void getCode(String bankName, String province, String city, String cardNo, String mobile) {
        progressSmall.setVisibility(View.VISIBLE);
        tvHqCode.setVisibility(View.INVISIBLE);
        JacksonRequest<YanZhengModel> jacksonRequest = new JacksonRequest<>(RequestMap.getIsbankidentity(bankName, province, city, cardNo, mobile), YanZhengModel.class, new Response.Listener<YanZhengModel>() {
            @Override
            public void onResponse(YanZhengModel baseVo) {
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    yanZheng = baseVo.result;
                    haveTime();
                } else {
                    progressSmall.setVisibility(View.GONE);
                    tvHqCode.setVisibility(View.VISIBLE);
                    showShortToastMessage(baseVo.getText());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                progressSmall.setVisibility(View.GONE);
                tvHqCode.setVisibility(View.VISIBLE);
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    /**
     * 绑定银行卡
     */
    public void bindBank(String bankName, String province, String city, String cardNo, String mobile, String ticket, String valid_code) {
        final MyProgressDialog myProgressDialog = new MyProgressDialog(this, "绑定银行卡");
        myProgressDialog.show();
        JacksonRequest<UserverificationModel> jacksonRequest = new JacksonRequest<>(RequestMap.getBindbank(bankName, province, city, cardNo, mobile, ticket, valid_code), UserverificationModel.class, new Response.Listener<UserverificationModel>() {
            @Override
            public void onResponse(UserverificationModel baseVo) {
                myProgressDialog.dismiss();
                if (baseVo.code.equals(Constants.SUCCESS)) {
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_ANQUAN_SETTING));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_PROPERTY));
                    EventBus.getDefault().post(new EventBarEntity(EventBarEntity.LOGIN_UPDATE_WEB));
                    if (isKaiHu) {
                        verification = baseVo.result;
                        status = baseVo.result.getVerificationstatus();
                        setStatusView(baseVo.result);
                    } else {
                        EventBus.getDefault().post(new EventBarEntity(EventBarEntity.LOGIN_UPDATE_WEB));
                        EventBus.getDefault().post(new EventBarEntity(EventBarEntity.UPDATE_SETTING));


                        finish();
                        showShortToastMessage(baseVo.getText());
                    }
                } else {
                    showShortToastMessage(baseVo.getText());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                myProgressDialog.dismiss();
                if (volleyError != null && volleyError.getCause() != null) {
                    LogUtil.d("volleyError", volleyError.getCause().getMessage());
                }
            }
        });
        jacksonRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 1, 1.0f));
        AiMiCrowdFundingApplication.context().addToRequestQueue(jacksonRequest, getClass().getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == SELECT_BANK) {
            if (data != null) {
                selectFkyh = true;
                bankVo = (BankVo) data.getSerializableExtra(CanBindBankCardActivity.BANK_VO);
                view2TvFkBank.setText(bankVo.getBankName());
            }
        } else if (requestCode == UPDATE_SETPWD) {
            if (data != null) {
                UserverificationModel.Verification verification = (UserverificationModel.Verification) data.getSerializableExtra(SetSinaPwdWebViewActivity.VERIFICATION);
                status = verification.getVerificationstatus();
                setStatusView(verification);
            }
        }
    }
}
