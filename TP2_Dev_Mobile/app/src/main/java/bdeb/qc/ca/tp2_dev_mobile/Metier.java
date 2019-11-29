package bdeb.qc.ca.tp2_dev_mobile;

import android.widget.ProgressBar;

public class Metier {

    private String metierLetter;
    private int progress;

    public Metier(String metierLetter, int progress) {
        this.metierLetter = metierLetter;
        this.progress = progress;
    }

    public String getMetierLetter() {
        return metierLetter;
    }

    public void setMetierLetter(String metierLetter) {
        this.metierLetter = metierLetter;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
