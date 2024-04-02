 package com.example.mathtutorial;

 import android.content.Intent;
 import android.graphics.Bitmap;
 import android.graphics.Canvas;
 import android.graphics.Color;
 import android.graphics.Paint;
 import android.media.MediaPlayer;
 import android.os.Bundle;
 import android.os.Environment;
 import android.view.MotionEvent;
 import android.view.View;
 import android.widget.Button;
 import android.widget.ImageView;
 import android.widget.Toast;

 import androidx.activity.EdgeToEdge;
 import androidx.appcompat.app.AppCompatActivity;
 import androidx.core.graphics.Insets;
 import androidx.core.view.ViewCompat;
 import androidx.core.view.WindowInsetsCompat;

 import java.io.File;
 import java.io.FileOutputStream;
 import java.util.Calendar;

 public class PaintActivity extends AppCompatActivity {
     private ImageView imageView;
     private MediaPlayer mediaPlayer;
     private float floatStartX = -1, floatStartY = -1,
             floatEndX = -1, floatEndY = -1;

     private Bitmap bitmap;
     private Canvas canvas;
     private Paint paint = new Paint();

     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         EdgeToEdge.enable(this);
         setContentView(R.layout.activity_paint);
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
             Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
             v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
             return insets;
         });

         mediaPlayer = MediaPlayer.create(this, R.raw.draw);

         // Start playback
         mediaPlayer.start();
         imageView = findViewById(R.id.draw_layout_paint);
         Button save = findViewById(R.id.save_btn_paint);
         save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 buttonSaveImage(v);
                     Intent xntent = new Intent(PaintActivity.this,TrignometryActivity.class);
                     startActivity(xntent);
                     finish();
             }

         });
     }

         private void drawPaintSketchImage () {

             if (bitmap == null) {
                 bitmap = Bitmap.createBitmap(imageView.getWidth(),
                         imageView.getHeight(),
                         Bitmap.Config.ARGB_8888);
                 canvas = new Canvas(bitmap);
                 paint.setColor(Color.RED);
                 paint.setAntiAlias(true);
                 paint.setStyle(Paint.Style.STROKE);
                 paint.setStrokeWidth(8);
             }
             canvas.drawLine(floatStartX,
                     floatStartY - 220,
                     floatEndX,
                     floatEndY - 220,
                     paint);
             imageView.setImageBitmap(bitmap);
         }


         @Override
         public boolean onTouchEvent (MotionEvent event) {

         if (event.getAction() == MotionEvent.ACTION_DOWN) {
             floatStartX = event.getX();
             floatStartY = event.getY();
         }

         if (event.getAction() == MotionEvent.ACTION_MOVE) {
             floatEndX = event.getX();
             floatEndY = event.getY();
             drawPaintSketchImage();
             floatStartX = event.getX();
             floatStartY = event.getY();
         }
         if (event.getAction() == MotionEvent.ACTION_UP) {
             floatEndX = event.getX();
             floatEndY = event.getY();
             drawPaintSketchImage();
         }
         return super.onTouchEvent(event);
        }

         public void buttonSaveImage (View view){
             File fileSaveImage = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                     Calendar.getInstance().getTime().toString() + ".jpg");
             try {
                 FileOutputStream fileOutputStream = new FileOutputStream(fileSaveImage);
                 bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                 fileOutputStream.flush();
                 fileOutputStream.close();
                 Toast.makeText(this,
                         "File Saved Successfully",
                         Toast.LENGTH_LONG).show();

             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
     @Override
     protected void onDestroy() {
         super.onDestroy();
         // Stop and release MediaPlayer when no longer needed
         if (mediaPlayer != null) {
             mediaPlayer.stop();
             mediaPlayer.release();
             mediaPlayer = null;
         }
     }

     @Override
     protected void onPause() {
         super.onPause();
         if (mediaPlayer != null) {
             mediaPlayer.stop();
             mediaPlayer.release();
             mediaPlayer = null;
         }
     }

     }

