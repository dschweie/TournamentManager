*** Variables ***
${TM_MAIN_WINDOW} =    Tournament Manager

*** Keywords ***

Open Tournament Wizard
    Select Window              ${TM_MAIN_WINDOW}
    Select From Menu And Wait  Datei|under construction
    Dialog Should Be Open      Create Tournament
    
Start Tournament Manager
    Start Application	       org.dos.tournament.application.TournamentManagerUI
    Select Window              ${TM_MAIN_WINDOW}
    
Close Tournament Manager
    Close Window               ${TM_MAIN_WINDOW}
