public class Plus extends Unary{
    
    public Plus(String fl, int line, int col) {
        super(fl, line, col);
    }

    public String operator(){
        return "+";
    }

    @Override
    public String toString(){
        return "Plus: \n";
    }
    
    Object accept(ASTVisitor visitor){
        return visitor.visit(this);
    }
}