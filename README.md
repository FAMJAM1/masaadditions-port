# MasaAdditions — unofficial port

An unofficial port of [MasaAdditions](https://github.com/hp3721/masaadditions),
an add-on for masa's mods: Tweakeroo, MiniHUD and Litematica. Built for both
**Fabric** and **NeoForge**.

Неофициальный порт [MasaAdditions](https://github.com/hp3721/masaadditions)
на **Fabric** и **NeoForge**. Дополнение к модам masa: Tweakeroo, MiniHUD и Litematica.

Supported game versions are listed on the [releases page](https://github.com/FAMJAM1/masaadditions-port/releases),
and every file is also on [Modrinth](https://modrinth.com/mod/masaadditions-port).
Поддерживаемые версии игры — на [странице релизов](https://github.com/FAMJAM1/masaadditions-port/releases),
все файлы есть и на [Modrinth](https://modrinth.com/mod/masaadditions-port).

The source is kept one branch per version line and loader — `fabric-1.21.11`,
`neoforge-1.21.8` and so on, with `fabric` and `main` holding the 26.1 line.
This branch is the 26.1 NeoForge build; the scripts in [tools/](tools) live here.

Исходники разложены по ветке на линию версий и загрузчик: `fabric-1.21.11`,
`neoforge-1.21.8` и так далее, а `fabric` и `main` — линия 26.1. На этой ветке
лежит сборка 26.1 под NeoForge и скрипты в [tools/](tools).

[English](#english) · [Русский](#русский)

---

## English

### What's inside

- [TweakerooAdditions](https://github.com/hp3721/masaadditions/wiki/TweakerooAdditions)
- [MiniHUDAdditions](https://github.com/hp3721/masaadditions/wiki/MiniHUDAdditions)
- [LitematicaAdditions](https://github.com/hp3721/masaadditions/wiki/LitematicaAdditions)

Each part stands on its own: without the mod it extends, it simply stays quiet.

### What you need alongside it

On Fabric this sits on masa's own mods:

| Mod | What for | Required |
|---|---|---|
| [MaLiLib](https://modrinth.com/mod/malilib) | the shared base | yes |
| [Litematica](https://modrinth.com/mod/litematica) | for LitematicaAdditions | no |
| [Tweakeroo](https://modrinth.com/mod/tweakeroo) | for TweakerooAdditions | no |
| [MiniHUD](https://modrinth.com/mod/minihud) | for MiniHUDAdditions | no |

masa's mods have no NeoForge builds, so there this port sits on top of *their* ports:

| Mod | What for | Required |
|---|---|---|
| [MaFgLib](https://modrinth.com/mod/mafglib) | the malilib port, the shared base | yes |
| [Forgematica](https://modrinth.com/mod/forgematica) | the Litematica port | for LitematicaAdditions |
| [Tweakerge](https://modrinth.com/mod/tweakerge) | the Tweakeroo port | for TweakerooAdditions |
| [BoccHUD](https://modrinth.com/mod/bocchud) | the MiniHUD port | for MiniHUDAdditions |

Each release names the exact versions it was built against. On NeoForge a few
of those are deliberately not the newest — read the release notes before
installing.

### Installing

1. Install [Fabric](https://fabricmc.net/) or [NeoForge](https://neoforged.net/)
   at the version the release names.
2. Download the library for your loader — MaLiLib on Fabric, MaFgLib on NeoForge —
   plus whichever mods from the table you want, and drop them in `.minecraft/mods`.
3. Drop the MasaAdditions jar for your loader from the
   [releases page](https://github.com/FAMJAM1/masaadditions-port/releases) in there too.

This is a client mod; the server does not need it.

### Building

```
./gradlew build
```

The jar lands in `build/libs/`. Gradle fetches the JDK itself, so nothing needs
installing by hand. Check the result before shipping it:

```
tools/mixinaudit.py build/devlibs/<jar>-dev.jar <the classpath it will run on>
```

Mixins are not checked by the compiler; that script reports the targets that
will not resolve at runtime.

---

## Русский

### Что внутри

- [TweakerooAdditions](https://github.com/hp3721/masaadditions/wiki/TweakerooAdditions)
- [MiniHUDAdditions](https://github.com/hp3721/masaadditions/wiki/MiniHUDAdditions)
- [LitematicaAdditions](https://github.com/hp3721/masaadditions/wiki/LitematicaAdditions)

Каждый раздел включается сам по себе: если соответствующего мода нет, его дополнения просто молчат.

### Что нужно поставить

На Fabric дополнение работает поверх самих модов masa:

| Мод | Зачем | Обязателен |
|---|---|---|
| [MaLiLib](https://modrinth.com/mod/malilib) | общая база | да |
| [Litematica](https://modrinth.com/mod/litematica) | для LitematicaAdditions | нет |
| [Tweakeroo](https://modrinth.com/mod/tweakeroo) | для TweakerooAdditions | нет |
| [MiniHUD](https://modrinth.com/mod/minihud) | для MiniHUDAdditions | нет |

Оригинальные моды masa под NeoForge не выходят, поэтому там порт работает
поверх *их* портов:

| Мод | Зачем | Обязателен |
|---|---|---|
| [MaFgLib](https://modrinth.com/mod/mafglib) | порт malilib, общая база | да |
| [Forgematica](https://modrinth.com/mod/forgematica) | порт Litematica | для LitematicaAdditions |
| [Tweakerge](https://modrinth.com/mod/tweakerge) | порт Tweakeroo | для TweakerooAdditions |
| [BoccHUD](https://modrinth.com/mod/bocchud) | порт MiniHUD | для MiniHUDAdditions |

У каждого релиза указаны версии, против которых он собран. На NeoForge часть
из них намеренно не самые свежие — прочитайте описание релиза перед установкой.

### Установка

1. Поставить [Fabric](https://fabricmc.net/) или [NeoForge](https://neoforged.net/)
   той же версии, что указана у релиза.
2. Скачать библиотеку под свой загрузчик — MaLiLib для Fabric, MaFgLib для NeoForge —
   и нужные из таблицы моды, положить в `.minecraft/mods`.
3. Туда же положить джарник MasaAdditions под свой загрузчик со
   [страницы релизов](https://github.com/FAMJAM1/masaadditions-port/releases).

Мод клиентский: на сервер ставить не нужно.

### Сборка

```
./gradlew build
```

Джарник появится в `build/libs/`. JDK Gradle скачает сам, ставить руками ничего
не нужно. Перед выкладкой стоит проверить результат:

```
tools/mixinaudit.py build/devlibs/<джарник>-dev.jar <classpath, на котором он побежит>
```

Компилятор миксины не проверяет; скрипт показывает цели, которые не найдутся
во время работы.

---

## License · Лицензия

GPL-3.0, see [LICENSE](LICENSE) · см. [LICENSE](LICENSE).

The original is [hp3721/masaadditions](https://github.com/hp3721/masaadditions). Some of the code goes
back to [UsefulMod](https://github.com/Nessiesson/UsefulMod) and
[CutelessMod](https://github.com/Nessiesson/CutelessMod) by [nessie](https://github.com/Nessiesson),
licensed under [MIT](LICENSE_MIT).

Оригинал — [hp3721/masaadditions](https://github.com/hp3721/masaadditions). Часть кода восходит к
[UsefulMod](https://github.com/Nessiesson/UsefulMod) и [CutelessMod](https://github.com/Nessiesson/CutelessMod)
за авторством [nessie](https://github.com/Nessiesson), под лицензией [MIT](LICENSE_MIT).
