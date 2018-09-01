package com.atm.ast.astatm.filepicker.fragment;


import com.atm.ast.astatm.filepicker.DocumentLoader;
import com.atm.ast.astatm.filepicker.adaptor.DocumentAdaptor;
import com.atm.ast.astatm.filepicker.model.MediaFile;
import com.atm.ast.astatm.utils.FNReqResCode;

import java.util.ArrayList;

import static com.atm.ast.astatm.utils.FNObjectUtil.isEmpty;
import static java.security.AccessController.getContext;

/**
 * Created 03-07-2017
 *
 * @author AST Inc.
 */
public class DocumentFragment extends MediaFragment {

	@Override
	public void permissionGranted(int requestCode) {
		switch (requestCode) {
			case FNReqResCode.PERMISSION_REQ_WRITE_EXTERNAL_STORAGE:
				startDocLoader();
				break;
		}
	}

	private void startDocLoader() {
		DocumentLoader loader = new DocumentLoader(getContext()) {
			@Override
			protected void onPostExecute(ArrayList<MediaFile> mediaFiles) {
				super.onPostExecute(mediaFiles);
				if (isEmpty(mediaFiles)) {
					showEmptyUI();
				} else {
					showDataUI();
					adapter = new DocumentAdaptor(getContext(), mediaFiles, DocumentFragment.this);
					setAdapter();
				}
			}
		};
		loader.execute();
	}
}
