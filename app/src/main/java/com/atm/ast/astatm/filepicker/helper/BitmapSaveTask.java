package com.atm.ast.astatm.filepicker.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.atm.ast.astatm.component.ASTProgressBar;
import com.atm.ast.astatm.filepicker.model.MediaFile;
import com.atm.ast.astatm.utils.FNObjectUtil;

import java.util.ArrayList;

/**
 * Created 16-06-2017
 *
 * @author AST Inc.
 */
public class BitmapSaveTask extends AsyncTask<ArrayList<MediaFile>, Void, ArrayList<MediaFile>> {

	private Context context;
	private ASTProgressBar _progressBar;

	public BitmapSaveTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		showProgressDialog();
	}

	@Override
	protected ArrayList<MediaFile> doInBackground(ArrayList<MediaFile>... params) {
		ArrayList<MediaFile> files = params[0];
		for (MediaFile deviceFile : files) {
			if (deviceFile.isPhoto() && !deviceFile.isCropped() && FNObjectUtil.isEmptyStr(deviceFile.getCompressFilePath())) {
				float height = FNImageUtil.MULTI_MODE_MAX_HEIGHT;
				float width = FNImageUtil.MULTI_MODE_MAX_HEIGHT;
				Bitmap bitmap = FNImageUtil.compressImage(deviceFile.getPath(), deviceFile.getOrientation(), width, height);
				deviceFile.setSize(bitmap.getByteCount());
				deviceFile.setCompressFilePath(FNImageUtil.saveToFile(bitmap, Bitmap.CompressFormat.PNG, 100));
			}
		}
		return files;
	}

	@Override
	protected void onPostExecute(ArrayList<MediaFile> deviceFiles) {
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
