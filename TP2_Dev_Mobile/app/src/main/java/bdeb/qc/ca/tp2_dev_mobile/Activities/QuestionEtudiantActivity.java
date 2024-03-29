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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.UUID;

import bdeb.qc.ca.tp2_dev_mobile.Model.QuestionListItem;
import bdeb.qc.ca.tp2_dev_mobile.R;

public class QuestionEtudiantActivity extends AppCompatActivity
{
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 1000;
    private static final String LOG_TAG = "AudioRecordTest";
    public static final int CHOISIR_IMAGE = 1;
    public static final int PRENDRE_PHOTO = 0;
    private QuestionListItem question;
    private ImageView ivPhoto;
    private FloatingActionButton fabCamera, fabGallery, fabVoice, fabComment, fabListen;
    private String pathSave = "";
    private boolean camGall;
    private boolean recording = false, start = false;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private TextView txtCommentaire, txtReponse;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_etudiant);
        setupVariables();
        setupToolbar();
        setupForProf();
    }

    /**
     * Cette méthode setup les variables avec les findViewById
     */
    private void setupVariables()
    {
        question = getIntent().getParcelableExtra(QuestionListActivity.KEY_QUESTION);
        ivPhoto = findViewById(R.id.ivPhoto);
        fabCamera = findViewById(R.id.fabCamera);
        fabGallery = findViewById(R.id.fabGallery);
        fabVoice = findViewById(R.id.fabReponseAudio);
        fabComment = findViewById(R.id.fabEcouterCommentaire);
        fabComment.setEnabled(false);
        fabListen = findViewById(R.id.fabEcouterReponse);
        txtCommentaire = findViewById(R.id.txtCommentaireEcrit);
        txtReponse = findViewById(R.id.txtReponseEtudiant);
    }

    /**
     * Cette méthode set la toolbar avec un onClick qui ferme l'activité
     */
    private void setupToolbar()
    {
        Toolbar toolbar = findViewById(R.id.toolbarQuestionEtudiant);
        toolbar.setTitle(question.getQuestion());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }

    /**
     * Cette méthode modifie la page si l'utilisateur qui l'ouvre est un prof
     */
    private void setupForProf()
    {
        boolean isProf = getIntent().getBooleanExtra("IsProf", false);
        if (isProf)
        {
            fabComment.setImageResource(R.drawable.ic_mic);
            fabComment.setEnabled(true);
            fabComment.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
            fabCamera.hide();
            fabVoice.hide();
            fabGallery.hide();
            fabListen.hide();
            txtCommentaire.setEnabled(true);
            txtReponse.setEnabled(false);
        }
    }

    /**
     * Cette méthode vérifie si l'application peut accéder à la caméra.
     * @param v
     */
    public void onCameraButtonClick(View v)
    {
        if (ivPhoto.getDrawable() == null)
        {
            verifyPermissions();
        }
        else
        {
            camGall = false;
            callAlertDialog();
        }
    }


    /**
     * Cette méthode laisse le professeur ajouter un commentaire vocal ou écouter un commentaire
     * selon l'utilisateur
     * @param v
     */
    public void onCommentaireButtonClick(View v)
    {

        boolean isProf = getIntent().getBooleanExtra("IsProf", false);
        if (isProf) {
            if (!recording)
            {
                verifyPermissionsMic();
                fabComment.setImageResource(R.drawable.ic_mic_off);
            }
            else
            {
                stopAudio();
                fabComment.setImageResource(R.drawable.ic_mic);
                txtCommentaire.setText(R.string.CommentaireExist);
            }
        }else{
            if (!start)
            {
                startPlaying();
                fabComment.setImageResource(R.drawable.ic_pause);
            }
            else
            {
                stopPlaying();
                fabComment.setImageResource(R.drawable.ic_play_arrow);
            }
        }

    }

    /**
     * Cette méthode permet d'appeler un alertDialog pour confirmer si l'utilsiateur
     * veut remplacer la photo courrante
     */
    private void callAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionEtudiantActivity.this);
        builder.setMessage(R.string.messageConfirmationPhoto).setCancelable(false)
                .setPositiveButton(R.string.Oui, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (!camGall)
                        {
                            verifyPermissions();
                        }
                        else
                        {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                            startActivityForResult(intent, CHOISIR_IMAGE);
                        }
                    }
                })
                .setNegativeButton(R.string.Non, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.setTitle(R.string.txtConfirmation);
        alert.show();
    }

    /**
     * Cette méthode demande les permissions afin de prendre la photo
     */
    private void verifyPermissions()
    {
        String[] permissions = { Manifest.permission.CAMERA };

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED)
        {
            openCamera();
        }
        else
        {
            ActivityCompat.requestPermissions(QuestionEtudiantActivity.this,
                    permissions,
                    PRENDRE_PHOTO);
        }
    }

    /**
     * Cette méthode ouvre la caméra sur l'appareil de l'étudiant
     */
    private void openCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, PRENDRE_PHOTO);
    }

    /**
     * Cette méthode ouvre la gallerie pour que l'étudiant choisisse une photo.
     * Si l'application n'a pas la permission, elle la demandera.
     * @param v
     */
    public void onGalleryButtonClick(View v)
    {
        if (ivPhoto.getDrawable() == null)
        {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(intent, CHOISIR_IMAGE);
        }
        else
        {
            camGall = true;
            callAlertDialog();
        }
    }

    /**
     * Cette méthode vérifie si l'application possède les droits
     * @param v
     */
    public void onRecordVoiceButtonClick(View v)
    {
        if (!recording)
        {
            verifyPermissionsMic();
            fabVoice.setImageResource(R.drawable.ic_mic_off);
        }
        else
        {
            stopAudio();
            fabVoice.setImageResource(R.drawable.ic_mic);
            fabListen.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        }
    }

    /**
     *
     */
    private void verifyPermissionsMic()
    {
        String[] permissions = {Manifest.permission.RECORD_AUDIO,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED)
        {
            recordAudio();
        }
        else
        {
            ActivityCompat.requestPermissions(this, permissions,
                    REQUEST_RECORD_AUDIO_PERMISSION);
        }
    }

    /**
     * Cette méthode commence à enregistrer la voix
     */
    private void recordAudio()
    {
        pathSave = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"
                + UUID.randomUUID().toString() + "_audio_record.3gp";
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(pathSave);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try
        {
            recorder.prepare();
            recorder.start();
            Toast.makeText(this, R.string.Recording, Toast.LENGTH_LONG).show();
            recording = true;
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "prepare() failed");
            Toast.makeText(this, R.string.RecordingFailed, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Cette méthode arrête d'enregistrer la voix
     */
    private void stopAudio()
    {
        recorder.stop();
        recorder.release();
        recorder = null;
        recording = false;
    }

    /**
     * Cette méthode fait jouer ou arrête de jouer le son
     * @param v
     */
    public void onPlayReponseClick(View v)
    {
        final FloatingActionButton fabListen = findViewById(R.id.fabEcouterReponse);
        if (!start)
        {
            startPlaying();
            fabListen.setImageResource(R.drawable.ic_pause);
        }
        else
        {
            stopPlaying();
            fabListen.setImageResource(R.drawable.ic_play_arrow);
        }
    }

    /**
     * Cette méthode commence à jouer la réponse
     */
    private void startPlaying()
    {
        player = new MediaPlayer();
        try
        {
            player.setDataSource(pathSave);
            player.prepare();
            player.start();
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "prepare() failed");
        }
        start = true;
    }

    /**
     * Cette méthode arrête de jouer la réponse
     */
    private void stopPlaying()
    {
        player.release();
        player = null;
        start = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PRENDRE_PHOTO:
                verifyPermissions();
                break;
            case REQUEST_RECORD_AUDIO_PERMISSION:
                verifyPermissionsMic();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_acceuil, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PRENDRE_PHOTO)
        {
            Bitmap bm = (Bitmap) data.getExtras().get("data");
            ivPhoto.setImageBitmap(bm);
        }
        else if (resultCode == RESULT_OK && requestCode == CHOISIR_IMAGE)
        {
            Uri imgUri = data.getData();
            ivPhoto.setImageURI(imgUri);
        }
    }
}
