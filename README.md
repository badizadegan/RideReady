# RideReady

RideReady ist eine Android-App, die Radfahrern hilft, den besten Tag und das beste Zeitfenster für eine Fahrradtour zu finden.

Die App analysiert Wetterdaten und gibt eine einfache Empfehlung basierend auf Temperatur, Niederschlag und Wind.

---

## Projektidee

Viele Menschen prüfen vor einer Fahrradtour das Wetter.  
Oft ist es aber nicht leicht zu entscheiden, ob ein Tag wirklich gut zum Radfahren geeignet ist.

RideReady unterstützt den Nutzer dabei, schneller eine Entscheidung zu treffen.  
Die App zeigt eine Wettervorhersage für mehrere Tage und hebt den besten Tag sowie ein empfohlenes Zeitfenster hervor.

---

## Hauptfunktionen

- Anzeige der Wettervorhersage für mehrere Tage
- Empfehlung des besten Tages zum Radfahren
- Empfehlung eines passenden Zeitfensters im Detail-Screen
- Verwaltung mehrerer Städte
- Auswahl einer aktiven Stadt
- Automatische Aktualisierung des Forecasts bei einem Stadtwechsel
- Speicherung einfacher App-Einstellungen
- Offline-Fallback über einen lokalen Cache
- Unit-Tests für zentrale Business-Logik

---

## Screens

### Home
Im Home-Screen sieht der Nutzer:
- die aktuell ausgewählte Stadt
- den besten Tag zum Radfahren
- eine Liste mit der Wettervorhersage für mehrere Tage

### Detail
Im Detail-Screen sieht der Nutzer:
- die Wetterdaten für einen bestimmten Tag
- ein empfohlenes Zeitfenster
- stündliche Wetterinformationen

### Cities
Im Cities-Screen kann der Nutzer:
- Städte speichern
- eine Stadt auswählen
- Städte löschen

### Settings
Im Settings-Screen kann der Nutzer aktuell:
- die Temperatureinheit auswählen
- die Länge des Zeitfensters einstellen

---

## Technologien

Für dieses Projekt wurden folgende Technologien verwendet:

- Kotlin
- Jetpack Compose
- MVVM
- Clean Architecture
- Retrofit
- Moshi
- Room
- DataStore
- Koin
- Coroutines
- Flow
- JUnit

---

## Projektstruktur

Die App ist in mehrere Bereiche aufgeteilt:

- **core**  
  Gemeinsame Hilfsklassen wie Result, Fehlerbehandlung und Speicher für Forecast-Daten

- **data**  
  Datenquellen, Repository-Implementierungen, API und lokale Datenbank

- **domain**  
  Business-Logik, UseCases, Domain-Modelle und Repository-Interfaces

- **presentation**  
  UI, Screens, ViewModels, UI-State und Navigation

- **di**  
  Koin-Module für Dependency Injection

---

## Architektur

Die App verwendet eine klare Trennung in drei Layer:

### Presentation
Dieser Layer enthält:
- Compose UI
- ViewModels
- UI-State

### Domain
Dieser Layer enthält:
- UseCases
- Domain-Modelle
- Repository-Interfaces

### Data
Dieser Layer enthält:
- API-Zugriff
- lokale Datenquellen
- Repository-Implementierungen

---

## Architekturdiagramm

Die App folgt einer Clean Architecture mit drei Hauptschichten:

```text
+-----------------------------+
|        Presentation         |
|-----------------------------|
| - Compose Screens           |
| - ViewModels                |
| - UI State                  |
+-------------+---------------+
              |
              v
+-----------------------------+
|           Domain            |
|-----------------------------|
| - UseCases                  |
| - Domain Models             |
| - Repository Interfaces     |
+-------------+---------------+
              |
              v
+-----------------------------+
|            Data             |
|-----------------------------|
| - Repository Implementations|
| - Remote API                |
| - Local Database (Room)     |
| - DataStore                 |
+-------------+---------------+
              |
      +-------+--------+
      |                |
      v                v
+------------+   +----------------+
| Open-Meteo |   | Room / Cache   |
| API        |   | + DataStore    |
+------------+   +----------------+
```
---

## Datenfluss

Der Datenfluss in der App sieht vereinfacht so aus:

1. Die UI löst eine Aktion aus.
2. Das ViewModel ruft einen UseCase auf.
3. Der UseCase verwendet ein Repository.
4. Das Repository lädt Daten aus:
    - der Remote API oder
    - dem lokalen Cache.
5. Das Ergebnis wird zurück an die UI gegeben.

---

## Wetterdaten

Die Wetterdaten werden über die Open-Meteo API geladen.

Verwendete Wetterinformationen:
- Temperatur
- Niederschlag
- Windgeschwindigkeit

Diese Werte werden für die Empfehlung des besten Tages und des besten Zeitfensters verwendet.

---

## Offline-Fallback

Die App verwendet einen lokalen Forecast-Cache mit Room.

Wenn keine Internetverbindung verfügbar ist, kann die App die zuletzt gespeicherten Wetterdaten aus dem lokalen Cache laden.

Dadurch bleibt die App auch bei Netzwerkproblemen nutzbar.

---

## Einstellungen

Für einfache App-Einstellungen wird DataStore verwendet.

Aktuell unterstützt die App:
- Temperatureinheit
- Länge des empfohlenen Zeitfensters

Diese Einstellungen werden lokal gespeichert und beim nächsten Start wieder geladen.

---

## Tests

Die wichtigste Business-Logik wird durch Unit-Tests geprüft.

Getestet wurden unter anderem:

- SaveCityUseCase
- DeleteCityUseCase
- CalculateRideScoreUseCase
- FindBestDayUseCase
- FindBestTimeWindowUseCase

Für die City-UseCases wurde ein FakeRepository verwendet, damit die Tests unabhängig von einer echten Datenquelle laufen.

---

## Warum dieses Projekt?

Mit RideReady wollte ich eine App entwickeln, die ein alltagsnahes Problem löst und gleichzeitig moderne Android-Konzepte verwendet.

Das Projekt war für mich eine gute Möglichkeit, folgende Themen praktisch umzusetzen:

- saubere Architektur
- State Management mit Flow
- lokale Datenspeicherung
- API-Anbindung
- Testbarkeit von Business-Logik

---

## Mögliche Erweiterungen

In Zukunft könnte die App erweitert werden mit:

- automatischer Standorterkennung
- Kartenintegration
- Push-Benachrichtigungen
- weiteren Wetterregeln für die Bewertung
- zusätzlichen Einstellungen

---

## Autorin

Fahimeh Badiezadegan