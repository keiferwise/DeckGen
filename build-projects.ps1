#Go to the base dir
Set-Location D:\Deckgen

#build models
Set-Location .\deckgen-models
mvn clean install

#build cardgen
Set-Location ..\cardgen
mvn clean install

#build deckservice
Set-Location ..\deckservice
mvn clean install

#build main
Set-Location ..\deck-gen-main
mvn clean install

#start the webservices
Start-Process powershell -ArgumentList "-NoProfile -Command Set-Location 'D:\Deckgen\cardgen';  mvn spring-boot:run" -WindowStyle Normal

Start-Process powershell -ArgumentList "-NoProfile -Command Set-Location 'D:\Deckgen\deckservice';  mvn spring-boot:run" -WindowStyle Normal

Start-Process powershell -ArgumentList "-NoProfile -Command Set-Location 'D:\Deckgen\deck-gen-main';  mvn spring-boot:run" -WindowStyle Normal


