# XPManager

XPManager is a drop-in replacement for [BottledExp](https://www.spigotmc.org/resources/bottledexp.2815/). BottledExp is a very old and bloated plugin, so I designed this to be a more lean replacement with some unique additions of its own. There are no dependencies for you to add to your server.

Backwards compatibility with BottledExp's store bottles is maintained in XPManager by default so old bottles will not be rendered useless, this plugin has no config as of now.

Before installing keep in mind that all the niche features of BottledExp may not necessarily be here, what you see below is what you get, but you are free to open a GitHub issue and there's a good chance it will be added. There has also been a design decision made to make the cost of a single regular bottle simply 10 XP. Creating it costs 10 XP, throwing it gives 10 XP.

## Things you might find neat

- You can create many store bottles simultaneously through writing a second int command argument, for example `/b store 1395 64` will create 64 bottles with an XP value of 1395.
- You can choose if store bottles are used instantly on consumption or if they are thrown through a command, useful if you want to use many quickly for instance when mass-enchanting.
- There are simple and intuitive "admin" commands that mirror the standard commands players have access to allowing XP to be refunded easily by your server's staff.
- If you accidentally create a bunch of the wrong XP bottle or XP quantity you can use `/b convert` to instantly turn all the bottles in your inventory into a singular store bottle.
- `/b mend` will give the appropriate XP bottle for the tool in your hand to be repaired.
- XPManager does and always will support Folia as supporting Folia for [EarthMC](https://earthmc.net) is what kicked off creating this plugin in the first place.

## Commands

- bottle (b, xpm): command for normal players
  - convert: used to convert all standard, non-store bottles in the user's inventory into a singular store bottle
  - get {amount/max}: creates the specified amount of standard XP bottles or the maximum possible given the user's XP
  - mend: creates a bottle that will exactly repair the tool currently in your hand
  - stats: shows stats about your current level
  - store {amount/max} {quantity/max}: stores the specified amount of XP in a singular throwable bottle, takes an optional quantity parameter
  - toggle: toggles settings in the plugin for the player running the command
    - thrown: toggles whether store bottles will be thrown or instantly consumed upon their use
  - until {level}: tells you how much xp you need to reach the specified level
- bottleadmin (ba, xpma): command for server staff
  - get {amount}: creates the given amount of bottles without the user needing that XP on their player
  - store {amount} {quantity}: stores the specified amount in a single bottle without the user needing to have that XP on them, takes an optional quantity parameter

## Credits

My thanks to the developers of [NBT API](https://github.com/tr7zw/Item-NBT-API) for their library.

## Building

If you would like to build XPManager, clone the repo and run the shadowJar task. The resulting jar will be located at `XPManager/build/libs/XPManager-VERSION.jar`.