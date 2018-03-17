package com.tiilii.rtc.ui.learn.content.write;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;
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

public class WriteFragment extends BaseFragment implements WriteContract.View {

    @BindView(R.id.sv_write)
    SignatureView writeSignatureView;

    @Inject
    WriteContract.Presenter mPresenter;

    @Inject
    public WriteFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_write, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.bindView(this);
    }

    @OnClick(R.id.btn_finish)
    void finishWrite() {

        writeSignatureView.setTouchable(false);
        writeSignatureView.saveBitmap();
    }

    @OnClick(R.id.btn_recognize)
    void recognize() {

        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Files/";
        String fileName = "img_" + "test" + ".jpeg";
        RecognizeService.recAccurateBasic(filePath + fileName,
                new RecognizeService.ServiceListener() {
                    @Override
                    public void onResult(String result) {
                        System.out.println("result = " + result);
                    }
                });
    }
}
