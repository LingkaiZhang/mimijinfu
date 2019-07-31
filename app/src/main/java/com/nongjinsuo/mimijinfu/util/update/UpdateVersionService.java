package com.nongjinsuo.mimijinfu.util.update;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.activity.MainActivity;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.dao.http.Urls;
import com.nongjinsuo.mimijinfu.dialog.MyDialog;
import com.nongjinsuo.mimijinfu.util.NetworkUtils;
import com.nongjinsuo.mimijinfu.util.TextUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;



/**
 * 检测安装更新文件的助手类
 * 
 * @author Administrator
 * 
 */
public class UpdateVersionService {
	private static final int DOWN = 1;// 用于区分正在下载
	private static final int DOWN_FINISH = 0;// 用于区分下载完成
	private HashMap<String, String> hashMap;// 存储跟心版本的xml信息
	private String fileSavePath;// 下载新apk的厨房地点
	//private String updateVersionXMLPath;// 检测更新的xml文件
	private int progress;// 获取新apk的下载数据量,更新下载滚动条
	private boolean cancelUpdate = false;// 是否取消下载
	private Context context;
	private ProgressBar progressBar;
	private String myMsg="";
	private boolean must=false;
	private String download;
	private boolean isUploading = true;
	private boolean isAuto;
	public static final String SUFFIX = ".apk";
	private Handler handler = new Handler() {// 更新ui

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch ((Integer) msg.obj) {
			case DOWN:
				progressBar.setProgress(progress);
				break;
			case DOWN_FINISH:
				Toast.makeText(context, "文件下载完成,正在安装更新", Toast.LENGTH_SHORT).show();
				((Activity)context).finish();
				if (!isAuto){
					MainActivity.getActivity().finish();
				}
				installAPK();
				break;

			default:
				break;
			}
		}

	};

	/**
	 * 构造方法
	 *            比较版本的xml文件地址(服务器上的)
	 * @param context
	 *            上下文
	 */
	public UpdateVersionService( Context context,String s,boolean must,String download) {
		super();
		//this.updateVersionXMLPath = updateVersionXMLPath;
		this.context = context;
		this.myMsg=s;
		this.must=must;
		this.download = download;
	}

	/**
	 * 检测是否可更新
	 * 
	 * @return
	 */
	public void checkUpdate(String code,String versionName,boolean isAuto) {
		if (isUpdate(code)) {
			if(must)
			{
				mustShowUpdateVersionDialog(versionName,isAuto);
			}
			else{
				showUpdateVersionDialog(versionName);// 显示提示对话框
			}
			
		} else {
			if (!isAuto)
				Toast.makeText(context, "你的米米金服已经是最新版本", Toast.LENGTH_SHORT).show();
		}
	}
	/**
	 * 更新提示框
	 */
	private void showUpdateVersionDialog(final String versionName) {
		// 构造对话框
		MyDialog.Builder builder = new MyDialog.Builder(context);
		builder.setTitle("版本更新");
		builder.setMessage(myMsg);
		builder.setNewVersion(versionName);
		// 更新
		builder.setPositiveButton("立即更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				if (!canDownloadState()) {
					Toast.makeText(context, "下载服务不能用,请您启用", Toast.LENGTH_SHORT).show();
					showDownloadSetting();
					return;
				}
				Toast.makeText(context, "下拉通知栏中查看升级", Toast.LENGTH_LONG).show();
				ApkUpdateUtils.download(context, Urls.PROJECT_URL+download, context.getResources().getString(R.string.app_name)+"V"+versionName);
				ApkUpdateUtils.ApkInstallReceiver apkInstallReceiver = new ApkUpdateUtils.ApkInstallReceiver();
				IntentFilter intent = new IntentFilter();
				intent.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
				context.registerReceiver(apkInstallReceiver,intent);

			}
		});
		// 稍后更新
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
	}

	private void showDownloadSetting() {
		String packageName = "com.android.providers.downloads";
		Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.setData(Uri.parse("package:" + packageName));
		if (intentAvailable(intent)) {
			context.startActivity(intent);
		}
	}
	private boolean intentAvailable(Intent intent) {
		PackageManager packageManager = context.getPackageManager();
		List list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
	private boolean canDownloadState() {
		try {
			int state = context.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");

			if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
					|| state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
					|| state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private void mustShowUpdateVersionDialog(String versionName, final boolean isAuto) {
		this.isAuto = isAuto;
		// 构造对话框
		final MyDialog.Builder builder = new MyDialog.Builder(context);
		builder.setTitle("版本更新");
		builder.setMessage(myMsg);
		builder.setNewVersion(versionName);
		// 更新
		builder.setPositiveButton("立即更新", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (!NetworkUtils.isNetworkAvailable(context)){
					Toast.makeText(context,context.getString(R.string.str_no_network),Toast.LENGTH_SHORT).show();
					return;
				}
				// 显示下载对话框
				if(isUploading){
					isUploading = false;
					builder.getProgressBar().setVisibility(View.VISIBLE);
					builder.btnPositive.setText("正在下载");
					builder.btnPositive.setTextColor(context.getResources().getColor(R.color.dialog_message_color));
					showDownloadDialog(builder);
				}
			}
		});
		MyDialog myDialog = builder.create();
		// 稍后更新
		myDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                    ((Activity)context).finish();
				if (!isAuto){
					MainActivity.getActivity().finish();
				}
            }
        });

		myDialog.show();
	}
	/**
	 * 下载的提示框
	 */
	protected void showDownloadDialog(MyDialog.Builder dialog) {
		{
			progressBar =dialog.getProgressBar();
			// 现在文件
			downloadApk();
		}

	}

	/**
	 * 下载apk,不能占用主线程.所以另开的线程
	 */
	private void downloadApk() {
		new downloadApkThread().start();

	}

	/**
	 * 判断是否可更新
	 * 
	 * @return
	 */
	private boolean isUpdate(String newVersionCode) {
		int versionCode = getVersionCode(context);
		//try {
			// 把version.xml放到网络上，然后获取文件信息
			//URL url = new URL(updateVersionXMLPath);
			//HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//conn.setReadTimeout(5 * 1000);
			//conn.setRequestMethod("GET");// 必须要大写
			//InputStream inputStream = conn.getInputStream();
			// 解析XML文件。
			//ParseXmlService service = new ParseXmlService();
		hashMap = new HashMap();
		hashMap.put("versionCode", newVersionCode);
		hashMap.put("fileName", context.getString(R.string.app_name));
		hashMap.put("loadUrl", Urls.PROJECT_URL+download);
		if (null != hashMap) {
			int serverCode;
			if (TextUtil.IsNotEmpty(hashMap.get("versionCode"))){
				serverCode = Integer.valueOf(hashMap.get("versionCode"));
			}else{
				serverCode =0;
			}
			// 版本判断
			if (serverCode > versionCode) {
				//Toast.makeText(context, "新版本是: " + serverCode, Toast.LENGTH_SHORT).show();
				return true;
			}
		}
		return false;

	}

	/**
	 * 获取当前版本和服务器版本.如果服务器版本高于本地安装的版本.就更新
	 * 
	 * @param context2
	 * @return
	 */
	public static int getVersionCode(Context context2) {
		try {
			return context2.getPackageManager().getPackageInfo(context2.getPackageName(), 0).versionCode;
		}catch(PackageManager.NameNotFoundException e){
			return 0;
		}

	}

	/**
	 * 安装apk文件
	 */
	private void installAPK() {
		File apkfile = new File(fileSavePath, hashMap.get("fileName") + SUFFIX);
		if (!apkfile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
//		Intent i = new Intent(Intent.ACTION_VIEW);
//		System.out.println("filepath=" + apkfile.toString() + "  " + apkfile.getPath());
//		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
//		context.startActivity(i);
//		android.os.Process.killProcess(android.os.Process.myPid());// 如果不加上这句的话在apk安装完成之后点击单开会崩溃


		Intent intent = new Intent(Intent.ACTION_VIEW);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(AiMiCrowdFundingApplication.context(), "com.nongjinsuo.mimijinfu.fileprovider", apkfile);
			intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
		} else {
			intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		if (AiMiCrowdFundingApplication.context().getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
			AiMiCrowdFundingApplication.context().startActivity(intent);
		}
	}
	/**
	 * 获取app的根目录
	 *
	 * @return 文件缓存根路径
	 */
	public static String getDiskCacheRootDir() {
		File diskRootFile;
		if (existsSdcard()) {
			diskRootFile = AiMiCrowdFundingApplication.context().getExternalCacheDir();
		} else {
			diskRootFile = AiMiCrowdFundingApplication.context().getCacheDir();
		}
		String cachePath;
		if (diskRootFile != null) {
			cachePath = diskRootFile.getPath();
		} else {
			throw new IllegalArgumentException("disk is invalid");
		}
		return cachePath;
	}
	/**
	 * 判断外置sdcard是否可以正常使用
	 *
	 * @return
	 */
	public static Boolean existsSdcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable();
	}
	/**
	 * 下载apk的方法
	 * 
	 * @author rongsheng
	 * 
	 */
	public class downloadApkThread extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
					// 获得存储卡的路径
					String sdpath = getDiskCacheRootDir() + "/";
					fileSavePath = sdpath + "download";
					URL url = new URL(hashMap.get("loadUrl"));
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(10 * 1000);// 设置超时时间
					conn.setRequestMethod("GET");
					conn.setRequestProperty("Charser", "GBK,utf-8;q=0.7,*;q=0.3");
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					File file = new File(fileSavePath);
					// 判断文件目录是否存在
					if (!file.exists()) {
						file.mkdir();
					}
					File apkFile = new File(fileSavePath, hashMap.get("fileName") + SUFFIX);
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						Message message = new Message();
						message.obj = DOWN;
						handler.sendMessage(message);
						if (numread <= 0) {
							// 下载完成
							// 取消下载对话框显示
							Message message2 = new Message();
							message2.obj = DOWN_FINISH;
							handler.sendMessage(message2);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}

