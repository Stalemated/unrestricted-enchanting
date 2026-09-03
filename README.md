# Unrestricted Enchanting

**Unrestricted Enchanting** is a tool that allows you to fully customize enchantment compatibilities, as well as incompatibilities.

---

## Features

### Make any enchantment compatible with any other!
- Customize a list of enchantments that will be made compatible with the selected one (e.g., **Infinity + Mending**, **Sharpness + Smite**, or allowing all the protections at the same time)
- Customize a list of custom restrictions if you want to balance your modpack (e.g., making **Mending** incompatible with **Unbreaking**)
- Restrictions always take priority over the Default and Allowed compatibilities
- Automatically detects every enchantment in the game, no matter if modded
- No need to write duplicate entries: allowing Enchantment A with Enchantment B automatically applies the rule in reverse (even if it isn't explicitly set in the config!)

### In-game GUI
- Configure and edit compatibilities directly inside the in-game config
- Server configs instantly sync to joining players
- Only OPed players can modify the config on servers, singleplayer world configs can be edited as usual
- The config file can also be edited directly with a text editor. It is located in `config/unrestricted_enchantments.json5`

---

## Building from Source

Unrestricted Enchanting depends on **S-Lib**, which must be published to your local Maven repository before compiling.

#### 1. Clone and Publish S-Lib
```bash
git clone https://github.com/Stalemated/s-lib.git
cd s-lib
# Publish to maven local
gradlew.bat publishToMavenLocal # (Windows)
./gradlew publishToMavenLocal   # (Linux / macOS)
cd ..
```

#### 2. Clone Unrestricted Enchanting and Build
```bash
git clone https://github.com/Stalemated/unrestricted-enchanting.git
cd unrestricted-enchanting
# Build the mod
gradlew.bat build # (Windows)
./gradlew build   # (Linux / macOS)
```

Output JARs will be located in `[loader]/build/libs/`.

---

## Available Platforms

| Platform | Versions             |
|----------|----------------------|
| Fabric   | 1.20.1, 1.21.1 (WIP) |
| Forge    | 1.20.1               |
| NeoForge | 1.21.1 (WIP)         |

---

## Dependencies

- [S-Lib](https://www.curseforge.com/minecraft/mc-mods/s-lib)
- [YACL](https://www.curseforge.com/minecraft/mc-mods/yacl)

### Fabric Only
- [Fabric API](https://www.curseforge.com/minecraft/mc-mods/fabric-api)
- [ModMenu](https://www.curseforge.com/minecraft/mc-mods/modmenu)
