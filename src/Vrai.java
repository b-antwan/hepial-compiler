public class Vrai extends Expression {
    public Vrai(String fl, int line, int col){
        super(fl, line, col);
    }

    public boolean getEtat(){
        return true;
    }

    @Override
    public String toString(){
        return "Vrai";
    }

    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }    
}
