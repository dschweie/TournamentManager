/**
 *  \page installMongoDB Installation des MongoDB Datenbankservers
 *  \tableofcontents
 *  
 *  Auf diesen Seiten wird die Installation des Datenbanksservers f�r 
 *  Windows erkl�rt. F�r den Fall, dass Sie ein anderes Betriebssystem
 *  nutzen, informieren Sie sich auf den Seiten von MongoDB, wie der 
 *  Datenbanksserver zu installieren ist.
 *  
 *  \section    preDownload   Setup f�r Datenbankserver aus dem Internet herunterladen
 *  
 *  \subsection preStep01     Aufruf des Download-Centers von MongoDB
 *  
 *  �ffnen Sie in einem Browser die URL <a href="https://www.mongodb.com/">https://www.mongodb.com/</a>.
 *  
 *  \image html download_database_01_mongo_page.png "Aufrufen des Download-Centers"
 *  
 *  Sie gelangen zu dem Download Center, indem Sie zuerst mit der Maus
 *  �ber "Produkte"<sup>(2)</sup> gehen und dann den Men�punkt "MongoDB Server"<sup>(3)</sup>
 *  anklicken. 
 *   
 *  \subsection preStep02     Download der Installationsdatei
 *  
 *  \image html download_database_02_download_mongo_server.png "Download der Installationsdatei"
 *  
 *  <ol>
 *    <li>Stellen Sie sicher, dass der Reiter "Server" ausgew�hlt ist.</li>
 *    <li>W�hlen Sie die Variante "Community Edition", falls sie nicht automatisch ausgew�hlt ist.</li>
 *    <li>Stellen Sie sicher, dass das ausgew�hlte Betriebssystem korrekt ist.</li>
 *    <li>Wenn alle Einstellung passen, kann der Download gestartet werden.</li>
 *  </ol>
 *  
 *  
 *  \section    Installation  Setup f�r den Datenbankserver ausf�hren
 *
 *  Starten Sie die Installation des Datenbankserver mit dem passenden Setup.
 *  
 *  \note Die Installation dieser Software ben�tigt lokale Administrationsrechte.
 *  
 *  \subsection instStep01    Start des Setup
 *  \image html install_database_01_start_installation.png "Startseite des Setup-Wizard"
 *  
 *  Der Wizard beginnt mit Informationen zu der Version, die installiert werden
 *  soll. Klicken Sie auf die Schaltfl�che "Next".
 *  
 *  \subsection instStep02    Annehmen der Lizenzbedingungen
 *  \image html install_database_02_end-user-license-agreement.png "Lizenzbedingungen der MongoDB"
 *  
 *  <ol>
 *    <li>Die Installation kann nur fortgesetzt werden, wenn der Haken in der Checkbox gesetzt wird.</li>
 *    <li>Wenn die Schaltfl�che "Next" jetzt aktiv ist, k�nnen Sie �ber diese Schaltfl�che zum n�chsten Schritt gelangen.</li>
 *  </ol>
 *  
 *  \subsection instStep03    Ausw�hlen des Funktionsumfangs
 *  \image html install_database_03_complete-setup.png "Auswahl der zu installierenden Funktionen"
 *  
 *  W�hlen Sie den vollst�ndigen Funktionsumfang �ber die Schaltfl�che 
 *  "Complete" aus.
 *  
 *  \note Wenn Sie eine eigene Auswahl des Funktionsumfangs ausw�hlen, kann es sein, dass am Ende Funktionen des Datenbankservers nicht installiert werden und der Tournament Manager nur eingeschr�nkt funktionst�chtig ist. 
 *  
 *  \subsection instStep04    Konfiguation des Datenbankservers als Dienst
 *  \image html install_database_04_service-configuration.png ""
 *  
 *  Der Datenbankserver soll unter Windows als Dienst installiert werden. In 
 *  diesem Fall wird die Datenbank automatisch mit dem Start des 
 *  Betriebssystems gestartet und der Anwender muss sich nicht um den Start
 *  k�mmern.
 *  
 *  Es wird empfohlen, die Einstellungen weitestgehend so zu �bernehmen, wie
 *  es die Installationssoftware vorschl�gt.
 *  
 *  <ol>
 *    <li>In diesem Verzeichnis werden die Daten gespeichert. Wenn Sie ein anderes Verzeichnis bevorzugen, k�nnen Sie den Vorschlag ab�ndern.</li>
 *    <li>In diesem Verzeichnis werden die Log-Dateien gespeichert. Wenn Sie ein anderes Verzeichnis bevorzugen, k�nnen Sie den Vorschlag ab�ndern.</li>
 *    <li>Fahren Sie mit der Installation fort �ber die Schaltfl�che "Next"</ol>
 *  </ol>
 *  
 *  \subsection instStep05    Option zur Installation von MongoDB Compass
 *  \image html install_database_05_install-compass.png "optional kann mit Mongo Compass ein Analyse-Werkzeug installiert werden"
 *  
 *  MongoDB Compass ist eine Software, mit der es m�glich ist, die Inhalte in
 *  der Datenbank anzuzeigen und zu �ndern.
 *  
 *  Entscheiden Sie selbst, ob Sie dieses Werkzeug installieren m�chten. Es kann
 *  auch zu einem sp�teren Zeitpunkt �ber eine gesonderte Installationsdatei
 *  installiert werden. Diese finden Sie im Download-Center unter der Kategorie
 *  Tools.
 *  
 *  Setzen Sie die Installation �ber die Schaltfl�che "Next" fort.
 *  
 *  \subsection instStep06    Start der Installation
 *  \image html install_database_06_install-components.png "Start der Installation"
 *  
 *  Nachdem jetzt alle Schritte der Konfiguration abgeschlossen sind, k�nnen 
 *  Sie �ber die Schaltfl�che "Install" die eigentliche Installation des 
 *  Datenbanksservers starten.
 *  
 *  Die Installation kann einige Minuten in Anspruch nehmen. Der Wizard 
 *  informiert �ber einen Fortschrittsbalken zu welchen Anteil die Schritte
 *  abgearbeitet sind. 
 *  
 *  Nach Abschluss aller Schritte k�nnen Sie mit der Schaltfl�che "Finish" 
 *  die Installation abschlie�en.  
 *  
 *  
 *  
 *  
 */