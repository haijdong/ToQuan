package com.whale.nangua.toquan.utils;

import java.io.File;

import android.os.Environment;

public class MyImageUtils {

	public static String convertUrlToFileName(String url) {
		final String[] strs = url.split("/");
		String fileName = strs[strs.length - 1];
		if (fileName.contains("@")) {
			String[] mystr = url.split("@");
			fileName = mystr[0];
		}
		return fileName;
	}

	public static String getSDPath() {
		File sdcardDir = Environment.getExternalStorageDirectory();
		String path = sdcardDir.getParent() + "/" + sdcardDir.getName();
		final String fileDirectory = path + "/"
				+ "aiquaner/imagecache";
		return fileDirectory;
	}
//	public static String getCZImgSDPath() {
//		File sdcardDir = Environment.getExternalStorageDirectory();
//		String path = sdcardDir.getParent() + "/" + sdcardDir.getName();
//		final String fileDirectory = path + "/"
//				+ Config.CACHDIR_CZ_IMAGE_CACH;
//		return fileDirectory;
//	}

	public static void deleteImageFile(String imageurl) {
		String savePath = getSDPath();
		String filename = convertUrlToFileName(imageurl);
		File file = new File(savePath + "/" + filename);
		if (file.exists()) {
			file.delete();
		}
	}

//	public static String getZoomImageUrl(String imageurl) {
//		// @1e_320w_480h_0c_0i_1o_90q_1x.jpg
//		int with = BaseApplication.getInstance().mScreenWidth;
//		int height = BaseApplication.getInstance().mScreenHeight;
//		if (with > 720) {
//			with = 720;
//			height = 1280;
//		}
//		String url = imageurl + "@1e_" + with + "w_" + height
//				+ "h_0c_0i_1o_90q_1x.jpg";
//		MyLog.error(MyImageUtils.class, "下载地址：" + url);
//		return url;
//
//	}

//	public static String getSmallImageUrl(String imageurl) {
//		// @1e_320w_480h_0c_0i_1o_90q_1x.jpg
//		int with = BaseApplication.getInstance().mScreenWidth;
//		int height = BaseApplication.getInstance().mScreenHeight;
//		if (with > 720) {
//			with = 720;
//			height = 1280;
//		}
//		String url = imageurl + "@1e_" + with / 2 + "w_" + height / 2
//				+ "h_0c_0i_1o_80q_1x.jpg";
//		MyLog.error(MyImageUtils.class, "下载地址：" + url);
//		return url;
//
//	}

	public static void deleteMySdcardFile() {
		File sdcardDir = Environment.getExternalStorageDirectory();
		String path = sdcardDir.getParent() + "/" + sdcardDir.getName()
				+ "/ksp";
		File file = new File(path);
		if (file.exists()) {
			delete(file);
		}
	}

	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
			return;
		}

		if (file.isDirectory()) {
			File[] childFiles = file.listFiles();
			if (childFiles == null || childFiles.length == 0) {
				file.delete();
				return;
			}

			for (int i = 0; i < childFiles.length; i++) {
				delete(childFiles[i]);
			}
			file.delete();
		}
	}

//	// 处理圆角
//	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
//		if(bitmap==null){
//			return null;
//		}
//		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
//				bitmap.getHeight(), Config.ARGB_8888);
//		Canvas canvas = new Canvas(output);
//
//		final int color = 0xff424242;
//		final Paint paint = new Paint();
//		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
//		final RectF rectF = new RectF(rect);
//		final float roundPx = MyDimenFactory.getInstance(BaseApplication
//				.getInstance()).adimage_corner_cricle;
//
//		paint.setAntiAlias(true);
//		canvas.drawARGB(0, 0, 0, 0);
//		paint.setColor(color);
//		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
//
//		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
//		canvas.drawBitmap(bitmap, rect, rect, paint);
//
//		return output;
//	}

}
