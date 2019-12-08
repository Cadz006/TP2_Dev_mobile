package bdeb.qc.ca.tp2_dev_mobile.Model;

public class Etudiant {
    private String nom;
    private String prenom;
    private String email;
    private boolean estActif;
    private String acc_token;
    private String type_token;


    public Etudiant(String nomFamille, String prenom, String e_mail, boolean actif, String access, String type) {
        this.nom = nomFamille;
        this.prenom = prenom;
        this.email = e_mail;
        this.estActif = actif;
        this.acc_token = access;
        this.type_token = type;
    }

    public String getNom() { return nom; }

    public String getPrenom() { return prenom; }

    public String getEmail() { return email; }

    public boolean getEstActif() { return estActif; }

    public String getAcc_token() { return acc_token; }

    public String getType_token() { return type_token; }

}
