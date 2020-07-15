package com.example.cameraxversion2

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.YuvImage
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.convertTo
import androidx.core.graphics.green
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

typealias YListener = (YStuff : Double) -> Unit
typealias UListener = (UStuff : Double ) -> Unit
typealias VListener = (VStuff : Double ) -> Unit
typealias AllListener = (allListener : Double ) -> Unit

class MainActivity : AppCompatActivity() {

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null

    //region random shit
//    private val myImageTest = ImageAnalysis.Builder()
//    .setTargetResolution(Size(1280, 720))
//    .build()
//    .also {
//        it.setAnalyzer(cameraExecutor,
//            MainAnalyzer( //FIGURE OUT WTF LAMBDA SHIT IS
//                { YStuff -> Log.d("Photo stuff", "Average Y is $YStuff")},
//                { UStuff -> Log.d("Photo stuff", "Average UU is $UStuff")},
//                { VStuff -> Log.d("Photo stuff", "Average VVVVV is $VStuff")}
//            )
//        )
//    }
    //endregion

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService

    private class MainAnalyzer(private val yListener: YListener,private val uListener: UListener,private val vListener: VListener) : ImageAnalysis.Analyzer {

        //get data from phone
        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array

        }

        //figure out what type is an image proxy
        override fun analyze(image: ImageProxy) {

            //Y cr cb
            val bufferY = image.planes[0].buffer //Y
            val dataY = bufferY.toByteArray()
            val pixelsY = dataY.map { it.toInt() and 0xFF } //what does map mean
            val YAverage = pixelsY.average()

            val bufferU = image.planes[1].buffer //V
            val dataU = bufferU.toByteArray()
            val pixelsU = dataU.map { it.toInt() and 0xFF } //what does map mean
            val UAverage = pixelsU.average()

            val bufferV = image.planes[2].buffer //V
            val dataV = bufferV.toByteArray()
            val pixelsV = dataV.map { it.toInt() and 0xFF } //what does map mean
            val VAverage = pixelsV.average()

            yListener(YAverage)
            uListener(UAverage)
            vListener(VAverage)



            image.close()
        }
    }

//    private class MainImageComparator(private val yListener: YListener, private val uListener: UListener, private val vListener: VListener) {
//
//        //figure out what type is an image proxy
//        fun analyze(image: YuvImage) {
//
//            //Y cr cb
//            //var test = image.yuvFormat
//
////            val bufferY = image.planes[0].buffer //Y
////            val dataY = bufferY.toByteArray()
//            val pixelsY = dataY.map { it.toInt() and 0xFF } //Transforms an array of data (prob int), into 255 hex stuff, deletes everything
//            // before the last 8 bits into 0's 000000000 11111111
//            val YAverage = pixelsY.average()
//
//            val bufferU = image.planes[1].buffer //V
//            val dataU = bufferU.toByteArray()
//            val pixelsU = dataU.map { it.toInt() and 0xFF }
//            val UAverage = pixelsU.average()
//
//            val bufferV = image.planes[2].buffer //V
//            val dataV = bufferV.toByteArray()
//            val pixelsV = dataV.map { it.toInt() and 0xFF }
//            val VAverage = pixelsV.average()
//
//            yListener(YAverage)
//            uListener(UAverage)
//            vListener(VAverage)
//
//            image.close()
//        }
//    }

    //region the other three
