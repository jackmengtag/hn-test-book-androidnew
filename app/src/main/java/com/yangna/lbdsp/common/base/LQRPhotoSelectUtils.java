package com.yangna.lbdsp.common.base;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * CSDN_LQR
 * 图片选择工具类
 */
public class LQRPhotoSelectUtils {

    public static final int REQ_TAKE_PHOTO = 10001;
    public static final int REQ_SELECT_PHOTO = 10002;
    public static final int REQ_ZOOM_PHOTO = 10003;

    public static final int RESULT_LOAD_IMAGE1 = 111, RESULT_LOAD_IMAGE2 = 222, RESULT_LOAD_IMAGE3 = 333;//设置自定义常量RESULT_LOAD_IMAGE

    private Activity mActivity;
    //拍照或剪切后图片的存放位置(参考file_provider_paths.xml中的路径)
    private String imgPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + String.valueOf(System.currentTimeMillis()) + ".jpg";
    //FileProvider的主机名：一般是包名+".fileprovider"，严格上是build.gradle中defaultConfig{}中applicationId对应的值+".fileprovider"
    private String AUTHORITIES = "packageName" + ".fileprovider";
    private boolean mShouldCrop = false;//是否要裁剪（默认不裁剪）
    private Uri mOutputUri = null;
    private File mInputFile;
    private File mOutputFile = null;

    //剪裁图片宽高比
    private int mAspectX = 1;
    private int mAspectY = 1;
    //剪裁图片大小
    private int mOutputX = 800;
    private int mOutputY = 480;
    PhotoSelectListener mListener;

    /**
     * 可指定是否在拍照或从图库选取照片后进行裁剪
     * <p>
     * 默认裁剪比例1:1，宽度为800，高度为480
     *
     * @param activity   上下文
     * @param listener   选取图片监听
     * @param shouldCrop 是否裁剪
     */
    public LQRPhotoSelectUtils(Activity activity, PhotoSelectListener listener, boolean shouldCrop) {
        mActivity = activity;
        mListener = listener;
        mShouldCrop = shouldCrop;
        AUTHORITIES = activity.getPackageName() + ".fileprovider";
        imgPath = generateImgePath();
    }

    /**
     * 可以拍照或从图库选取照片后裁剪的比例及宽高
     *
     * @param activity 上下文
     * @param listener 选取图片监听
     * @param aspectX  图片裁剪时的宽度比例
     * @param aspectY  图片裁剪时的高度比例
     * @param outputX  图片裁剪后的宽度
     * @param outputY  图片裁剪后的高度
     */
    public LQRPhotoSelectUtils(Activity activity, PhotoSelectListener listener, int aspectX, int aspectY, int outputX, int outputY) {
        this(activity, listener, true);
        mAspectX = aspectX;
        mAspectY = aspectY;
        mOutputX = outputX;
        mOutputY = outputY;
    }

    /**
     * 设置FileProvider的主机名：一般是包名+".fileprovider"，严格上是build.gradle中defaultConfig{}中applicationId对应的值+".fileprovider"
     * <p>
     * 该工具默认是应用的包名+".fileprovider"，如项目build.gradle中defaultConfig{}中applicationId不是包名，则必须调用此方法对FileProvider的主机名进行设置，否则Android7.0以上使用异常
     *
     * @param authorities FileProvider的主机名
     */
    public void setAuthorities(String authorities) {
        this.AUTHORITIES = authorities;
    }

    /**
     * 修改图片的存储路径（默认的图片存储路径是SD卡上 Android/data/应用包名/时间戳.jpg）
     *
     * @param imgPath 图片的存储路径（包括文件名和后缀）
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * 拍照获取
     */
    public void takePhoto() {
        File imgFile = new File(imgPath);
        if (!imgFile.getParentFile().exists()) {
            imgFile.getParentFile().mkdirs();
        }
        Uri imgUri = null;

        //        if (Build.VERSION.SDK_INT >= 24) {//这里用这种传统的方法无法调起相机
        //            imgUri = FileProvider.getUriForFile(mActivity, AUTHORITIES, imgFile);
        //        } else {
        //            imgUri = Uri.fromFile(imgFile);
        //        }
        /*
        * 1.现象
            在项目中调用相机拍照和录像的时候，android4.x,Android5.x,Android6.x均没有问题,在Android7.x下面直接闪退
           2.原因分析
            Android升级到7.0后对权限又做了一个更新即不允许出现以file://的形式调用隐式APP，需要用共享文件的形式：content:// URI
           3.解决方案
            下面是打开系统相机的方法，做了android各个版本的兼容:
        * */

        if (Build.VERSION.SDK_INT < 24) {
            // 从文件中创建uri
            imgUri = Uri.fromFile(imgFile);
        } else {
            //兼容android7.0 使用共享文件的形式
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, imgFile.getAbsolutePath());
            imgUri = mActivity.getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        mActivity.startActivityForResult(intent, REQ_TAKE_PHOTO);
    }

