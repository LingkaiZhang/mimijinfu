package com.nongjinsuo.mimijinfu.task;

/**
 * 
 * @author ty
 * @createdate 2012-6-6 下午2:09:31
 * @Description: 封装对AsyncTack操作，对外提供以下方法
 * @param <Params> 启动任务执行的参数
 * @param <Progress> 后台任务执行的百分比。
 * @param <Result> 后台执行任务最终返回的结果
 */
public interface ITask<Params, Progress, Result> {
	
	/**
	 * 异步操作
	 * @throws Exception
	 */
	public Result execInBackground(Params... params) throws Exception;
	
	/**
	 * 异步操作完成，更新UI
	 */
	public void after(Result result);
	
	/**
	 * 异步操作出现问题，如网络不能连接，数据加载异常...
	 */
	public void exception();
}
