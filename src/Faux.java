public class Faux extends Expression {
    public Faux(String fl, int line, int col){
        super(fl, line, col);
    }

    public boolean getEtat(){
        return false;
    }

    @Override
    public String toString(){
        return "Faux";
    }

    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }    
}
