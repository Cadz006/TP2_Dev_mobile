package bdeb.qc.ca.tp2_dev_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;

import bdeb.qc.ca.tp2_dev_mobile.Model.QuestionListItem;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class QuestionEtudiantActivity extends AppCompatActivity
{
    private static String fileName = null;
    private static final String LOG_TAG = "AudioRecordTest";
    public static final int CHOISIR_IMAGE = 1;
    public static final int PRENDRE_PHOTO = 0;
    private QuestionListItem question;
    private ImageView ivPhoto;
    private ImageView ivCommentaireAudio;
    private Uri imgUri;
    private boolean recording = false, start = false;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_etudiant);

        question = getIntent().getParcelableExtra(QuestionListActivity.KEY_QUESTION);
        ivPhoto = findViewById(R.id.ivPhoto);
        ivCommentaireAudio = findViewById(R.id.fabEcouterCommentaire);
        ivCommentaireAudio.setEnabled(false);

        btnCameraListener();
        btnRecordVoice();
        btnPlayReponse();
        setToolbar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_acceuil, menu);
        return true;
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbarQuestionEtudiant);
        toolbar.setTitle(question.getQuestion());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void btnCameraListener() {
        FloatingActionButton fabCamera = findViewById(R.id.fabCamera);
        FloatingActionButton fabGallery = findViewById(R.id.fabGallery);

        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyPermissions();
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

    private void btnRecordVoice(){
        final FloatingActionButton fabRecord = findViewById(R.id.fabReponseAudio);
        fabRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recording){
                    recordAudio();
                    fabRecord.setImageResource(R.drawable.ic_mic_off);
                }else{
                    stopAudio();
                    fabRecord.setImageResource(R.drawable.ic_mic);
                }

            }
        });
    }

    private void btnPlayReponse(){
        FloatingActionButton fabListen = findViewById(R.id.fabEcouterReponse);
        fabListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!start){
                    startPlaying();
                }else{
                    stopPlaying();
                }

            }
        });
    }

    /**
     * Méthode qui arrète de jouer la réponse
     */
    private void stopPlaying() {
        player.release();
        player = null;

    }

    /**
     * Méthode qui commence a jouer la réponse
     */
    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    /**
     * Méthode qui arrète d'enregistrer la voie
     */
    private void stopAudio() {
        recorder.stop();
        recorder.release();
        recorder = null;
        recording = false;
    }

    /**
     * Méthode qui commence à enregistrer la voie
     */
    private void recordAudio(){
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        recorder.start();
        recording = true;
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PRENDRE_PHOTO);
    }

    private void verifyPermissions(){
        String[] permissions = {Manifest.permission.CAMERA};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED){
            openCamera();
        }
        else{
            ActivityCompat.requestPermissions(QuestionEtudiantActivity.this,
                    permissions,
                    PRENDRE_PHOTO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
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
