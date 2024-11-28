
JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

# L'ensemble des fichiers java
CLASSES = \
        src/Axel.java \
        src/Block.java \
        src/Field.java \
        src/GamePanel.java \
        src/Hop.java 

# Avec "make" coompile les fichiers
default: classes

classes: $(CLASSES:.java=.class)

# Nettoie le répertoire src des .class
clean:
		$(RM) *.class

# Envoie sur Git
push:
	git push origin main