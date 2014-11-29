#chunkgen

Mod to pre-generate chunks. This is only required on the server.

##Usage

This mod adds the following command to Minecraft:

```
/chunkgen <x> <y> <height> <width> [dimension]
```

where `x` and `y` are the origin chunk coordinates, height and width describe a rectangle centered at x,y of chunks to generate and dimension is the dimension ID to generate the chunks in.

All arguments should be whole numbers, however `x` and `y` may also be `~` which represents the players current position. 

`dimension` is an optional argument.

```
/chunkgen stop
```

Can be used to stop an in-progress chunk generation.

##Config File

The config file for chunkgen is in the expected place (`config/chunkgen.cfg`).
Setting `width` and `height` to something non-zero will allow you to generate extra chunks as the world is loading. This will run everytime the world is loaded, however it will not overwrite chunks that have already been generated. There are also no performance hits from doing this.

The value `numChunksPerTick` dictates how many chunks are generated per Server Tick. The default value is 1. Take care when setting this value as it may cause severe lag if set too high.

Configuration values can be set via the in-game Mod Config menu.

##Versions

[1.6.4-1.0](https://github.com/gecgooden/chunkgen/releases/tag/v1.0) is for Minecraft 1.6.4 and Forge-9.11.1.964+.

[1.7.10-1.2](https://github.com/gecgooden/chunkgen/releases/tag/1.2) is for Minecraft 1.7.10 and Forge-10.13.0.1180+.

##Feature Request

Either submit a pull request, or open an issue and label it as a feature-request.

##Permissions

Feel free to use this mod in any modpack or server, however please credit me (gecgooden).

##Credits

I'd like to thank my adventurous friend for inspiring this mod.
