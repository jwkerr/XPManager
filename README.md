# XPManager

Backwards compatibility with BottledExp's store bottles is maintained in XPManager.

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