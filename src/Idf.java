/*
 * Represent an identifier node inside the AST.
*/

public class Idf extends Expression {
    /**
     * Name of the
     */
    private String nom;

    /**
     * Constructor
     */
    public Idf(String nom, String fl, int line, int col) {
        super(fl, line, col);
        this.nom = nom;
    }

    /**
     * Get the identifier value
     */
    public String getNom() {
        return this.nom;
    }


    @Override 
    public String toString(){
        return this.nom;
    }

    /**
     * Accepts a AST visitor
     */
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
