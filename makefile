JFLEX=jflex
JAVACUP=java-cup-11a.jar
CLASSPATH=$(JAVACUP):./src

all : test.txt sym.class parser.class HepialLexer.class Hepialc.class
	@java -classpath $(JAVACUP):./src:. Hepialc test.txt 
	@java -jar jasmin.jar test.j 

HepialLexer.java : hepial.flex
	jflex hepial.flex

sym.java parser.java : hepial.cup
	java -jar $(JAVACUP) hepial.cup

%.class : %.java
	javac -classpath $(JAVACUP):.:./src:. $<

clean :
	rm -rf  *.j *.class TDS/*class ArbreAbstrait/*class *~ parser.java sym.java $(FILE_JAVA_NAME).java
