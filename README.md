**Hausarbeit Internet-Anwendungsarchitekturen**
===============================================

Was ihr braucht:

1. einen github Account
2. jdk6, git und Maven3 auf euren Rechnern installiert (*https://help.github.com/articles/set-up-git*)
3. Eclipse
4. das Maven- und EGit-Plugin für Eclipse
5. einen RSA-Key, den ihr bei github eintragen könnt

Den RSA-Key könnt ihr euch von Eclipse generieren lassen, dazu:
Windows -> Preferences -> General -> Network Connections -> SSH2
unter Key Management den RSA-Key generieren lassen und den public-Key bei github hinterlegen.

Außerdem solltet ihr das bin-Verzeichnis von Maven zur Path-Variable hinzufügen.

Dann in Eclipse das Projekt importieren, siehe hier:
*http://blog.brockha.us/archives/443-EGit-Eclipse-mit-Git-benutzen.html*

Wenn alles geklappt hat, solltet ihr euch lokal in den Projekt-Ordner stellen, wo die pom.xml liegt und den Server mit:

`mvn jetty:run`

starten können.