package com.tiilii.rtc.ui.learn.content.write;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.googlecode.tesseract.android.TessBaseAPI;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
import com.tiilii.rtc.model.TextRecogResult;
import com.tiilii.rtc.recognizetext.RecognizeService;
import com.tiilii.rtc.widget.SignatureView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author fox
 * @since 2017/11/20
 */

public class WriteFragment extends BaseFragment implements WriteContract.View, SignatureView.OnSavePictureListener {

    @BindView(R.id.sv_write)
    SignatureView writeSignatureView;

    @Inject
    WriteContract.Presenter mPresenter;

    Gson gson;

    KProgressHUD mKProgressHUD;

    @Inject
    public WriteFragment() {
        mPresenter = new WritePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_write, container, false);
        ButterKnife.bind(this, root);

        mKProgressHUD = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true);
        writeSignatureView.setOnSavePictureListener(this);

        gson = new Gson();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.bindView(this);
    }

    @OnClick(R.id.btn_finish)
    void finishWrite() {

        mKProgressHUD.setLabel("保存中...")
                .show();
        writeSignatureView.setTouchable(false);
        writeSignatureView.saveBitmap();
    }

    @OnClick(R.id.btn_recognize)
    void recognize() {

        mKProgressHUD.setLabel("识别中...")
                .show();

        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Files/";
        String fileName = "img_" + "test" + ".jpeg";

        Log.e("WriteFragment",filePath);

        File file = new File(filePath + fileName);

        if (file.exists() == false) {
            finishWrite();
        }

        rec();

//        RecognizeService.recGeneral(mContext,filePath + fileName,
//                new RecognizeService.ServiceListener() {
//                    @Override
//                    public void onResult(String result) {
//                        mKProgressHUD.dismiss();
//
//                        Log.e("wer",result);
//
//                        TextRecogResult recogResult = null;
//
//                        try {
//                            recogResult = gson.fromJson(result, TextRecogResult.class);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//                        System.out.println("result = " + result);
//
//                        final String content;
//                        // 多个字
//                        // 识别失败
//                        if (recogResult == null || recogResult.getWords_result_num() == 0) {
//
//                            content = "识别失败";
//                        } else {
//                            String recognizeTexts = "";
//                            for (TextRecogResult.Word word : recogResult.getWords_result()) {
//
//                                recognizeTexts = recognizeTexts + word.getWords() + "，";
//                            }
//                            content = "您写的字可能是:" + recognizeTexts;
//                        }
//
//                        AlertDialog dialog = new AlertDialog.Builder(getContext())
//                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//
//                                    }
//                                })
//                                .setMessage(content)
//                                .create();
//                        dialog.show();
//                    }
//                });
    }

    @Override
    public void onSuccess() {

        ToastUtils.showShort("保存成功");
        mKProgressHUD.dismiss();
    }

    @Override
    public void onFailure() {

        ToastUtils.showShort("保存失败");
        mKProgressHUD.dismiss();
    }


    void rec() {
        TessBaseAPI baseApi = new TessBaseAPI();

        String datapath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/tesseract/";

        File act = new File(datapath);

        if (act.exists() == false) {
            act.mkdir();
        }

        File dir = new File(datapath + "tessdata/");

        if (!dir.exists()) {
            dir.mkdirs();
        }

        copyToSD(datapath + "tessdata/chi_sim.traineddata","chi_sim.traineddata");

        Boolean success = baseApi.init(datapath,"chi_sim");

        String content = "识别失败";

        if (success) {
            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Files/";
            String fileName = "img_" + "test" + ".jpeg";

            Bitmap bitmap = BitmapFactory.decodeFile(filePath+fileName);

            baseApi.setImage(bitmap);

            String result = baseApi.getUTF8Text();

            content = result;
            Log.e("TessBaseApi" ,result);

        } else {
            Log.e("TessBaseApi" ,"init Failed");
        }

        mKProgressHUD.dismiss();

        AlertDialog dialog = new AlertDialog.Builder(getContext())
                                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setMessage(content)
                                .create();
                        dialog.show();

    }

    void copyToSD(String path, String name) {
        //如果存在就删掉
        File f = new File(path);

        if (f.exists()) {
            return;
        }

        if (!f.exists()) {
            File p = new File(f.getParent());
            if (!p.exists()) {
                p.mkdirs();
            }
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            is = mContext.getAssets().open(name);
            File file = new File(path);
            os = new FileOutputStream(file);
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = is.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null)
                    is.close();
                if (os != null)
                    os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
