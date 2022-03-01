public class DeclarConst  extends Instruction{

    private Idf idf;
    private Expression expr;

    public DeclarConst(Idf idf, Expression expr, String fl, int line, int col){
        super(fl, line, col);
        this.idf = idf;
        this.expr = expr;
    }

    public Idf getId(){
        return this.idf;
    }

    public Expression getExpression(){
        return this.expr;
    }

    public void lierIdf(Idf idf){
        this.idf = idf;
    }

    public void lierExpression(Expression expr){
        this.expr = expr;
    }

    @Override
    public String toString(){
        return "Declaration de Constante: ";
    }

    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}
