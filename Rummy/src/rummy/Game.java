package rummy;
import java.util.*;

public class Game 
{
	static int numberOfPlayers;
	static Character yesOrNo;
	static boolean aceHigh;
	static int scoreCap;
	static Queue<Player> players;
	static Scanner playerInput;
	static ArrayList<Card> discardPile;
	static Deck shuffledDeck = new Deck();
	static Player currentPlayer; 
	
	AlignString leftJustifyPlayer;
	AlignString centerJustifyPlayer;
	AlignString rightJustifyPlayer;
	AlignString leftJustifyCard;
	AlignString centerJustifyCard;
	AlignString rightJustifyCard;
	//AlignString centerCase;
	
	
	
	public Game()
	{
		numberOfPlayers = 0;
		yesOrNo = 'n';
		aceHigh = false;
		scoreCap = 0;
		players = new LinkedList<Player>();
		playerInput = new Scanner(System.in);
		leftJustifyPlayer = new AlignString(200, "LEFTPLAYER");
		centerJustifyPlayer = new AlignString(200, "CENTERPLAYER");
		rightJustifyPlayer = new AlignString(200, "RIGHTPLAYER");
		rightJustifyCard = new AlignString(200, "RIGHTCARD");
		leftJustifyCard = new AlignString(200, "LEFTCARD");
		centerJustifyCard = new AlignString(200, "CENTERCARD");
		discardPile = new ArrayList<Card>();
		currentPlayer = new Player();
	}
			
	void setUpGame() 
	{
		int input = 0;
		welcomeScreen();
		getGameInfo();
		setUpPlayers();
//		clearConsole();
		distributeCards();
		
		while(input != -1)
		{				
//		
			
			
		//printStats();
		displayTable();
		displayOptions();
		System.out.println("printed this line---------------------------------------------------------------------------------------------------------------------");
		displayTurn();
		System.out.println("*********************************************************************************************************************");
		nextTurn();

		//clearConsole();
		
		
		System.out.println("=====================================================================================================================");
		}
	}
	
	public static void welcomeScreen()
	{
		System.out.print("\tWelcome to the rummy: \n");
		System.out.println("\tThis game could be played by upto 4: ");
		System.out.println("\tYou would have an option to play with high or low!");
		System.out.println("\n\tOkay! Enough of it. lets dive in setting up the game...");
	}
	
	public static void getGameInfo()
	{
		System.out.print("\n\tCan you please enter number of players: ");
		numberOfPlayers = playerInput.nextInt();
		
		while(numberOfPlayers > 4 || numberOfPlayers < 2)
		{
			System.out.println("\tWooooowooow!!! Players must be between 2 - 4...");
			System.out.print("1\tCan you please enter number of players: ");
			numberOfPlayers = playerInput.nextInt();
		}
		
		System.out.print("\tHow about ace with high? (y/n): ");
		yesOrNo = playerInput.next().charAt(0);
		yesOrNo = Character.toLowerCase(yesOrNo);
		aceHigh = yesOrNo.equals('y');
				
		/* TODO: Check if the user enters valid input or not */
		
		System.out.print("\tHow about the score cap: ");
		scoreCap = playerInput.nextInt();
		
		System.out.println("\tAlright! Now lets set up players profile...\n");
	}

	public void setUpPlayers()
	{
		for(int i = 0; i < numberOfPlayers; i++)
		{
			int playerID = i;
			Player player = new Player();
			System.out.print("\tPlease enter player " + (playerID + 1) + "'s name : ");
			player.playerName = playerInput.next();
			player.playerID = playerID;
			players.add(player);			
			playerID++;
		}
		System.out.println();
	}
	
	public void distributeCards()
	{
		
		shuffledDeck.createDeck();
		Player tempPlayer = new Player();
		Card tempCard = new Card(0, 0);
		int cardsToBeDealt = 0;
		
		if(numberOfPlayers  == 2)
			cardsToBeDealt = 20;
		else if(numberOfPlayers == 3)
			cardsToBeDealt  = 21;
		else
			cardsToBeDealt = 28;
		
		while(cardsToBeDealt != 0)
		{
			tempPlayer = players.peek();
			tempCard = shuffledDeck.pop();
			tempPlayer.hand.add(tempCard);	
			players.add(players.poll());
			cardsToBeDealt--;
		}	
	}
	
