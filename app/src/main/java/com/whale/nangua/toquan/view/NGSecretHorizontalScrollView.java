package com.whale.nangua.toquan.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.whale.nangua.toquan.act.Images;
import com.whale.nangua.toquan.R;
import com.whale.nangua.toquan.utils.NGImageLoader;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by nangua on 2016/8/3.
 */
public class NGSecretHorizontalScrollView extends HorizontalScrollView implements View.OnTouchListener{

    /**
     * 是否已加载过一次layout，这里onLayout中的初始化只需加载一次
     */
    private boolean loadOnce;


    /**
     *  列的布局
     */
    private static LinearLayout linearlayout_userinfo_personal_column;


    /**
     * 总的宽度
     */
    private static int ColumnWith;

    /**
     * 每张照片的宽度
     */
    private int perphotoWidth;

    /**
     * 记录上垂直方向的滚动距离。
     */
    private static int lastScrollX = -1;

    /**
     * 进行一些关键性的初始化操作，NGHorizontalScrollView，以及得到高度值。并在这里开始加载第一页的图片。
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed && !loadOnce) {
            scrollViewHeight = getHeight();
            // linearlayout_userinfo_personal_column = (LinearLayout) findViewById(R.id.linearlayout_userinfo_personal_column);
            linearlayout_userinfo_personal_column = new LinearLayout(getContext());
            linearlayout_userinfo_personal_column.setLayoutParams(
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                            , ViewGroup.LayoutParams.MATCH_PARENT)
            );
            linearlayout_userinfo_personal_column.setOrientation(LinearLayout.HORIZONTAL);
            this.addView(linearlayout_userinfo_personal_column);

            perphotoWidth = scrollViewHeight;  //每张照片的宽度等于列高
            //在第一列添加照相的按钮
            FrameLayout.LayoutParams btnParams = new FrameLayout.LayoutParams(perphotoWidth, perphotoWidth);
            btnParams.setMargins(dpToPx(getResources(), 40), dpToPx(getResources(), 40), 0, 0);
            ImageButton imageButton = new ImageButton(getContext());
            imageButton.setBackgroundResource(R.drawable.ic_add);
            imageButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageButton.setLayoutParams(btnParams);
            //TODO 点击打开相册选取，并获得拍下来的图片，存储
            imageButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "点击打开相册选取，并获得拍下来的图片，存储.", Toast.LENGTH_SHORT).show();
                }
            });
            //添加照相按钮
            linearlayout_userinfo_personal_column.addView(imageButton);
            loadOnce = true;
            //加载下一页图片
            loadMoreImages();
        }
    }

    /**
     * 记录当前已加载到第几页
     * @param context
     */
    private int page = 0;

    /**
     * 每页要加载的图片数量
     * 默认为5张
     * @param context
     */
    public static final int PAGE_SIZE = 5;

    /**
     * 记录所有正在下载或等待下载的任务。
     */
    private static Set<LoadImageTask> taskCollection;


    /**
     * 对图片进行管理的工具类
     */
    //  private com.whale.nangua.toquan.utils.NGImageLoader NGImageLoader;

    /**
     * MyScrollView布局的高度。
     */
    private static int scrollViewHeight;

