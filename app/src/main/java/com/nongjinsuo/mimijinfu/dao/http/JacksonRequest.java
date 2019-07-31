package com.nongjinsuo.mimijinfu.dao.http;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nongjinsuo.mimijinfu.util.LogUtil;

import java.util.Iterator;
import java.util.Map;

public class JacksonRequest<T> extends Request<T> {

    private final Response.Listener<T> mListener;


    private Class<T> mClass;


    private Map<String, String> mParams;//post Params

    private Gson gson;

    public JacksonRequest(Map<String, String> params, Class<T> clazz, Listener<T> listener,
                          Response.ErrorListener errorListener) {
        super(Method.POST, Urls.url, errorListener);
        mClass = clazz;
        mListener = listener;
        mParams = params;
        gson = new GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调教格式
                .disableHtmlEscaping() //默认是GSON把HTML 转义的
                .create();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (LogUtil.DEBUG) {
            if (mParams != null) {
                StringBuffer sb = new StringBuffer();
                Iterator<Map.Entry<String, String>> it = mParams.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, String> entry = it.next();
                    sb.append(entry.getKey() + "=" + entry.getValue() + ";");
                }
                LogUtil.e("volly", Urls.PROJECT_URL+"请求数据(" + mParams.get(RequestMap.INTERFACE) + ")：" + sb.toString());
            }
        }
        return mParams;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            LogUtil.e("volly",  Urls.PROJECT_URL+"响应数据(" +mParams.get(RequestMap.INTERFACE)+ ")：" + jsonString);
            return  Response.success(gson.fromJson(jsonString, mClass), null);
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

}