	public void displayTable()
	{
		if(numberOfPlayers == 2)
			displayTableForTwoPlayers();
		else if(numberOfPlayers == 3)
			displayTableForThreePlayers();
		else
			displayTableForFourPlayers();
		
	}
	
	public void displayTableForTwoPlayers()
	{
		players.add(players.poll());
		currentPlayer = players.peek();
		printPlayerInfoCenter(currentPlayer);
		
		printDeckAndDiscard();
		
		players.add(players.poll());
		currentPlayer = players.peek();
		printCurrentPlayerInfo(currentPlayer);
				
	}
	
	public void printPlayerInfoCenter(Player p)
	{
		int playerID = p.getPlayerID() + 1;
		String playerName = p.getPlayerName();
		System.out.print(centerJustifyPlayer.format((playerID )+".  " + playerName));
		System.out.println();
		System.out.println(centerJustifyPlayer.format("Laid down cards: "));
		int i = 1;
		for(Card c: p.laidDown)
		{
			if(i < 10)
			System.out.print(centerJustifyCard.format(i +  ".  | " + c.toString() + "  | " ));
			i++;
		}
	}
	
	public void printPlayerInfoRight(Player p)
	{
		int playerID = p.getPlayerID() + 1;
		String playerName = p.getPlayerName();
		System.out.print(rightJustifyPlayer.format((playerID )+".  " + playerName));
		
		System.out.println(rightJustifyPlayer.format("Laid down"));
		int i = 1;
		for(Card c: p.laidDown)
		{
			System.out.print(rightJustifyCard.format(i +  ".  | " + c.toString() + "  | " ));
			i++;
		}
	}
	
	public void printPlayerInfoLeft(Player p)
	{
		int playerID = p.getPlayerID() + 1;
		String playerName = p.getPlayerName();
		System.out.print(leftJustifyPlayer.format((playerID )+".  " + playerName));
		
		System.out.println(leftJustifyPlayer.format("Laid down"));
		int i = 1;
		for(Card c: p.laidDown)
		{
			System.out.print(leftJustifyCard.format(i +  ".  | " + c.toString() + "  | " ));
			i++;
		}
	} 
	
	public void printCurrentPlayerInfo(Player p)
	{
		int playerID = p.getPlayerID() + 1;
		String playerName = p.getPlayerName();
		System.out.print(centerJustifyPlayer.format((playerID )+".  " + playerName));
		System.out.println();
		System.out.println(centerJustifyPlayer.format("Laid down cards: "));
		int i = 1;
		for(Card c: p.laidDown)
		{
			System.out.print(centerJustifyCard.format(i +  ".  | " + c.toString() + "  | " ));
			i++;
		}
		System.out.println();
		System.out.println(centerJustifyPlayer.format("Cards in hand: "));
		i = 1;
		
		sortHand(p.hand);
		
		for(Card c: p.hand)
		{
			if(i < 10)
				System.out.print(centerJustifyCard.format(i + "....| " + c.toString() + "  | " ));
			else
				System.out.print(centerJustifyCard.format(i + "...| " + c.toString() + "  | " ));
			i++;
		}
	}
	
	public void displayTableForThreePlayers()

	{
		//players.add(players.poll());
		players.add(players.poll());
		
		currentPlayer = players.peek();
		
		printPlayerInfoLeft(currentPlayer);
		
		//players.add(players.poll());
		players.add(players.poll());
		
		currentPlayer = players.peek();
				
		printPlayerInfoRight(currentPlayer);
		
		printDeckAndDiscard();
		
		//players.add(players.poll());
		players.add(players.poll());
		
		currentPlayer = players.peek();
		
		printCurrentPlayerInfo(currentPlayer);
	}
	
	public void displayTableForFourPlayers()
	{
		players.add(players.poll());
		players.add(players.poll());
	
		currentPlayer = players.peek();
		
		printPlayerInfoCenter(currentPlayer);
		
		players.add(players.poll());
		players.add(players.poll());
		players.add(players.poll());
		
		currentPlayer = players.peek();
		
		printPlayerInfoLeft(currentPlayer);
		
		
		printDeckAndDiscard();
		
		players.add(players.poll());
		players.add(players.poll());
		
		currentPlayer = players.peek();
		
		printPlayerInfoRight(currentPlayer);
			
		players.add(players.poll());
		
		currentPlayer = players.peek();
		
		printCurrentPlayerInfo(currentPlayer);
	}
	
