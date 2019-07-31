package com.nongjinsuo.mimijinfu.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nongjinsuo.mimijinfu.R;

public class MyDialog extends Dialog {

    public MyDialog(Context context) {
        super(context);
    }

    public MyDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;
        private TextView tvTitle;
        private ProgressBar progressBar;
        public Button btnPositive;
        public Button btnNegative;
        public TextView tvNewVersion;
        public String newVersion;



        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public void setNewVersion(String newVersion) {
            this.newVersion = newVersion;
        }

        /**
         * Set the Dialog message from resource
         *
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        public ProgressBar getProgressBar() {
            return progressBar;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }


        /**
         * Set the positive button resource and it's listener
         *
         * @param positiveButtonText
         * @return
         */
        public Builder setPositiveButton(int positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = (String) context
                    .getText(positiveButtonText);
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(int negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = (String) context
                    .getText(negativeButtonText);
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public MyDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // instantiate the dialog with the custom Theme
            final MyDialog dialog = new MyDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_normal, null);
            dialog.addContentView(layout, new LayoutParams(
                    LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            tvTitle = (TextView) layout.findViewById(R.id.title);
            progressBar = (ProgressBar) layout.findViewById(R.id.my_progress);
            btnPositive = (Button) layout.findViewById(R.id.positiveButton);
            btnNegative = (Button) layout.findViewById(R.id.negativeButton);
            tvNewVersion = (TextView) layout.findViewById(R.id.tvNewVersion);
            tvNewVersion.setText("最新版本（V"+newVersion+"）");
            tvTitle.setText(title);
            // set the confirm button
            if (positiveButtonText != null) {
                btnPositive.setText(positiveButtonText);
                if (positiveButtonClickListener != null) {

                    btnPositive.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            positiveButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_POSITIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                layout.findViewById(R.id.positiveButton).setVisibility(
                        View.GONE);
            }
            // set the cancel button
            if (negativeButtonText != null) {

                btnNegative.setText(negativeButtonText);
                if (negativeButtonClickListener != null) {
                    btnNegative.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            negativeButtonClickListener.onClick(dialog,
                                    DialogInterface.BUTTON_NEGATIVE);
                        }
                    });
                }
            } else {
                // if no confirm button just set the visibility to GONE
                btnNegative.setVisibility(View.GONE);
            }
            // set the content message
            TextView tvMessage = (TextView) layout.findViewById(R.id.message);
            if (message != null) {
                tvMessage.setVisibility(View.VISIBLE);
                tvMessage.setText(message);
            } else {
                tvMessage.setVisibility(View.GONE);
            }
            dialog.setCanceledOnTouchOutside(false);
            dialog.setContentView(layout);
            return dialog;
        }
    }
}
