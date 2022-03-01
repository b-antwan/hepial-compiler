public abstract class Unary extends Expression{
    protected Expression expr;

    public Unary(String fl, int line, int col) {
        super(fl, line, col);
    }

    public Expression getExpression(){
        return this.expr;
    }

    public void lierExpression(Expression expr){
        this.expr = expr;
    }
    
    @Override
    public String toString(){
        return "Unary: " + this.expr.toString() + "\n";
    }

}
