import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**Class used for coding and decoding, contains coding and decoding sheme.
 * Map karakteri - key is character and value is its code.
 * Map kodovi - key is code and value is its character.
 * Set znakovi - contains all characters used to write code
 * numCodes - number of characters in sheme*/

public class Sifrovanje {
    private Map<Character, Sifrarnik> karakteri;
    private Map<String, Sifrarnik> kodovi;
    private Set<Character> znakovi;
    private int numCodes = 0;

    //default morse sheme
    private static Sifrarnik[] morsSifrovnik = {new Sifrarnik('a', ".-"),
        new Sifrarnik('b', "-..."),
        new Sifrarnik('c', "-.-."),
        new Sifrarnik('d', "-.."),
        new Sifrarnik('e', "."),
        new Sifrarnik('f', "..-."),
        new Sifrarnik('g', "--."),
        new Sifrarnik('h', "...."),
        new Sifrarnik('i', ".."),
        new Sifrarnik('j', ".---"),
        new Sifrarnik('k', "-.-"),
        new Sifrarnik('l', ".-.."),
        new Sifrarnik('m', "--"),
        new Sifrarnik('n', "-."),
        new Sifrarnik('o', "---"),
        new Sifrarnik('p', ".--."),
        new Sifrarnik('q', "--.-"),
        new Sifrarnik('r', ".-."),
        new Sifrarnik('s', "..."),
        new Sifrarnik('t', "-"),
        new Sifrarnik('u', "..-"),
        new Sifrarnik('v', "...-"),
        new Sifrarnik('w', ".--"),
        new Sifrarnik('x', "-..-"),
        new Sifrarnik('y', "-.--"),
        new Sifrarnik('z', "--.."),

        new Sifrarnik('0', "-----"),
        new Sifrarnik('1', ".----"),
        new Sifrarnik('2', "..---"),
        new Sifrarnik('3', "...--"),
        new Sifrarnik('4', "....-"),
        new Sifrarnik('5', "....."),
        new Sifrarnik('6', "-...."),
        new Sifrarnik('7', "--..."),
        new Sifrarnik('8', "---.."),
        new Sifrarnik('9', "----."),
    };

    public Sifrovanje(){
        this(morsSifrovnik);
    }

    public Sifrovanje(File file) throws Exception{
        this.karakteri = new TreeMap<>();
        this.kodovi = new TreeMap<>();
        this.znakovi = new TreeSet<>();
        try {
            List<String> list = Files.readAllLines(Paths.get(file.getAbsolutePath()));
//            List<Sifrarnik> sifre = new ArrayList<>(); //unused
            for (String s : list){
                Scanner sc = new Scanner(s);
                Sifrarnik sifrarnik = new Sifrarnik(sc.next().charAt(0), sc.next());

                for (Character c : sifrarnik.getKod().toCharArray())
                    znakovi.add(c);

                karakteri.put(sifrarnik.getKarakter(), sifrarnik);
                kodovi.put(sifrarnik.getKod(), sifrarnik);
                numCodes++;

                sc.close();
            }
        } catch (IOException e) {
            System.err.println("Error while loading sheme from file.");
            throw new Exception();
        }
        catch (Exception e){
            System.err.println("Error while loading sheme from file, probably wrong format.");
            throw new Exception();
        }
    }

    public Sifrovanje(Sifrarnik[] niz){
        this.karakteri = new TreeMap<>();
        this.kodovi = new TreeMap<>();
        this.znakovi = new TreeSet<>();
        for (Sifrarnik s : niz) {
            karakteri.put(s.getKarakter(), s);
            kodovi.put(s.getKod(), s);
            numCodes++;
            for (Character c : s.getKod().toCharArray())
                znakovi.add(c);
        }
    }

    public String Sifruj(String s){
        StringBuilder sb = new StringBuilder();
        StringBuilder nepoznatiKarakteri = new StringBuilder();
        char[] text = s.toCharArray();
        for (char c : text) {
            c = Character.toLowerCase(c);
            if(c==' ')
                sb.append(" / ");
            else if(karakteri.containsKey(c))
                sb.append(karakteri.get(c).getKod() + " ");
            else
                nepoznatiKarakteri.append(c + " ");
        }
        return sb.toString() + "\n\n" +
                (nepoznatiKarakteri.toString().isEmpty() ? "" : ("Unknown characters: "+nepoznatiKarakteri.toString()));
    }

    public String Desifruj(String s){
        StringBuilder sb = new StringBuilder();
        StringBuilder nepoznatiKodovi = new StringBuilder();
        Scanner sc = new Scanner(s);
        sc.useDelimiter(" ");

        while (sc.hasNext()){
            String kod = sc.next();
            if(kod.trim().equals("/"))
                sb.append(" ");
            else if(kodovi.containsKey(kod))
                sb.append(kodovi.get(kod).getKarakter());
            else
                nepoznatiKodovi.append(kod + " ");
        }

        return sb.toString() + "\n\n" +
                (nepoznatiKodovi.toString().isEmpty() ? "" : ("Unknown code: " + nepoznatiKodovi.toString()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int i=0;
        for (Sifrarnik s : karakteri.values()) {
            sb.append(s + "\t\t");
            i++;
            if(i%2==0)
                sb.append("\n");
        }

        return sb.toString();
    }

    public String ZnakoviToString() {
        StringBuilder sb = new StringBuilder();
        znakovi.stream().forEach(z -> sb.append(z+" "));
        return sb.toString();
    }

    public int getNumCodes() {
        return numCodes;
    }

    public List<Character> getListOfCharacters(){
        List<Character> list = new ArrayList<>();
        karakteri.keySet().stream().forEach(e -> list.add(e));
        return list;
    }

    public Map<Character, Sifrarnik> getKarakteri() {
        return karakteri;
    }

    public Map<String, Sifrarnik> getKodovi() {
        return kodovi;
    }
}
