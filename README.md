#chunkgen

Mod to pre-generate chunks. This is only required on the server.

##Usage

This mod adds the following command to Minecraft:

```
1.10.2+:
/chunkgen <zone|radius|stop>
/chunkgen zone <x> <z> <xSize> <zSize> [dimension] [logToChat]
/chunkgen radius <x> <z> <radius> [dimension] [logToChat]
/chunkgen stop

1.7.10-1.9.4
/chunkgen <x> <z> <xSize> <zSize> [dimension]
/chunkgen stop
```

Using either `zone` or `radius`, you can set shape of chunk generation.

When using `zone`, `x` and `z` define origin of rectangular zone and `xSize` and `zSize`, it's size.
When using `radius`, `x` and `z` define center of circular zone and `radius` it's radius.
`x` and `z` are chunk coordinates (integers), absolute (ex: `45`) or relative (ex: `~5`). `xSize`, `ySize` and `radius` are in chunks (integers).

`dimension` is an optional argument, which can be used to specify dimension to generate chunks in (integer). Can be also replaced with `~` to define dimension you're currently in.

`logToChat`, if `true` will print coordinates of each generated chunk to chat (boolean).

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
[1.10.2-1.4.0](https://github.com/gecgooden/chunkgen/releases/tag/1.4.0) is for Minecraft 1.10.2 and Forge-12.18.1.2014+.

[1.9.4-1.3.0](https://github.com/gecgooden/chunkgen/releases/tag/1.3.0) is for Minecraft 1.9.4 and Forge-12.17.0.1976+

[1.8.9-1.3.0](https://github.com/gecgooden/chunkgen/releases/tag/1.3.0) is for Minecraft 1.8.9 and Forge-11.15.1.1722+.

[1.7.10-1.3.0](https://github.com/gecgooden/chunkgen/releases/tag/1.3.0) is for Minecraft 1.7.10 and Forge-10.13.4.1448+.

Note: Only the latest version of Minecraft will be actively supported. 

##Feature Request

Either submit a pull request, or open an issue and label it as a feature-request.

##Permissions

Feel free to use this mod in any modpack or server, however please credit me (gecgooden).

##Credits

I'd like to thank my adventurous friend for inspiring this mod.