    /**
     * 从图库获取
     */
    public void selectPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, REQ_SELECT_PHOTO);
    }

    /**
     * 从图库获取 自定义正面
     */
    public void selectPhoto1() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, RESULT_LOAD_IMAGE1);
    }

    /**
     * 从图库获取 自定义反面
     */
    public void selectPhoto2() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, RESULT_LOAD_IMAGE2);
    }

    /**
     * 从图库获取 自定义手持
     */
    public void selectPhoto3() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        mActivity.startActivityForResult(intent, RESULT_LOAD_IMAGE3);
    }

    private void zoomPhoto(File inputFile, File outputFile) {
        File parentFile = outputFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setDataAndType(getImageContentUri(mActivity, inputFile), "image/*");
        } else {
            intent.setDataAndType(Uri.fromFile(inputFile), "image/*");
        }
        intent.putExtra("crop", "true");

        //设置剪裁图片宽高比
        intent.putExtra("mAspectX", mAspectX);
        intent.putExtra("mAspectY", mAspectY);

        //设置剪裁图片大小
        intent.putExtra("mOutputX", mOutputX);
        intent.putExtra("mOutputY", mOutputY);

        // 是否返回uri
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outputFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        mActivity.startActivityForResult(intent, REQ_ZOOM_PHOTO);
    }

    public void attachToActivityForResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case LQRPhotoSelectUtils.REQ_TAKE_PHOTO://拍照
                    mInputFile = new File(imgPath);
                    if (mShouldCrop) {//裁剪
                        mOutputFile = new File(generateImgePath());
                        mOutputUri = Uri.fromFile(mOutputFile);
                        zoomPhoto(mInputFile, mOutputFile);
                    } else {//不裁剪
                        mOutputUri = Uri.fromFile(mInputFile);
                        if (mListener != null) {
                            mListener.onFinish(mInputFile, mOutputUri);
                        }
                    }
                    break;
                case LQRPhotoSelectUtils.REQ_SELECT_PHOTO://图库
                    if (data != null) {
                        Uri sourceUri = data.getData();
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = mActivity.managedQuery(sourceUri, proj, null, null, null);
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String imgPath = cursor.getString(columnIndex);
                        mInputFile = new File(imgPath);

                        if (mShouldCrop) {//裁剪
                            mOutputFile = new File(generateImgePath());
                            mOutputUri = Uri.fromFile(mOutputFile);
                            zoomPhoto(mInputFile, mOutputFile);
                        } else {//不裁剪
                            mOutputUri = Uri.fromFile(mInputFile);
                            if (mListener != null) {
                                mListener.onFinish(mInputFile, mOutputUri);
                            }
                        }
//                        cursor.close();//没有必要加了
                    }
                    break;
                case LQRPhotoSelectUtils.REQ_ZOOM_PHOTO://裁剪
                    if (data != null) {
                        if (mOutputUri != null) {
                            //删除拍照的临时照片
                            File tmpFile = new File(imgPath);
                            if (tmpFile.exists())
                                tmpFile.delete();
                            if (mListener != null) {
                                mListener.onFinish(mOutputFile, mOutputUri);
                            }
                        }
                    }
                    break;
                case RESULT_LOAD_IMAGE1://图库 自定义正面返回值111
                    if (data != null) {
                        Uri sourceUri = data.getData();
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = mActivity.managedQuery(sourceUri, proj, null, null, null);
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String imgPath = cursor.getString(columnIndex);
                        mInputFile = new File(imgPath);

                        if (mShouldCrop) {//裁剪
                            mOutputFile = new File(generateImgePath());
                            mOutputUri = Uri.fromFile(mOutputFile);
                            zoomPhoto(mInputFile, mOutputFile);
                        } else {//不裁剪
                            mOutputUri = Uri.fromFile(mInputFile);
                            if (mListener != null) {
                                mListener.onFinish(mInputFile, mOutputUri);
                            }
                        }
//                        cursor.close();//没有必要加了
                    }
                    break;
                case RESULT_LOAD_IMAGE2://图库 自定义反面返回值222
                    if (data != null) {
                        Uri sourceUri = data.getData();
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = mActivity.managedQuery(sourceUri, proj, null, null, null);
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String imgPath = cursor.getString(columnIndex);
                        mInputFile = new File(imgPath);

                        if (mShouldCrop) {//裁剪
                            mOutputFile = new File(generateImgePath());
                            mOutputUri = Uri.fromFile(mOutputFile);
                            zoomPhoto(mInputFile, mOutputFile);
                        } else {//不裁剪
                            mOutputUri = Uri.fromFile(mInputFile);
                            if (mListener != null) {
                                mListener.onFinish(mInputFile, mOutputUri);
                            }
                        }
//                        cursor.close();//没有必要加了
                    }
                    break;
                case RESULT_LOAD_IMAGE3://图库 自定义手持返回值333
                    if (data != null) {
                        Uri sourceUri = data.getData();
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = mActivity.managedQuery(sourceUri, proj, null, null, null);
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        cursor.moveToFirst();
                        String imgPath = cursor.getString(columnIndex);
                        mInputFile = new File(imgPath);

                        if (mShouldCrop) {//裁剪
                            mOutputFile = new File(generateImgePath());
                            mOutputUri = Uri.fromFile(mOutputFile);
                            zoomPhoto(mInputFile, mOutputFile);
                        } else {//不裁剪
                            mOutputUri = Uri.fromFile(mInputFile);
                            if (mListener != null) {
                                mListener.onFinish(mInputFile, mOutputUri);
                            }
                        }
//                        cursor.close();//没有必要加了
                    }
                    break;
            }
        }
    }

    /**
     * 安卓7.0裁剪根据文件路径获取uri
     */
    private Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
