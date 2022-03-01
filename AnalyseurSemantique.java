import java.util.Map;

public class AnalyseurSemantique implements ASTVisitor {

    private Map<String, Object> TDS;
    private Map<String, Boolean> constantOrNot;

    // Pour distinguer de si oui ou non on se trouve dans le bloc des declaration
    private boolean positionBloc = false;

    public AnalyseurSemantique(Map<String, Object> TDS, Map<String, Boolean> constantOrNot) {
        this.TDS = TDS;
        this.constantOrNot = constantOrNot;
    }

    public Class<?> getTheClass(Object node) {
        Class<?> className = null;
        if (node.getClass() == Nombre.class) {
            className = Nombre.class;
        } else if (node.getClass() == Vrai.class || node.getClass() == Faux.class || node instanceof Relation) {
            // On traite tout les booleens comme la classe Vrai quand on compare les classes
            // pour la sémentique
            className = Vrai.class;
        } else if (node.getClass() == Idf.class) {
            if (TDS.containsKey(((Idf) node).getNom())) {
                className = TDS.get(((Idf) node).getNom()).getClass();
            } else {
                throw new RuntimeException("Variable non déclarée à la ligne: " + ((ASTNode) node).getLine());
            }
        }else if(node instanceof Binary){
            className = getTheClass(((Binary)node).getGauche());
        }
        return className;
    }

    public boolean binaryOperationIsOkay(Binary node) {
        // Flag pour les parentheses
        boolean leftOkay = true;
        boolean rightOkay = true;
        boolean okayFlag = false;

        Class<?> classDroite = getTheClass(node.getDroite().accept(this));
        Class<?> classGauche = getTheClass(node.getGauche().accept(this));

        if (node.getDroite() instanceof Parenthese) {
            if (((Parenthese) node.getDroite()).getExpression() instanceof Binary) {
                rightOkay = binaryOperationIsOkay(((Binary) ((Parenthese) node.getDroite()).getExpression()));
                okayFlag = true;
            
            } else if ( ((Parenthese) node.getDroite()).getExpression() instanceof Unary){
                classDroite = getTheClass(((Parenthese) node.getDroite()).getExpression().accept(this));
                rightOkay = true;
            }else { // C'est à dire qu'on a juste mit un nombre ou un boolean dans la parenthese
                     // comme ça: (3)
                classDroite = getTheClass(((Parenthese) node.getDroite()).getExpression());
                rightOkay = true;
            }
        }

        if (node.getGauche() instanceof Parenthese) {
            if (((Parenthese) node.getGauche()).getExpression() instanceof Binary) {
                leftOkay = binaryOperationIsOkay(((Binary) ((Parenthese) node.getGauche()).getExpression()));
                okayFlag = true;
            } else if ( ((Parenthese) node.getGauche()).getExpression() instanceof Unary){
                classGauche = getTheClass(((Parenthese) node.getGauche()).getExpression().accept(this));
                leftOkay = true;
            }else { // C'est à dire qu'on a juste mit un nombre ou un boolean dans la parenthese
                     // comme ça: (3)
                classGauche = getTheClass(((Parenthese) node.getGauche()).getExpression());
                leftOkay = true;
            }
        }
        
        if (okayFlag) {
            return leftOkay && rightOkay;
        }
        return classGauche == classDroite;
    }

    public Object visit(Addition node) {
        if (!binaryOperationIsOkay(node)) {
            throw new RuntimeException("Opération entre deux types différents à la ligne: " + node.getLine());
        }

        return node;
    }

    public Object visit(Assignment node) {

        Object dst = node.getDestination().accept(this);
        Object src = node.getSource().accept(this);

        // On fait ça surtout pour voir si c'est une variable définie ou pas.
        Class<?> dstClass = getTheClass(dst);

        // Dst est forcément un Idf, mais juste au cas ou, on vérifie
        if (dst.getClass() == Idf.class) {
            if (constantOrNot.get(((Idf) dst).getNom()) && this.positionBloc) {
                throw new RuntimeException("Affectation à une variable constante à la ligne : " + node.getLine());
            }
        }
        // Dst est forcéement un Idf, par contre src peut être binaire, ou oper.
        if (src instanceof Binary) {
            Class<?> srcClass = getTheClass(((Binary) src));
            if (srcClass != null && srcClass != dstClass) {
                throw new RuntimeException("Affectation illégale à la ligne : " + node.getLine());
            }
        } else if (src instanceof Unary) {
            if (getTheClass(((Unary) src).getExpression().accept(this)) != dstClass) {
                throw new RuntimeException("Affectation illégale à la ligne : " + node.getLine());
            }
        } else if (getTheClass(src) != dstClass) {
            throw new RuntimeException("Affectation illégale à la ligne : " + node.getLine());
        }
        return node;
    }

