JFLAGS = 
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Symbol.java \
	SymbolTable.java \
        LexicalAnalyzer.java \
        Parser.java \
	Compiler.java
		
default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
