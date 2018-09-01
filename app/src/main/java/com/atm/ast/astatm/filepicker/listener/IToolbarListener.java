package com.atm.ast.astatm.filepicker.listener;


import com.atm.ast.astatm.filepicker.view.FNCropImageView;

/**
 * Created 16-06-2017
 *
 * @author AST Inc.
 */
public interface IToolbarListener {
	void onDone();

	void onCameraClick();

	void rotateImage(FNCropImageView.RotateDegrees degrees);

	void openCropFragment();
}
