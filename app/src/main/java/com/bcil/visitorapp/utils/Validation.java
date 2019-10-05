package com.bcil.visitorapp.utils;
import android.app.Activity;
import android.graphics.Color;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static final String PHONE_REGEX = "^[789]\\d{9}$";

    public static final String PHONE_MSG = "invalid number";

    public static final String EMAIL = "invalid email address";

    public static final String REQUIRED_MSG =  "cannot be empty";
    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);
        editText.setFocusable(true);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

    public static boolean checkMobileNumber(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);
        editText.setFocusable(true);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }else  if (!Validation.isValidMobile(editText.getText().toString().trim())) {
            editText.setError(Validation.PHONE_MSG);
            return  false;
        }

        return true;
    }

    public static boolean checkEmailAddress(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);
        editText.setFocusable(true);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }else  if (!(!TextUtils.isEmpty(editText.getText().toString().trim()) && Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString().trim()).matches())) {
            editText.setError(Validation.EMAIL);
            return  false;
        }

        return true;
    }

    public static boolean checkScanData(String scanData , EditText editText) {

        editText.setError(null);
        editText.setFocusable(true);
        // length 0 means there is no text
        if(scanData == null)
            scanData = AppConstants.EMPTY;
        if (scanData.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

    public static boolean hasTextForTextView(TextView editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);
        editText.setFocusable(true);
        // length 0 means there is no text
        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

    public static boolean hasActText(AutoCompleteTextView autoCompleteTextView) {

        String text = autoCompleteTextView.getText().toString().trim();
        autoCompleteTextView.setError(null);
        autoCompleteTextView.setFocusable(true);
        // length 0 means there is no text
        if (text.length() == 0) {
            autoCompleteTextView.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }

    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    private static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;
        if(regex.equalsIgnoreCase(PHONE_REGEX)){
            return PhoneNumberUtils.isGlobalPhoneNumber(editText.getText().toString().trim());
        }else {

            // pattern doesn't match so returning false
            if (required && !Pattern.matches(regex, text)) {
                editText.setError(errMsg);
                return false;
            }
        }

        return true;
    }


    public static boolean isValidMobile(String mobileno) {
        Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(mobileno);
        return (m.find() && m.group().equals(mobileno));
    }

    public static boolean selectedSpinner(Spinner snUom, FragmentActivity activity) {
        boolean ret = true;
        if (snUom.getSelectedItem() != null) {
            if (snUom.getSelectedItem().toString().equals("Select Uom")) {
                TextView errorText = (TextView) snUom.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);
                String message = "Please select uom";
                errorText.setText(message);
                ret = false;
            }
        } else {
            Toast.makeText(activity, "Please select uom", Toast.LENGTH_SHORT).show();
            ret = false;
        }
        return ret;
    }

    public static boolean selectedTransNo(Spinner spinner, FragmentActivity activity) {
        boolean ret = true;
        if (spinner.getSelectedItem() != null) {
            if (spinner.getSelectedItem().toString().equals("Select Transaction No")) {
                TextView errorText = (TextView) spinner.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);
                String message = "Please select transno.";
                errorText.setText(message);
                ret = false;
            }
        } else {
            Toast.makeText(activity, "Please select transno.", Toast.LENGTH_SHORT).show();
            ret = false;
        }
        return ret;
    }

    public static boolean selectedPlant(Spinner spinner, Activity activity) {
        boolean ret = true;
        if (spinner.getSelectedItem() != null) {
            if (spinner.getSelectedItem().toString().equals("Select Plant")) {
                TextView errorText = (TextView) spinner.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);
                String message = "Please select plant.";
                errorText.setText(message);
                ret = false;
            }
        } else {
            Toast.makeText(activity, "Please select plant.", Toast.LENGTH_SHORT).show();
            ret = false;
        }
        return ret;
    }

    public static boolean selectedAvlLoc(Spinner snAvLoc, FragmentActivity activity) {
        boolean ret = true;
        if (snAvLoc.getSelectedItem() != null) {
            if (snAvLoc.getSelectedItem().toString().equals("Select Available Loc")) {
                TextView errorText = (TextView) snAvLoc.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);
                String message = "Please select loc";
                errorText.setText(message);
                ret = false;
            }
        } else {
            Toast.makeText(activity, "Please select loc", Toast.LENGTH_SHORT).show();
            ret = false;
        }
        return ret;
    }
}
