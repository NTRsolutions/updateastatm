package com.atm.ast.astatm.filepicker.model;


import com.atm.ast.astatm.filepicker.FNFileUtil;

import java.io.File;
import java.util.ArrayList;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

/**
 * Created 05-06-2017
 *
 * @author AST Inc.
 */
public class PhotoFactory {

	public static ArrayList<MediaFile> singleListFromPath(String path) {
		ArrayList<MediaFile> images = new ArrayList<>();
		File file = new File(path);
		long fileSize = FNFileUtil.getFileSize(file);
		String mimeType = FNFileUtil.mimeType(file);
		MediaFile captureImage = new MediaFile(0, file.getName(), path, true);
		captureImage.setIsfromCamera(true);
		captureImage.setMimeType(mimeType);
		captureImage.setSize(fileSize);
		captureImage.setMediaType(MEDIA_TYPE_IMAGE);
		images.add(captureImage);
		return images;
	}
}
