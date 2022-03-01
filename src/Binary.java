
public abstract class Binary extends Expression {
    /**
     * The expression at its left
     */
    protected Expression operandeGauche;
    /**
     * The expression at its right
     */
    protected Expression operandeDroite;

    /**
     * Constructor
     */
    public Binary(String fl, int line, int col) {
        super(fl, line, col);
    }

    /**
     * Get the left expression
     */
    public Expression getGauche() {
        return this.operandeGauche;
    }
    /**
     * Get the right expression
     */
    public Expression getDroite() {
        return this.operandeDroite;
    }
    /**
     * Get the binary operator.
     * Must be implemented by the child class.
     */
    public abstract String operateur();

    /**
     * Set the left expression
     */
    public void lierGauche(Expression exp) {
        this.operandeGauche = exp;
    }
    /**
     * Set the right expression
     */
    public void lierDroit(Expression exp) {
        this.operandeDroite = exp;
    }

    /**
     * Apply the operator on the two given values.
     * Must be implemented by the child class.
     */
    //public abstract int apply(int gauche, int droite);
    /**
     * Apply the operator on the two given values.
     * Must be implemented by the child class.
     */
    //public abstract boolean apply(boolean gauche, boolean droite);

    /**
     * Transform this node into a visualisable string
     */
    public String toString() {
        return this.operandeGauche + " " + this.operateur() + " " + this.operandeDroite;
    }
}
