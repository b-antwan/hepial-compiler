public class Ecrire extends Instruction{
    private Expression expr;
    private String val;

    //Si on écrit un string constant
    public Ecrire(String val, String fl, int line, int col){
        super(fl, line, col);
        this.val = val;
    }

    //Si on écrit une expr.
    public Ecrire(Expression expr, String fl, int line, int col){
        super(fl, line, col);
        this.expr = expr;
    }
    
    public String getValeur(){
        return this.val;
    }

    public Expression getExpr(){
        return this.expr;
    }

    public void lierString(String val){
        this.val = val;
    }

    public void lierExpression(Expression expr){
        this.expr = expr;
    }

    @Override
    public String toString(){
        return "Ecrire:";
    }

    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
