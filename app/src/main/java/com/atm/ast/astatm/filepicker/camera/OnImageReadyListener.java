package com.atm.ast.astatm.filepicker.camera;


import com.atm.ast.astatm.filepicker.model.MediaFile;

import java.util.ArrayList;

/**
 * Created 05-06-2017
 *
 * @author AST Inc.
 */
public interface OnImageReadyListener {
	void onImageReady(ArrayList<MediaFile> image);
}