	public void displayOptions()
	{
		int option = 0;
		System.out.println("\n1.  Pick up from Deck.");
		System.out.println("2.  Pick up from discard pile.");	
		option = playerInput.nextInt();
		if(option == 1)
		{
			draw(currentPlayer.hand);
			System.out.println();
		}
		else
		{
			drawFromDiscard(currentPlayer.hand);
		}
	}
	
	public void printDeckAndDiscard()
	{
		System.out.println();
		System.out.print(centerJustifyPlayer.format("========================"));
		System.out.print(centerJustifyPlayer.format("|  Deck: | X |         |"));
		System.out.print(centerJustifyPlayer.format("|  Deck has " + (shuffledDeck.size() + 1) + " card(s) |"));
		System.out.print(centerJustifyPlayer.format("|                      |"));
		System.out.print(centerJustifyPlayer.format("|  Discarded Cards:    |"));		
		
		int i = 1;
		
		for(Card c: discardPile)
		{
			if(i < 10)
				System.out.print(centerJustifyCard.format("|  " + i + "....| " + c.toString() + "  |      | " ));
			else
				System.out.print(centerJustifyCard.format("|  " + i + "...| " + c.toString() + "  |      | " ));
			i++;
		}
		System.out.print(centerJustifyPlayer.format("========================"));
		
		System.out.println("\n");
	}
	
	public void displayTurn()
	{
		int option = 0;
		do
		{
			System.out.println("\n1. Make a run.");
			System.out.println("2.  Discard a card.");	
			option = playerInput.nextInt();
			if(option == 1)
			{	
				if(checkRun(currentPlayer.hand))
				{	
					currentPlayer.laidDown.addAll(suitRun(currentPlayer.hand));
					displayTable();
				}
				else
				{
					System.out.println("A run is not possible with cards in your hand!");
					displayTurn();
				}
			}
			else
				discard(currentPlayer.hand);
		}while(option != 2);
	}

	public ArrayList<Card> suitRun(ArrayList<Card> hand)
	{
		int numberOfCards = 0;
		int cardNumber = 0;
		ArrayList<Card> runCards = new ArrayList<Card>();
		
		do
		{
			numberOfCards = 0;
			cardNumber = 0;
			runCards = new ArrayList<Card>();
			do
			{
				System.out.print("Please enter the number of cards you want to lay down: " );
				numberOfCards = playerInput.nextInt();
				if(numberOfCards < 3)
					System.out.println("Number of cards to be laid down must be more than 2");
			}while(numberOfCards < 3);

			for(int i = 0; i < numberOfCards; i++)
			{
				System.out.println("Enter card number: ");
				cardNumber = playerInput.nextInt() - 1;
				runCards.add(hand.get(cardNumber));
			}
			
			if(checkRun(runCards))
			{
				for(Card c: runCards)
				{
					hand.remove(c);
				}
				break;
			}
			
			if(!checkRun(runCards))
			{
				System.out.println("A run not possible with given cards!");
			}
			
			sortHand(hand);
			
		}while(!checkRun(runCards));
		
		return runCards;
	}
	
