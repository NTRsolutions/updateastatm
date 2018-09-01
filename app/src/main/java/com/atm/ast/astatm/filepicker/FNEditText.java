package com.atm.ast.astatm.filepicker;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

import com.atm.ast.astatm.R;
import com.atm.ast.astatm.utils.ASTEnum;
import com.atm.ast.astatm.utils.ASTUIUtil;


/**
 * <h4>Created</h4> 16/02/17
 *
 * @author AST Inc.
 */
public class FNEditText extends AppCompatEditText {

	public FNEditText(Context context) {
		super(context);
		init();
	}

	public FNEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public FNEditText(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		if (this.isInEditMode()) {
			return;
		}
		if (getTypeface() != null) {
			ASTUIUtil.setFontTypeFace(this, getTypeface().getStyle());
		} else {
			this.setTypeFace(ASTEnum.FONT_REGULAR);
		}
		if (getTextColors() == null) {
			this.setTextColor(ASTUIUtil.getColor(R.color.black));
		}
	}

	public void setTypeFace(ASTEnum fontTypeFace) {
		ASTUIUtil.setFontTypeFace(this, fontTypeFace);
	}
}
