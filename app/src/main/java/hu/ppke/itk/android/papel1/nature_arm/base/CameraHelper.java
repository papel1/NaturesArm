package hu.ppke.itk.android.papel1.nature_arm.base;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

import hu.ppke.itk.android.papel1.nature_arm.R;

public class CameraHelper extends BitmapHelper
{
    protected static final int RESULT_LOAD_IMG = 55;
    protected static final int MY_CAMERA_PERMISSION_CODE = 6;
    protected static final int REQUEST_IMAGE_CAPTURE = 66;

    protected Uri imageUri;

    protected void dispatchTakePictureIntent()
    {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            File photoFile = null;
            try
            {
                photoFile = createImageFile();
            } catch (IOException ex)
            {
                Log.d(String.valueOf(R.string.error), ex.getMessage());
            }
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "hu.ppke.itk.android.papel1.nature_arm.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, R.string.cameraPermission, Toast.LENGTH_LONG).show();
                dispatchTakePictureIntent();
            } else
            {
                Toast.makeText(this, R.string.no_cameraPermission, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE)
        {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
            bmOptions.inJustDecodeBounds = false;

            Bitmap photo = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
            try
            {
                ExifInterface exif = new ExifInterface(currentPhotoPath);
                float rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

                float rotationInDegrees = exifToDegrees(rotation);

                Matrix matrix = new Matrix();
                matrix.postRotate(rotationInDegrees);

                if (rotationInDegrees != 0)
                    photo = Bitmap.createBitmap(photo, 0, 0, photo.getWidth(), photo.getHeight(), matrix, true);

            } catch (OutOfMemoryError | Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                if (photo.getWidth() > photo.getHeight())
                {
                    photo = Bitmap.createBitmap(photo, photo.getWidth() / 2 - photo.getHeight() / 2, 0, photo.getHeight(), photo.getHeight());
                } else
                {
                    photo = Bitmap.createBitmap(photo, 0, photo.getHeight() / 2 - photo.getWidth() / 2, photo.getWidth(), photo.getWidth());
                }

                imageUri = saveBitmap(CameraHelper.this, photo);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        } else if (requestCode == RESULT_LOAD_IMG)
        {
            assert data != null;
            imageUri = data.getData();
            currentPhotoPath = imageUri.toString();
        }
    }
}