//    private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {
//
//        //this stuff gets the raw data?? return an array of bytes
//        //this thing is run a lot
//        private fun ByteBuffer.toByteArray(): ByteArray {
//            rewind()    // Rewind the buffer to zero
//            val data = ByteArray(remaining())
//            get(data)   // Copy the buffer into a byte array
//       //     Log.i("Return stuff", data.toString())
//            return data // Return the byte array
//
//        }
//
//        //figure out what type is an image proxy
//        override fun analyze(image: ImageProxy) {
//
//            //Y cr cb
//            val buffer = image.planes[0].buffer //Y
//            val data = buffer.toByteArray()
//            val pixels = data.map { it.toInt() and 0xFF } //what does map mean
//            val luma = pixels.average()
//
//            val buffer1 = image.planes[1].buffer //U
//            val data1 = buffer.toByteArray()
//            val pixels1 = data.map { it.toInt() and 0xFF }
//            val U1 = pixels.average()
//
//            val buffer2 = image.planes[2].buffer //V
//            val data2 = buffer.toByteArray()
//            val pixels2 = data.map { it.toInt() and 0xFF }
//            val V2 = pixels.average()
//
//            listener(luma)
////            listener(U1)
////            listener(V2)
//
//            image.close()
//        }
//    }
//
//    private class UAnalyzer(private val listener: UListener) : ImageAnalysis.Analyzer {
//
//        private fun ByteBuffer.toByteArray(): ByteArray {
//            rewind()    // Rewind the buffer to zero
//            val data = ByteArray(remaining())
//            get(data)   // Copy the buffer into a byte array
//           // Log.i("Return stuff", data.toString())
//            return data
//        }
//
//        override fun analyze(image: ImageProxy) {
//
//            val buffer = image.planes[1].buffer //U
//            val data = buffer.toByteArray()
//            val pixels = data.map { it.toInt() and 0xFF }
//            val U1 = pixels.average()
//
//            val buffer2 = image.planes[2].buffer //V
//            val data2 = buffer.toByteArray()
//            val pixels2 = data.map { it.toInt() and 0xFF }
//            val V2 = pixels.average()
//
//              listener(U1)
////            listener(V2)
//
//            image.close()
//        }
//    }
//
//    private class VAnalyzer(private val listener: VListener) : ImageAnalysis.Analyzer {
//
//        private fun ByteBuffer.toByteArray(): ByteArray {
//            rewind()    // Rewind the buffer to zero
//            val data = ByteArray(remaining())
//            get(data)   // Copy the buffer into a byte array
//           // Log.i("Return stuff", data.toString())
//            return data
//        }
//
//        override fun analyze(image: ImageProxy) {
//
//            val buffer = image.planes[2].buffer //V
//            val data = buffer.toByteArray()
//            val pixels = data.map { it.toInt() and 0xFF }
//            val V2 = pixels.average()
//
//            listener(V2)
//
//            image.close()
//        }
//    }
   //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  val testImage = R.drawable.test_photo



        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Setup the listener for take photo button
        camera_capture_button.setOnClickListener { takePhoto() }

        outputDirectory = getOutputDirectory()

        cameraExecutor = Executors.newSingleThreadExecutor() //need to know this
    } //end of on create section

    //basically the onCreate function for the camera
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this) //no clue
        //i think processCameraProvider is a class

        //what is a runnable
        cameraProviderFuture.addListener(Runnable {
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            preview = Preview.Builder()
                    .build()

            imageCapture = ImageCapture.Builder()
                    .build()

            imageAnalyzer = ImageAnalysis.Builder()
                .setTargetResolution(Size(1280, 720))
                    .build()
                    .also {
                        it.setAnalyzer(cameraExecutor,
                            MainAnalyzer( //FIGURE OUT WTF LAMBDA SHIT IS
                                { YStuff -> Log.d("Photo stuff", "Average Y is $YStuff")},
                                { UStuff -> Log.d("Photo stuff", "Average UU is $UStuff")},
                                { VStuff -> Log.d("Photo stuff", "Average VVVVV is $VStuff")}
                            )
                        )
                    }

            // Select back camera
            val cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                camera = cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview, imageCapture, imageAnalyzer)
                preview?.setSurfaceProvider(viewFinder.createSurfaceProvider(camera?.cameraInfo))
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))


    }

    //Copy and paste luminosity another 2 times, change it for U and V, figure out where to output it
    //for now take the average, then compare it to 3 different images, see which ever one is most similar
    //pick that one as true.



    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create timestamped output file to hold the image
        val photoFile = File(
                outputDirectory,
                SimpleDateFormat(FILENAME_FORMAT, Locale.US
                ).format(System.currentTimeMillis()) + ".jpg")

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        // Setup image capture listener which is triggered after photo has
        // been taken
        imageCapture.takePicture(
                outputOptions, ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageSavedCallback {
            override fun onError(exc: ImageCaptureException) {
                Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
            }

            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                val savedUri = Uri.fromFile(photoFile)
                val msg = "Photo capture succeeded: $savedUri"
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                Log.d(TAG, msg)
            }
        })
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
                baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() } }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    companion object {
        private const val TAG = "CameraXBasic"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults:
            IntArray) {
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(this,
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }




}