//            cursor.close();//没有必要加了
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            assert cursor != null;
//            cursor.close();//没有必要加了
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 产生图片的路径，带文件夹和文件名，文件名为当前毫秒数
     */
    private String generateImgePath() {
        return getExternalStoragePath() + File.separator + String.valueOf(System.currentTimeMillis()) + ".jpg";
        //        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + String.valueOf(System.currentTimeMillis()) + ".jpg";//测试用
    }

    /**
     * 获取SD下的应用目录
     */
    private String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        String ROOT_DIR = "Android/data/" + mActivity.getPackageName();
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }

    public interface PhotoSelectListener {
        void onFinish(File outputFile, Uri outputUri);
    }


    //https://blog.csdn.net/xzytl60937234/article/details/82826728 文章 Android实现图片压缩并上传到服务器
    private static String PHOTO_FILE_NAME = "PMSManagerPhoto";

    /**
     * 获取图片的旋转角度
     *
     * @param filePath
     * @return
     */
    public static int getRotateAngle(String filePath) {
        int rotate_angle = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate_angle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate_angle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate_angle = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotate_angle;
    }

    /**
     * 旋转图片角度
     *
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap setRotateAngle(int angle, Bitmap bitmap) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(angle);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    //转换为圆形状的bitmap
    public static Bitmap createCircleImage(Bitmap source) {
        int length = source.getWidth() < source.getHeight() ? source.getWidth() : source.getHeight();
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(length, length, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(length / 2, length / 2, length / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    /**
     * 图片压缩-质量压缩
     *
     * @param filePath 源图片路径
     * @return 压缩后的路径
     */
    public static String compressImage(String filePath) {//压缩图片？？成功了！！！2020年4月7日16:29:15 https://blog.csdn.net/xzytl60937234/article/details/82826728
        //原文件 旧路径，例如 /storage/sdcard0/dcim/Camera/IMG_20200414_175708.jpg
        File oldFile = new File(filePath);
        //压缩文件路径 照片路径/
//        String targetPath = oldFile.getPath();//新路径，例如 /storage/sdcard0/dcim/Camera/hlytu20200414165720.jpg
        String targetPath = filePath.replace(".", "_1.");
        int quality = 50;//压缩比例 0-100
        Bitmap bm = getSmallBitmap(filePath);//获取一定尺寸的图片
        int degree = getRotateAngle(filePath);//获取相片拍摄角度

        if (degree != 0) {//旋转照片角度，防止头像横着显示
            bm = setRotateAngle(degree, bm);
        }
        File outputFile = new File(targetPath);
        try {
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
                //outputFile.createNewFile();
            } else {
                outputFile.delete();
            }
            FileOutputStream out = new FileOutputStream(outputFile);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            return filePath;
        }
        return outputFile.getPath();
    }

    /**
     * 根据路径获得图片信息并按比例压缩，返回bitmap
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//只解析图片边沿，获取宽高
        BitmapFactory.decodeFile(filePath, options);
        // 计算缩放比
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        // 完整解析图片返回bitmap
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
    //https://blog.csdn.net/xzytl60937234/article/details/82826728 文章 Android实现图片压缩并上传到服务器

}
