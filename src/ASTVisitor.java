/*
 * AST visiteur interface
*/


public interface ASTVisitor {
    Object visit(Addition node);
    Object visit(Assignment node);
    Object visit(Block node);
    Object visit(SiAlors node);
    Object visit(SiAlorsSinon node);
    Object visit(DeclarConst node);
    Object visit(DeclarVar node);
    Object visit(ProgramDeclaration node);
    Object visit(Different node);
    Object visit(Division node);
    Object visit(Ecrire node);
    Object visit(Egal node);
    Object visit(Et node);
    Object visit(Faux node);
    Object visit(Idf node);
    Object visit(InfEgal node);
    Object visit(Inferieur node);
    Object visit(Lire node);
    Object visit(Moins node);
    Object visit(Nombre node);
    Object visit(Non node);
    Object visit(Ou node);
    Object visit(Parenthese node);
    Object visit(Plus node);
    Object visit(Pour node);
    Object visit(Multiplication node);
    Object visit(Soustraction node);
    Object visit(SupEgal node);
    Object visit(Superieur node);
    Object visit(TantQue node);
    Object visit(Tilda node);
    Object visit(Vrai node);

}

















































































