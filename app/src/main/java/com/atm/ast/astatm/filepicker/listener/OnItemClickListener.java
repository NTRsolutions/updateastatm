package com.atm.ast.astatm.filepicker.listener;

import android.view.View;

import com.atm.ast.astatm.FNObject;
import com.atm.ast.astatm.filepicker.AndroidDeviceFile;


/**
 * Created 05-06-2017
 *
 * @author AST Inc.
 */
public interface OnItemClickListener {
	void onFileClick(View view, FNObject object);

	boolean isSelected(AndroidDeviceFile file);
}
