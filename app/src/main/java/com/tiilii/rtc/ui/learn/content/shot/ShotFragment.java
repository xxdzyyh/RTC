package com.tiilii.rtc.ui.learn.content.shot;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tiilii.rtc.R;
import com.tiilii.rtc.base.BaseFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author fox
 * @since 2017/11/20
 */

public class ShotFragment extends BaseFragment implements ShotContract.View {
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
     * 摄像头
     */
    private Camera camera;
    /**
     * 摄像头参数
     */
    private Camera.Parameters parameters;
    /**
     * 权限被拒绝
     */
    private boolean permissionDenied = false;
    /**
     * 文件地址
     */
    public static String filePath;
    /**
     * 摄像头预览
     */
    @BindView(R.id.tv_camera)
    TextureView cameraTextureView;
    /**
     * 需要申请的权限
     */
    private String[] requestPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Inject
    ShotContract.Presenter mPresenter;

    @Inject
    public ShotFragment() {
        mPresenter = new ShotPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shot, container, false);
        ButterKnife.bind(this, root);

        requestPermission(0);
        setDefaultSavePath();
        setListeners();
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter.bindView(this);
    }


    @OnClick(R.id.btn_shot)
    void shotPhoto() {
        if (permissionDenied) {
            Toast.makeText(mContext, "没有权限，无法使用功能", Toast.LENGTH_SHORT).show();
            return;
        }
        if (camera == null) {
            Toast.makeText(mContext, "相机无法使用", Toast.LENGTH_SHORT).show();
            return;
        }

        takePicture();
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
    /**
     * 拍摄照片
     */
    private void takePicture() {

        camera.takePicture(null, null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                SavePictureThread savePictureThread = new SavePictureThread(data);
                savePictureThread.start();
                camera.stopPreview();// 停止预览
            }
        });
    }

    /**
     * 储存照片线程
     */
    private class SavePictureThread extends Thread {
        private byte[] data;

        public SavePictureThread(byte[] data) {
            this.data = data;
        }

        @Override
        public void run() {
            String fileName = "img_" + System.currentTimeMillis() + ".jpeg";
            resultFile = new File(filePath + File.separator + fileName);
            // 储存图片
            OutputStream os = null;
            try {
                os = new FileOutputStream(resultFile);
                os.write(data);
                os.close();
            } catch (IOException e) {
                Log.w("CameraActivity", "Cannot write to " + resultFile, e);
            } finally {
                if (os != null) {// 储存照片成功
                    // 通知Handler显示照片
                    try {
//                        Bitmap bitmap = getBitmap(resultFile.getAbsolutePath());
//                        Message msg = new Message();
//                        msg.obj = bitmap;
//                        viewHandler.sendMessage(msg);
                        os.close();
                    } catch (IOException e) {
                        // Ignore
                    }
                }
            }
        }
    }

    private void requestPermission(int index) {

        for (int i = index; i < requestPermissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(mContext, requestPermissions[i]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{requestPermissions[i]}, i);
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
     * 根据照片文件的参数进行相应旋转，使其在竖屏时能全屏显示
     *
     * @param path 图片地址
     * @return 图片经过旋转后的Bitmap
     */
    public static Bitmap getBitmap(String path) {
        Bitmap rotateBitmap = null;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int degree;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    degree = 0;
                    break;
            }

            Bitmap originBitmap = BitmapFactory.decodeFile(path);
            Matrix matrix = new Matrix();
            matrix.setRotate(degree, originBitmap.getWidth() / 2, originBitmap.getHeight() / 2);
            rotateBitmap = Bitmap.createBitmap(originBitmap, 0, 0, originBitmap.getWidth(), originBitmap.getHeight(), matrix, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotateBitmap;
    }
    /**
     * 初始化摄像头对象
     */
    private void initCamera() {

        try {
            camera = Camera.open();
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
        parameters.setPreviewSize(width, height);
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
     * 释放摄像头
     */
    private void releaseCamera() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}
