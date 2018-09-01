package com.atm.ast.astatm.filepicker;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;

import com.atm.ast.astatm.ApplicationHelper;
import com.atm.ast.astatm.R;
import com.atm.ast.astatm.utils.ASTUIUtil;

/**
 * @author AST Inc.
 */
public class FNFontViewField extends AppCompatTextView {

	public FNFontViewField(Context context) {
		this(context, null);
	}

	public FNFontViewField(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public FNFontViewField(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.init();
	}

	private void init() {
		if (this.isInEditMode()) {
			return;
		}
		try {
			setTypeface(ApplicationHelper.application().getIconTypeFace());
		} catch (Exception e) {
		}
		if (getTextColors() == null) {
			this.setTextColor(ASTUIUtil.getColor(R.color.black));
		}
	}

	@Override
	public void setTextSize(float size) {
		super.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
	}

	public void setTextDimen(@DimenRes int dimenID) {
		super.setTextSize(TypedValue.COMPLEX_UNIT_PX, ASTUIUtil.getDimension(dimenID));
	}
}
