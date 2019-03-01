package com.solutionavenues.deedee.imagePicker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;

import java.io.File;

/**
 * Created by bitu on 9/9/17.
 */

public class FileInformation {

    public static final int IMAGE_SIZE_THUMB = 200;
    public static final int IMAGE_SIZE_LARGE = 500;

    Uri fileUri;
    String filePath;
    String mimeType;
    Matrix orientation = new Matrix();


    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }


    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Matrix getOrientation() {
        return orientation;
    }

    public void setOrientation(Matrix orientation) {
        this.orientation = orientation;
    }

    public boolean hasFilePath() {
        return filePath != null && !filePath.trim().isEmpty();
    }

    public boolean checkValidFileInformation() {
        return fileUri != null || hasFilePath();
    }

    public boolean isVideo() {
        return mimeType != null && mimeType.trim().toLowerCase().startsWith("video");
    }

    public boolean isImage() {
        return mimeType != null && mimeType.trim().toLowerCase().startsWith("image");
    }

    public Bitmap getThumbBitmap(Context context) {
        if (isImage()) {
            return new BitmapHelper().getImageBitmap(context, this, IMAGE_SIZE_THUMB, IMAGE_SIZE_THUMB);
        } else if (isVideo()) {
            return new BitmapHelper().getVideoBitmap(filePath);
        }
        return null;
    }

    public String getBitmapPathForUpload(Context context) {
        BitmapHelper bitmapHelper = new BitmapHelper();
        if (!isImage() && !isVideo()) return null;
        Bitmap bitmap;
        if (isVideo()) {
            bitmap = bitmapHelper.getVideoBitmap(filePath);
        } else {
            bitmap = bitmapHelper.getImageBitmap(context, this, IMAGE_SIZE_LARGE, IMAGE_SIZE_LARGE);
        }
        String newFilePath = new File(context.getFilesDir(),
                "image_" + System.currentTimeMillis()
                        + "." + bitmapHelper.getExtenstion(this.filePath)).getAbsolutePath();
        return bitmapHelper.saveBitmap(newFilePath, bitmap);
    }


}
