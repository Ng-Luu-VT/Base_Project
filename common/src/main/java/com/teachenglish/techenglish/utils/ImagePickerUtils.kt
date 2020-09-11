package com.teachenglish.techenglish.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Parcelable
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URISyntaxException
import java.text.SimpleDateFormat
import java.util.*

class ImagePickerUtils {
    companion object{
        private val DEFAULT_MIN_WIDTH_QUALITY = 400        // min pixels
        private val TAG = "ImagePicker"
        private val TEMP_IMAGE_NAME = "tempImage"
        var imageTakenFromCameraPath: String=""

        var minWidthQuality = DEFAULT_MIN_WIDTH_QUALITY

        fun getPickGalleryImageIntent(): Intent {
            return Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        }

        fun getPickCameraImageIntent(context: Context): Intent {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(context.packageManager) != null) {
                val photoFile: File?
                photoFile = getTempFile(context)
                if (photoFile != null) {
                    val photoURI = FileProvider.getUriForFile(
                        context,
                        context.packageName,
                        photoFile
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                }
            }
            return takePictureIntent
        }

        fun getPickImageIntent(context: Context): Intent? {
            var chooserIntent: Intent? = null

            var intentList: MutableList<Intent> = ArrayList()

            val pickIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            takePhotoIntent.putExtra("return-data", true)
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getTempFile(context)))
            intentList = addIntentsToList(context, intentList, pickIntent)
            intentList = addIntentsToList(context, intentList, takePhotoIntent)

            if (intentList.size > 0) {
                chooserIntent =
                    Intent.createChooser(intentList.removeAt(intentList.size - 1), "choose")
                chooserIntent!!.putExtra(
                    Intent.EXTRA_INITIAL_INTENTS,
                    intentList.toTypedArray<Parcelable>()
                )
            }

