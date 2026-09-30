# Changelog

## 1.1.0+1.20.1

### General Changes
- Updated the mod to work with S-Lib 3.0.1

## 1.0.0+1.20.1

Initial release!

### Features

#### Make any enchantment compatible with any other!
- Customize a list of enchantments that will be made compatible with the selected one (e.g., **Infinity + Mending**, **Sharpness + Smite**, or allowing all the protections at the same time)
- Customize a list of custom restrictions if you want to balance your modpack (e.g., making **Mending** incompatible with **Unbreaking**)
- Restrictions always take priority over the Default and Allowed compatibilities
- Automatically detects every enchantment in the game, no matter if modded
- No need to write duplicate entries: allowing Enchantment A with Enchantment B automatically applies the rule in reverse (even if it isn't explicitly set in the config!)

#### In-game GUI
- Configure and edit compatibilities directly inside the in-game config
- Server configs instantly sync to joining players
- Only OPed players can modify the config on servers, singleplayer world configs can be edited as usual
- The config file can also be edited directly with a text editor. It is located in `config/unrestricted_enchantments.json5`