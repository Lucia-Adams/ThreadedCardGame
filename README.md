# ThreadedCardGame
Threaded multiplayer card game simulation in Java

This project was completed as part of a University Module with @Jessie-McColm

## Game rules
- The game has n players, each numbered 1 to n, with n being a positive integer, and n decks of cards, again, each numbered 1 to n. 

- Each player will hold a hand of 4 cards. Both these hands and the decks will be drawn from a pack which contains 8n cards. Each card has a face value (denomination) of a non- negative integer1.

- The decks and players will form a ring topology.

At the start of the game, each player will be distributed four cards in a round-robin fashion, from the top of the pack, starting by giving one card to player1, then one card to player2, etc. After the hands have been distributed, the decks will then be filled from the remaining cards in the pack, again in a round-robin fashion.

To win the game, a player needs four cards of the same value in their hand. If a player is given four cards which are all the same value at the start of the game, they should immediately declare this (by their thread printing “Player i wins”, where i should be replaced with the player index), that player thread should then notify the other threads, and exit2.

If the game is not won immediately, then the game progresses as follows: each player picks a card from the top of the deck to their left, and discards one to the bottom of the deck to their right3. This process continues until the first player declares that they have four cards of the same value, at which point the game ends
