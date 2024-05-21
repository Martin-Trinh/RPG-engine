# Využité technologie

**JavaFx** - realizace grafického rozhraní

**Maven** - správa závislostí

**JUnit** - testování

**GitLab** - verzování

**Lombok** - generování getterů a setterů

**Slfj4** - kombinací s lombokem pro logování

**LogBack** - konfigurace logování

**Jackson** - serializace a deserializace objektů

**Mockito** - mockování objektů pro testování

# Základní návrh OOP modelu hry

![RPG-OOP](uploads/da12c442533e6397110e71d5d18708fb/RPG-OOP.png)

## Popis třídy.

**Mapa**
- Třída sloužící k uložení pozic jednotlivé entity ve hře.

**Position**
- Reprezentuje x,y souřadnic na mapě.

**Quest**
- Reprezetuje úkol, který bude hrdina dostávat od NPC.

**Entity**
- Abstraktní třída reprezentující jednotlivý objekt, který se bude zobrazovat na mapě.

**NPC**
- NPC, který provede dialog s hrdinou a dává hrdině úkol.

**Obstacle**
- Reprezentuje, překážka na mapě překonatelná pomocí předmětu.

**Wall**
- Reprezetuje, zeď na mapě, přes kterou nelze projít.

**Item**

- Abstraktní třída reprezentující předmět sbíratelné hrdinou.

**ObstacleItem**

- Třída, která reprezentuje překážku ve světě hry, kterou hrdina může odstranit, pokud má v inventáři požadovaný předmět.

**Consumable**

-Třída, která reprezentuje předmět, který může hrdina použít k obnovení života nebo many.

**Inventory**

- Třída, která reprezentuje inventář hrdiny. Umožňuje přidávání a odstranění předmětu.

**Equipment**

- Třída, která reprezentuje vybavení hrdiny. Zvyšuje vlastnost hrdiny při nošení.

**Character**

- Třída, která reprezentuje pohybující postavu.

**Hero**

- Třída, která reprezentuje hrdinu ve světě hry.

**Monster**

- Třída, která reprezentuje příšeru ve hře.

**Stat**

- Třída, která reprezentuje vlastnost hrdiny.

**Ability**

- Abstraktní třída, která reprezentuje schopnosti postavy.

**AttackAbility**

- Třída, která reprezentuje útočnou schopnost hrdiny.

**ModifyStatsAbility**

- Třída, která reprezentuje schopnost hrdiny modifikovat své vlastnosti.