
The program must start with at least one command line argument, 
it has to be the Player name, (matching the value: -playername 'P1' or -playername 'P2') -> every other string passed will cause the program exit.
The parameter P1 or P2 is used to load the a different property file for the player, used for messages and connection parameters.

example: 
java -jar gameofthree.jar -playername P1
or
java -jar gameofthree.jar -playername P2

Providing only the Player name the program will start in listening mode. 
To start the game you have to provide a second command line parameter to one Player named '-sendfirst'
to the Player if you want it to begin sending the first number to the other.

(in two different console):
example 1: 
java -jar gameofthree.jar -playername P2 (listening mode)
java -jar gameofthree.jar -playername P1 -sendfirst (listening and start to send)

example 2:
java -jar gameofthree.jar -playername P1 -sendfirst (listening and start) --> will try to find a connection every 2 seconds until the other player will be started.
java -jar gameofthree.jar -playername P2 (listening mode) --> P1 can now send the first number and the game can start.

You can choose which player has to start providing the command line parameter '-sendfirst' after '-playername' P1 or P2.

You can have a starting number passing it as third argument in the command line after the '-sendfirst'
If the third parameter is accepted as a valid number (not negative) it will be used otherwise a random one (range 2-1000) will be used.

example:
Player.jar gameofthree.jar -playername P1 -sendfirst -initnumber 99 --> starting number = 56
Player.jar gameofthree.jar -playername P1 -sendfirst --> starting number = random 2-1000

When a player get the value equals to 1, the game will stop and the winner will display that, then the players will exit both. 

Note:
-The program is implemented using Sockets + Event Types Subscriber model + BlockingQueue Producer/Consumer
-The first number is random generated in a range 2 - 1000 or passed as command line argument.