    /**
     * MyScrollView的构造函数。
     * @param context
     * @param attrs
     */
    public NGSecretHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //   NGImageLoader = NGImageLoader.getInstance();
        taskCollection = new HashSet<LoadImageTask>();
        setOnTouchListener(this);
    }

    /**
     * 在Handler中进行图片可见性检查的判断，以及加载更多图片的操作。
     */
    private static Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            NGSecretHorizontalScrollView myScrollView = (NGSecretHorizontalScrollView) msg.obj;
            int scrollX = myScrollView.getScrollX();
            // 如果当前的滚动位置和上次相同，表示已停止滚动
            if (scrollX == lastScrollX) {
                // 当滚动的最右边，并且当前没有正在下载的任务时，开始加载下一页的图片
                if ( ColumnWith + scrollX >= (linearlayout_userinfo_personal_column.getWidth() )
                        && taskCollection.isEmpty()) {
                    myScrollView.loadMoreImages();
                }
                myScrollView.checkVisibility();
            } else {
                lastScrollX = scrollX;
                Message message = new Message();
                message.obj = myScrollView;
                // 5毫秒后再次对滚动位置进行判断
                handler.sendMessageDelayed(message, 5);
            }
        }
    };


    /**
     * 监听用户的触屏事件，如果用户手指离开屏幕则开始进行滚动检测。
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Message message = new Message();
            message.obj = this;
            handler.sendMessageDelayed(message, 5);
        }
        return false;
    }

    /**
     * 遍历imageViewList中的每张图片，对图片的可见性进行检查，如果图片已经离开屏幕可见范围，则将图片替换成一张空图。
     */
    public void checkVisibility() {
        for (int i = 0; i < imageViewList.size(); i++) {
            ImageView imageView = imageViewList.get(i);
            int borderLeft = (Integer) imageView.getTag(R.string.border_top);
            int borderRight = (Integer) imageView
                    .getTag(R.string.border_bottom);
            if (borderLeft > getScrollX()
                    && borderRight < getScrollX() + ColumnWith) {
                String imageUrl = (String) imageView.getTag(R.string.image_url);
                //从内存中获取图片
                Bitmap bitmap = NGImageLoader.getInstance().getBitmapFromMemoryCache(imageUrl);
                if (bitmap!=null) {
                    imageView.setImageBitmap(bitmap);
                }else {
                    //TODO 显示空图
                }

            } else {
            }
        }
    }


    static boolean hasOver = false;

    private String[] imageUrls;
    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    /**
     * 开始加载下一页的图片，每张图片都会开启一个异步线程去下载。
     */
    public void loadMoreImages() {
        if (hasSDCard()&& Images.imageUrls!=null) {  //判断是否有SD卡
            int startIndex = page * PAGE_SIZE;  //起始位置
            int endIndex = page * PAGE_SIZE + PAGE_SIZE;    //结束位置
            if (startIndex < Images.imageUrls.length) {
                //Toast.makeText(getContext(), "正在加载...", Toast.LENGTH_SHORT).show();
                if (endIndex > Images.imageUrls.length) {
                    endIndex = Images.imageUrls.length;
                }
                for (int i = startIndex; i < endIndex; i++) {
                    //加载图片
                    LoadImageTask task = new LoadImageTask();
                    taskCollection.add(task);
                    task.execute(Images.imageUrls[i]);

                }
                page++;
            } else {
                if (hasOver == false) {
                    Toast.makeText(getContext(), "已没有更多图片", Toast.LENGTH_SHORT).show();
                    hasOver = true;
                }
            }
        } else {
            //Toast.makeText(getContext(), "未发现SD卡", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 判断手机是否有SD卡。
     * @return 有SD卡返回true，没有返回false。
     */
    private boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState());
    }

    public int dpToPx(Resources res, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());
    }


    /**
     * 记录所有界面上的图片，用以可以随时控制对图片的释放。
     */
    private List<ImageView> imageViewList = new ArrayList<ImageView>();

    /**
     * 异步下载图片的任务。
     *
     * @author guolin
     */
    class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

        /**
         * 图片的URL地址
         */
        private String mImageUrl;

        /**
         * 可重复使用的ImageView
         */
        private ImageView mImageView;

        public LoadImageTask() {
        }

        /**
         * 将可重复使用的ImageView传入
         *
         * @param imageView
         */
        public LoadImageTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            mImageUrl = params[0];
            Bitmap imageBitmap = NGImageLoader.getInstance()
                    .getBitmapFromMemoryCache(mImageUrl);
            if (imageBitmap == null) {
                imageBitmap = loadImage(mImageUrl);
            }
            return imageBitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                addImage(bitmap, perphotoWidth, perphotoWidth);
            }
            taskCollection.remove(this);
        }

        /**
         * 根据传入的URL，对图片进行加载。如果这张图片已经存在于SD卡中，则直接从SD卡里读取，否则就从网络上下载。
         *
         * @param imageUrl
         *            图片的URL地址
         * @return 加载到内存的图片。
         */
        private Bitmap loadImage(String imageUrl) {
            File imageFile = new File(getImagePath(imageUrl));
            if (!imageFile.exists()) {
                downloadImage(imageUrl);
            }
            if (imageUrl != null) {
                Bitmap bitmap = NGImageLoader.decodeSampledBitmapFromResource(
                        imageFile.getPath(), perphotoWidth);
                if (bitmap != null) {
                    NGImageLoader.getInstance().addBitmapToMemoryCache(imageUrl, bitmap);
                    return bitmap;
                }
            }
            return null;
        }

        /**
         * 向ImageView中添加一张图片
         *
         * @param bitmap
         *            待添加的图片
         * @param imageWidth
         *            图片的宽度
         * @param imageHeight
         *            图片的高度
         */
        private void addImage(Bitmap bitmap, int imageWidth, int imageHeight) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    imageWidth, imageHeight);
            if (mImageView != null) {
                mImageView.setImageBitmap(bitmap);
            } else {
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(params);
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                //imageView.setPadding(5, 5, 5, 5);
                imageView.setTag(R.string.image_url, mImageUrl);

                //这里应该计算图片当前的位置
                // imageView.setTag(R.string.border_top, firstColumnHeight);
                imageView.setTag(R.string.border_top, ColumnWith);
                ColumnWith += perphotoWidth;//增加总宽
                imageView.setTag(R.string.border_bottom, ColumnWith);
                linearlayout_userinfo_personal_column.addView(imageView);

                imageViewList.add(imageView);
            }
        }

        /**
         * 将图片下载到SD卡缓存起来。
         *
         * @param imageUrl
         *            图片的URL地址。
         */
        private void downloadImage(String imageUrl) {
            HttpURLConnection con = null;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            BufferedInputStream bis = null;
            File imageFile = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(15 * 1000);
                con.setDoInput(true);
                con.setDoOutput(true);
                bis = new BufferedInputStream(con.getInputStream());
                imageFile = new File(getImagePath(imageUrl));
                fos = new FileOutputStream(imageFile);
                bos = new BufferedOutputStream(fos);
                byte[] b = new byte[1024];
                int length;
                while ((length = bis.read(b)) != -1) {
                    bos.write(b, 0, length);
                    bos.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (bos != null) {
                        bos.close();
                    }
                    if (con != null) {
                        con.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (imageFile != null) {
                Bitmap bitmap = NGImageLoader.decodeSampledBitmapFromResource(
                        imageFile.getPath(), perphotoWidth);
                if (bitmap != null) {
                    NGImageLoader.getInstance().addBitmapToMemoryCache(imageUrl, bitmap);
                }
            }
        }

        /**
         * 获取图片的本地存储路径。
         *
         * @param imageUrl
         *            图片的URL地址。
         * @return 图片的本地存储路径。
         */
        private String getImagePath(String imageUrl) {
            int lastSlashIndex = imageUrl.lastIndexOf("/");
            String imageName = imageUrl.substring(lastSlashIndex + 1);
            String imageDir = Environment.getExternalStorageDirectory()
                    .getPath() + "/PhotoWallFalls/";
            File file = new File(imageDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            String imagePath = imageDir + imageName;
            return imagePath;
        }
    }

}
