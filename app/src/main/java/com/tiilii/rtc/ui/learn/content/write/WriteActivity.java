package com.tiilii.rtc.ui.learn.content.write;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

/**
 * @author fox
 * @since 2017/11/20
 */

public class WriteActivity extends BaseActivity {

    /**
     * 标题
     */
    @BindView(R.id.tv_title)
    TextView titleTextView;

    @Inject
    WriteFragment writeFragment;

    @Inject
    WritePresenter writePresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fl_write_content, writeFragment)
                .commit();

        titleTextView.setText("写字");


        initAccessTokenWithAkSk();
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance().initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
            }
        }, getApplicationContext(), "NGo827dVORO7D7y3rQY1XO4G", "OAiAafDFNVmllYYsDCzWgOCe7ICdlMYp");
    }

    @OnClick(R.id.iv_back)
    void backActivity() {

        finish();
    }
}
