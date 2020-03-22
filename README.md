# Rainbow Table (krysi FS20  @FHNW)

**Assigment:**  
Erstellen Sie mit Java eine Rainbow-Table fur MD5 ausgehend von den ersten 2.000 Passwörtern
der Länge 7 bestehend aus Kleinbuchstaben und Ziffern. (Also von 0000000; ... 0000009; 000000a,
000000b ... ; 000000z; 0000010; ...). Die Kettenlänge soll 2000 betragen, es soll also jeweils 2000
mal die Hashfunktion und die entsprechende Reduktionsfunktion anwendet werden. Verwenden Sie als Reduktionsfunktion 
die auf Folie 3.27 angegebene Konstruktion mit der Menge Z = f0; 1; ... ; 9; a; b; ... ; zg.  

Ermitteln Sie mit der Tabelle und dem Algorithmus aus der Vorlesung (insbesondere dürfen Sie nicht bei der Erstellung der Tabelle "mitlauschen") 
den Klartext zu dem in Hexadezimal Schreibweise angegebenen Hashwert 1d56a37fb6b08aa709fe90e12ca59e12 oder begründen Sie, 
dass dies mit der zu konstruierenden Rainbow-Table nicht möglich ist.

Coded by [Luca Fluri](https://github.com/lucafluri)