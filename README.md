# XPManager

Backwards compatibility with BottledExp's store bottles is maintained in XPManager, store bottles no longer disappear on use but are instead thrown like a normal bottle with their XP value overridden on impact.

## Commands

- bottle (b, xpm)
  - convert: used to convert all standard, non-store bottles in the user's inventory into a singular store bottle
  - get {amount/max}: creates the specified amount of standard XP bottles or the maximum possible given the user's XP
  - store {amount/max} {quantity}: stores the specified amount of XP in a singular throwable bottle, takes an optional quantity parameter
- bottleadmin (ba, xpma)
  - get {amount}: creates the given amount of bottles without the user needing that XP on their player
  - store {amount} {quantity}: stores the specified amount in a single bottle without the user needing to have that XP on them, takes an optional quantity parameter