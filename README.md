![logo](/src/main/resources/assets/renameit/icon.png)

# RenameIt

## Introduction

This mod can be used only on server or client, it provides commands for changing item/block name, lore or color value.
It also supports [PlaceholderAPI](https://placeholders.pb4.eu/user/mod-placeholders/)
by [Patbox](https://github.com/Patbox).

## Commands

All commands are provided below, be sure to hold item that you want to edit in your main hand.

| Command                              | Description                    |      Permission      |
|:-------------------------------------|:-------------------------------|:--------------------:|
| **/renameit** OR **/ri**             | Base command                   |          X           |
| /ri name _\<name>_                   | Changes name of the item       |    renameit.name     |
| /ri color _\<integer>_               | Changes color of the dyed item |    renameit.color    |
| /ri lore                             | Base lore command              |    renameit.lore     |
| /ri lore add _\<text>_               | Adds new lore line             |  renameit.lore.add   |
| /ri lore set _\<position>_ _\<text>_ | Changes existing lore line     |  renameit.lore.set   |
| /ri lore delete _\<position>_        | Removes existing lore line     | renameit.lore.delete |