package com.example.gmf_aeroasia.iormobile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class FileOpen {
    final String TAG = this.getClass().getSimpleName();
    private Context context;
    private Uri fileUri;

    public void setFileUri(Uri photoUri) {
        this.fileUri = photoUri;
    }

    public FileOpen(Context context) {
        this.context = context;
    }

    public Intent openStorageIntent() {
        Intent intent = new Intent();
        intent.setType("*/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        return Intent.createChooser(intent, this.getChooserTitle());
    }

    public String getChooserTitle() {
        return "Select Pictures";
    }

    public String getPath() {
        String path;
        if (Build.VERSION.SDK_INT < 11) {
            path = RealPathUtil.getRealPathFromURI_BelowAPI11(this.context, this.fileUri);
        } else if (Build.VERSION.SDK_INT < 19) {
            path = RealPathUtil.getRealPathFromURI_API11to18(this.context, this.fileUri);
        } else {
            path = RealPathUtil.getRealPathFromURI_API19(this.context, this.fileUri);
        }

        return path;
    }
}
