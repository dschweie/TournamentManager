*** Settings ***
Library    SwingLibrary
Library    Screenshot

Resource   ../keywords/common.robot

Suite Setup         Start Tournament Manager
Suite Teardown      Close Tournament Manager

*** Test Cases ***

FirstResponse
    Log    Hello Word!
    
Applikation starten und stoppen 
    Select From Menu And Wait  Datei|Turnier anlegen
    Take Screenshot
    
Turnier durch Wizard anlegen
    Open Tournament Wizard

    Select Dialog                Create Tournament
    Button Should Exist          Abbrechen   

    Button Should Be Enabled     Abbrechen
    Button Should Be Disabled    Zurück
    Button Should Be Disabled    Weiter
    
    Push Radio Button            Petanque
    Sleep                        10s
    Button Should Be Enabled     Weiter
    
    Push Button                  Abbrechen
