package hu.ppke.itk.android.papel1.nature_arm.base;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;

import androidx.exifinterface.media.ExifInterface;

import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import hu.ppke.itk.android.papel1.nature_arm.R;

public class BitmapHelper extends LanguageHelper
{
    protected String currentPhotoPath;

    protected static float exifToDegrees(float exifOrientation)
    {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90)
        {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180)
        {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270)
        {
            return 270;
        }
        return 0;
    }

    @NonNull
    public Uri saveBitmap(@NonNull final Context context, @NonNull final Bitmap bitmap) throws IOException
    {
        final ContentValues contentValues = new ContentValues();
        final ContentResolver resolver = context.getContentResolver();
        String relativeLocation = Environment.DIRECTORY_PICTURES;
        OutputStream stream = null;
        Uri uri = null;

        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, String.valueOf(System.currentTimeMillis()));
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, getString(R.string.imageType));
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation);

        try
        {
            final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            uri = resolver.insert(contentUri, contentValues);

            if (uri == null)
            {
                throw new IOException(String.valueOf(R.string.mediastoreIOException));
            }

            stream = resolver.openOutputStream(uri);

            if (stream == null)
            {
                throw new IOException(String.valueOf(R.string.outputstreamIOException));
            }

            if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream))
            {
                throw new IOException(String.valueOf(R.string.bitmapIOException));
            }

            return uri;
        } catch (IOException e)
        {
            if (uri != null)
            {
                resolver.delete(uri, null, null);
            }

            throw e;
        } finally
        {
            if (stream != null)
            {
                stream.close();
            }
        }
    }

    protected File createImageFile() throws IOException
    {
        @SuppressLint("SimpleDateFormat")
        String timeStamp = new SimpleDateFormat(getString(R.string.imageDatePattern)).format(new Date());
        String imageFileName = getString(R.string.imagePrefix) + timeStamp + getString(R.string.imageDelimeter);
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, getString(R.string.imageFormat), storageDir);

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
