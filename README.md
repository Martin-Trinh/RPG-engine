# Herní engine - RPG

Tento RPG umožní hráči ovládat vlastního hrdinu v konfigurovatelném světě. Hrdina prochází mapou, sbírá předměty pro zvýšení své energie a života, a další předměty, které mu pomáhají porazit příšery. Kromě toho se ve hře vyskytují NPC postavy, s nimiž může hrdina komunikovat a plnit jejich úkoly. Po splnění úkolu hrdina získá klíč k odemčení brány do dalšího levelu. Na cestě se může setkat s příšerami, které musí porazit, aby splnil úkoly. Mimo příšery se na mapě nacházejí neživé překážky, které hrdina musí překonat pomocí speciálních předmětů (například žebřík, bomba).

## Konfigurace mapy

- Hra umožňuje hráči vybrat mapu (levely), na které jsou umístěny předměty, překážky a NPC postavy. Tyto prvky se načítají ze souboru.
## Konfigurace hrdiny a postavy

- Hráč si vybere typ hrdiny (válečník, mág, lučištník, atd.), kterého poté může dále konfigurovat.
- Hráč má možnost nastavit vlastnosti svého hrdiny, jako je síla, obrana, energie, a další, pomocí konfiguračního souboru.
- Lze také nakonfigurovat předměty, překážky a předměty z souboru.
## Interakce ve hře

- Implementace soubojového systému s příšerami, který se odehrává v novém okně.
- Hráč má možnost překonávání překážek pomocí speciálních pomůcek, které získává během dialogu s NPC postavami. Například použití vody k uhašení ohně na cestě, stavění mostu ze železa přes lávové pole, atd.
- Hrdina může interagovat s dalšími předměty pomocí některých sebraných předmětů, například otevření dveří klíčem nebo rozbíjení truhly palicí.
- Hrdina může vytvářet nové předměty z sebraných surovin, jako je výroba lampy ze žárovky a baterie, pečení chleba z pšenice, vody a ohně, a podobně.