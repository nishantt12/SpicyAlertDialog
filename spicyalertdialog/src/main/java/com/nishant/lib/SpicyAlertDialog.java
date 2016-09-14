package com.nishant.lib;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by nishant on 10/9/16.
 */
public class SpicyAlertDialog extends Dialog {

    Context context;

    private View mDialogView;

    private AnimationSet mModalOutAnim;
    private Animation mOverlayOutAnim;

    private AnimationSet mErrorXInAnim;

    private TextView mTitleTextView;
    private TextView mContentTextView;
    private String mTitleText;
    private String mContentText;
    private boolean mShowContent;

    private Drawable mCustomImgDrawable;
    private CircleImageView mCustomImage;
    private TextView mConfirmButton;
    private TextView mCancelButton;

    private OnSpicyClickListener mCancelClickListener;
    private OnSpicyClickListener mConfirmClickListener;
    private boolean mCloseFromCancel;

    public static interface OnSpicyClickListener {
        public void onClick(SpicyAlertDialog spicyAlertDialog);
    }

    public SpicyAlertDialog(Context context) {
        this(context, 1);
    }

    public SpicyAlertDialog(Context context, int alertType) {

        super(context, R.style.alert_dialog);

        this.context = context;

        setCancelable(true);
        setCanceledOnTouchOutside(false);

        mErrorXInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.error_x_in);
        // 2.3.x system don't support alpha-animation on layer-list drawable
        // remove it from animation set
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            List<Animation> childAnims = mErrorXInAnim.getAnimations();
            int idx = 0;
            for (; idx < childAnims.size(); idx++) {
                if (childAnims.get(idx) instanceof AlphaAnimation) {
                    break;
                }
            }
            if (idx < childAnims.size()) {
                childAnims.remove(idx);
            }
        }

        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCloseFromCancel) {
                            SpicyAlertDialog.super.cancel();
                        } else {
                            SpicyAlertDialog.super.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // dialog overlay fade out
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = getWindow().getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mTitleTextView = (TextView) findViewById(R.id.mainTitle);
        mContentTextView = (TextView) findViewById(R.id.mainContent);

        mContentTextView.setText("kjjfkw");
        mTitleTextView.setText("kjjfkw");

        mCustomImage = (CircleImageView) findViewById(R.id.customImage);

        Log.e("create", "create");
        setCustomImage(R.drawable.piggy_graphic);
        Log.e("create", "create2");

        mConfirmButton = (TextView) findViewById(R.id.onOk);
        mCancelButton = (TextView) findViewById(R.id.onCancel);

        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mConfirmClickListener != null) {
                    mConfirmClickListener.onClick(SpicyAlertDialog.this);
                } else {
                    dismissWithAnimation();
                }

            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mCancelClickListener != null) {
                    mCancelClickListener.onClick(SpicyAlertDialog.this);
                } else {
                    dismissWithAnimation();
                }

            }
        });

        setTitleText(mTitleText);
        setContentText(mContentText);


    }

    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    private void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        mConfirmButton.startAnimation(mOverlayOutAnim);
        mDialogView.startAnimation(mModalOutAnim);
    }

    public String getTitleText() {
        return mTitleText;
    }

    public SpicyAlertDialog setTitleText(String text) {
        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            mTitleTextView.setText(mTitleText);
        }
        return this;
    }

    public String getContentText() {
        return mContentText;
    }

    public SpicyAlertDialog setContentText(String text) {
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            showContentText(true);
            mContentTextView.setText(mContentText);
        }
//        mContentTextView.setText("kjbkbhkbk");
        return this;
    }

    public SpicyAlertDialog showContentText(boolean isShow) {
        mShowContent = isShow;
        if (mContentTextView != null) {
            mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
        }
        return this;
    }


    public SpicyAlertDialog setCustomImage(Drawable drawable) {

        Log.e("setCustomImage", "setCustomImage");

        mCustomImgDrawable = drawable;
        if (mCustomImage != null && mCustomImgDrawable != null) {
            mCustomImage.setVisibility(View.VISIBLE);
            mCustomImage.setImageDrawable(mCustomImgDrawable);
        }

        return this;
    }

    public SpicyAlertDialog setCustomImage(int resourceId) {
        return setCustomImage(getContext().getResources().getDrawable(resourceId));
    }

    public SpicyAlertDialog setCancelClickListener(OnSpicyClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }

    public SpicyAlertDialog setConfirmClickListener(OnSpicyClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }
}
