package com.atm.ast.astatm.filepicker.listener;

import com.atm.ast.astatm.filepicker.model.MediaFile;

/**
 * Created 16-06-2017
 *
 * @author AST Inc.
 */
public interface CropCallback {
	void onSuccess(MediaFile file);

	void onError();
}
