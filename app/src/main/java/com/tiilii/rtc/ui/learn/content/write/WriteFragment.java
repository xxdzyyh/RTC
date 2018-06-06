package com.tiilii.rtc.ui.learn.content.write;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
import com.tiilii.rtc.model.TextRecogResult;
import com.tiilii.rtc.recognizetext.RecognizeService;
import com.tiilii.rtc.widget.SignatureView;

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
        RecognizeService.recAccurateBasic(filePath + fileName,
                new RecognizeService.ServiceListener() {
                    @Override
                    public void onResult(String result) {
                        mKProgressHUD.dismiss();

                        TextRecogResult recogResult = gson.fromJson(result, TextRecogResult.class);
                        System.out.println("result = " + result);

                        final String content;
                        // 多个字
                        // 识别失败
                        if (recogResult.getWords_result_num() == 0) {

                            content = "识别失败";
                        } else {
                            String recognizeTexts = "";
                            for (TextRecogResult.Word word : recogResult.getWords_result()) {

                                recognizeTexts = recognizeTexts + word.getWords() + "，";
                            }
                            content = "您写的字可能是:" + recognizeTexts;
                        }

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
                });
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
}
