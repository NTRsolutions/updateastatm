package com.atm.ast.astatm.filepicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.filepicker.helper.FileLoaderTask;
import com.atm.ast.astatm.filepicker.listener.FilePickerCallback;
import com.atm.ast.astatm.filepicker.model.FNUIEntity;
import com.atm.ast.astatm.filepicker.model.MediaFile;
import com.atm.ast.astatm.filepicker.view.GridSpacingItemDecoration;
import com.atm.ast.astatm.utils.ASTStringUtil;
import com.atm.ast.astatm.utils.FNObjectUtil;

import java.util.ArrayList;
import java.util.List;

import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;
import static android.provider.MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

/**
 * Created 05-06-2017
 *
 * @author AST Inc.
 */
public abstract class FNFilePicker {

    public static final int MEDIA_TYPE_DOCUMENT = 5;
    public static final String EXTRA_SELECTED_MEDIA = "selectedMedia";
    public static final String EXTRA_LIMIT = "limit";
    public static final String EXTRA_SIZE_LIMIT = "sizeLimit";
    public static final String EXTRA_SHOW_CAMERA = "showCamera";
    public static final String EXTRA_MODE = "mode";
    public static final String EXTRA_FOLDER_MODE = "folderMode";
    public static final String EXTRA_IMAGE_DIRECTORY = "imageDirectory";
    public static final String EXTRA_RETURN_AFTER_FIRST = "returnAfterFirst";
    public static final String EXTRA_MEDIA_TYPE = "mediaType";
    public static final String EXTRA_SHOW_DONE = "showDone";
    public static final String EXTRA_HEADER_TITLE = "headerTitle";
    public static final String EXTRA_SHOW_ROTATE = "showRotate";
    public static final String EXTRA_SHOW_CROP = "showCrop";
    public static final String EXTRA_DONE_TEXT = "doneText";
    public static final String EXTRA_CROP_MODE = "cropMode";
    public static final String EXTRA_PICKER_MENU = "picker_menu";
    public static final int MAX_LIMIT = 10;
    public static final long SIZE_LIMIT = 5 * 1024 * 1024; // 5mb
    public static final int MODE_SINGLE = 1;
    public static final int MODE_MULTIPLE = 2;
    public static String SCHEME_VIDEO = "video";
    protected FNFilePickerConfig config;

    protected ArrayList<FNUIEntity> mediaTypes;

    public static BottomDialog options(Activity activity) {
        return new BottomDialog(activity);
    }

    public static void getPickedFiles(Context context, Intent intent, final FilePickerCallback callback) {
        if (intent == null) {
            if (callback != null)
                callback.onError();
            return;
        }
        ArrayList<MediaFile> files = intent.getParcelableArrayListExtra(FNFilePicker.EXTRA_SELECTED_MEDIA);
        if (FNObjectUtil.isEmpty(files)) {
            if (callback != null)
                callback.onError();
            return;
        }
        FileLoaderTask loaderTask = new FileLoaderTask(context) {
            @Override
            protected void onPostExecute(ArrayList<MediaFile> deviceFiles) {
                super.onPostExecute(deviceFiles);
                if (callback != null)
                    callback.onSuccess(deviceFiles);
            }
        };
        loaderTask.execute(files);
    }

    public abstract void start(int requestCode);

    public void init(Context context) {
        config = new FNFilePickerConfig(context);
    }

    public FNFilePicker single() {
        config.setMode(FNFilePicker.MODE_SINGLE);
        return this;
    }

    public FNFilePicker multi() {
        config.setMode(FNFilePicker.MODE_MULTIPLE);
        return this;
    }

    public FNFilePicker addMedia(int mediaType) {
        if (mediaTypes == null) {
            mediaTypes = new ArrayList<>();
        }
        mediaTypes.add(getUIItem(mediaType));
        return this;
    }

