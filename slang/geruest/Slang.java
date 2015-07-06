public class Slang {

    public static void main(String[] args) {

        AtomScanner sc = null;

        try {
            sc = new AtomScanner(System.in);
        } catch (LexerException ex) {
            System.out.println(ex);
            return;
        }

        while (sc.hasNext()) {
            if (sc.hasNextString()) {
                System.out.println("STRING: " + sc.nextString());
            }
            else if (sc.hasNextInteger()) {
                System.out.println("INTEGER: " + sc.nextInteger());
            }
            else if (sc.hasNextAtom()) {
                System.out.println("ATOM: " + sc.nextAtom());
            }
            else {
                System.out.println("UNKNOWN: " + sc.next());
            }
        }
    }

}
