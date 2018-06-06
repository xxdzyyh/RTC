package com.tiilii.rtc.ui.read;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.speech.EventListener;
import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;
import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 主页
 *
 * @author fox
 * @since 2017/10/30
 */
public class ReadViewFragment extends BaseFragment implements ReadContract.View {

    /**
     * 视频的宽度
     */
    private int videoWidth = 0;
    /**
     * 视频的高度
     */
    private int videoHeight = 0;
    /**
     * 录制的视频文件
     */
    private File resultFile;
    /**
     * 文件地址
     */
    public static String filePath;
    /**
     * 摄像头
     */
    private Camera camera;
    /**
     * 摄像头参数
     */
    private Camera.Parameters parameters;
    /**
     * 视频录制
     */
    private MediaRecorder recorder;
    /**
     * 权限被拒绝
     */
    private boolean permissionDenied = false;

    private final String TAG = "ReadFragment";
    private EventManager eventManager;

    private EventListener eventListener;
    /**
     * 摄像预览、结果层
     */
    @BindView(R.id.fl_view)
    FrameLayout viewFrameLayout;

    /**
     * 需要申请的权限
     */
    private String[] requestPermissions = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_EXTERNAL_STORAGE};
    /**
     * 摄像头预览
     */
    @BindView(R.id.tv_camera)
    TextureView cameraTextureView;
    /**
     * 临时识别
     */
    @BindView(R.id.tv_temporary)
    TextView temporaryTextView;

    @Inject
    ReadContract.Presenter mPresenter;

    @Inject
    public ReadViewFragment() {
        mPresenter = new ReadPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_read_view, container, false);
        ButterKnife.bind(this, root);


        requestPermission(0);
        setDefaultSavePath();
        setListeners();
        initEventManager();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.bindView(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (cameraTextureView.getParent() == null) {
            viewFrameLayout.addView(cameraTextureView);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (cameraTextureView.getParent() != null) {
            viewFrameLayout.removeView(cameraTextureView);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        release();

        releaseRecorder();
        releaseCamera();

    }

    @OnClick(R.id.btn_start)
    void startIdentify() {

        if (permissionDenied) {
            Toast.makeText(mContext, "没有权限，无法使用功能", Toast.LENGTH_SHORT).show();
            return;
        }
        if (camera == null) {
            Toast.makeText(mContext, "相机无法使用", Toast.LENGTH_SHORT).show();
            return;
        }

        // 录视频
        startRecord();

        // 识别语音
        Map<String, Object> map = new HashMap<>();
        map.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0);// 开启长语音

        PidBuilder pidBuilder = new PidBuilder();
        map = pidBuilder.addPidInfo(map);

        start(map);

    }

    @OnClick(R.id.btn_cancel)
    void cancelIdentify() {

        // 结束录制视频
        if (permissionDenied || camera == null) {
            return;
        }

        // 停止录制视频
        releaseRecorder();
//        cameraTextureView.setVisibility(View.GONE);

        // 结束语音识别
        cancel();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (grantResults[0]) {
            case PackageManager.PERMISSION_GRANTED:
                if (requestCode == requestPermissions.length - 1) {// 获得所有权限

                    cameraTextureView.setVisibility(View.VISIBLE);// 将触发SurfaceTextureListener中的事件
                } else {

                    requestPermission(requestCode + 1);
                }
                break;
            case PackageManager.PERMISSION_DENIED:
                permissionDenied = true;
                break;
        }
    }

    private void requestPermission(int index) {

        for (int i = index; i < requestPermissions.length; i++) {
            if (ContextCompat.checkSelfPermission(mContext, requestPermissions[i]) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{requestPermissions[i]}, i);
                break;
            }
            if (i == requestPermissions.length - 1) {// 获得所有权限

                cameraTextureView.setVisibility(View.VISIBLE);// 将触发SurfaceTextureListener中的事件
            }
        }
    }

    /**
     * 设置默认照片和视频储存路径
     */
    private void setDefaultSavePath() {
        if (filePath == null) {

            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/Files/");
            if (!file.exists()) {
                file.mkdirs();
            }
            filePath = file.getAbsolutePath();
        }
    }

    /**
     * 设置事件监听
     */
    private void setListeners() {

        // 预览视图变化事件
        cameraTextureView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                System.out.println("CameraActivity.onSurfaceTextureAvailable");
                initCamera();
                if (camera != null) {
                    // 开始预览
                    camera.startPreview();
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                System.out.println("CameraActivity.onSurfaceTextureDestroyed");
                releaseCamera();
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });

    }

    private void initEventManager() {
        eventManager = EventManagerFactory.create(mContext, "asr");
        eventListener = new RecogEventAdapter(iRecogListener);
        eventManager.registerListener(eventListener);

    }

    private void start(Map<String, Object> params) {

        String json = new JSONObject(params).toString();
        System.out.println(json);
        eventManager.send(SpeechConstant.ASR_START, json, null, 0, 0);
    }

    private void stop() {
        eventManager.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0);

    }

    private void cancel() {

        if (eventManager != null) {
            eventManager.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
        }
    }

    public void release() {

        if (eventManager == null) {
            return;
        }

        cancel();
        eventManager.unregisterListener(eventListener);
        eventManager = null;
    }


    private IRecogListener iRecogListener = new IRecogListener() {
        @Override
        public void onAsrReady() {


        }

        @Override
        public void onAsrBegin() {

            Log.e(TAG, "说话开始");
        }

        @Override
        public void onAsrEnd() {

            Log.e(TAG, "说话结束");
        }

        @Override
        public void onAsrPartialResult(String[] results, RecogResult recogResult) {

            Log.e(TAG, "持续识别中");
            temporaryTextView.setText(results[0]);
        }

        @Override
        public void onAsrFinalResult(String[] results, RecogResult recogResult) {

            temporaryTextView.setText(results[0]);
        }

        @Override
        public void onAsrFinish(RecogResult recogResult) {

            Log.e(TAG, "识别结束,如果是长语音可能会继续识别");
        }

        @Override
        public void onAsrFinishError(int errorCode, int subErrorCode, String errorMessage, String descMessage, RecogResult recogResult) {

            Log.e(TAG, "识别错误");
        }

        @Override
        public void onAsrLongFinish() {

            Log.e(TAG, "长语音识别结束");
        }

        @Override
        public void onAsrVolume(int volumePercent, int volume) {

        }

        @Override
        public void onAsrAudio(byte[] data, int offset, int length) {

        }

        @Override
        public void onAsrExit() {

        }

        @Override
        public void onAsrOnlineNluResult(String nluResult) {

            Log.e(TAG, nluResult);
        }

        @Override
        public void onOfflineLoaded() {

        }

        @Override
        public void onOfflineUnLoaded() {

        }
    };

    /**
     * 初始化摄像头对象
     */
    private void initCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        try {
//            camera = Camera.open();
            for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
                Camera.getCameraInfo(camIdx, cameraInfo);
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    camera = Camera.open(camIdx);
                }
            }

        } catch (Exception e) {

            Toast.makeText(mContext, "相机无法使用", Toast.LENGTH_SHORT).show();
            return;
        }
        camera.setDisplayOrientation(90);// 旋转90度适应竖屏

        try {
            camera.setPreviewTexture(cameraTextureView.getSurfaceTexture());
        } catch (IOException e) {
            e.printStackTrace();
        }

        parameters = camera.getParameters();
        // 影响输出的图片的角度
        parameters.setRotation(90);

        chooseOptimalSize(cameraTextureView.getWidth(), cameraTextureView.getHeight());// 获取合适的尺寸

        // 自动聚焦
        List<String> focusList = parameters.getSupportedFocusModes();
        if (focusList.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        } else if (focusList.contains(Camera.Parameters.FOCUS_MODE_FIXED)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_FIXED);
        } else if (focusList.contains(Camera.Parameters.FOCUS_MODE_INFINITY)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_INFINITY);
        } else {
            parameters.setFocusMode(focusList.get(0));
        }

        camera.setParameters(parameters);
    }

    /**
     * 选择合适的尺寸
     * 因为默认Activity的Orientation是竖屏，所以在筛选宽高时，desireWidth和desireHeight互换了
     *
     * @param desireWidth  想要设置的宽
     * @param desireHeight 想要设置的高
     */
    private void chooseOptimalSize(int desireWidth, int desireHeight) {

        int width = 0;
        int height = 0;

        // 选择预览尺寸
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            // 选出比宽、高稍大或相等的尺寸
            if (desireHeight <= size.width && desireWidth <= size.height) {
                width = size.width;
                height = size.height;
            }
        }
        parameters.setPreviewSize(640, 640);
        System.out.println("width = " + width);
        System.out.println("height = " + height);

        // 选择照片尺寸
        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
            // 选出比宽、高稍大或相等的尺寸
            if (desireHeight <= size.width && desireWidth <= size.height) {
                width = size.width;
                height = size.height;
            }
        }
        parameters.setPictureSize(width, height);
        System.out.println("width = " + width);
        System.out.println("height = " + height);

        // 选择视频尺寸
        for (Camera.Size size : parameters.getSupportedVideoSizes()) {
            // 选出比宽、高稍大或相等的尺寸
            if (desireHeight <= size.width && desireWidth <= size.height) {
                videoWidth = size.width;
                videoHeight = size.height;
            }
        }

        // 当设备录制视频支持的尺寸比屏幕的尺寸小时，选取所支持的最大尺寸
        if (videoWidth == 0 || videoHeight == 0) {
            videoWidth = parameters.getSupportedVideoSizes().get(0).width;
            videoHeight = parameters.getSupportedVideoSizes().get(0).height;
        }
        System.out.println("videoWidth = " + videoWidth);
        System.out.println("videoHeight = " + videoHeight);
    }

    /**
     * 录制视频
     */
    private void startRecord() {
        recorder = new MediaRecorder();

        camera.unlock();
        recorder.setCamera(camera);
//        recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

        String fileName = "video_" + System.currentTimeMillis() + ".mp4";
        resultFile = new File(filePath + File.separator + fileName);
        recorder.setOutputFile(resultFile.getPath());
        recorder.setVideoSize(videoWidth, videoHeight);
        // 解决花屏问题
        recorder.setVideoEncodingBitRate(2000000);
        recorder.setVideoFrameRate(24);

        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            recorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 释放摄像头
     */
    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    /**
     * 释放Recorder
     */
    private void releaseRecorder() {

        if (recorder != null) {
            recorder.release();
            recorder = null;
            if (camera != null) {
                camera.lock();
            }
        }
    }
}