	public boolean checkRun(ArrayList<Card> hand)
	{
		// match run check
		
		for(int i = 0 ; i < hand.size()-2; i++)
		{
			if(hand.get(i).getRank() == hand.get(i + 1).getRank())
				if(hand.get(i).getRank() == hand.get(i+2).getRank())
					return true;
		}

		// suit run check
		
		
		ArrayList<Card> clubCards = new ArrayList<Card>();
		ArrayList<Card> spadeCards = new ArrayList<Card>();
		ArrayList<Card> diamondCards = new ArrayList<Card>();
		ArrayList<Card> heartCards = new ArrayList<Card>();
		
		for(Card c: hand)
		{
			if(c.getSuit() == 0)	// add club cards to arraylist
			{
				clubCards.add(c);
				sortHand(clubCards);
			}
			else if(c.getSuit() == 1) 	//add all diamond cards to the arraylist
			{
				diamondCards.add(c);
				sortHand(diamondCards);
			}
			else if(c.getSuit() == 2)	// add all heart cards to the arraylist
			{
				heartCards.add(c);
				sortHand(heartCards);
			}
			else						// add all spade cards to the arraylist
			{
				spadeCards.add(c);
				sortHand(spadeCards);
			}
		}
		
		if(clubCards.size() > 2)
		{
			for(int i = 0 ; i < clubCards.size()-2; i++)
			{
				if((clubCards.get(i + 1).getRank() - clubCards.get(i).getRank()) == 1)
					if((clubCards.get(i + 2).getRank() - clubCards.get(i).getRank()) == 2)
						return true;
			}
		}
		
		if(diamondCards.size() > 2)
		{
			for(int i = 0 ; i < diamondCards.size()-2; i++)
			{
				if((diamondCards.get(i + 1).getRank() - diamondCards.get(i).getRank()) == 1)
					if((diamondCards.get(i + 2).getRank() - diamondCards.get(i).getRank()) == 2)
						return true;
			}
		}
		
		if(heartCards.size() > 2)
		{
			for(int i = 0 ; i < heartCards.size()-2; i++)
			{
				if((heartCards.get(i + 1).getRank() - heartCards.get(i).getRank()) == 1)
					if((heartCards.get(i + 2).getRank() - heartCards.get(i).getRank()) == 2)
						return true;
			}
		}
		
		if(spadeCards.size() > 2)
		{
			for(int i = 0 ; i < spadeCards.size()-2; i++)
			{
				if((spadeCards.get(i + 1).getRank() - spadeCards.get(i).getRank()) == 1)
					if((spadeCards.get(i + 2).getRank() - spadeCards.get(i).getRank()) == 2)
						return true;
			}
		}
		
		return false;
	}
	
	public void sortHand(ArrayList<Card> hand)
	{
		for(int i = 0; i < hand.size()-1; i++)
		{
			for(int j = i + 1; j < hand.size(); j++)
			{
				if(hand.get(i).getRank() > hand.get(j).getRank())
				{
					Card temp = hand.get(i);
					hand.set(i, hand.get(j));
					hand.set(j, temp);
				}
			}
		}
	}
	
	public void discard(ArrayList<Card> hand)
	{
		int num = 0;
		System.out.print("Choose a card to discard: ");
		num = playerInput.nextInt();
		
		discardPile.add(hand.remove(num-1));
		nextTurn();
	}
	
	public void draw(ArrayList<Card> hand)
	{
		hand.add(shuffledDeck.pop());
		sortHand(hand);
		clearConsole();
		displayTable();
	}
	
	public void drawFromDiscard(ArrayList<Card> hand)
	{
		int cardNumber = 0;
		System.out.print("Enter the card number you want: ");
		cardNumber = (playerInput.nextInt()-1);
		ArrayList<Card> tempHand = new ArrayList<Card>();
		
		for(int i=0; i < hand.size(); i++) 
		{
			tempHand.add(hand.get(i));
		}
		
		for(int i = cardNumber; i < discardPile.size(); i++ )
		{
			tempHand.add(discardPile.get(cardNumber)); 
		}
		
		int temp = cardNumber;
		sortHand(tempHand);
		if(!checkRun(tempHand))
		{
			System.out.println("temp hand below");
			System.out.println();
			System.out.println();
			for(Card c: tempHand)
				System.out.println(c);
				
			System.out.println();System.out.println();System.out.println();
			System.out.println("No run possible with the card chosen...");
			displayOptions();
			return;
		}
		
		while(cardNumber != discardPile.size())
		{
			Card removeCard = discardPile.get(cardNumber);
			hand.add(removeCard);
			cardNumber++;
		}
		int pileSize = discardPile.size()-1;
		while(temp-1 != pileSize)
		{
			discardPile.remove(pileSize);
			pileSize--;
		}
		
		clearConsole();
		displayTable();
		
	}
	
	public void nextTurn()
	{
		clearConsole();
		players.add(players.poll());
		displayTable();
		displayOptions();
		System.out.println("---------------------------------------------------------------------------------------------------------------------");
		displayTurn();
		
	}
	
	public void printStats()
	{
		for(Player p: players)
		{
			int playerID = 0;
			p.printPlayerInfo(playerID);
			playerID++;
		}		
	}
	
	public final static void clearConsole()
	{
		for(int i = 0; i < 50; i++)
			System.out.println();
	}
}
