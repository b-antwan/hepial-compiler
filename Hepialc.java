import java.util.Vector;
import java_cup.runtime.Symbol;
import java.util.HashMap;
import java.io.*;

public class Hepialc {
    public static void main(String[] arg) {
        // parser myP = new parser(new HepialLexer(new FileReader(arg[1])));
        try { FileReader myFile = new FileReader(arg[0]);
                //Lexicale
                HepialLexer myLex = new HepialLexer(myFile);
                parser myP = new parser(myLex);
                System.out.println("Lex. looking good!");
                //Syntaxique
                ProgramDeclaration program = (ProgramDeclaration)myP.parse().value;
                HashMap<String, Object> TDS = program.getTDS();
                HashMap<String, Boolean> constantOrNot = program.getConstantOrNot();
                System.out.println("Syntax looking good!");

                //Sémentique
                AnalyseurSemantique as = new AnalyseurSemantique(TDS, constantOrNot);
                program.accept(as);
                System.out.println("Sematincs looking good!");
                //Gen de code
                GenerateurDeCode gdc = new GenerateurDeCode(TDS);
                program.accept(gdc);
                
                File fichier = new File("test.j");
                FileOutputStream outputStream = new FileOutputStream(fichier, true);
        
                PrintWriter pW = new PrintWriter("test.j");
                pW.println(gdc.code());
                pW.close();
                
                
                
                System.out.println("Jasmin code generated!");
                
                try {
                }
                catch (Exception e) {

                    System.out.println("parse error");
                }
            }
            catch (Exception e){
                e.printStackTrace();
                System.out.println("invalid file");
            }
    }
}
