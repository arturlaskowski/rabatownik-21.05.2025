## Zadanie 1

### Opis aplikacji
Głównym zadaniem aplikacji "rabatownik",jest naliczanie zniżki, która ma zostać przyznana na zamówienie, na podstawie konfiguracji aplikacji.

#### Aktualne funkcjonalności:
- Możliwość dodawania zamówienia za pomocą [OrderService#addOrder](./src/main/java/com/rabatownik/order/OrderService.java).
- Podczas dodawania zamówienia jest naliczana zniżka, zależna od konfiguracji.
- Czy zniżka ma się naliczyć i jaka zniżka ma się naliczyć, zależy od właściwości `discount.type`. Jeśli jest ustawiona na `none`, żadna zniżka się nie naliczy. Jeśli jest ustawiona na `value`, od kwoty zamówienia zostaje odjęta stała kwota, pochodząca z parametru `discount.value`.

Aplikacja jest rozwijana iteracyjnie, a my będziemy dodawać do niej kolejne funkcjonalności. Po każdej iteracji robimy wspólny przegląd dodanych funkcjonalności.

### Iteracja 1
**Cel:** Dodanie możliwości korzystania ze zniżek procentowych.

#### Wymagania
1. **Dodaj zniżkę procentową.** Aplikacja ma pracować w trybie zniżki procentowej, jeśli właściwość `discount.type` jest ustawiona na `percentage`. Procent zniżki, który ma się naliczyć na zamówieniu, ma być przekazany w właściwości `discount.percentage` jako wartość całkowita, np. `discount.percentage=20` oznacza obniżenie kwoty zamówienia o 20%.
   Metoda do obliczenia kwoty po obniżce, [Money#applyPercentageDiscount](./src/main/java/com/rabatownik/Money.java), jest już gotowa. Dodatkowo, ta funkcjonalność jest już pokryta testem akceptacyjnym, więc jeśli zostanie prawidłowo zaimplementowana, test powinien przejść [OrderServicePercentageDiscountAcceptanceTest](./src/test/java/com/rabatownik/order/OrderServicePercentageDiscountAcceptanceTest.java)`.
2. **Dodaj testy jednostkowe** dla nowo powstałej klasy, analogicznie jak to jest zrobione dla [ValueDiscountStrategy](./src/main/java/com/rabatownik/discount/ValueDiscountStrategy.java) w 
[ValueDiscountStrategyTest](./src/test/java/com/rabatownik/discount/ValueDiscountStrategyTest.java).

### Iteracja 2
**Cel:** Ustawienie cyklicznych wywołań metod do dodawania zamówień i generowania raportów.

#### Wymagania
1. **Dodaj Scheduler do dodawania zamówień.** Ma on wywoływać metodę [OrderService#addOrder](./src/main/java/com/rabatownik/order/OrderService.java) cyklicznie domyśnie co 5 sekund (sparametryzowane), nie zwracając uwagi, czy ostatni job się już zakończył.
   Metoda ta powinna być wywoływana z losowymi wartościami, np. `orderCode=UUID.randomUUID().toString()` i `totalPrice=BigDecimal.valueOf(Math.random() * 1000)`.
2. **Dodaj profil `random-order`.** Ustaw, aby scheduler do dodawania losowych zamówień tworzył się tylko w tym profilu.
3. **Dodaj Scheduler do generowania raportów.** Ma on działać tylko w profilu `report`. Ma on pobierać wszystkie zamówienia, które są w [OrderRepository](./src/main/java/com/rabatownik/order/OrderRepository.java) (dodaj metody, jeśli brakuje), i przekazywać listę tych zamówień do istniejącego komponentu [ReportGenerator](./src/main/java/com/rabatownik/report/ReportGenerator.java).
   Po wykonaniu metody z `ReportGenerator` mają zostać usunięte wszystkie zamówienia. Scheduler ten ma się odpalać domyśnie minutę po każdym ostatnim zakończeniu procesowania oraz rozpocząć swoją pracę domyślnie minutę po starcie aplikacji.

Ostatecznym efektem będzie, że jeśli uruchomisz aplikację w profilach: `random-order`, `report`,
to po minucie od uruchomienia wygeneruje się plik PDF z wszystkimi zamówieniami, które się dodały w ostatniej minucie.
Taki plik będzie się generować co minutę w ścieżce, którą ustawisz dla parametru `report.path`.
Na zamówieniach będą naliczały się zniżki w zależności od ustawień parametrów `discount.*`.

Powodzonka!