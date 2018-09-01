package com.atm.ast.astatm.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.utils.ASTStringUtil;
import com.atm.ast.astatm.utils.ASTUIUtil;


public class TileView extends LinearLayout {

    protected TextView titleView;
    protected ImageView imageView;
    protected View view;
    protected CardView cardViewLayout;
    protected LinearLayout imageContainer;
    private int imgHeight,imgWidth;
    public TileView(Context context) {
        this(context, null);
    }

    public TileView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TileView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray array=getContext().obtainStyledAttributes(attrs, R.styleable.TileView,defStyle,0);
        if (array.hasValue(R.styleable.TileView_imgHeight)) {
            imgHeight = (int)array.getDimension(R.styleable.TileView_imgHeight, getResources().getDimension(R.dimen._30dp));
        }
        if (array.hasValue(R.styleable.TileView_imgWidth)) {
            imgWidth = (int)array.getDimension(R.styleable.TileView_imgWidth, getResources().getDimension(R.dimen._30dp));
        }
        array.recycle();
        this.init();
    }

    private void init() {
        if (this.isInEditMode()) {
            return;
        }
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.view = inflater.inflate(layoutId(), this, true);
        this.titleView = view.findViewById(R.id.title);
        this.imageView = view.findViewById(R.id.image);
        this.imageContainer = view.findViewById(R.id.imageContainer);

        this.cardViewLayout = view.findViewById(R.id.cardViewLayout);
        setTileImageSize();
        addImageCircle();
    }
    public void setTileImageSize() {
        if (this.imgHeight > 0 && imgWidth > 0) {
            LayoutParams parms = new LayoutParams(imgWidth,imgHeight);
            this.imageView.setLayoutParams(parms);
        }
    }
    protected int layoutId() {
        return R.layout.fn_tile_view ;
    }

    public void setTitle(String title) {
        TextView titleView=getTileView();
        if (titleView == null) {
            return;
        }
        titleView.setText(title);
    }

    public String getTitle(){
        if(titleView!=null){
            return titleView.getText().toString();
        }
        return "";
    }
    public void setTitle(@StringRes int title) {
        setTitle(ASTStringUtil.getStringForID(title));
    }

    public void setTitleColor(@ColorRes int colorId) {
        TextView titleView=getTileView();
        if (titleView == null) {
            return;
        }
        int resId = ASTUIUtil.getColor(colorId);
        if (resId != 0) {
            titleView.setTextColor(resId);
        }
    }


    public void hideImage() {
        if (imageView != null) {
            this.imageView.setVisibility(View.GONE);
        }
    }

    private boolean isImageViewInit(){
        if (this.imageView == null) {
            return false;
        }
        this.imageView.setVisibility(View.VISIBLE);
        return true;
    }


    public void setImageDrawable(Drawable icon) {
        if(isImageViewInit()){
            this.imageView.setImageDrawable(icon);
        }
    }
    public void setImageResource(@DrawableRes int icon) {
        if(isImageViewInit()){
            this.imageView.setImageResource(icon);
        }
    }



    public void hideTitleView() {
        if (titleView != null) {
            titleView.setVisibility(GONE);
        }
    }

    public void addImageCircle(){
        imageContainer.post(new Runnable() {
            @Override
            public void run() {
                int radius=imageContainer.getWidth()>imageContainer.getHeight()?imageContainer.getWidth()/2:imageContainer.getHeight()/2;
             }
        });
    }
    private TextView getTileView(){
       if(titleView!=null){
           return   titleView;
       }
         return null;
    }

    public void setCardViewBg(int colorId) {
        if (this.cardViewLayout != null) {
            cardViewLayout.setCardBackgroundColor(ASTUIUtil.getColor(colorId));
        }
    }
}
