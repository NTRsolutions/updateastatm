package com.atm.ast.astatm.filepicker.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.exception.FNExceptionUtil;
import com.atm.ast.astatm.filepicker.AndroidDeviceFile;
import com.atm.ast.astatm.utils.FNObjectUtil;

import java.util.ArrayList;

/**
 * Created 16-06-2017
 *
 * @author AST Inc.
 */
public class BitmapConverterTask extends AsyncTask<ArrayList<? extends AndroidDeviceFile>, Void, ArrayList<? extends AndroidDeviceFile>> {

	private Context context;
	private ASTProgressBar _progressBar;

	public BitmapConverterTask(Context context) {
		this.context = context;
	}

	@Override
	protected ArrayList<? extends AndroidDeviceFile> doInBackground(ArrayList<? extends AndroidDeviceFile>... params) {
		ArrayList<? extends AndroidDeviceFile> fileList = params[0];
		for (AndroidDeviceFile deviceFile : fileList) {
			Bitmap bitmap = null;
			float height = FNImageUtil.SINGLE_MODE_MAX_HEIGHT;
			float width = FNImageUtil.SINGLE_MODE_MAX_WIDTH;
			try {
				if (FNObjectUtil.isNonEmptyStr(deviceFile.getPath())) {
					bitmap = FNImageUtil.compressImage(deviceFile.getPath(), deviceFile.getOrientation(), width, height);
				} else if (!FNObjectUtil.isEmpty(deviceFile.getPhotoUri())) {
					if (deviceFile.isContactPhoto()) {
						bitmap = FNImageUtil.getScaledBitmap(MediaStore.Images.Media.getBitmap(context.getContentResolver(), deviceFile.getPhotoUri()),
								(int) FNImageUtil.PHOTO_MAX_WIDTH, (int) FNImageUtil.PHOTO_MAX_HEIGHT);
					} else {
						bitmap = FNImageUtil.compressImage(context, deviceFile.getPhotoUri(), FNImageUtil.PHOTO_MAX_WIDTH, FNImageUtil.PHOTO_MAX_HEIGHT);
					}
				}
			} catch (Exception e) {
				FNExceptionUtil.logException(e);
				bitmap = null;
			}
			if (bitmap != null) {
				deviceFile.setPhoto(bitmap);
			}
		}
		return fileList;
	}

	@Override
	protected void onPreExecute() {
		showProgressDialog();
	}

	@Override
	protected void onPostExecute(ArrayList<? extends AndroidDeviceFile> fileList) {
		dismissProgressBar();
	}

	public void showProgressDialog() {
		this._progressBar = new ASTProgressBar(this.context);
		this._progressBar.show();
	}

	public void dismissProgressBar() {
		try {
			if (this._progressBar != null) {
				this._progressBar.dismiss();
			}
		} catch (Exception e) {
		}
	}
}
