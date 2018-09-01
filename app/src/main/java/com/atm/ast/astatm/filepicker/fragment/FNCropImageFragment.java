package com.atm.ast.astatm.filepicker.fragment;

import android.view.View;


import com.atm.ast.astatm.R;
import com.atm.ast.astatm.filepicker.AndroidDeviceFile;
import com.atm.ast.astatm.filepicker.FNFilePicker;
import com.atm.ast.astatm.filepicker.helper.BitmapConverterTask;
import com.atm.ast.astatm.filepicker.listener.CropCallback;
import com.atm.ast.astatm.filepicker.listener.IPageListener;
import com.atm.ast.astatm.filepicker.model.MediaFile;
import com.atm.ast.astatm.filepicker.view.FNCropImageView;
import com.atm.ast.astatm.fragment.MainFragment;

import java.util.ArrayList;

/**
 * Created 14-06-2017
 *
 * @author AST Inc.
 */
public class FNCropImageFragment extends MainFragment {

	private FNCropImageView cropImageView;
	private MediaFile imageFile;
	private boolean isProfilePic;

	@Override
	protected void getArgs() {
		this.imageFile = this.getParcelable("imageFile");
		//this.isProfilePic = this.getArgsBoolean(FNFilePicker.EXTRA_CROP_MODE, false);

		isProfilePic=false;
	}

	@Override
	protected int fragmentLayout() {
		return R.layout.picker_crop_view;
	}

	@Override
	protected void loadView() {
		cropImageView = this.findViewById(R.id.cropImageView);
		cropImageView.setCropMode(isProfilePic ? FNCropImageView.CropMode.CIRCLE_SQUARE : FNCropImageView.CropMode.SQUARE);
	}

	@Override
	protected void setClickListeners() {
	}

	@Override
	protected void setAccessibility() {
	}

	@Override
	protected void dataToView() {
		ArrayList<MediaFile> mediaFiles = new ArrayList<>();
		mediaFiles.add(imageFile);
		BitmapConverterTask converterTask = new BitmapConverterTask(this.getContext()) {
			@Override
			protected void onPostExecute(ArrayList<? extends AndroidDeviceFile> fileList) {
				this.dismissProgressBar();
				if (fileList.size() > 0) {
					cropImageView.setOrientation(imageFile.getOrientation());
					cropImageView.setImageBitmap(fileList.get(0).getPhoto());
				}
			}
		};
		converterTask.execute(mediaFiles);
	}



	@Override
	public boolean onBackPressed() {
		getHostActivity().redirectToHomeMenu();
		return false;
	}

	public void rotateImage(FNCropImageView.RotateDegrees degrees) {
		cropImageView.rotateImage(degrees);
	}

	public void startCrop(CropCallback cropCallback) {
		if (activityListener() == null) {
			return;
		}
		this.cropImageView.startCrop(imageFile, cropCallback);
	}

	private IPageListener activityListener() {
		return getHostActivity() != null ? (IPageListener) getHostActivity() : null;
	}
}
