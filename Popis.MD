## Výběr aplikace
Pro testování jsem si vybral svou vlastní semestrální práci napsané v Javě. Jde o RPG herní engine, které umožňuje uživatel nakonfigurovat entity ve hře z konfiguračních souboru.
## Popis aplikace
Hra má definované rozhraní a způsob jak se hraje. Uživatelé pak mohou jen nakonfigurovat vlastnosti jednotlivé entity a mapa hry z souboru. Aplikace je rozdělena do MVC modelu, a tak se budeme hlavně soustředit do části modelu, ve kterém se odehrává veškeré logiky hry.
## Testované třídy
### Mapa
Reprezentuje mapa hry. Načítá mapu a entity v něm z souborů do hry. Třída dále řeší pohyb a kolize mezi jednotlivé entity na mapě.
### GameConfig
Zodpovídá za deserializace dat z souboru do jednotlivé třídy. Používá ji Mapa při načítání entit z souboru.
Hero
Reprezentuje hlavní hrdiny hry interaguje se všemi entity na mapě (sbírá předměty, vstupuje do souboje s příšery, atd.)
### Inventory
reprezentuje inventář hrdiny, který uchovává všechny předměty, které hrdina nashromáždil. Má maximální počet předmětů, které může hrdina uchovávat. Přes může hrdina používat sbírané předměty.
### NPC
reprezentuje nehratelné postavy, které mohou být v interakci s hrdinou. Může nabízet úkoly hrdinovi a poté je odměňovat předmětem za splnění úkolu.
### Ability
reprezentuje schopnosti, které mohou být použity hrdinou nebo příšerou. Schopnosti mohou být například útok, léčení nebo zvýšení vlastnosti.
### Combat 
reprezentuje souboj mezi hrdinou a příšerou. Třída řeší průběh souboje a výsledek souboje.

