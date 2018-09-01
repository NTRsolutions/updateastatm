package com.atm.ast.astatm.filepicker.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;


import com.atm.ast.astatm.FNObject;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.filepicker.AndroidDeviceFile;
import com.atm.ast.astatm.filepicker.FNFilePicker;
import com.atm.ast.astatm.filepicker.FNFilePickerConfig;
import com.atm.ast.astatm.filepicker.FNFileUtil;
import com.atm.ast.astatm.filepicker.adaptor.AudioCursorAdaptor;
import com.atm.ast.astatm.filepicker.adaptor.CursorRecyclerViewAdapter;
import com.atm.ast.astatm.filepicker.adaptor.MediaCursorAdaptor;
import com.atm.ast.astatm.filepicker.adaptor.VideoCursorAdaptor;
import com.atm.ast.astatm.filepicker.camera.DefaultCameraModule;
import com.atm.ast.astatm.filepicker.camera.FNCameraHelper;
import com.atm.ast.astatm.filepicker.camera.FNCameraModule;
import com.atm.ast.astatm.filepicker.camera.OnImageReadyListener;
import com.atm.ast.astatm.filepicker.listener.IPageListener;
import com.atm.ast.astatm.filepicker.listener.OnItemClickListener;
import com.atm.ast.astatm.filepicker.model.MediaFile;
import com.atm.ast.astatm.filepicker.view.GridSpacingItemDecoration;
import com.atm.ast.astatm.fragment.MainFragment;
import com.atm.ast.astatm.utils.ASTUIUtil;
import com.atm.ast.astatm.utils.FNReqResCode;

import java.util.ArrayList;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
import static com.atm.ast.astatm.filepicker.FNFilePicker.MODE_SINGLE;
import static com.atm.ast.astatm.utils.FNObjectUtil.isNonEmptyStr;

/**
 * Created 23-06-2017
 *
 * @author AST Inc.
 */
public class MediaFragment extends MainFragment implements LoaderManager.LoaderCallbacks<Cursor>, OnItemClickListener {

	private final int LOADER_ID = 1;
	protected RecyclerView.Adapter adapter;
	protected GridLayoutManager layoutManager;
	protected GridSpacingItemDecoration itemOffsetDecoration;
	private RecyclerView recyclerView;
	private View noRecordView;
	private IPageListener pageListener;
	private FNCameraModule cameraModule = new DefaultCameraModule();

	@Override
	protected int fragmentLayout() {
		return R.layout.fn_recycle_layout;
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		this.pageListener = (IPageListener) context;
	}

	@Override
	protected void loadView() {
		this.recyclerView = this.findViewById(R.id.recyclerView);
		this.noRecordView = findViewById(R.id.no_record);
		this.layoutManager = new GridLayoutManager(getContext(), gridColumns());
	}

	@Override
	protected void dataToView() {
		this.recyclerView.setLayoutManager(layoutManager);
		this.recyclerView.setHasFixedSize(true);
		getHostActivity().requestPermission(FNReqResCode.PERMISSION_REQ_WRITE_EXTERNAL_STORAGE);
	}

	@Override
	public void permissionGranted(int requestCode) {
		switch (requestCode) {
			case FNReqResCode.PERMISSION_REQ_WRITE_EXTERNAL_STORAGE:
				getLoaderManager().initLoader(LOADER_ID, null, this);
				break;
			case FNReqResCode.PERMISSION_REQ_CAMERA:
				captureImage(FNReqResCode.CAPTURE_IMAGE);
				break;
		}
	}

