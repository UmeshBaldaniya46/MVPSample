package com.MVP.Sample.utils;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.MVP.Sample.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Utility {

    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\._%\\-\\+]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");
    private static final String TAG = Utility.class.getSimpleName();
    private static Snackbar snackbar;
    private static Dialog progressDialog = null;
    private static final Hashtable<String, Typeface> cache = new Hashtable<>();
    private static Dialog dialog;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }

    public static float getDensity(Context mContext) {
        return mContext.getResources().getDisplayMetrics().density;
    }

    public static void hideKeyboard(Activity aContext) {
        if (aContext != null) {
            InputMethodManager im = (InputMethodManager) aContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(aContext.getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showKeyboard(EditText aEditText, Activity aContext) {
        if (aContext != null) {
            InputMethodManager im = (InputMethodManager) aContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            im.showSoftInput(aEditText, InputMethodManager.SHOW_IMPLICIT);
        }
    }


    public static RequestBody getRequest(String reqString) {
        MediaType mediaType = MediaType.parse(Constants.ApiHeaders.API_TYPE_JSON);
        return RequestBody.create(mediaType, reqString);
    }

    public static String join(ArrayList<String> list, String delim) {

        StringBuilder sb = new StringBuilder();
        String loopDelim = "";
        for (String s : list) {
            sb.append(loopDelim);
            sb.append(s);
            loopDelim = delim;
        }
        return sb.toString();
    }


    public static void showSnackBar(View view, String message, int time, boolean isTypeError, final Context context) {

        final Snackbar snackbar = Snackbar.make(view, message, time);
        View snackBarView = snackbar.getView();
        TextView snackTextView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        snackTextView.setMaxLines(4);
        if (isTypeError) {
            snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
        } else {
            snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.green));
        }
        snackbar.show();
    }

    public static void hideSnackBar() {
        if (snackbar != null && snackbar.isShown()) {
            snackbar.dismiss();
        }

    }


    public static void showSnackBarWithOneActionButton(View view, String message, int time, String actionTitle, View.OnClickListener actionClickListener) {
        Snackbar snackbar = Snackbar
                .make(view, message, time)
                .setAction(actionTitle, actionClickListener);
        snackbar.show();

    }


    public static void showSnackBarWithAction(View view, final String message, final int time, final String actionName, View.OnClickListener aOnclick) {
        Snackbar snackbar = Snackbar
                .make(view, message, time)
                .setAction(actionName, aOnclick);

        snackbar.show();
    }

    public static void showSnackBarWithOK(View view, final String message, final int time) {
        final Snackbar snackBar = Snackbar.make(view, message, time);
        snackBar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });
        snackBar.show();
    }

    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }


    public static double limitDecimalPoint(String value) {
        double d = Double.parseDouble(value);
        return limitDecimalPoint(d);
    }

    public static double limitDecimalPoint(double d) {
        DecimalFormat df = new DecimalFormat("0.00");
        String s = df.format(d);
        double output = Double.parseDouble(s);
        return output;
    }

    public static String removeDecimalPoint(String value) {
        double d = Double.parseDouble(value);
        DecimalFormat df = new DecimalFormat("0.00");
        String s = df.format(d);
        double output = Double.parseDouble(s);
        if (s.endsWith(".00")) {
            // return String.valueOf((int)output);
            return s;
        }
        return String.valueOf(df.format(output));
    }

    public static String stringToStringDecimalFormat(String value) {
        double d = Double.parseDouble(value);
        DecimalFormat df = new DecimalFormat("0.00");
        String s = df.format(d);
        return s;

    }

    public static void showDialog(Context context, String title, String message, Boolean isCancelable) {
        if (context != null && !((Activity) context).isFinishing()) {
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setMessage(message);
            alertDialog.setCancelable(isCancelable);
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            if (!alertDialog.isShowing()) {
                alertDialog.show();
            }
        } else {

        }
    }

    /**
     * This method is used to show progress indicator dialog with message when
     * web service is called.
     */
    public static void showProgressDialog(Context context, String message) {

        if (context != null && !((Activity) context).isFinishing()) {
            if (progressDialog == null || !progressDialog.isShowing()) {
                progressDialog = new Dialog(context);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                try {
                    int dividerId = progressDialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
                    View divider = progressDialog.findViewById(dividerId);
                    if (divider != null)
                        divider.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    TextView mTitle = (TextView) progressDialog.findViewById(android.R.id.title);
                    if (mTitle != null)
                        mTitle.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    int x = Resources.getSystem().getIdentifier("titleDivider", "id", "android");
                    View titleDivider = progressDialog.findViewById(x);
                    if (titleDivider != null)
                        titleDivider.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));

                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.setContentView(R.layout.custom_progressbar);
                TextView tvMessage = (TextView) progressDialog.findViewById(R.id.txtMessage);
                if (!message.equals("")) {
                    tvMessage.setText(message);
                }
                progressDialog.setCancelable(false);
                if (!((Activity) context).isFinishing())
                    progressDialog.show();
            }
        } else {
            Log.e(TAG, context.toString() + " Context Null");

        }
    }


    public static void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Throwable throwable) {

        } finally {
            progressDialog = null;
        }
    }


    /*
     * Check Permission at Runtime for above Marshmallow Version
     **/
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {

        int currentAPIVersion = Build.VERSION.SDK_INT;
        String[] PERMISSIONS = {Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (currentAPIVersion >= Build.VERSION_CODES.M) {

            for (String permission : PERMISSIONS) {
                if (ContextCompat.checkSelfPermission(context, permission)
                        != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {

                        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                        alertBuilder.setCancelable(true);
                        alertBuilder.setTitle(R.string.label_permission_necessary);
                        alertBuilder.setMessage(R.string.label_external_storage_permission);

                        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                            }
                        });
                        AlertDialog alert = alertBuilder.create();
                        alert.show();
                    } else {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
        return true;
    }

    /*
     * For Set Current Timestamp Name
     * */
    public static String getDateString() {
        return new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static Animation floatingVisibleAnimation(Context mContext, final View view) {
        view.setVisibility(View.VISIBLE);
        Log.e(TAG, "floatingVisibleAnimation ");
        final Animation growAnimation = AnimationUtils.loadAnimation(mContext, R.anim.simple_grow);
        growAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return growAnimation;
    }



    public static String getCompereDate(String orgDate) {
        SimpleDateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date datestartdate = inputFormat1.parse(orgDate);
            return inputFormat.format(datestartdate);
        } catch (Exception e) {
            return orgDate;
        }
    }

    public static String getDisplayDate(String orgDate) {
        SimpleDateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy");

        try {
            Date datestartdate = inputFormat1.parse(orgDate);
            return inputFormat.format(datestartdate);
        } catch (Exception e) {
            return orgDate;
        }
    }

    public static String getDisplayDateTime(String orgDate) {
        SimpleDateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");

        try {
            Date datestartdate = inputFormat1.parse(orgDate);
            return inputFormat.format(datestartdate);
        } catch (Exception e) {
            return orgDate;
        }
    }

    public static String getDisplayTime(String orgDate) {
        SimpleDateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm");

        try {
            Date datestartdate = inputFormat1.parse(orgDate);
            return inputFormat.format(datestartdate);
        } catch (Exception e) {
            return orgDate;
        }
    }

    public static int getYear(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Log.e(TAG, "inputFormat1.parse(date) " + inputFormat1.parse(date));
            cal.setTime(inputFormat1.parse(date));
            return Integer.parseInt((new SimpleDateFormat("yyyy").format(inputFormat1.parse(date))));
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getMonth(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            cal.setTime(inputFormat1.parse(date));
            return Integer.parseInt((new SimpleDateFormat("MM").format(inputFormat1.parse(date))));
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getDay(String date) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat inputFormat1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            cal.setTime(inputFormat1.parse(date));
            return Integer.parseInt((new SimpleDateFormat("dd").format(inputFormat1.parse(date))));
        } catch (Exception e) {
            return 0;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }


    /**
     * @param context     context of your activity
     * @param textView    textview object instance
     * @param drawableRes drawable resource
     * @param tintRes     color resources
     * @Method: setTintedCompoundDrawable()
     * Use to set TextView's left compoundDrawable programmatically
     * @tintDrawable() to change color of drawable tint
     */
    public static void setTintedCompoundDrawable(Context context, TextView textView, int drawableRes, int tintRes) {

        textView.setCompoundDrawablesWithIntrinsicBounds(
                tintDrawable(ContextCompat.getDrawable(context, drawableRes), ContextCompat.getColor(context, tintRes)),  // Left
                null, // Top
                null, // Right
                null); //Bottom
        // if you need any space between the icon and text.
        textView.setCompoundDrawablePadding(12);
    }

    /**
     * @param drawable drawable resource
     * @param tint     color resource tint
     * @return
     * @Method tintDrawable()
     */
    public static Drawable tintDrawable(Drawable drawable, int tint) {

        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, tint);
        DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_ATOP);

        return drawable;
    }

    /**
     * @param context
     * @param textView
     * @param colorTint
     * @param pos       0=left,1=top,2=right,3=bottom
     * @method :  tintColorDrawable()
     * Use to change or set color of drawable tint color
     */
    public static void tintColorDrawable(Context context, TextView textView, int colorTint, int pos) {

        DrawableCompat.setTint(textView.getCompoundDrawables()[pos], ContextCompat.getColor(context, colorTint));

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } else {
            Log.e(TAG, "Software Keyboard was not shown");
        }
    }

}