    public Object visit(Block node) {
        node.getInstructions().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(SiAlors node) {
        node.getExpr().accept(this);
        node.getInstr().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(SiAlorsSinon node) {
        node.getExpr().accept(this);
        node.getInstr().forEach(i -> i.accept(this));
        node.getInstr2().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(DeclarConst node) {
        Expression expr = node.getExpression();
        Idf idf = node.getId();
        if (getTheClass(idf) != getTheClass(expr)) {
            throw new RuntimeException("Affectation illégale à la ligne : " + node.getLine());
        }
        return node;
    }

    public Object visit(DeclarVar node) {
        node.getIds().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(ProgramDeclaration node) {

        node.getDeclaration().accept(this);
        node.getIdentifier().accept(this);

        // On n'est plus dans les déclarations, du coup on peut intérdire
        // maintenant les affectations aux constantes
        this.positionBloc = !this.positionBloc;
        node.getInstructions().accept(this);
        return node;
    }

    public Object visit(Different node) {
        if (!binaryOperationIsOkay(node) || getTheClass(node.getDroite()) == Vrai.class) {
            throw new RuntimeException("Problème de types à la ligne: " + node.getLine());
        }
        return node;
    }

    public Object visit(Division node) {
        if (!binaryOperationIsOkay(node)) {
            throw new RuntimeException("Opération entre deux types différents à la ligne: " + node.getLine());
        }

        return node;
    }

    public Object visit(Ecrire node) {
        //Si on imprime une expr.
        if(node.getExpr() != null)
            node.getExpr().accept(this);
        return node;
    }

    public Object visit(Egal node) {
        if (!binaryOperationIsOkay(node) || getTheClass(node.getDroite()) == Vrai.class) {
            throw new RuntimeException("Problème de types à la ligne: " + node.getLine());
        }
        return node;
    }

    public Object visit(Et node) {
        if (!binaryOperationIsOkay(node) || getTheClass(node.getDroite().accept(this)) != Vrai.class) {
            throw new RuntimeException("Problème de types à la ligne: " + node.getLine());
        }
        return node;
    }

    public Object visit(Faux node) {
        return node;
    }

    public Object visit(Idf node) {
        if (!TDS.containsKey(node.getNom()) && this.positionBloc) {
            throw new RuntimeException("Variable non déclarée à la ligne: " + node.getLine());
        }
        return node;
    }

    public Object visit(InfEgal node) {
        if (!binaryOperationIsOkay(node) || getTheClass(node.getDroite()) == Vrai.class) {
            throw new RuntimeException("Problème de types à la ligne: " + node.getLine());
        }
        return node;
    }

    public Object visit(Inferieur node) {
        if (!binaryOperationIsOkay(node) || getTheClass(node.getDroite()) == Vrai.class) {
            throw new RuntimeException("Problème de types à la ligne: " + node.getLine());
        }
        return node;
    }

    public Object visit(Lire node) {
        node.getIdf().accept(this);
        return node;
    }

    public Object visit(Moins node) {
        if (getTheClass(node.getExpression().accept(this)) != Nombre.class) {
            throw new RuntimeException("Opération illégale à la ligne: " + node.getLine());
        }
        node.getExpression().accept(this);
        return node.getExpression();
    }

    public Object visit(Plus node) {
        if (getTheClass(node.getExpression().accept(this)) != Nombre.class) {
            throw new RuntimeException("Opération illégale à la ligne: " + node.getLine());
        }
        node.getExpression().accept(this);
        return node;
    }

    public Object visit(Nombre node) {
        return node;
    }

    public Object visit(Non node) {
        if (getTheClass(node.getExpression().accept(this)) != Vrai.class) {
            throw new RuntimeException("Opération illégale à la ligne: " + node.getLine());
        }
        node.getExpression().accept(this);
        return node;
    }

    public Object visit(Ou node) {
        if (!binaryOperationIsOkay(node) || getTheClass(node.getDroite().accept(this)) != Vrai.class) {
            throw new RuntimeException("Problème de types à la ligne: " + node.getLine());
        }
        return node;
    }

    public Object visit(Parenthese node) {
        node.getExpression().accept(this);
        return node.getExpression();
    }

    public Object visit(Pour node) {
        // Il faut vérifier que la borneInf, l'Idf, et la borneSup sont du meme type!
        // Et on ne veut pas incrémenter un booléen
        if (getTheClass(node.getIdf()) == Vrai.class || getTheClass(node.getIdf()) != getTheClass(node.getBorneInf())
                || getTheClass(node.getIdf()) != getTheClass(node.getBorneSup())) {

            throw new RuntimeException("Erreur de types à la ligne: " + node.getLine());
        }
        node.getInstr().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(Multiplication node) {
        if (!binaryOperationIsOkay(node)) {
            throw new RuntimeException("Opération entre deux types différents à la ligne: " + node.getLine());
        }
        return node;
    }

    public Object visit(Soustraction node) {
        if (!binaryOperationIsOkay(node)) {
            throw new RuntimeException("Opération entre deux types différents à la ligne: " + node.getLine());
        }
        return node;
    }

    public Object visit(SupEgal node) {
        if (!binaryOperationIsOkay(node) || getTheClass(node.getDroite()) == Vrai.class) {
            throw new RuntimeException("Problème de types à la ligne: " + node.getLine());
        }
        return node;
    }

    public Object visit(Superieur node) {
        if (!binaryOperationIsOkay(node) || getTheClass(node.getDroite()) == Vrai.class) {
            throw new RuntimeException("Problème de types à la ligne: " + node.getLine());
        }
        return node;
    }

    public Object visit(TantQue node) {
        node.getExpr().accept(this);
        node.getInstr().forEach(i -> i.accept(this));
        return node;
    }

    public Object visit(Tilda node) {
        if (getTheClass(node.getExpression().accept(this)) != Nombre.class) {
            throw new RuntimeException("Opération illégale à la ligne: " + node.getLine());
        }
        node.getExpression().accept(this);
        return node.getExpression();
    }

    public Object visit(Vrai node) {
        return node;
    }

}
