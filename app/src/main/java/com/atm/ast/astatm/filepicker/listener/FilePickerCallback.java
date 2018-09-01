package com.atm.ast.astatm.filepicker.listener;


import com.atm.ast.astatm.filepicker.model.MediaFile;

import java.util.ArrayList;

/**
 * Created 16-06-2017
 *
 * @author AST Inc.
 */
public interface FilePickerCallback {

	void onSuccess(ArrayList<MediaFile> files);

	void onError();
}
