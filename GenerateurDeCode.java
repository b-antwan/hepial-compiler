import java.util.Map;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class GenerateurDeCode implements ASTVisitor {

    private Map<String, Object> TDS;

    // Pour distinguer de si oui ou non on se trouve dans le bloc des declaration
    private ArrayList<String> indexes = new ArrayList<>();

    private String tgtCode = "";

    private int labelIndex = 0;

    public String code() {
        return this.tgtCode;
    }

    // Complète le if_cmpXX en ajoutant les labels et goto -> Pas très efficace dans
    // le cas des conditions,
    // mais bien plus facile à mettre en oueuvre
    public void remplirCondition() {
        int tmp = this.labelIndex;
        this.tgtCode += "label_" + tmp + "\n";
        this.labelIndex++;
        this.tgtCode += "ldc 0\n";
        this.tgtCode += "goto label_" + labelIndex + "\n";
        this.tgtCode += "label_" + tmp + ":\n";
        this.tgtCode += "ldc 1\n";
        this.tgtCode += "label_" + labelIndex + ":\n";
        this.labelIndex++;
    }

    // A chaque fois qu'il faut manipuler une variable,
    // Il faut vérifier son index; et si l'index est -1;
    // Alors il faut l'ajouter à la liste et utiliser
    // this.indexes.size() comme nouvel index.
    private int indexVariable(String nom) {
        int index = 0;
        for (String s : this.indexes) {
            if (indexes.get(index).equals(nom))
                return index;
            index += 1;
        }
        return -1;
    }

    public GenerateurDeCode(Map<String, Object> TDS) {
        this.TDS = TDS;
    }

    public Object visit(Addition node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "iadd\n";
        return node;
    }

    public Object visit(Assignment node) {
        if (node.getSource() instanceof Plus) {
            node.getDestination().accept(this);
        }
        node.getSource().accept(this);

        this.tgtCode += "istore " + indexVariable(node.getDestination().getNom()) + "\n";
        return node;
    }

    public Object visit(Block node) {
        node.getInstructions().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(SiAlors node) {
        node.getExpr().accept(this);

        int indexTemporaire = this.labelIndex;
        this.tgtCode += "ifeq label_" + indexTemporaire + "\n";
        this.labelIndex += 1;
        node.getInstr().forEach(i -> i.accept(this));
        this.tgtCode += "label_" + indexTemporaire + ":\n";

        return node;
    }

    public Object visit(SiAlorsSinon node) {
        node.getExpr().accept(this);

        // If Then
        int indexTemporaire = this.labelIndex;
        this.tgtCode += "ifeq label_" + indexTemporaire + "\n";
        this.labelIndex += 1;
        node.getInstr().forEach(i -> i.accept(this));
        int indexTemp2 = this.labelIndex;
        this.tgtCode += "goto label_" + indexTemp2 + "\n";

        // Else
        this.tgtCode += "label_" + indexTemporaire + ":\n";
        node.getInstr2().forEach(i -> i.accept(this));
        this.tgtCode += "label_" + indexTemp2 + ":\n";

        return node;
    }

    public void ecrireDeclaration(Idf node) {
        String id = node.getNom();
        Class<?> clas = TDS.get(id).getClass();
        if (indexVariable(id) != -1) {
            throw new RuntimeException("Variable déjà déclarée!");
        } else {
            this.tgtCode += ".var " + this.indexes.size() + " is " + id;
            this.indexes.add(id);
        }
        if (clas == Nombre.class) {
            this.tgtCode += " I\n";
        } else if (clas == Vrai.class || clas == Faux.class) {
            this.tgtCode += " Z\n";
        } else {
            throw new RuntimeException("Type inconnu à la ligne: " + node.getLine());
        }
    }

    public Object visit(DeclarConst node) {
        ecrireDeclaration(node.getId());
        node.getExpression().accept(this);
        this.tgtCode += "istore " + indexVariable(node.getId().getNom()) + "\n";
        return node;
    }

    public Object visit(DeclarVar node) {

        ArrayList<Idf> idfs = node.getIds();
        for (Idf i : idfs) {
            ecrireDeclaration(i);
        }
        return node;
    }

    public Object visit(ProgramDeclaration node) {

        this.tgtCode += ".class public " + node.getIdentifier().getNom() + "\n";
        this.tgtCode += ".super java/lang/Object\n";
        this.tgtCode += ".method public static main([Ljava/lang/String;)V\n";
        this.tgtCode += ".limit stack 20000\n";
        this.tgtCode += ".limit locals 100\n";

        node.getDeclaration().accept(this);
        // On n'est plus dans les déclarations, du coup on peut intérdire
        // maintenant les affectations aux constantes
        node.getInstructions().accept(this);
        this.tgtCode += "return\n";
        this.tgtCode += ".end method\n";

        return node;
    }

    public Object visit(Different node) {
        node.getDroite().accept(this);
        node.getGauche().accept(this);
        this.tgtCode += "if_icmpne ";
        remplirCondition();
        return node;
    }

    public Object visit(Division node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "idiv\n";
        return node;
    }

    public Object visit(Ecrire node) {
        if (node.getValeur() != null) {
            this.tgtCode += "getstatic java/lang/System/out Ljava/io/PrintStream;\n";
            this.tgtCode += "ldc " + node.getValeur() + "\n";
            this.tgtCode += "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V\n";
        } else {
            node.getExpr().accept(this);
            this.tgtCode += "getstatic java/lang/System/out Ljava/io/PrintStream;\n";
            this.tgtCode += "swap\n";
            this.tgtCode += "invokevirtual java/io/PrintStream/print(I)V\n";
        }
        return node;
    }

    public Object visit(Egal node) {
        node.getDroite().accept(this);
        node.getGauche().accept(this);
        this.tgtCode += "if_icmpeq ";
        remplirCondition();
        return node;
    }

    public Object visit(Et node) {
        node.getDroite().accept(this);
        node.getGauche().accept(this);
        this.tgtCode += "iand\n";
        return node;
    }

    public Object visit(Faux node) {
        tgtCode += "ldc 0\n";
        return node;
    }

    // On utilise ça seulement dans le cas ou le Idf est "à droite" du égal
    // Que pour les iload
    public Object visit(Idf node) {
        this.tgtCode += "iload " + indexVariable(node.getNom()) + "\n";
        return node;
    }

    public Object visit(InfEgal node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "if_icmple ";
        remplirCondition();
        return node;
    }

    public Object visit(Inferieur node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "if_icmplt ";
        remplirCondition();
        return node;
    }

    public Object visit(Lire node) {
        this.tgtCode += "new java/util/Scanner\n";
        this.tgtCode += "dup\n";
        this.tgtCode += "getstatic java/lang/System/in Ljava/io/InputStream;\n";
        this.tgtCode += "invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V\n";
        this.tgtCode += "invokevirtual java/util/Scanner/nextInt()I\n";
        this.tgtCode += "istore " + indexVariable(node.getIdf().getNom()) + "\n";
        return node;
    }

    public Object visit(Moins node) {
        node.getExpression().accept(this);
        this.tgtCode += "ineg\n";
        return node;
    }

    public Object visit(Plus node) {
        node.getExpression().accept(this);
        this.tgtCode += "iadd\n";
        return node;
    }

    public Object visit(Nombre node) {
        this.tgtCode += "ldc " + node.getValeur() + "\n";
        return node;
    }

    public Object visit(Non node) {

        // On met la valeur de notre Expression en haut de la pile
        node.getExpression().accept(this);

        int tmp = this.labelIndex;
        this.labelIndex++;

        this.tgtCode += "ifeq label_" + tmp + "\n";
        this.tgtCode += "ldc 0\n";
        this.tgtCode += "goto label_" + this.labelIndex + "\n";

        tgtCode += "label_" + tmp + ":\n";
        this.tgtCode += "ldc 1\n";

        this.tgtCode += "label_" + this.labelIndex + ":\n";
        this.labelIndex++;

        return node;
    }

    public Object visit(Ou node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "ior\n";
        return node;
    }

    public Object visit(Parenthese node) {
        node.getExpression().accept(this);
        return node.getExpression();
    }

    public Object visit(Pour node) {
        node.getBorneInf().accept(this);

        this.tgtCode += "istore " + indexVariable(node.getIdf().getNom()) + "\n";

        int tmp = this.labelIndex;
        this.labelIndex++;
        int tmp2 = this.labelIndex;
        this.labelIndex++;

        this.tgtCode += "label_" + tmp + ":\n";
        node.getBorneSup().accept(this);
        node.getIdf().accept(this);

        this.tgtCode += "if_icmplt label_" + tmp2 + "\n";

        node.getInstr().forEach(i -> i.accept(this));

        node.getIdf().accept(this);
        this.tgtCode += "ldc 1\n";
        this.tgtCode += "iadd\n";
        this.tgtCode += "istore " + indexVariable(node.getIdf().getNom()) + "\n";
        this.tgtCode += "goto label_" + tmp + "\n";
        this.tgtCode += "label_" + tmp2 + ":\n";

        return node;
    }

    public Object visit(Multiplication node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "imul\n";
        return node;
    }

    public Object visit(Soustraction node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "isub\n";
        return node;
    }

    public Object visit(SupEgal node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "if_icmpge ";
        remplirCondition();
        return node;
    }

    public Object visit(Superieur node) {
        node.getGauche().accept(this);
        node.getDroite().accept(this);
        this.tgtCode += "if_icmpgt ";
        remplirCondition();
        return node;
    }

    public Object visit(TantQue node) {
        int tmp = this.labelIndex;
        this.labelIndex++;
        this.tgtCode += "label_" + tmp + ":\n";
        node.getExpr().accept(this);

        int tmp2 = this.labelIndex;
        this.labelIndex++;

        this.tgtCode += "ifeq label_" + tmp2 + "\n";

        node.getInstr().forEach(i -> i.accept(this));
        this.tgtCode += "goto label_" + tmp + "\n";
        this.tgtCode += "label_" + tmp2 + ":\n";
        return node;
    }

    public Object visit(Tilda node) {
        this.tgtCode += "ldc 1\n";
        this.tgtCode += "ineg\n";
        node.getExpression().accept(this);
        this.tgtCode += "ixor\n";
        return node;
    }

    public Object visit(Vrai node) {
        this.tgtCode += "ldc 1\n";
        return node;
    }

    public Object visit(Unary node) {
        return node;
    }

}
