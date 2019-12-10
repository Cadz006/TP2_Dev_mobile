package bdeb.qc.ca.tp2_dev_mobile.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.UUID;

import bdeb.qc.ca.tp2_dev_mobile.Model.QuestionListItem;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class QuestionEtudiantActivity extends AppCompatActivity {
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1000;
    private static String fileName = null;
    private static final String LOG_TAG = "AudioRecordTest";
    public static final int CHOISIR_IMAGE = 1;
    public static final int PRENDRE_PHOTO = 0;
    private QuestionListItem question;
    private ImageView ivPhoto;
    private Uri imgUri;
    private FloatingActionButton fabCamera;
    private FloatingActionButton fabGallery;
    private FloatingActionButton fabVoice;
    private FloatingActionButton fabComment;
    private String pathSave = "";
    private boolean camGall;
    private boolean IsProf;
    private boolean recording = false, start = false;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_etudiant);
        setupVariables();
        btnCameraListener();
        btnGalleryListener();
        btnRecordVoice();
        btnPlayReponse();
        setupToolbar();
        setupForProf();
    }

    /**
     * Cette méthode setup les variables avec les findViewById
     */
    private void setupVariables() {
        question = getIntent().getParcelableExtra(QuestionListActivity.KEY_QUESTION);
        ivPhoto = findViewById(R.id.ivPhoto);
        fabCamera = findViewById(R.id.fabCamera);
        fabGallery = findViewById(R.id.fabGallery);
        fabVoice = findViewById(R.id.fabReponseAudio);
        fabComment = findViewById(R.id.fabEcouterCommentaire);
        fabComment.setEnabled(false);
    }

    /**
     * Cette méthode modifie la page si l'utilisateur qui l'ouvre est un prof
     */
    private void setupForProf() {
        IsProf = getIntent().getBooleanExtra("IsProf", false);
        if (IsProf) {
            fabComment.setImageResource(R.drawable.ic_mic);
            fabComment.setEnabled(true);
            fabComment.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
            fabCamera.hide();
            fabVoice.hide();
            fabGallery.hide();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_acceuil, menu);
        return true;
    }

    /**
     * Cette méthode set la toolbar avec un onClick qui ferme l'activité
     */
    private void setupToolbar() {
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

    /**
     * Cette méthode permet à l'étudiant de prendre une photo pour sa réponse
     */
    private void btnCameraListener() {
        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ivPhoto.getDrawable() == null) {
                    verifyPermissions();
                } else {
                    camGall = false;
                    callAlertDialog();
                }
            }
        });
    }

    /**
     * Cette méthode permet à l'étudiant de prendre une photo de sa gallerie pour sa réponse
     */
    private void btnGalleryListener() {
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ivPhoto.getDrawable() == null) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                    startActivityForResult(intent, CHOISIR_IMAGE);
                } else {
                    camGall = true;
                    callAlertDialog();
                }
            }
        });
    }

    /**
     * Cette méthode permet à l'étudiant d'enregistrer un sa réponse avec sa voie
     */
    private void btnRecordVoice() {
        final FloatingActionButton fabRecord = findViewById(R.id.fabReponseAudio);
        final FloatingActionButton fabListen = findViewById(R.id.fabEcouterReponse);
        fabRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!recording) {
                    verifyPermissionsMic();
                    fabRecord.setImageResource(R.drawable.ic_mic_off);

                } else {
                    stopAudio();
                    fabRecord.setImageResource(R.drawable.ic_mic);
                    fabListen.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                }
            }
        });
    }

    /**
     * Cette méthode permet de jouer la réponse de l'étudiant
     */
    private void btnPlayReponse() {
        final FloatingActionButton fabListen = findViewById(R.id.fabEcouterReponse);
        fabListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!start) {
                    startPlaying();
                    fabListen.setImageResource(R.drawable.ic_pause);
                } else {
                    stopPlaying();
                    fabListen.setImageResource(R.drawable.ic_play_arrow);
                }
            }
        });
    }

    /**
     * Cette méthode arrète de jouer la réponse
     */
    private void stopPlaying() {
        player.release();
        player = null;
        start = false;
    }

    /**
     * Cette méthode commence a jouer la réponse
     */
    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(pathSave);
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
        start = true;
    }

    /**
     * Cette méthode arrète d'enregistrer la voie
     */
    private void stopAudio() {
        recorder.stop();
        recorder.release();
        recorder = null;
        recording = false;
    }

    /**
     * Cette méthode commence à enregistrer la voie
     */
    private void recordAudio() {
        Toast toast = null;
        pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                + UUID.randomUUID().toString() + "_audio_record.3gp";
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(pathSave);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
            toast = Toast.makeText(this, R.string.Recording, Toast.LENGTH_LONG);
            recording = true;
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
            Toast.makeText(this, R.string.RecordingFailed, Toast.LENGTH_LONG);
        }
        toast.show();
    }

    /**
     * Cette méthode ouvre la caméra sur l'appareil de l'étudiant
     */
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PRENDRE_PHOTO);
    }

    /**
     * Cette méthode demande les permissions afin de prendre la photo
     */
    private void verifyPermissions() {
        String[] permissions = {Manifest.permission.CAMERA};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(QuestionEtudiantActivity.this,
                    permissions,
                    PRENDRE_PHOTO);
        }

    }

    private void verifyPermissionsMic() {
        String[] permissions = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED) {
            recordAudio();
        } else {
            ActivityCompat.requestPermissions(this, permissions,
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PRENDRE_PHOTO:
                verifyPermissions();
                break;

            case REQUEST_RECORD_AUDIO_PERMISSION:
                verifyPermissionsMic();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PRENDRE_PHOTO) {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageBitmap(bm);
        } else if (resultCode == RESULT_OK && requestCode == CHOISIR_IMAGE) {
            imgUri = data.getData();
            ivPhoto.setImageURI(imgUri);
        }
    }

    /**
     * Cette méthode permet d'appeler un alertDialog pour confirmer si l'utilsiateur
     * veut remplacer la photo courrante
     */
    private void callAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionEtudiantActivity.this);
        builder.setMessage(R.string.messageConfirmationPhoto).setCancelable(false)
                .setPositiveButton(R.string.Oui, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (camGall == false) {
                            verifyPermissions();
                        } else {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            startActivityForResult(intent, CHOISIR_IMAGE);
                        }
                    }
                })
                .setNegativeButton(R.string.Non, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle(R.string.txtConfirmation);
        alert.show();
    }
}
