# Devoir-1-IFT3913
CLOE CHANDONNET  20136319,
SARA HADDAD 20208373
Lien du GitHub : https://github.com/saraahaddad/Devoir-1-IFT3913/

# Comment exécuter les JARs ?
1. Il faut télécharger le dossier contenant les 5 jars (tloc.jar, tassert.jar, tls.jar, tropcomp.jar et utilities.jar)
2. Ouvrir un terminal à partir de SARA-CLOE-TP1
3. Ensuite dans son terminal entrer la commande suivante (donner le nom approprié du jar souhaité et de ses arguments correspondants):
          java -jar nomDuJar.jar Args

# Comment compiler et exécuter le projet ?
1. à partir du terminal, cd jusqu'à la source
2. compiler tous les fichiers:
       "javac devoir1\Tls.java"
   Ici, Tls pourrait être remplacé par une étoile (*) pour compiler tous les fichiers en même temps
4. exécuter la classe choisie en mentionnant le package devoir1 dans l'appel, par exemple:
       "java devoir1/Tls C:\Users\15143\OneDrive\OneDrive-UniversitedeMontreal\Documents\GitHub\jfreechart"

# Exécuter avec option afin de générer un fichier CSV
Pour Tls:
java devoir1/Tls -o NomDuCSV CheminDEntree

Pour TropComp:
java devoir1/TropComp -o NomDuCSV CheminDEntree Seuil

