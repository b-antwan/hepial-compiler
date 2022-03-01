public class Parenthese extends Expression {
    
    private Expression expr;
    
    public Parenthese(Expression e, String fl, int line, int col){
        super(fl, line, col);
        this.expr = e;
    }

    public Expression getExpression(){
        return this.expr;
    }

    public void lierExpression(Expression e){
        this.expr = e;
    }

    @Override
    public String toString(){
        return "Parentheses: " + this.expr.toString();
    }

    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
