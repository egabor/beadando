# Programozási technológiák

## A program leírása

Egy 4 sorból és 10 oszlopból alló játéktábla mezőire az egyik játékosnak 6 piros, a másik játékosnak 6 kék korongot helyezünk el. A játékosok felváltva következnek, minden lépésben egy saját korongot kell elmozdítani egy négyszomszédos üres mezőre. Az a játékos nyer, aki 4 saját korongot helyez el egymás mellett egy sorban vagy oszlopban.

#### Indítás:
```sh
mvn clean install exec:java -Dexec.mainClass="Controllers.GameController"
```

### Egyéb követelmények

* Minimum maven verzió: __3__
* Minimum java verzió: __1.7__