            return chooserIntent
        }

        private fun addIntentsToList(
            context: Context,
            list: MutableList<Intent>,
            intent: Intent
        ): MutableList<Intent> {
            val resInfo = context.packageManager.queryIntentActivities(intent, 0)
            for (resolveInfo in resInfo) {
                val packageName = resolveInfo.activityInfo.packageName
                val targetedIntent = Intent(intent)
                targetedIntent.setPackage(packageName)
                list.add(targetedIntent)
                Log.d(TAG, "Intent: " + intent.action + " package: " + packageName)
            }
            return list
        }


        fun getImageFromResult(
            context: Context, resultCode: Int,
            imageReturnedIntent: Intent?
        ): Bitmap? {
            Log.d(TAG, "getImageFromResult, resultCode: $resultCode")
            var bm: Bitmap? = null
            val imageFile = getTempFile(context)
            if (resultCode == Activity.RESULT_OK) {
                val isCamera = imageReturnedIntent == null ||
                        imageReturnedIntent.data == null ||
                        imageReturnedIntent.data!!.toString().contains(imageFile.toString())

                val selectedImage =
                    getUriFromResult(context, Activity.RESULT_OK, imageReturnedIntent, isCamera)

                try {
                    bm = MediaStore.Images.Media.getBitmap(context.contentResolver, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                val rotation = getRotation(context, selectedImage, isCamera)
                bm = rotate(bm, rotation)
            }
            return bm
        }

        fun getUriFromResult(
            context: Context, resultCode: Int, intent: Intent?,
            isCamera: Boolean
        ): Uri? {
            Log.d(TAG, "getUriFromResult, resultCode: $resultCode")
            if (resultCode == Activity.RESULT_OK) {
                val selectedImage: Uri?
                if (isCamera) {
                    /** CAMERA  */
                    selectedImage = Uri.fromFile(File(imageTakenFromCameraPath))
                } else {
                    /** ALBUM  */
                    selectedImage = intent!!.data
                }
                Log.d(TAG, "selectedImage: " + selectedImage!!)
                return selectedImage
            }
            return null
        }


        fun getTempFile(context: Context): File {
            // Create an image file name
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFileName = "JPEG_" + timeStamp + "_"
            val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            var image: File? = null
            try {
                image = File.createTempFile(
                    imageFileName, /* prefix */
                    ".jpg", /* suffix */
                    storageDir      /* directory */
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }

            imageTakenFromCameraPath = image!!.absolutePath
            return image
        }

        private fun decodeBitmap(context: Context, theUri: Uri, sampleSize: Int): Bitmap {
            val options = BitmapFactory.Options()
            options.inSampleSize = sampleSize

            var fileDescriptor: AssetFileDescriptor? = null
            try {
                fileDescriptor = context.contentResolver.openAssetFileDescriptor(theUri, "r")
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

            val actuallyUsableBitmap = BitmapFactory.decodeFileDescriptor(
                fileDescriptor!!.fileDescriptor, null, options
            )

            Log.d(
                TAG, options.inSampleSize.toString() + " sample method bitmap ... " +
                        actuallyUsableBitmap.width + " " + actuallyUsableBitmap.height
            )

            return actuallyUsableBitmap
        }

        /**
         * Resize to avoid using too much memory loading big images (e.g.: 2560*1920)
         */
        fun getImageResized(context: Context, resource: Bitmap): Bitmap {
            var bm: Bitmap? = null
            val sampleSizes = intArrayOf(5, 3, 2, 1)
            var i = 0
            do {
                bm = resource
                Log.d(TAG, "resizer: new bitmap width = " + bm.width)
                i++
            } while (bm!!.width < minWidthQuality && i < sampleSizes.size)
            return bm
        }


        fun getRotation(context: Context, imageUri: Uri?, isCamera: Boolean): Int {
            val rotation: Int
            if (isCamera) {
                rotation = getRotationFromCamera(context, imageUri)
            } else {
                rotation = getRotationFromGallery(context, imageUri)
            }
            Log.d(TAG, "Image rotation: $rotation")
            return rotation
        }

        private fun getRotationFromCamera(context: Context, imageFile: Uri?): Int {
            var rotate = 0
            try {

                context.contentResolver.notifyChange(imageFile!!, null)
                val exif = ExifInterface(imageFile.path!!)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )

                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return rotate
        }

        fun getRotationFromGallery(context: Context, imageUri: Uri?): Int {
            var result = 0
            val columns = arrayOf(MediaStore.Images.Media.ORIENTATION)
            var cursor: Cursor? = null
            try {
                cursor = context.contentResolver.query(imageUri!!, columns, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val orientationColumnIndex = cursor.getColumnIndex(columns[0])
                    result = cursor.getInt(orientationColumnIndex)
                }
            } catch (e: Exception) {
                //Do nothing
            } finally {
                cursor?.close()
            }//End of try-catch block
            return result
        }


        fun rotate(bm: Bitmap?, rotation: Int): Bitmap? {
            if (rotation != 0) {
                val matrix = Matrix()
                matrix.postRotate(rotation.toFloat())
                return Bitmap.createBitmap(bm!!, 0, 0, bm.width, bm.height, matrix, true)
            }
            return bm
        }

        private fun flip(bitmap: Bitmap, horizontal: Boolean, vertical: Boolean): Bitmap {
            val matrix = Matrix()
            matrix.preScale(
                (if (horizontal) -1 else 1).toFloat(),
                (if (vertical) -1 else 1).toFloat()
            )
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        }

        fun modifyOrientation(
            context: Context, bitmap: Bitmap, uri: Uri,
            isCamera: Boolean
        ): Bitmap? {
            var exifInterface: ExifInterface? = null
            try {
                if (isCamera) {
                    exifInterface = ExifInterface(uri.path!!)
                } else {
                    val file = File(getFilePath(context, uri)!!)
                    exifInterface = ExifInterface(file.path)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }

            val orientation = exifInterface!!.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> return rotate(bitmap, 90)
                ExifInterface.ORIENTATION_ROTATE_180 -> return rotate(bitmap, 180)

                ExifInterface.ORIENTATION_ROTATE_270 -> return rotate(bitmap, 270)

                ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> return flip(bitmap, true, false)

                ExifInterface.ORIENTATION_FLIP_VERTICAL -> return flip(bitmap, false, true)
                else -> return bitmap
            }

        }

        @SuppressLint("NewApi")
        @Throws(URISyntaxException::class)
        fun getFilePath(context: Context, uri: Uri): String? {
            var uri = uri
            var selection: String? = null
            var selectionArgs: Array<String>? = null
            // Uri is different in versions after KITKAT (Android 4.4), we need to
            if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(
                    context.applicationContext,
                    uri
                )
            ) {
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)
                    )
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split =
                        docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    val type = split[0]
                    if ("image" == type) {
                        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    selection = "_id=?"
                    selectionArgs = arrayOf(split[1])
                }
            }
            if ("content".equals(uri.scheme!!, ignoreCase = true)) {
                val projection = arrayOf(MediaStore.Images.Media.DATA)
                var cursor: Cursor? = null
                try {
                    cursor = context.contentResolver
                        .query(uri, projection, selection, selectionArgs, null)
                    val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    if (cursor.moveToFirst()) {
                        return cursor.getString(column_index)
                    }
                } catch (e: Exception) {
                }

            } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
                return uri.path
            }
            return null
        }

        fun isExternalStorageDocument(uri: Uri): Boolean {
            return "com.android.externalstorage.documents" == uri.authority
        }

        fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

        fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }
    }
}