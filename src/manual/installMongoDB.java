/**
 *  \page installMongoDB Installation des MongoDB Datenbankservers
 *  \tableofcontents
 *  
 *  Auf diesen Seiten wird die Installation des Datenbanksservers für 
 *  Windows erklärt. Für den Fall, dass Sie ein anderes Betriebssystem
 *  nutzen, informieren Sie sich auf den Seiten von MongoDB, wie der 
 *  Datenbanksserver zu installieren ist.
 *  
 *  \section    preDownload   Setup für Datenbankserver aus dem Internet herunterladen
 *  
 *  \subsection preStep01     Aufruf des Download-Centers von MongoDB
 *  
 *  Öffnen Sie in einem Browser die URL <a href="https://www.mongodb.com/">https://www.mongodb.com/</a>.
 *  
 *  \image html download_database_01_mongo_page.png "Aufrufen des Download-Centers"
 *  
 *  Sie gelangen zu dem Download Center, indem Sie zuerst mit der Maus
 *  über "Produkte"<sup>(2)</sup> gehen und dann den Menüpunkt "MongoDB Server"<sup>(3)</sup>
 *  anklicken. 
 *   
 *  \subsection preStep02     Download der Installationsdatei
 *  
 *  \image html download_database_02_download_mongo_server.png "Download der Installationsdatei"
 *  
 *  <ol>
 *    <li>Stellen Sie sicher, dass der Reiter "Server" ausgewählt ist.</li>
 *    <li>Wählen Sie die Variante "Community Edition", falls sie nicht automatisch ausgewählt ist.</li>
 *    <li>Stellen Sie sicher, dass das ausgewählte Betriebssystem korrekt ist.</li>
 *    <li>Wenn alle Einstellung passen, kann der Download gestartet werden.</li>
 *  </ol>
 *  
 *  
 *  \section    Installation  Setup für den Datenbankserver ausführen
 *
 *  Starten Sie die Installation des Datenbankserver mit dem passenden Setup.
 *  
 *  \note Die Installation dieser Software benötigt lokale Administrationsrechte.
 *  
 *  \subsection instStep01    Start des Setup
 *  \image html install_database_01_start_installation.png "Startseite des Setup-Wizard"
 *  
 *  Der Wizard beginnt mit Informationen zu der Version, die installiert werden
 *  soll. Klicken Sie auf die Schaltfläche "Next".
 *  
 *  \subsection instStep02    Annehmen der Lizenzbedingungen
 *  \image html install_database_02_end-user-license-agreement.png "Lizenzbedingungen der MongoDB"
 *  
 *  <ol>
 *    <li>Die Installation kann nur fortgesetzt werden, wenn der Haken in der Checkbox gesetzt wird.</li>
 *    <li>Wenn die Schaltfläche "Next" jetzt aktiv ist, können Sie über diese Schaltfläche zum nächsten Schritt gelangen.</li>
 *  </ol>
 *  
 *  \subsection instStep03    Auswählen des Funktionsumfangs
 *  \image html install_database_03_complete-setup.png "Auswahl der zu installierenden Funktionen"
 *  
 *  Wählen Sie den vollständigen Funktionsumfang über die Schaltfläche 
 *  "Complete" aus.
 *  
 *  \note Wenn Sie eine eigene Auswahl des Funktionsumfangs auswählen, kann es sein, dass am Ende Funktionen des Datenbankservers nicht installiert werden und der Tournament Manager nur eingeschränkt funktionstüchtig ist. 
 *  
 *  \subsection instStep04    Konfiguation des Datenbankservers als Dienst
 *  \image html install_database_04_service-configuration.png ""
 *  
 *  Der Datenbankserver soll unter Windows als Dienst installiert werden. In 
 *  diesem Fall wird die Datenbank automatisch mit dem Start des 
 *  Betriebssystems gestartet und der Anwender muss sich nicht um den Start
 *  kümmern.
 *  
 *  Es wird empfohlen, die Einstellungen weitestgehend so zu übernehmen, wie
 *  es die Installationssoftware vorschlägt.
 *  
 *  <ol>
 *    <li>In diesem Verzeichnis werden die Daten gespeichert. Wenn Sie ein anderes Verzeichnis bevorzugen, können Sie den Vorschlag abändern.</li>
 *    <li>In diesem Verzeichnis werden die Log-Dateien gespeichert. Wenn Sie ein anderes Verzeichnis bevorzugen, können Sie den Vorschlag abändern.</li>
 *    <li>Fahren Sie mit der Installation fort über die Schaltfläche "Next"</ol>
 *  </ol>
 *  
 *  \subsection instStep05    Option zur Installation von MongoDB Compass
 *  \image html install_database_05_install-compass.png "optional kann mit Mongo Compass ein Analyse-Werkzeug installiert werden"
 *  
 *  MongoDB Compass ist eine Software, mit der es möglich ist, die Inhalte in
 *  der Datenbank anzuzeigen und zu ändern.
 *  
 *  Entscheiden Sie selbst, ob Sie dieses Werkzeug installieren möchten. Es kann
 *  auch zu einem späteren Zeitpunkt über eine gesonderte Installationsdatei
 *  installiert werden. Diese finden Sie im Download-Center unter der Kategorie
 *  Tools.
 *  
 *  Setzen Sie die Installation über die Schaltfläche "Next" fort.
 *  
 *  \subsection instStep06    Start der Installation
 *  \image html install_database_06_install-components.png "Start der Installation"
 *  
 *  Nachdem jetzt alle Schritte der Konfiguration abgeschlossen sind, können 
 *  Sie über die Schaltfläche "Install" die eigentliche Installation des 
 *  Datenbanksservers starten.
 *  
 *  Die Installation kann einige Minuten in Anspruch nehmen. Der Wizard 
 *  informiert über einen Fortschrittsbalken zu welchen Anteil die Schritte
 *  abgearbeitet sind. 
 *  
 *  Nach Abschluss aller Schritte können Sie mit der Schaltfläche "Finish" 
 *  die Installation abschließen.  
 *  
 *  
 *  
 *  
 */