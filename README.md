# Programozási technológiák / Programozási környezetek beadandó program

## A program leírása

Egy 4 sorból és 10 oszlopból alló játéktábla mezőire az egyik játékosnak 6 piros, a másik játékosnak 6 kék korongot helyezünk el. A játékosok felváltva következnek, minden lépésben egy saját korongot kell elmozdítani egy négyszomszédos üres mezőre. Az a játékos nyer, aki 4 saját korongot helyez el egymás mellett egy sorban vagy oszlopban.

## Használat

Első használat előtt az alábbi parancsot ki kell adni a projekt könyvtárában:
* unix:
```sh
mvn install:install-file -Dfile=lib/ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar
```

* windows:
```sh
mvn install:install-file -Dfile=lib\ojdbc6.jar -DgroupId=com.oracle -DartifactId=ojdbc6 -Dversion=11.2.0 -Dpackaging=jar
```

A használathoz szükséges megadni az src/main/resources/project.properties fájlban a jdbc.username, jdbc.password és a jdbc.url értékeket.

### Egyéb követelmények

* Minimum maven verzió: __3__
* Minimum java verzió: __1.7__