package com.nongjinsuo.mimijinfu.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;
import com.nongjinsuo.mimijinfu.R;
import com.nongjinsuo.mimijinfu.config.AiMiCrowdFundingApplication;
import com.nongjinsuo.mimijinfu.dialog.MyProgressDialog;
import com.nongjinsuo.mimijinfu.util.LogUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author czz
 * @Description: (PDF查看器)
 */
public class PDFViewActivity extends AbstractActivity implements OnPageChangeListener , OnLoadCompleteListener {

    public static final String URL = "url";
    public static final String PROJECTBM_AND_USERID = "projectbm_and_userid";
    @BindView(R.id.pdfView)
    PDFView pdfView;
    private MyProgressDialog myProgressDialog;
    private String fileSavePath;//PDF文件路径
    private File pdfFile;
    private boolean downloading = false;
    private static final int PDF_DOWNLOAD_SUCCESS = 2;
    private boolean cancelUpdate = false;// 是否取消下载
    Integer pageNumber = 0;
    private static final String TAG = PDFViewActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pdf_view);
        ButterKnife.bind(this);
        titleView.setText("合同详情");
        init();
        addListener();
    }

    @Override
    public void init() {
        String pdfurl = getIntent().getStringExtra(URL);
        String pdfName = getIntent().getStringExtra(PROJECTBM_AND_USERID);
        DownloadPDFThread downloadPDFThread = new DownloadPDFThread(pdfurl, pdfName);
        downloadPDFThread.start();
        showProgress();
    }

    /**
     * 显示对话框
     */
    private void showProgress() {
        myProgressDialog = new MyProgressDialog(PDFViewActivity.this, "加载合同");
        myProgressDialog.show();
        cancelUpdate = false;
        myProgressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (downloading) {
                    cancelUpdate = true;
                    if (pdfFile != null && pdfFile.exists()) {
                        pdfFile.delete();
                    }
                }
            }
        });
    }

    @Override
    public void addListener() {

    }
    private void displayFromFile(File file) {
        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAnnotationRendering(false)
                .defaultPage(pageNumber)
                 .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }
    /**
     * 关闭等待框
     */
    private void hideProgress() {
        myProgressDialog.dismiss();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {

    }

    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        LogUtil.e(TAG, "title = " + meta.getTitle());
        LogUtil.e(TAG, "author = " + meta.getAuthor());
        LogUtil.e(TAG, "subject = " + meta.getSubject());
        LogUtil.e(TAG, "keywords = " + meta.getKeywords());
        LogUtil.e(TAG, "creator = " + meta.getCreator());
        LogUtil.e(TAG, "producer = " + meta.getProducer());
        LogUtil.e(TAG, "creationDate = " + meta.getCreationDate());
        LogUtil.e(TAG, "modDate = " + meta.getModDate());

        printBookmarksTree(pdfView.getTableOfContents(), "-");
    }
    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            LogUtil.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }
    /**
     * 下载apk的方法
     *
     * @author rongsheng
     */
    public class DownloadPDFThread extends Thread {
        private String pdfurl;
        private String pdfName;

        public DownloadPDFThread(String pdfurl, String pdfName) {
            this.pdfName = pdfName;
            this.pdfurl = pdfurl;
        }

        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            try {
                // 获得存储卡的路径
                String sdpath = getDiskCacheRootDir() + "/";
                fileSavePath = sdpath + "pdf";
                java.net.URL url = new URL(pdfurl);
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
                pdfFile = new File(fileSavePath, pdfName + ".pdf");
                if (pdfFile.isFile()) {
                    downloading = false;
                    Message message1 = new Message();
                    message1.obj = pdfFile;
                    message1.what = PDF_DOWNLOAD_SUCCESS;
                    handler.sendMessage(message1);
                    return;
                }
                downloading = true;
                FileOutputStream fos = new FileOutputStream(pdfFile);
                int count = 0;
                // 缓存
                byte buf[] = new byte[1024];
                // 写入到文件中
                do {
                    int numread = is.read(buf);
                    count += numread;
                    // 计算进度条位置
                    // 更新进度
                    if (numread <= 0) {
                        // 下载完成
                        // 取消下载对话框显示
                        Message message1 = new Message();
                        message1.obj = pdfFile;
                        message1.what = PDF_DOWNLOAD_SUCCESS;
                        handler.sendMessage(message1);
                        downloading = false;
                        break;
                    }
                    // 写入文件
                    fos.write(buf, 0, numread);
                } while (!cancelUpdate);// 点击取消就停止下载.
                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
                if (pdfFile != null && pdfFile.exists()) {
                    pdfFile.delete();
                }
            }

        }

    }

    private Handler handler = new Handler() {// 更新ui

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PDF_DOWNLOAD_SUCCESS:
                    hideProgress();
                    File file = (File) msg.obj;
                    displayFromFile(file);
                    break;
            }
        }

    };

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


}
