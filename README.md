# Project - Gwentstone Game Of Cards

### First thoughts
In order to implement this game, it was necessary to
fully understand the input game info and then parse all data
into new classes created by me.

Of course, the new classes are similiar to old ones, but some of
them are a little different or have new fields in them.

### Examples of new implemented classes and a few features of them
class Player() -> it has fields, such as hand of cards of a player,
decks of cards, boolean isTankOnTable (check if player has a "tank" card
on table)

class Game() -> table (arraylist of arraylists) which stores the cards
that are on the table, 2 players, and methods for statistics

class ActionHandler() -> contains all methods that handle commands from
input

class ErrorHandler() -> handles all errors for commands and adds them 
to output node

### Difficulty
The only difficult part in order to implement this game was, as I said,
to fully understand the game rules and classes from input and what they
contain. After that, coding became easier.

### Summary
The project was fun to implement because the rules and ideas were
very good presented.