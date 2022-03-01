.class public FibonacciQuiz
.super java/lang/Object
.method public static main([Ljava/lang/String;)V
.limit stack 20000
.limit locals 100
.var 0 is fibo I
.var 1 is fiboPrecedent I
.var 2 is entree I
.var 3 is score I
.var 4 is tmp I
.var 5 is test Z
ldc 0
istore 3
ldc 1
istore 0
ldc 1
istore 1
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "Quel est le premier nombre de Fibonacci?"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
new java/util/Scanner
dup
getstatic java/lang/System/in Ljava/io/InputStream;
invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
invokevirtual java/util/Scanner/nextInt()I
istore 2
label_0:
iload 1
iload 2
if_icmpne label_1
ldc 0
goto label_2
label_1:
ldc 1
label_2:
ifeq label_3
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "Non ce n'est pas ça!"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "Quel est le premier nombre de Fibonacci?"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
new java/util/Scanner
dup
getstatic java/lang/System/in Ljava/io/InputStream;
invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
invokevirtual java/util/Scanner/nextInt()I
istore 2
goto label_0
label_3:
ldc 1
istore 2
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "Quel est le deuxième nombre de Fibonacci?"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
new java/util/Scanner
dup
getstatic java/lang/System/in Ljava/io/InputStream;
invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
invokevirtual java/util/Scanner/nextInt()I
istore 2
label_4:
iload 0
iload 2
if_icmpne label_5
ldc 0
goto label_6
label_5:
ldc 1
label_6:
ifeq label_7
iload 3
ldc 1
iadd
istore 3
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "Non ce n'est pas ça!"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "Quel est le deuxième nombre de Fibonacci?"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
new java/util/Scanner
dup
getstatic java/lang/System/in Ljava/io/InputStream;
invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
invokevirtual java/util/Scanner/nextInt()I
istore 2
goto label_4
label_7:
label_8:
iload 0
iload 2
if_icmpeq label_9
ldc 0
goto label_10
label_9:
ldc 1
label_10:
ifeq label_11
iload 3
ldc 1
iadd
istore 3
iload 0
istore 4
iload 0
iload 1
iadd
istore 0
iload 4
istore 1
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "Quel est le nombre de Finbonacci Suivant?"
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
new java/util/Scanner
dup
getstatic java/lang/System/in Ljava/io/InputStream;
invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V
invokevirtual java/util/Scanner/nextInt()I
istore 2
goto label_8
label_11:
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "Non, le bon nombre est: "
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
iload 0
getstatic java/lang/System/out Ljava/io/PrintStream;
swap
invokevirtual java/io/PrintStream/print(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc ""
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc "Votre score est: "
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
iload 3
getstatic java/lang/System/out Ljava/io/PrintStream;
swap
invokevirtual java/io/PrintStream/print(I)V
getstatic java/lang/System/out Ljava/io/PrintStream;
ldc ""
invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V
return
.end method

