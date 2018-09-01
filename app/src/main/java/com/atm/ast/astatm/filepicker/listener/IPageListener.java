package com.atm.ast.astatm.filepicker.listener;


import com.atm.ast.astatm.filepicker.FNFilePickerConfig;
import com.atm.ast.astatm.filepicker.model.MediaFile;

/**
 * Created 07-06-2017
 *
 * @author AST Inc.
 */
public interface IPageListener {
	boolean isValidToAddFile();

	FNFilePickerConfig config();

	void openCropFragment(MediaFile file);

	void updateHeader();
}
