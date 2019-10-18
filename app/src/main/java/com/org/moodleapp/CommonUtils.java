package com.org.moodleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {
    /**
     * method to inflate the fragment with animation
     */
    public static void inflateFragment(Fragment iFragmentToInflate, FragmentManager iFragmentManager, int iContentFrameId, Boolean isAddToBacStack, Boolean isReplace) {
        inflateFragment(iFragmentToInflate, iFragmentManager, iContentFrameId, isAddToBacStack, isReplace, null);
    }

    /**
     * method to inflate the fragment with animation and bundle
     */
    public static void inflateFragment(Fragment iFragmentToInflate, FragmentManager iFragmentManager, int iContentFrameId, Boolean isAddToBacStack, Boolean isReplace, Bundle iBundle) {
        try {

            FragmentTransaction transaction = iFragmentManager.beginTransaction();
            String TAG = iFragmentToInflate.getClass().getSimpleName();

            if (isReplace) transaction.replace(iContentFrameId, iFragmentToInflate, TAG);
            else transaction.add(iContentFrameId, iFragmentToInflate, TAG);

            if (isAddToBacStack) transaction.addToBackStack(TAG);

            if (iBundle != null) iFragmentToInflate.setArguments(iBundle);
            transaction.commit();
        } catch (IllegalStateException e) {

        }
    }

    /**
     *  open email app
     * @param emailId to email id
     * @param subject email subject
     * @param mailBody email body
     */
    public static Intent sendMailIntent(String [] emailId, String subject, String mailBody) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.putExtra(Intent.EXTRA_EMAIL, emailId);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, mailBody);

        intent.setType("message/rfc822");
        intent.setPackage("com.google.android.gm");
        return intent;
    }

    public  static List<String> courseArray = Arrays.asList("Select Your Course ......","Bachelor’s in Business", "Bachelor’s in Computing and Multimedia","HealthCare Support","ChildCare Course");

    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
