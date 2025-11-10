### Aufgabe 1
Wir haben folgende Beschreibung einer Verkaufssoftware:

*Über die Verkaufssoftware kann das Autohaus seinen Verkäufern Rabattregeln vorgeben: Bei einem Kaufpreis von weniger
als 15’000 CHF soll kein Rabatt gewährt werden. Bei einem Preis bis zu 20’000 CHF sind 5% Rabatt angemessen. Liegt der
Kaufpreis unter 25’000 CHF sind 7% Rabatt möglich, darüber sind 8,5 % Rabatt zu gewähren.*

Leiten Sie aus dieser Beschreibung Testfälle ab. Wir wollen beide Varianten von Testfällen untersuchen.

* Eine Tabelle mit abstrakten Testfällen. Hier verwenden Sie logische Operatoren wie > , < , etc.

* Eine Tabelle mit konkreten Testfällen. Hier verwenden Sie ganz konkrete Eingabe-Werte, um die Testfälle zu erstellen.

### Aufgabe 2
Suchen Sie sich eine Webseite zum Thema **Autovermietung**.

Definieren Sie *funktionale Black-Box Tests*, die Sie brauchen, um diese Plattform zu betreiben. <br/>
*Listen Sie die 5 wichtigsten Testfälle auf*

Erstellen Sie eine Tabelle mit diesen Testfälle als Markdown und stellen Sie diese in Ihr Repository. Hier eine Idee als Tabelle:

| ID | Beschreibung               | Erwartetes Resultat               |Effektives Resultat               |Status               |Mögliche Ursache               |
|-----------|-------------------------|-------------------------|-------------------------|-------------------------|-------------------------|
|   1     | Website loads successfully | NAfter entering the URL in the browser, the homepage opens and displays car rental options.| Website fails to load, showing “502 Bad Gateway.” | Error |Server downtime or network configuration issue |
|   2     | Search for available cars | After selecting “Zurich” and rental dates, a list of available cars appears.|No cars are displayed; page keeps loading. | Error |API call to database failed or timeout occurred |
|   3     | Booking form submission | After filling in customer details and clicking “Continue,” user is redirected to payment page.| User remains on same page, no confirmation or redirect. | Error |Form validation or backend request issue |
|   4     | Responsive design on mobile | When opening the website on a smartphone, layout adapts to screen size and remains usable. | Page layout breaks; buttons overlap on small screens. | Error |Missing or incorrect CSS media queries  |
|   5     | Secure connection (HTTPS) | The website should automatically use HTTPS and show a valid SSL certificate. | Browser warning: “Connection not secure.” | Error |SSL certificate expired or misconfigured |