JFLAGS = -g
JC = javac
J = java
.SUFFIXES: .java .class
.java.class:
		$(JC) $(JFLAGS) $*.java

# L'ensemble des fichiers java
CLASSES = \
        src/Block.java \
        src/Field.java \
        src/Axel.java \
        src/GamePanel.java \
		src/MenuPanel.java \
		src/Db.java \
        src/Hop.java 

# Avec "make" coompile les fichiers
default: \
		classes \
		execute

execute:
		$(J) src.Hop

classes: $(CLASSES:.java=.class)

# Nettoie le répertoire src des .class
clean:
		$(RM) src/*.class

# Envoie sur Git
push:
	git push origin main