
/**Class contains one pair character and its code*/
public class Sifrarnik implements Comparable<Sifrarnik>{
    private String kod;
    private char karakter;

    public Sifrarnik(char karakter, String morz){
        this.kod = morz;
        this.karakter = karakter;
    }

    public char getKarakter() {
        return karakter;
    }

    public String getKod() {
        return kod;
    }

    @Override
    public String toString() {
        return karakter + " " + kod;
    }


    @Override
    public int compareTo(Sifrarnik sifrarnik) {
        return sifrarnik.getKod().compareTo(this.kod);
    }
}
