package bdeb.qc.ca.tp2_dev_mobile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class QuestionEtudiantActivity extends AppCompatActivity {
    public static final int CHOISIR_IMAGE = 1;
    public static final int PRENDRE_PHOTO = 0;
    ImageView ivPhoto;
    Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_etudiant);

        ivPhoto = findViewById(R.id.ivPhoto);
        btnCameraListener();
    }

    private void btnCameraListener() {
        FloatingActionButton fabCamera = findViewById(R.id.fabCamera);
        FloatingActionButton fabGallery = findViewById(R.id.fabGallery);

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, PRENDRE_PHOTO);
            }
        });

        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intent, CHOISIR_IMAGE);
            }
        });
    }

    private void verifyPermissions(){
        String[] permissions = {Manifest.permission.CAMERA};

        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PRENDRE_PHOTO){
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageBitmap(bm);
        }
        else if(resultCode == RESULT_OK && requestCode == CHOISIR_IMAGE){
            imgUri = data.getData();
            ivPhoto.setImageURI(imgUri);
        }
    }
}
