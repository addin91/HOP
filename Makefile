JFLAGS = -cp
JC = javac
J = java
LIB = lib/gson-2.8.9.jar

# Avec "make" coompile les fichiers et execute
default: \
		compile \
		execute

# Compile les fichiers .java
compile:
		$(JC) $(JFLAGS) $(LIB) src/*.java

# Execute le programme
execute:
		$(J) $(JFLAGS) .:$(LIB) src/Hop


# Nettoie le répertoire src des .class
clean:
		$(RM) src/*.class

# Envoie sur Git
push:
	git push origin main