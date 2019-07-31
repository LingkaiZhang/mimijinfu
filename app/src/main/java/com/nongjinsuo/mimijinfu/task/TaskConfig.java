package com.nongjinsuo.mimijinfu.task;

import android.app.Dialog;

public class TaskConfig{
	private boolean isShowDialog=true;//是否弹出对话框
	private boolean isNetRequired=true;//是否必须要联网
	private boolean cancelable = true; //是否能取消
	private boolean isCache = true;//是否缓存
	private boolean showReloadView=true;//是否显示重新加载
	private boolean isTemplateEngine;//是否使用模板引擎，如果使用 就不要混淆对应的实体bean
	private Dialog progressDialog;
	
	public boolean isCancelable() {
		return cancelable;
	}
	public TaskConfig setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
		return this;
	}
	public boolean isCache() {
		return isCache;
	}
	public TaskConfig setCache(boolean isCache) {
		this.isCache = isCache;
		return this;
	}
	public boolean isShowReloadView() {
		return showReloadView;
	}
	public TaskConfig setShowReloadView(boolean showReloadView) {
		this.showReloadView = showReloadView;
		return this;
	}
	public boolean isTemplateEngine() {
		return isTemplateEngine;
	}
	public TaskConfig setTemplateEngine(boolean isTemplateEngine) {
		this.isTemplateEngine = isTemplateEngine;
		return this;
	}
	public boolean isNetRequired() {
		return isNetRequired;
	}
	public TaskConfig setNetRequired(boolean isNetRequired) {
		this.isNetRequired = isNetRequired;
		return this;
	}
	public boolean isShowDialog() {
		return isShowDialog;
	}
	public TaskConfig setShowDialog(boolean isShowDialog) {
		this.isShowDialog = isShowDialog;
		return this;
	}
	public Dialog getProgressDialog() {
		return progressDialog;
	}
	public TaskConfig setProgressDialog(Dialog progressDialog) {
		this.progressDialog = progressDialog;
		return this;
	}
}