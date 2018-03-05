package com.example.ludwi.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * FingerPrintActivity checks the users finger print and acts as a log in into the app.
 * Created by Ludwig Ninn on 2017-02-09.
 */
public class FingerprintActivity extends AppCompatActivity {
    FingerprintManager mFingerprintManager;
    static final int  FINGERPRINT_PERMISSION_REQUEST_CODE =1;
    AlertDialog mFingerPrintDialog;
    CancellationSignal mCancellationSignal;
    Cipher mCipher;
    KeyStore mKeyStore;
    KeyGenerator mKeyGenerator;
    String KEY_NAME="KEYNAME";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);
        mFingerprintManager = (FingerprintManager) getSystemService
                (FINGERPRINT_SERVICE);
        //As soon as Activity starts, check for the finger print conditions

        checkFingerPrintConditions();

    }

    /**
     *CheckFingerPrintConditions checks for the finger print conditions
     */
    public void checkFingerPrintConditions() {
        if (mFingerprintManager.isHardwareDetected()) {
            if (mFingerprintManager.hasEnrolledFingerprints()) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) !=
                        PackageManager.PERMISSION_GRANTED) {
                    //Requesting runtime finger print permission
                    requestPermissions(new String[]
                            {Manifest.permission.USE_FINGERPRINT}, FINGERPRINT_PERMISSION_REQUEST_CODE);
                } else {
                    //After all 3 conditions are met, then show FingerPrint Dialog
                    showFingerPrintDialog();
                }
            } else {
                showAlertDialog("Finger Print Not Registered!", "Go to Settings -> Security -> Fingerprint and register at least one fingerprint");
            }
        } else {
            showAlertDialog("Finger Print Sensor Not Found!", "Finger Print Sensor could not be found on your phone.");
        }
    }

    /**
     * Checks the persmissons and requestcode.
     * @param requestCode
     * @param permissions
     * @param state
     */
    public void onRequestPermissionsResult(int requestCode, String[]
            permissions, int[] state) {
        //show FingerPrint Dialog, when runtime permission is granted
        if (requestCode == FINGERPRINT_PERMISSION_REQUEST_CODE
                && state[0] == PackageManager.PERMISSION_GRANTED) {
            showFingerPrintDialog();
        }
    }

    /**
     * Alertdialog for user feedback.
     * @param title
     * @param message
     */
    public void showAlertDialog(String title, String message) {
        new AlertDialog.Builder(this).setTitle(title)
                .setMessage(message).setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * Shows the finherprintdialog which acts as feedback for the user.
     */
    public void showFingerPrintDialog() {
        //First Initialize the FingerPrint Settings
        if (initFingerPrintSettings()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Waiting for fingerprint authorization")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mCancellationSignal.cancel();
                            mFingerPrintDialog.dismiss();
                        }
                    });
            // Create the AlertDialog object and return it
            mFingerPrintDialog = builder.create();
            //Stops the cancelling of the fingerprint dialog
            //by back press or touching accidentally on screen
            mFingerPrintDialog.setCanceledOnTouchOutside(false);
            mFingerPrintDialog.setCancelable(false);
            mFingerPrintDialog.show();
        } else {
            showAlertDialog("Error!", "Error in initiating FingerPrint Cipher or Key!");
        }
    }

    /**
     * initFingerPrintSettings Checks the permissons on the phone.
     * @return
     */
    public boolean initFingerPrintSettings() {

//CancellationSignal requests authenticate api to stop scanning
        mCancellationSignal = new CancellationSignal();
        if (initKey() && initCipher()) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED)
            {

                requestPermissions(new String[] {Manifest.permission.USE_FINGERPRINT},FINGERPRINT_PERMISSION_REQUEST_CODE);
            }
            else{
                mFingerprintManager.authenticate(
                        new FingerprintManager.CryptoObject(mCipher),
                        mCancellationSignal,
                        0,
                        new AuthenticationListener(),
                        null
                );
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Initkey initializes the key.
     * @return
     */
    public boolean initKey() {
        try {
            mKeyStore = KeyStore.getInstance("AndroidKeyStore");
            mKeyStore.load(null);
            mKeyGenerator = KeyGenerator.getInstance
                    (KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            mKeyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT |
                    KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                    .build());
            mKeyGenerator.generateKey();

            return true;
        } catch (Exception e) {

            return false;
        }
    }

    /**
     * initCipher initializes the cipher needed for the key initializes.
     * @return
     */
    public boolean initCipher() {
        try {
            mKeyStore.load(null);
            SecretKey key = (SecretKey) mKeyStore.getKey(KEY_NAME, null);
            mCipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES +
                    "/" + KeyProperties.BLOCK_MODE_CBC + "/" +
                    KeyProperties.ENCRYPTION_PADDING_PKCS7);
            mCipher.init(Cipher.ENCRYPT_MODE, key);

            return true;
        } catch (KeyStoreException | CertificateException |
                UnrecoverableKeyException | IOException |
                NoSuchAlgorithmException | InvalidKeyException |
                NoSuchPaddingException e) {

            return false;
        }
    }

    /**
     * AuthenticationListener handels AuthenticationCallback.
     */
    class AuthenticationListener extends FingerprintManager.AuthenticationCallback{
        @Override
        public void onAuthenticationError(int errMsgId, CharSequence
                errString) { //Fingerprint not valid
            Toast.makeText(getApplicationContext(), "Authentication Error!", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onAuthenticationHelp(int helpMsgId, CharSequence
                helpString) { //Sensor may be dirty
        }
        @Override
        public void onAuthenticationFailed() { //Fingerprint valid but not recognized
            Toast.makeText(getApplicationContext(), "Authentication Failed!", Toast.LENGTH_LONG).show();
        }
        @Override
        public void onAuthenticationSucceeded
                (FingerprintManager.AuthenticationResult result) { //Everything went OK
            Toast.makeText(getApplicationContext(), "Authentication Success!", Toast.LENGTH_LONG).show();
            mFingerPrintDialog.dismiss();
            startMainActivity();

        }
    }

    /**
     * startMainActivity initializes the mainAcitivty
     */
    public void startMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