	@Override
	public void updateOnResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == FNReqResCode.CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
			finishCaptureImage(data);
		}
	}

	private void initAdaptor(Cursor cursor) {
		switch (pageListener.config().getMediaType()) {
			case MEDIA_TYPE_IMAGE:
				adapter = new MediaCursorAdaptor(getContext(), cursor, gridColumns(), MEDIA_TYPE_IMAGE, this);
				break;
			case MEDIA_TYPE_VIDEO:
				adapter = new VideoCursorAdaptor(getContext(), cursor, gridColumns(), MEDIA_TYPE_VIDEO, this);
				break;
			default:
				adapter = new AudioCursorAdaptor(getContext(), cursor, MEDIA_TYPE_AUDIO, this);
		}
	}

	@Override
	protected void setClickListeners() {

	}

	@Override
	protected void setAccessibility() {

	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		ASTUIUtil.disableTouch();
		if (id != LOADER_ID) {
			return null;
		}
		int mediaType = pageListener.config().getMediaType();
		return new CursorLoader(getContext(), FNFileUtil.contentUri(mediaType), projection(), searchQuery(), null, FNFileUtil.dateAddedKey(mediaType) + " DESC");
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (data == null || data.getCount() == 0) {
			showEmptyUI();
		} else {
			showDataUI();
			initAdaptor(data);
			setAdapter();
		}
		ASTUIUtil.enableTouch();
	}

	protected void showEmptyUI() {
		this.recyclerView.setVisibility(View.GONE);
		this.noRecordView.setVisibility(View.VISIBLE);
	}

	protected void showDataUI() {
		this.noRecordView.setVisibility(View.GONE);
		this.recyclerView.setVisibility(View.VISIBLE);
	}

	protected void setAdapter() {
		this.setItemDecoration(gridColumns());
		this.recyclerView.setAdapter(adapter);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (adapter != null && adapter instanceof CursorRecyclerViewAdapter) {
			((CursorRecyclerViewAdapter) adapter).changeCursor(null);
		}
	}

	protected int gridColumns() {
		switch (pageListener.config().getMediaType()) {
			case MEDIA_TYPE_VIDEO:
			case MEDIA_TYPE_IMAGE:
				return 4;
			default:
				return 1;
		}
	}

	private String[] projection() {
		String[] projection = new String[6];
		int mediaType = pageListener.config().getMediaType();
		projection[0] = FNFileUtil.idKey(mediaType);
		projection[1] = FNFileUtil.dataKey(mediaType);
		projection[2] = FNFileUtil.titleKey(mediaType);
		projection[3] = FNFileUtil.sizeKey(mediaType);
		projection[4] = FNFileUtil.mimeTypeKey(mediaType);
		String mediaTypeKey = FNFileUtil.mediaTypeKey(mediaType);
		String orientationKey = FNFileUtil.orientationKey(mediaType);
		if (isNonEmptyStr(mediaTypeKey)) {
			projection[5] = mediaTypeKey;
		} else if (isNonEmptyStr(orientationKey)) {
			projection[5] = orientationKey;
		}
		return projection;
	}

	protected void setItemDecoration(int columns) {
		layoutManager.setSpanCount(columns);
		if (itemOffsetDecoration != null)
			recyclerView.removeItemDecoration(itemOffsetDecoration);
		itemOffsetDecoration = new GridSpacingItemDecoration(columns, getResources().getDimensionPixelSize(R.dimen._1dp), false);
		recyclerView.addItemDecoration(itemOffsetDecoration);
	}

	@Override
	public void onFileClick(View view, FNObject object) {
		FNFilePickerConfig config = pageListener.config();
		MediaFile deviceFile = (MediaFile) object;
		int selectedItemPosition = config.getSelectedPosition(deviceFile);
		if (selectedItemPosition != -1) {
			config.removeSelectedPosition(selectedItemPosition);
			pageListener.updateHeader();
		} else if (pageListener.isValidToAddFile()) {
			if (config.getMode() == MODE_SINGLE && config.isReturnAfterFirst()) {
				pageListener.openCropFragment(deviceFile);
			} else {
				if (config.getMode() == MODE_SINGLE) {
					config.getSelectedFiles().clear();
				}
				config.getSelectedFiles().add(deviceFile);
				pageListener.updateHeader();
			}
		}
		adapter.notifyDataSetChanged();
	}

	private void captureImage(int requestCode) {
		if (!FNCameraHelper.checkCameraAvailability(getActivity())) {
			return;
		}
		Context context = getActivity().getApplicationContext();
		Intent intent = cameraModule.getCameraIntent(getActivity(), pageListener.config());
		if (intent == null) {
			Toast.makeText(context, context.getString(R.string.error_create_image_file), Toast.LENGTH_LONG).show();
			return;
		}
		getActivity().startActivityForResult(intent, requestCode);
	}

	public void finishCaptureImage(Intent data) {
		cameraModule.getImage(getContext(), data, new OnImageReadyListener() {
			@Override
			public void onImageReady(ArrayList<MediaFile> images) {
				onImageCaptured(images);
			}
		});
	}

	public void onImageCaptured(final ArrayList<MediaFile> mediaFiles) {
		if (pageListener.config().getMode() == FNFilePicker.MODE_SINGLE) {
			pageListener.config().getSelectedFiles().clear();
		}
		if (pageListener.config().isReturnAfterFirst()) {
			pageListener.openCropFragment(mediaFiles.get(0));
		} else {
			pageListener.config().getSelectedFiles().add(mediaFiles.get(0));
			Handler handler = new Handler(Looper.getMainLooper());
			handler.post(new Runnable() {
				@Override
				public void run() {
					pageListener.updateHeader();
					getLoaderManager().restartLoader(LOADER_ID, null, MediaFragment.this);
				}
			});

		}
	}

	@Override
	public boolean isSelected(AndroidDeviceFile file) {
		for (MediaFile mediaFile : pageListener.config().getSelectedFiles()) {
			if (mediaFile.getPath().equals(file.getPath())) {
				return true;
			}
		}
		return false;
	}

	public String searchQuery() {
		int mediaType = pageListener.config().getMediaType();
		return FNFileUtil.mimeTypeKey(mediaType) + " IS NOT NULL AND " + FNFileUtil.sizeKey(mediaType) + ">0";
	}
}