    private FNUIEntity getUIItem(int mediaType) {
        FNUIEntity fnuiEntity = new FNUIEntity();
        fnuiEntity.tag = mediaType;
        switch (mediaType) {
            case MEDIA_TYPE_AUDIO:
                fnuiEntity.setDetail1(ASTStringUtil.getStringForID(R.string.picker_audio));
                fnuiEntity.setDetail2(ASTStringUtil.getStringForID(R.string.icon_volume));
                break;
            case MEDIA_TYPE_IMAGE:
                fnuiEntity.setDetail1(ASTStringUtil.getStringForID(R.string.picker_images));
                fnuiEntity.setDetail2(ASTStringUtil.getStringForID(R.string.icon_image));
                break;
            case MEDIA_TYPE_VIDEO:
                fnuiEntity.setDetail1(ASTStringUtil.getStringForID(R.string.picker_videos));
                fnuiEntity.setDetail2(ASTStringUtil.getStringForID(R.string.icon_play));
                break;
            case MEDIA_TYPE_DOCUMENT:
                fnuiEntity.setDetail1(ASTStringUtil.getStringForID(R.string.picker_documents));
                fnuiEntity.setDetail2(ASTStringUtil.getStringForID(R.string.icon_file));
                break;
        }
        return fnuiEntity;
    }

    public FNFilePicker returnAfterFirst(boolean returnAfterFirst) {
        config.setReturnAfterFirst(returnAfterFirst);
        return this;
    }

    public FNFilePicker limit(int count) {
        config.setLimit(count);
        return this;
    }

    public FNFilePicker sizeLimit(long size) {
        config.setSizeLimit(size);
        return this;
    }

    public FNFilePicker showCamera(boolean show) {
        config.setShowCamera(show);
        return this;
    }

    public FNFilePicker folderMode(boolean folderMode) {
        config.setFolderMode(folderMode);
        return this;
    }

    public FNFilePicker imageDirectory(String directory) {
        config.setImageDirectory(directory);
        return this;
    }

    public Intent getIntent(Context context) {
        Intent intent = new Intent(context, FNFilePickerActivity.class);
        intent.putExtra(FNFilePickerConfig.class.getSimpleName(), config);
        return intent;
    }

    public void startPickerActivity(Activity context, int requestCode) {
        Intent intent = new Intent(context, FNFilePickerActivity.class);
        intent.putExtra(FNFilePickerConfig.class.getSimpleName(), config);
        context.startActivityForResult(intent, requestCode);
    }

    public static class BottomDialog extends FNFilePicker {

        int requestCode;
        Activity context;
        BottomSheetDialog dialog;

        public BottomDialog(Activity context) {
            this.context = context;
            init(context);
        }

        @Override
        public void start(int requestCode) {
            this.requestCode = requestCode;
            if (mediaTypes.size() == 1) {
                config.setMediaType((Integer) mediaTypes.get(0).tag);
                startPickerActivity(context, requestCode);
            } else {
                buildDialog();
                dialog.show();
            }
        }

        private void buildDialog() {
            dialog = new BottomSheetDialog(context);
            dialog.setContentView(R.layout.picker_choice_dialog);
            dialog.setCanceledOnTouchOutside(true);
            RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);

            GridLayoutManager layoutManager = new GridLayoutManager(context, mediaTypes.size());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);

            layoutManager.setSpanCount(mediaTypes.size());
            GridSpacingItemDecoration itemOffsetDecoration = new GridSpacingItemDecoration(mediaTypes.size(), context.getResources().getDimensionPixelSize(R.dimen._1dp), false);
            recyclerView.addItemDecoration(itemOffsetDecoration);

            recyclerView.setAdapter(new PickerDialogAdaptor(context, mediaTypes));
            dialog.show();
        }

        private class PickerDialogAdaptor extends RecyclerView.Adapter<PickerDialogAdaptor.ViewHolder> {

            Activity context;
            List<FNUIEntity> items = new ArrayList<>();

            public PickerDialogAdaptor(Activity context, List<FNUIEntity> items) {
                this.items = items;
                this.context = context;
            }

            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(this.context).inflate(R.layout.picker_choice_item, parent, false);
                return new ViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(ViewHolder holder, int position) {
                final FNUIEntity fnuiEntity = items.get(position);
                holder.itemName.setText(fnuiEntity.getDetail1());
                holder.itemIcon.setIcon(fnuiEntity.getDetail2());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        config.setMediaType((Integer) fnuiEntity.tag);
                        startPickerActivity(context, requestCode);
                        dialog.dismiss();
                    }
                });
            }

            @Override
            public int getItemCount() {
                return items.size();
            }

            public class ViewHolder extends RecyclerView.ViewHolder {

                FNFrameIcon itemIcon;
                FNTextView itemName;

                public ViewHolder(View itemView) {
                    super(itemView);
                    this.itemIcon = itemView.findViewById(R.id.frameIcon);
                    this.itemName = itemView.findViewById(R.id.itemName);
                }
            }
        }
    }
}
