
import java.util.ArrayList;
import java.util.List;

public class Medlem {

    private String navn;
    private int alder;
    private String email;
    private String  adresse;
    private int telefonnummer;
    private boolean erAktivtMedlem;
    private String medlemsType;
    private boolean erIRestance;
    private final List<TræningsResultat> resultater;
    private String forældreNavn;
    private String forældreTelefonnummer;





    public Medlem(String navn, int alder, String email, String adresse, int telefonnummer, boolean erAktivtMedlem, String medlemsType, String forældreNavn, String forældreTelefonnummer) {

        this.navn = navn;
        this.alder = alder;
        this.email = email;
        this.adresse = adresse;
        this.telefonnummer = telefonnummer;
        this.erAktivtMedlem = erAktivtMedlem;
        this.medlemsType = medlemsType;
        this.erIRestance = false;

        this.resultater = new ArrayList<>();


    }
    public void tilføjResultat(TræningsResultat resultat) {
        resultater.add(resultat);
    }

    public TræningsResultat findBedsteResultat(String disciplin) {
        return resultater.stream()
                .filter(r -> r.getDisciplin().equalsIgnoreCase(disciplin))
                .min((r1, r2) -> Double.compare(r1.getTid(), r2.getTid()))
                .orElse(null);
    }
    public boolean tilføjOgTjekRekord(TræningsResultat nytResultat) {
        TræningsResultat bedsteResultat = findBedsteResultat(nytResultat.getDisciplin());
        if (bedsteResultat == null || nytResultat.getTid() < bedsteResultat.getTid()) {
            resultater.add(nytResultat);
            return true; // Det er en ny personlig rekord
        } else

        resultater.add(nytResultat);
        return false; // Ikke en personlig rekord
    }

    public List<TræningsResultat> getResultater() {
        return resultater;
    }



    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public int getAlder() {
        return alder;
    }

    public void setAlder(int alder) {
        this.alder = alder;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public int getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(int telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public boolean erAktivtMedlem() {
        return erAktivtMedlem;
    }

    public void setErAktivtMedlem(boolean erAktivtMedlem) {
        this.erAktivtMedlem = erAktivtMedlem;
    }

    public String getMedlemsType() {
        return medlemsType;
    }

    public void setMedlemsType(String medlemsType) {
        this.medlemsType = medlemsType;
    }

    public boolean erIRestance() {
        return erIRestance;
    }

    public void setErIRestance(boolean erIRestance) {
        this.erIRestance = erIRestance;
    }

    public String getForældreNavn() {
        return forældreNavn;
    }

    public void setForældreNavn(String forældreNavn) {
        this.forældreNavn = forældreNavn;
    }

    public String getForældreTelefonnummer() {
        return forældreTelefonnummer;
    }

    public void setForældreTelefonnummer(String forældreTelefonnummer) {
        this.forældreTelefonnummer = forældreTelefonnummer;
    }

    public double beregnKontigent() {
        if (!erAktivtMedlem) return 500;
        if (alder < 18) return 1000;
        if (alder >= 60) return 1600 * 0.75;
        return 1600;
    }

    public boolean erJunior() {
        return alder < 18;
    }

    public boolean erSenior() {
        return alder >= 18;
    }

    public boolean erOver60() {
        return alder >= 60;
    }

@Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Navn: ").append(navn)
            .append(", Alder: ").append(alder)
            .append(", Kontaktinfo: ").append(email)
            .append(", Telefonnummer: ").append(telefonnummer)
            .append(", Adresse: ").append(adresse)
            .append(", Medlemstype: ").append(medlemsType)
            .append(", Kontingentpris: ").append(beregnKontigent());

    // Tilføj forældreinformation, hvis medlemmet er under 18 år
    if (alder < 18) {
        sb.append(", Forældre/Værge: ").append(forældreNavn)
                .append(", Forældre/Værge Telefonnummer: ").append(forældreTelefonnummer);
    }

    return sb.toString();
}

    public static Medlem fraCSV(String csv) {
        String[] dele = csv.split(",");
        String navn = dele[0];
        int alder = Integer.parseInt(dele[1]);
        String email = dele[2];
        String adresse = dele[3];
        int telefonnummer = Integer.parseInt(dele[4]);
        boolean erAktivtMedlem = Boolean.parseBoolean(dele[5]);
        String medlemsType = dele[6];
        String forældreNavn = dele.length > 7 && !dele[7].isEmpty() ? dele[7] : null;
        String forældreTelefonnummer = dele.length > 8 && !dele[8].isEmpty() ? dele[8] : null;

        return new Medlem(navn, alder, email, adresse, telefonnummer, erAktivtMedlem, medlemsType, forældreNavn, forældreTelefonnummer);
    }


    public String toCSV() {
        return navn + "," + alder + "," + email + "," + adresse + "," + telefonnummer + "," + erAktivtMedlem + "," + medlemsType + "," +
                (forældreNavn != null ? forældreNavn : "") + "," +
                (forældreTelefonnummer != null ? forældreTelefonnummer : "");
    }

}