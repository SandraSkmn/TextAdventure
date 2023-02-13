import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Game {
	
	JFrame window; //Using JFrame to create window for game GUI
	Container con;
	JPanel titlePanel, grimPanel, startPanel, gamePanel, buttonPanel, statusPanel, invPanel, imgPanel, playAgainPanel, endPanel, endImgPanel, losePanel, loseImgPanel, playAgainLosePanel; //Panels for game
	JLabel titleLabel, grimLabel, hpLabel, invLabel, imgLabel, endLabel, endImgLabel, loseLabel, loseImgLabel, gameImageLabel; //For text and images
	Font titleFont = new Font("Chiller", Font.PLAIN, 100); //Setting title font
	Font defaultFont = new Font("Courier New", Font.PLAIN, 28); //Setting other font
	Font smallFont = new Font("Courier New", Font.PLAIN, 20); //Small font
	Font verySmallFont = new Font("Courier New", Font.PLAIN, 18); //Very small font
	JButton startButton, choice1, choice2, choice3, choice4, playAgainButton, playAgainLoseButton; 
	ImageIcon grimage, endImage, loseImage, gameImage;
	JTextArea mainTextArea; 
	
	TitleScreenHandler tsHandler = new TitleScreenHandler();
	ChoiceHandler choiceHandler = new ChoiceHandler();
	RestartScreenHandler restartHandler = new RestartScreenHandler();
	DeathScreenHandler deathHandler = new DeathScreenHandler();
	
	int playerHP, findDaisy, bearHP;
	boolean spiritAlive, keyRetrieved, berriesPicked, berriesFed, vialRetrieved, doorOpen, creatureDead, creatureDaisy, vialThrown;
	Boolean[] playerInv = {false, false, false, false};
	String position, imageName;

	public static void main(String[] args) {
		
		new Game();
		

	}
	
	public Game() {
		
		//Window settings//
		window = new JFrame();
		window.setSize(800, 600); //Setting window dimensions//
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Allows closing window with 'X'
		
		window.getContentPane().setBackground(Color.black); //Background colour of window
		window.setLayout(null); //Disables default layout
		window.setResizable(false); //Cannot be resized
		window.setTitle("Grim's Adventure"); //Title of window
		window.setLocationRelativeTo(null); //So window appears in centre
		
		con = window.getContentPane(); //Creating container for other objects within window
		
		//Title panel settings//
		
		titlePanel = new JPanel();
		titlePanel.setBounds(100, 100, 600, 150); //Box for title - starts at 100px to left and down, width is 600px, height is 150px
		titlePanel.setBackground(Color.black);
		
		titleLabel = new JLabel("Grim's Adventure"); //Set title for game to show
		titleLabel.setForeground(Color.white); //Sets text colour
		titleLabel.setFont(titleFont); //Sets font as defined above
		
		//Picture of grim//
		grimPanel = new JPanel();
		grimPanel.setBounds(340, 250, 120, 120);
		grimPanel.setBackground(Color.black);
		
		grimage = new ImageIcon(getClass().getResource("/Images/Grim_art.jpg"));
		grimLabel = new JLabel(grimage);
		
		//Same as title panel but for start button panel//
		startPanel = new JPanel();
		startPanel.setBounds(275, 420, 250, 70);
		startPanel.setBackground(Color.black);
		
		//Start button//
		startButton = new JButton("Start Game");
		startButton.setBackground(Color.black);
		startButton.setForeground(Color.white);
		startButton.setFont(defaultFont);
		startButton.setFocusPainted(false); //Removes inner border around start button text
		startButton.addActionListener(tsHandler); //Adds function to button to run tsHandler code when clicked
		
		
		//Placing everything//
		titlePanel.add(titleLabel); //Adds label to above title panel
		startPanel.add(startButton); //Adds button to start panel
		grimPanel.add(grimLabel); //Adds grim to panel
		con.add(titlePanel); //Adding title panel to container
		con.add(startPanel); //Adding start panel to container
		con.add(grimPanel); //Adding panel with grim to container
		
		
		window.setVisible(true); //So window can actually show
	
	}
	
	public void createGameScreen() {
		
		
		//Gets rid of title panels//
		titlePanel.setVisible(false);
		grimPanel.setVisible(false);
		startPanel.setVisible(false);
		
		//HP Panel//
		
		statusPanel = new JPanel();
		statusPanel.setBounds(100, 15, 100, 45);
		statusPanel.setBackground(Color.black);
		statusPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		
		con.add(statusPanel);
		
		//Inventory Panel//
		
		invPanel = new JPanel();
		invPanel.setBounds(200, 15, 500, 45);
		invPanel.setBackground(Color.black);
		invPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
		
		con.add(invPanel);
		
		//Status display//
		hpLabel = new JLabel();
		invLabel = new JLabel();
		
		hpLabel.setBackground(Color.black);
		hpLabel.setForeground(Color.white);
		hpLabel.setFont(verySmallFont);
		hpLabel.setPreferredSize(new Dimension(100, 45));
		hpLabel.setHorizontalAlignment(SwingConstants.LEFT);
		hpLabel.setVerticalAlignment(SwingConstants.TOP);
		
		invLabel.setBackground(Color.black);
		invLabel.setForeground(Color.white);
		invLabel.setFont(verySmallFont);
		invLabel.setPreferredSize(new Dimension(500, 45));
		invLabel.setHorizontalAlignment(SwingConstants.LEFT);
		invLabel.setVerticalAlignment(SwingConstants.TOP);
		
		statusPanel.add(hpLabel);
		invPanel.add(invLabel);
		
		//Text Panel//
		gamePanel = new JPanel();
		gamePanel.setBounds(100, 100, 600, 250);
		gamePanel.setBackground(Color.black);
		
		con.add(gamePanel);
		
		//Text display//
		mainTextArea = new JTextArea("");
		mainTextArea.setBounds(100, 100, 600, 250);
		mainTextArea.setBackground(Color.black);
		mainTextArea.setForeground(Color.white);
		mainTextArea.setFont(smallFont);
		mainTextArea.setLineWrap(true); //Sets line wrap if text is out of bounds
		
		gamePanel.add(mainTextArea);
		
		
		
		//Button display//
		buttonPanel = new JPanel();
		buttonPanel.setBounds(300, 350, 400, 150);
		buttonPanel.setBackground(Color.black);
		buttonPanel.setLayout(new GridLayout(4,1)); //Sets buttons in an arrangement of 4x1 (i.e. in a single column) rather than default
		
		con.add(buttonPanel);
		
		//Actual Buttons//
		
		choice1 = new JButton("1");
		choice1.setBackground(Color.black);
		choice1.setForeground(Color.white);
		choice1.setFont(defaultFont);
		choice1.setFocusPainted(false);
		choice1.addActionListener(choiceHandler);
		choice1.setActionCommand("c1");
		buttonPanel.add(choice1);
		
		choice2 = new JButton("2");
		choice2.setBackground(Color.black);
		choice2.setForeground(Color.white);
		choice2.setFont(defaultFont);
		choice2.setFocusPainted(false);
		choice2.addActionListener(choiceHandler);
		choice2.setActionCommand("c2");
		buttonPanel.add(choice2);
		
		choice3 = new JButton("3");
		choice3.setBackground(Color.black);
		choice3.setForeground(Color.white);
		choice3.setFont(defaultFont);
		choice3.setFocusPainted(false);
		choice3.addActionListener(choiceHandler);
		choice3.setActionCommand("c3");
		buttonPanel.add(choice3);
		
		choice4 = new JButton("4");
		choice4.setBackground(Color.black);
		choice4.setForeground(Color.white);
		choice4.setFont(defaultFont);
		choice4.setFocusPainted(false);
		choice4.addActionListener(choiceHandler);
		choice4.setActionCommand("c4");
		buttonPanel.add(choice4);
		
		//Image layout//
		imgPanel = new JPanel();
		imgPanel.setBounds(100, 350, 190, 150);
		imgPanel.setBackground(Color.black);
		con.add(imgPanel);
		
		
		
		setPlayer(); //Initial game setup
		playDecision(wakeUp);
		
		imgPanel.setVisible(true);
		
		
	}
	
	//Show image setup//
	
	public void showImage(GameDecision decision) {
		
		imgPanel.removeAll();
		imageName = decision.getImgName();
		gameImage = new ImageIcon(getClass().getResource(imageName));
		gameImageLabel = new JLabel(gameImage);
		imgPanel.add(gameImageLabel);
		
	}
	
	//End screen setup//
	
	public void createEndScreen() {
		//Gets rid of game panels except image//
		statusPanel.setVisible(false);
		gamePanel.setVisible(false);
		buttonPanel.setVisible(false);
		imgPanel.setVisible(false);
		invPanel.setVisible(false);
		
		
		//End message//
		endPanel = new JPanel();
		endPanel.setBounds(100, 40, 600, 130);
		endPanel.setBackground(Color.black);
		
		endLabel = new JLabel("Thanks for playing!"); 
		endLabel.setForeground(Color.white); 
		endLabel.setFont(titleFont); 
		
		//Picture of grim at end//
		endImgPanel = new JPanel();
		endImgPanel.setBounds(200, 180, 400, 250);
		endImgPanel.setBackground(Color.black);
		
		endImage = new ImageIcon(getClass().getResource("/Images/ygrimwin.jpg"));
		endImgLabel = new JLabel(endImage);
		
		
		//Play again button//
		playAgainPanel = new JPanel();
		playAgainPanel.setBounds(275, 450, 250, 70);
		playAgainPanel.setBackground(Color.black);
		
		playAgainButton = new JButton("Play Again");
		playAgainButton.setBackground(Color.black);
		playAgainButton.setForeground(Color.white);
		playAgainButton.setFont(defaultFont);
		playAgainButton.setFocusPainted(false); 
		playAgainButton.addActionListener(restartHandler); 
		
		endPanel.add(endLabel); 
		playAgainPanel.add(playAgainButton); 
		endImgPanel.add(endImgLabel); 
		con.add(endPanel); 
		con.add(playAgainPanel); 
		con.add(endImgPanel);
	}
	
	//Lose screen setup//
	
	public void createLoseScreen() {
		//Gets rid of game panels//
		statusPanel.setVisible(false);
		gamePanel.setVisible(false);
		buttonPanel.setVisible(false);
		imgPanel.setVisible(false);
		invPanel.setVisible(false);
		
		
		//Lose message//
		losePanel = new JPanel();
		losePanel.setBounds(100, 40, 600, 130);
		losePanel.setBackground(Color.black);
		
		loseLabel = new JLabel("You killed death..."); 
		loseLabel.setForeground(Color.white); 
		loseLabel.setFont(titleFont); 
		
		//Picture of grim at end//
		loseImgPanel = new JPanel();
		loseImgPanel.setBounds(200, 180, 400, 250);
		loseImgPanel.setBackground(Color.black);
		
		loseImage = new ImageIcon(getClass().getResource("/Images/xgrimdead.jpg"));
		loseImgLabel = new JLabel(loseImage);
		
		
		//Play again button//
		playAgainLosePanel = new JPanel();
		playAgainLosePanel.setBounds(275, 450, 250, 70);
		playAgainLosePanel.setBackground(Color.black);
		
		playAgainLoseButton = new JButton("Play Again");
		playAgainLoseButton.setBackground(Color.black);
		playAgainLoseButton.setForeground(Color.white);
		playAgainLoseButton.setFont(defaultFont);
		playAgainLoseButton.setFocusPainted(false); 
		playAgainLoseButton.addActionListener(deathHandler); 
		
		losePanel.add(loseLabel); 
		playAgainLosePanel.add(playAgainLoseButton); 
		loseImgPanel.add(loseImgLabel); 
		con.add(losePanel); 
		con.add(playAgainLosePanel); 
		con.add(loseImgPanel);
	}
	
	
	//Gives functionality to start button to change to game screen when clicked//
	
	public class TitleScreenHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			createGameScreen(); //Runs above create game screen method 
		}
	}
	
	public class RestartScreenHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			endPanel.setVisible(false);
			endImgPanel.setVisible(false);
			playAgainPanel.setVisible(false);
			createGameScreen(); 
		}
	}
	
	public class DeathScreenHandler implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			losePanel.setVisible(false);
			loseImgPanel.setVisible(false);
			playAgainLosePanel.setVisible(false);
			createGameScreen(); 
		}
	}
	
	
	
	
	//Adds setup details to game//
	
	public void setPlayer() {
		playerHP = 20;
		bearHP = 20;
		playerInv[0] = false;
		playerInv[1] = false;
		playerInv[2] = false;
		playerInv[3] = false;
		
		spiritAlive = true;
		keyRetrieved = false;
		berriesPicked = false;
		berriesFed = false;
		vialRetrieved = false;
		doorOpen = false;
		creatureDead = false;
		creatureDaisy = false;
		vialThrown = false;
		
		hpLabel.setText("HP: " + playerHP);
		invLabel.setText("Inventory: ");
		
		
		lightningSwear.setGameText("A large bolt of lightning crashes from the sky. \nElectricity jolts through you. \nYou hear the booming voice again: \n\n'Try swear at me one more time, I dare you.' \n\nYou lose 10HP.");
		lightningSwear.setImgName("blightning.jpg");
		lightningSwear.setOption1("Continue");
		lightningSwear.setOption2("");
		lightningSwear.setOption3("");
		lightningSwear.setOption4("");
		lightningSwear.setPosition("lightning");
		lightningSwear.setChangeHP(-10);
		
		lightningNothing.setGameText("A small bolt of lightning crashes from the sky. \nElectricity jolts through you. \nYou hear the booming voice again: \n\n'Maybe that will wake you up.' \n\nYou lose 5HP.");
		lightningNothing.setImgName("blightning.jpg");
		lightningNothing.setOption1("Continue");
		lightningNothing.setOption2("");
		lightningNothing.setOption3("");
		lightningNothing.setOption4("");
		lightningNothing.setPosition("lightning");
		lightningNothing.setChangeHP(-5);
	
		getUp.setGameText("You find yourself in a large open plain. \nIt doesn't look like heaven, hell or Earth. \nYou see a faint glow in the distance. \n\nWhat do you do?");
		getUp.setImgName("copenplain.jpg");
		getUp.setOption1("Walk towards the glow");
		getUp.setOption2("Explore around here");
		getUp.setOption3("");
		getUp.setOption4("");
		getUp.setPosition("plains");
		getUp.setChangeHP(0);
		
		speakSpirit.setGameText("You say hi to the spirit and ask where you are. \nHe tells you you're in purgatory. \nHe's complaining that he's very hungry. \nHe said he'll give you a gift for food. \n\nWhat do you do?");
		speakSpirit.setImgName("gspeakspirit.jpg");
		speakSpirit.setOption1("Look for food");
		speakSpirit.setOption2("Go back");
		speakSpirit.setOption3("");
		speakSpirit.setOption4("");
		speakSpirit.setPosition("spiritSpeak");
		speakSpirit.setChangeHP(0);
		
		giveSpiritDaisy.setGameText("You offer the spirit a daisy. He looks at you \ndisappointedly. \n\n'I appreciate the offer, but I don't eat daisies'\n\nYou shrug back at him. \n\nWhat do you do?");
		giveSpiritDaisy.setImgName("hsadspirit.jpg");
		giveSpiritDaisy.setOption1("Go back");
		giveSpiritDaisy.setOption2("");
		giveSpiritDaisy.setOption3("");
		giveSpiritDaisy.setOption4("");
		giveSpiritDaisy.setPosition("spiritSpeakDaisy");
		giveSpiritDaisy.setChangeHP(0);
		
		attackSpirit.setGameText("You swing your scythe at the spirit. \nHe starts dissolving into thin air.\nYou absorb some of his life energy (+5HP). \nYou hear him faintly cry:\n\n'Why do you have to be so cruel?'\n\nA single key is left behind. You pick it up.");
		attackSpirit.setImgName("jdyingspirit.jpg");
		attackSpirit.setOption1("Continue");
		attackSpirit.setOption2("");
		attackSpirit.setOption3("");
		attackSpirit.setOption4("");
		attackSpirit.setPosition("spiritAttack");
		attackSpirit.setChangeHP(5);
		
		toCampfire.setGameText("You reach a campfire. \nThere is a spirit sitting by it, smiling at you. \n\nWhat do you do?");
		toCampfire.setImgName("fcampfirespirit.jpg");
		toCampfire.setOption1("Speak to the spirit");
		toCampfire.setOption2("Attack the spirit");
		toCampfire.setOption3("Continue walking");
		toCampfire.setOption4("Go back to the plain");
		toCampfire.setPosition("campfire");
		toCampfire.setChangeHP(0);
		
		explorePlain.setGameText("You stay and investigate the area. \nThere's nothing here. \n\nWhat do you do?");
		explorePlain.setImgName("copenplain.jpg");
		explorePlain.setOption1("Keep looking");
		explorePlain.setOption2("Go back");
		explorePlain.setOption3("");
		explorePlain.setOption4("");
		explorePlain.setPosition("daisy");
		explorePlain.setChangeHP(0);
		
		toTrees.setGameText("You continue walking on.\nYou hear a rustling noise behind some trees.\n\nWhat do you do?");
		toTrees.setImgName("ltrees.jpg");
		toTrees.setOption1("Investigate the noise");
		toTrees.setOption2("Ignore and walk on");
		toTrees.setOption3("Back to campfire");
		toTrees.setOption4("");
		toTrees.setPosition("trees");
		toTrees.setChangeHP(0);
		
		toBerries.setGameText("You reach a large berry bush.\nWhatever was causing the rustling seems to have \nscurried away.\nYou pick the berries and snack on a couple\nof them yourself (+2HP).\n\nWhat do you do?");
		toBerries.setImgName("mberrybushfull.jpg");
		toBerries.setOption1("Head back to campfire");
		toBerries.setOption2("Continue walking");
		toBerries.setOption3("");
		toBerries.setOption4("");
		toBerries.setPosition("bush");
		toBerries.setChangeHP(2);
		
		feedBerries.setGameText("You hand over the berries to the spirit. \nHe's extremely thankful and gives you a key in \nreturn.\nHe adds in a vial of mystery liquid as a bonus.\n\nWhat do you do?");
		feedBerries.setImgName("ihappyspirit.jpg");
		feedBerries.setOption1("Sip from the vial");
		feedBerries.setOption2("Continue on");
		feedBerries.setOption3("");
		feedBerries.setOption4("");
		feedBerries.setPosition("feed");
		feedBerries.setChangeHP(0);
		
		sipVial.setGameText("You take a sip from the vial.\nThere's a strong foul taste and you start \nwretching.\nYou don't want to imagine what might have been in\nthere.\nYou lose 5HP.\n\nWhat do you do?");
		sipVial.setImgName("ovial.jpg");
		sipVial.setOption1("Sip from the vial");
		sipVial.setOption2("Continue on");
		sipVial.setOption3("");
		sipVial.setOption4("");
		sipVial.setPosition("vial");
		sipVial.setChangeHP(-5);
		

		
		oldBuilding.setGameText("You get to an old, crumbly building.\nThere's a rusted lock on the front door.\n\nWhat do you do?");
		oldBuilding.setImgName("poldbuilding.jpg");
		oldBuilding.setOption1("Try kick the door");
		oldBuilding.setOption2("Go back");
		oldBuilding.setOption3("");
		oldBuilding.setOption4("");
		oldBuilding.setPosition("building");
		oldBuilding.setChangeHP(0);
		
		
		kickDoor.setGameText("You give the door the most powerful kick you \ncan muster. \nIt doesn't budge.\nYour foot starts throbbing.\n\nYou lose 2HP.");
		kickDoor.setImgName("poldbuilding.jpg");
		kickDoor.setOption1("Continue");
		kickDoor.setOption2("");
		kickDoor.setOption3("");
		kickDoor.setOption4("");
		kickDoor.setPosition("door");
		kickDoor.setChangeHP(-2);
		
		daisyBuilding.setGameText("Seriously?");
		daisyBuilding.setImgName("poldbuilding.jpg");
		daisyBuilding.setOption1("Go back");
		daisyBuilding.setOption2("");
		daisyBuilding.setOption3("");
		daisyBuilding.setOption4("");
		daisyBuilding.setPosition("daisyDoor");
		daisyBuilding.setChangeHP(0);
		
		intoBuilding.setGameText("You step into the building.\nThe main room is large with a tattered carpet \nleading to an old staircase.\nThe stairs look like they go to the basement.\n\nOn your left is a painting of an old man.\nUnderneath the painting is a note that reads:\n'Do not touch'.\n\nWhat do you do?");
		intoBuilding.setImgName("qfrontroom.jpg");
		intoBuilding.setOption1("Go down the stairs");
		intoBuilding.setOption2("Touch the painting");
		intoBuilding.setOption3("Leave the building");
		intoBuilding.setOption4("");
		intoBuilding.setPosition("inside");
		intoBuilding.setChangeHP(0);
		
		touchPainting.setGameText("You run your bony finger along the canvas.\nThe man in the painting slaps you away.\nYou leave him be for now.\n\nYou lost 2HP.");
		touchPainting.setImgName("rpainting.jpg");
		touchPainting.setOption1("Continue");
		touchPainting.setOption2("");
		touchPainting.setOption3("");
		touchPainting.setOption4("");
		touchPainting.setPosition("painting");
		touchPainting.setChangeHP(-2);
		
	
		goDownstairs.setGameText("You head down the stairs. The rotting wooden door\nto the basement is slightly ajar.\nBehind it you can hear a deep growling noise.\nYou peak through the gap in the door.\n\nA giant spider-bear hybrid is frothing at the \nmouth inches from your face.\n\nWhat do you do?");
		goDownstairs.setImgName("sbasementdoor.jpg");
		goDownstairs.setOption1("Fight the creature");
		goDownstairs.setOption2("Go back upstairs");
		goDownstairs.setOption3("");
		goDownstairs.setOption4("");
		goDownstairs.setPosition("basement");
		goDownstairs.setChangeHP(0);
		
		giveDaisy.setGameText("You stick the daisy through the gap in the door.\nThe creature snatches it out of your hand.\nHe gives it a sniff an puts it in a vase in the\ncorner. He's blushing profusely.\nHe admires the daisy with his back to you.\n\nYou walk straight past him.");
		giveDaisy.setImgName("ubeardaisy.jpg");
		giveDaisy.setOption1("Continue");
		giveDaisy.setOption2("Go back upstairs");
		giveDaisy.setOption3("");
		giveDaisy.setOption4("");
		giveDaisy.setPosition("bearDaisy");
		giveDaisy.setChangeHP(0);
		
		throwVial.setGameText("You throw the mystery vial at the creature.\nYou hear a blood-curdling shriek.\nThe creature seems to be in pain.\nIt takes 5HP of damage.\n\nCreature HP remaining: 15\n\nWhat do you do?");
		throwVial.setImgName("vspiderbearfight.jpg");
		throwVial.setOption1("Fight the creature");
		throwVial.setOption2("Go back upstairs");
		throwVial.setOption3("");
		throwVial.setOption4("");
		throwVial.setPosition("throwVialBasement");
		throwVial.setChangeHP(0);
		
		fightBear.setGameText("");
		fightBear.setImgName("tspiderbear.jpg");
		fightBear.setOption1("Continue");
		fightBear.setOption2("");
		fightBear.setOption3("");
		fightBear.setOption4("");
		fightBear.setPosition("fight");
		fightBear.setChangeHP(0);
		
		retaliateBear.setGameText("");
		retaliateBear.setImgName("vspiderbearfight.jpg");
		retaliateBear.setOption1("Continue fighting");
		retaliateBear.setOption2("Leave");
		retaliateBear.setOption3("");
		retaliateBear.setOption4("");
		retaliateBear.setPosition("fightBack");
		retaliateBear.setChangeHP(0);
		
		endScene.setGameText("You walk to the back of the creature's den.\nPinned to the wall is a map of purgatory.\nThe portal to Earth is clearly circled.\nYou let out a sigh of relief, and head there in a\nhurry.\n\nYou'll still have time to meet your soul quota\ntoday.");
		endScene.setImgName("wendmap.jpg");
		endScene.setOption1("End");
		endScene.setOption2("");
		endScene.setOption3("");
		endScene.setOption4("");
		endScene.setPosition("finalScene");
		endScene.setChangeHP(0);
		
		
	}
	
	//Game stages and decisions//
	
	//Starting stage//
	GameDecision wakeUp = new GameDecision("You wake up with your face wedged in a bush.\nYour head is pounding.\nA booming voice shouts from the skies.\n\n'Get up Grim! You still have souls to collect!' \n\nWhat do you do?", "abush.jpg", "Swear at the voice", "Get up obediently", "Do nothing", "", "start", 0);
	
	
	//Generic method for all decisions to use//
	public void playDecision(GameDecision game) {
		position = game.getPosition();
		mainTextArea.setText(game.getGameText());
//		imageName = game.getImgName();
//		gameImgPanel = new JPanel();
//		gameImgPanel.setBounds(100, 350, 190, 150);
//		gameImgPanel.setBackground(Color.black);
//		con.add(gameImgPanel);
//		gameImage = new ImageIcon(getClass().getResource(imageName));
//		gameImageLabel = new JLabel(gameImage);
//		gameImgPanel.add(gameImageLabel);
//		gameImgPanel.setVisible(showGameImage);
		showImage(game);
		
		
		choice1.setText(game.getOption1());
		choice2.setText(game.getOption2());
		choice3.setText(game.getOption3());
		choice4.setText(game.getOption4());
		
		playerHP += game.getChangeHP();
		hpLabel.setText("HP: " + playerHP);
		
	}
	
	//Creating game decision objects//
	
	GameDecision lightningSwear = new GameDecision();
	GameDecision lightningNothing = new GameDecision();
	GameDecision getUp = new GameDecision();
	GameDecision toCampfire = new GameDecision();
	GameDecision explorePlain = new GameDecision();
	GameDecision speakSpirit = new GameDecision();
	GameDecision giveSpiritDaisy = new GameDecision();
	
	GameDecision attackSpirit = new GameDecision();
	
	GameDecision toTrees = new GameDecision();
	GameDecision toBerries = new GameDecision();
	GameDecision feedBerries = new GameDecision();
	GameDecision sipVial = new GameDecision();
	
	
	GameDecision oldBuilding = new GameDecision ();
	GameDecision kickDoor = new GameDecision ();
	GameDecision daisyBuilding = new GameDecision();
	GameDecision intoBuilding = new GameDecision ();
	GameDecision touchPainting = new GameDecision();
	GameDecision goDownstairs = new GameDecision();
	
	GameDecision giveDaisy = new GameDecision();
	GameDecision fightBear = new GameDecision();
	GameDecision retaliateBear = new GameDecision();
	GameDecision throwVial = new GameDecision();
	GameDecision endScene = new GameDecision();	
	
	
	//Individual stage checkers//
	
	//Check to see if player will find daisy//
	public void foundDaisy() {
		if (findDaisy >= 2 && !playerInv[0]) {
			playerInv[0] = true;
			explorePlain.setGameText("You stay and investigate the area. \nYou see a small daisy among the overgrown grass. \nYou pick it up. \n\nWhat do you do?");
			updateInventory(playerInv);
			explorePlain.setImgName("edaisy.jpg");
		} else if (findDaisy >=2 && playerInv[0]) {
			explorePlain.setGameText("You stay and investigate the area. \nThere doesn't seem to be anything else here. \n\nWhat do you do?");
			explorePlain.setImgName("copenplain.jpg");
		}
	}
	
	
	
	//Feeding spirit//

	public void spiritCheck() {
		
		if (!spiritAlive) {
			speakSpirit.setGameText("You try speaking to the pile of spirit dust.\nIt doesn't say anything back.\nSpirit dust doesn't talk.");
			speakSpirit.setOption1("Go back");
			speakSpirit.setOption2("");
			speakSpirit.setOption3("");
			speakSpirit.setOption4("");
			speakSpirit.setPosition("spiritDead");
			speakSpirit.setImgName("kcampfiredeadspirit.jpg");
			attackSpirit.setGameText("You swing your scythe at the pile of spirit dust.\nHe's already dead.\n\nYou're unsure why you did that.");
			attackSpirit.setChangeHP(0);
			attackSpirit.setImgName("kcampfiredeadspirit.jpg");
			toCampfire.setGameText("You reach a campfire.\nThe powdered remains of a spirit lies beside it.\n\nWhat do you do?");
			toCampfire.setImgName("kcampfiredeadspirit.jpg");
		} else if (berriesFed) {
			speakSpirit.setGameText("The spirit is thankful that you fed him.\nYou share a nice exchange.");
			speakSpirit.setOption1("Go back");
			speakSpirit.setOption2("");
			speakSpirit.setOption3("");
			speakSpirit.setOption4("");
			speakSpirit.setPosition("spiritFed");
			attackSpirit.setGameText("You swing your scythe at the spirit.\nHe starts dissolving into thin air.\nYou absorb some of his life energy (+5HP).\nYou hear him faintly cry:\n\n'Why do you have to be so cruel?'\n\nHe already gave you all he had. He leaves nothing\nelse behind.");
		} else if (playerInv[0] == true && playerInv[2] == false) {
			speakSpirit.setOption3("Give Daisy");
			speakSpirit.setPosition("spiritDaisy");
		} else if (playerInv[0] == true && playerInv[2] == true) {
			speakSpirit.setOption3("Give daisy");
			speakSpirit.setOption4("Give berries");
			speakSpirit.setPosition("spiritBerriesDaisy");
		} else if (playerInv[0] == false && playerInv[2] == true) {
			speakSpirit.setOption3("Give berries");
			speakSpirit.setPosition("spiritBerries");
		}
	}
	
	//Berry check//
	
	public void berryCheck() {
		if (berriesPicked) {
			toBerries.setGameText("You reach a large berry bush.\nAll of the berries have been picked.\nBy you.\n\nWhat do you do?");
			toBerries.setChangeHP(0);
			toBerries.setImgName("nberrybushempty.jpg");
		}
	}
	
	//Key Check//
	
	public void keyCheck() {
		if (doorOpen && playerInv[0]) {
			oldBuilding.setOption1("Go into building");
			oldBuilding.setGameText("You get to an old, crumbly building.\nThe door is wide open.\n\nWhat do you do?");
			oldBuilding.setOption3("");
			oldBuilding.setOption4("");
			oldBuilding.setPosition("buildingOpen");
		} else if (doorOpen) {
			oldBuilding.setOption1("Go into building");
			oldBuilding.setGameText("You get to an old, crumbly building.\nThe door is wide open.\n\nWhat do you do?");
			oldBuilding.setOption3("");
			oldBuilding.setPosition("buildingOpen");
		} else if (playerInv[0] && playerInv[1] && !doorOpen) {
			oldBuilding.setOption3("Unlock door with key");
			oldBuilding.setOption4("Try fit daisy in lock");
			oldBuilding.setPosition("buildingKeyDaisy");
		} else if (playerInv[1] && !doorOpen) {
			oldBuilding.setOption3("Unlock door with key");
			oldBuilding.setPosition("buildingKey");
		} else if (playerInv[0] && !playerInv[1] && !doorOpen) {
			oldBuilding.setOption3("Try fit daisy in lock");
			oldBuilding.setPosition("buildingDaisy");
		}
	}
	
	//Basement check//
	
	public void basementCheck() {
		if (creatureDead) {
			goDownstairs.setGameText("You head down the stairs. The rotting wooden door\nto the basement is slightly ajar.\nBehind it is silence.\nYou peak through the gap in the door.\n\nA giant spider-bear hybrid is lying dead.\n\nWhat do you do?");
			goDownstairs.setOption1("Go through the door");
			goDownstairs.setOption2("Go back upstairs");
			goDownstairs.setOption3("");
			goDownstairs.setOption4("");
			goDownstairs.setPosition("creatureDone");
		} else if (creatureDaisy) {
			goDownstairs.setGameText("You head down the stairs.\nThe door to the basement is wide open.\nA spider-bear hybrid is sitting in the corner,\ncompletely distracted.\nIt seems to be admiring a daisy.\n\nWhat do you do?");
			goDownstairs.setOption1("Go through the door");
			goDownstairs.setOption2("Go back upstairs");
			goDownstairs.setOption3("");
			goDownstairs.setOption4("");
			goDownstairs.setPosition("creatureDone");
		} else if (playerInv[3] && playerInv[0]) {
			goDownstairs.setOption3("Throw vial");
			goDownstairs.setOption4("Offer daisy");
			goDownstairs.setPosition("basementVialDaisy");
		} else if (playerInv[3]) {
			goDownstairs.setOption3("Throw vial");
			goDownstairs.setOption4("");
			goDownstairs.setPosition("basementVial");
		} else if (playerInv[0] && !vialThrown) {
			goDownstairs.setOption3("Offer daisy");
			goDownstairs.setOption4("");
			goDownstairs.setPosition("basementDaisy");
		} else if (playerInv[0] && vialThrown) {
			goDownstairs.setOption3("Offer daisy");
			goDownstairs.setOption4("");
			goDownstairs.setPosition("basementDaisy");
			goDownstairs.setGameText("You head down the stairs. The rotting wooden door\nto the basement is slightly ajar.\nBehind it you can hear a deep growling noise.\nYou peak through the gap in the door.\n\nA giant spider-bear hybrid is frothing at the\nmouth inches from your face.\nThere are burn marks from where the vial hit it.\n\nWhat do you do?");
		}
	}
	
	public void throwVialCheck() {
		if (playerInv[0]) {
			throwVial.setOption3("Offer daisy");
			throwVial.setPosition("basementDaisy");
		}
	}
	
	//Grim damage generator - damage from 2-8HP//
	public int grimAttack() {
		int min = 2;
		int max = 8;
		int value = (int)Math.floor(Math.random()*(max - min + 1) + min);
		return value;
	}
	
	//Bear damage generator - damage from 3-6HP//
	public int bearAttack() {
		int min = 3;
		int max = 6;
		int value = (int)Math.floor(Math.random()*(max - min + 1) + min);
		return value;
	}
	
	//Fight sequence//
	
	public void fightCheck() {
		int damage = grimAttack();
		bearHP -= damage;
		int bearRemaining;
		if (bearHP >0) {
			bearRemaining = bearHP;
		} else {
			bearRemaining = 0;
		}
		fightBear.setGameText("You swing your scythe at the creature.\n\nYou do " + damage + " damage.\n\nCreature HP remaining: " + bearRemaining);
		
		if (bearRemaining == 0) {
			creatureDead = true;
			fightBear.setGameText("You swing your scythe at the creature.\n\nYou do " + damage + " damage.\n\nCreature HP remaining: " + bearRemaining + "\n\nYou defeated the creature.");
			fightBear.setPosition("fightWin");
			fightBear.setOption1("Continue forward");
			fightBear.setOption2("Go back upstairs");
		}
	}
	
	public void retaliateCheck() {
		int damage = bearAttack();
		playerHP -= damage;
		hpLabel.setText("HP: " + playerHP);
		retaliateBear.setGameText("The creature scratches you with its sharp claws.\n\nYou lose " + damage + "HP.\n\nWhat do you do?");
	}
	
	
	//Lose checker//
	
	public void loseCheck(GameDecision game) {
		
		boolean isDead = false; 
		
		if (playerHP <= 0) {
			playerHP = 0;
			hpLabel.setText("HP: " + playerHP);
			isDead = true;
		}
		
		String currentText = game.getGameText();
		
		if (isDead) {
			if (currentText.contains("What do you do?")) {
				currentText = currentText.replace("What do you do?", "You died.");
			} else {
				currentText += "\n\nYou died.";
			}
			mainTextArea.setText(currentText);
			position = "dead";
			choice1.setText("Oops!");
			choice2.setText("");
			choice3.setText("");
			choice4.setText("");
		}
	}
	
	//Update inventory on screen//
	
	public void updateInventory(Boolean[] playerInv2) {
		
		String currentInv = "";
		String invHeading = "Inventory: ";
		
		for (int i = 0; i < 4; i++) {
			if (playerInv2[i]) {
				if (i == 0) {
					currentInv += "Daisy";
				} else if (i == 1 && currentInv.equals("")) {
					currentInv += "Key";
				} else if (i == 1 && !currentInv.equals("")) {
					currentInv += ", Key";
				} else if (i == 2 && currentInv.equals("")) {
					currentInv += "Berries";
				} else if (i == 2 && !currentInv.equals("")) {
					currentInv += ", Berries";
				} else if (i == 3 && currentInv.equals("")) {
					currentInv += "Mystery Vial";
				} else if (i == 3 && !currentInv.equals("")) {
					currentInv += ", Mystery Vial";
				}
			}
		}
		
		invLabel.setText(invHeading + currentInv);
		
	}
	
	
	
	
	
	//Handle choice buttons//
	
	public class ChoiceHandler implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			String playerChoice = event.getActionCommand();
			
			switch(position) {
			case "start":
				switch(playerChoice) {
				case "c1": playDecision(lightningSwear); loseCheck(lightningSwear); break;
				case "c2": playDecision(getUp); break;
				case "c3": playDecision(lightningNothing); loseCheck(lightningNothing); break;
				case "c4": break; 
				}
				break;
			case "lightning":
				switch(playerChoice) {
				case "c1": playDecision(wakeUp); break;
				case "c2": break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "plains":
				switch(playerChoice) {
				case "c1": playDecision(toCampfire); break;
				case "c2": playDecision(explorePlain); break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "campfire":
				switch(playerChoice) {
				case "c1": spiritCheck(); playDecision(speakSpirit); break;
				case "c2": spiritCheck(); playDecision(attackSpirit); spiritAlive = false; if (!keyRetrieved) {playerInv[1] = true; keyRetrieved = true;}; updateInventory(playerInv); break;
				case "c3": playDecision(toTrees); break;
				case "c4": playDecision(getUp);
				default: break;
				}
			break;
			case "daisy":
				switch(playerChoice) {
				case "c1": findDaisy += 1; foundDaisy(); playDecision(explorePlain); break;
				case "c2": playDecision(getUp); break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "spiritSpeak":
				switch(playerChoice) {
				case "c1": playDecision(toTrees); break;
				case "c2": playDecision(toCampfire); break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "spiritDead":
				switch(playerChoice) {
				case "c1": playDecision(toCampfire); break;
				case "c2": break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "spiritDaisy":
				switch(playerChoice) {
				case "c1": playDecision(toTrees); break;
				case "c2": playDecision(toCampfire); break;
				case "c3": playDecision(giveSpiritDaisy); break;
				case "c4": break;
				default: break;
				}
			break;
			case "spiritSpeakDaisy":
				switch(playerChoice) {
				case "c1": playDecision(speakSpirit); break;
				case "c2": break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "spiritBerries":
				switch(playerChoice) {
				case "c1": playDecision(toTrees); break;
				case "c2": playDecision(toCampfire); break;
				case "c3": spiritCheck(); playDecision(feedBerries); playerInv[2] = false; berriesFed = true; if (!keyRetrieved) {playerInv[1] = true; keyRetrieved = true;}; if (!vialRetrieved) {playerInv[3] = true; vialRetrieved = true;}; updateInventory(playerInv); break;
				case "c4": break;
				default: break;
				}
			break;
			case "spiritBerriesDaisy":
				switch(playerChoice) {
				case "c1": playDecision(toTrees); break;
				case "c2": playDecision(toCampfire); break;
				case "c3": playDecision(giveSpiritDaisy); break;
				case "c4": spiritCheck(); playDecision(feedBerries); playerInv[2] = false; berriesFed = true; if (!keyRetrieved) {playerInv[1] = true; keyRetrieved = true;}; if (!keyRetrieved) {playerInv[1] = true; keyRetrieved = true;}; if (!vialRetrieved) {playerInv[3] = true; vialRetrieved = true;}; updateInventory(playerInv); break;
				default: break;
				}
			break;
			case "spiritFed":
				switch(playerChoice) {
				case "c1": playDecision(toCampfire);
				case "c2": break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "spiritAttack":
				switch(playerChoice) {
				case "c1": spiritCheck(); playDecision(toCampfire); break;
				case "c2": break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "trees":
				switch(playerChoice) {
				case "c1": berryCheck(); playDecision(toBerries); if (!berriesPicked) {playerInv[2] = true;}; berriesPicked = true; updateInventory(playerInv); break; 
				case "c2": keyCheck(); playDecision(oldBuilding); break; 
				case "c3": spiritCheck(); playDecision(toCampfire); break;
				case "c4": break;
				default: break;
				}
			break;
			case "bush":
				switch(playerChoice) {
				case "c1": playDecision(toCampfire); break;
				case "c2": keyCheck(); playDecision(oldBuilding); break; 
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "feed":
				switch(playerChoice) {
				case "c1": playDecision(sipVial); loseCheck(sipVial); break;
				case "c2": keyCheck(); playDecision(oldBuilding); break; 
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "vial":
				switch(playerChoice) {
				case "c1": playDecision(sipVial); loseCheck(sipVial); break;
				case "c2": keyCheck(); playDecision(oldBuilding); break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "building":
				switch(playerChoice) {
				case "c1": playDecision(kickDoor); break;
				case "c2": playDecision(toTrees); break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "door":
				switch(playerChoice) {
				case "c1": keyCheck(); playDecision(oldBuilding); break;
				case "c2": break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "buildingKey":
				switch(playerChoice) {
				case "c1": playDecision(kickDoor); loseCheck(kickDoor); break;
				case "c2": playDecision(toTrees); break;
				case "c3": playerInv[1] = false; doorOpen = true; playDecision(intoBuilding); updateInventory(playerInv); break; 
				case "c4": break;
				default: break;
				}
			break;
			case "buildingDaisy":
				switch(playerChoice) {
				case "c1": playDecision(kickDoor); loseCheck(kickDoor); break;
				case "c2": playDecision(toTrees); break;
				case "c3": playDecision(daisyBuilding); break;
				case "c4": break;
				default: break;
				}
			break;
			case "buildingKeyDaisy":
				switch(playerChoice) {
				case "c1": playDecision(kickDoor); loseCheck(kickDoor); break;
				case "c2": playDecision(toTrees); break;
				case "c3": playerInv[1] = false; doorOpen = true; playDecision(intoBuilding); updateInventory(playerInv); break; 
				case "c4": playDecision(daisyBuilding); break;
				default: break;
				}
			break;
			case "daisyDoor":
				switch(playerChoice) {
				case "c1": keyCheck(); playDecision(oldBuilding); break;
				case "c2": break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "buildingOpen":
				switch(playerChoice) {
				case "c1": playDecision(intoBuilding); break; 
				case "c2": playDecision(toTrees); break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "inside":
				switch(playerChoice) {
				case "c1": basementCheck(); playDecision(goDownstairs); break;
				case "c2": playDecision(touchPainting); loseCheck(touchPainting); break;
				case "c3": keyCheck(); playDecision(oldBuilding); break; 
				case "c4": break;
				default: break;
				}
			break;
			case "painting":
				switch(playerChoice) {
				case "c1": playDecision(intoBuilding); break;
				case "c2": break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "basement":
				switch(playerChoice) {
				case "c1": fightCheck(); playDecision(fightBear); break; 
				case "c2": playDecision(intoBuilding); break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "creatureDone":
				switch(playerChoice) {
				case "c1": playDecision(endScene); break; 
				case "c2": playDecision(intoBuilding); break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "basementVialDaisy":
				switch(playerChoice) {
				case "c1": fightCheck(); playDecision(fightBear); break; 
				case "c2": playDecision(intoBuilding); break;
				case "c3": throwVialCheck(); playDecision(throwVial); playerInv[3] = false; bearHP = 15; vialThrown = true; updateInventory(playerInv); break; 
				case "c4": creatureDaisy = true; playerInv[0] = false; playDecision(giveDaisy); updateInventory(playerInv); break;
				default: break;
				}
			break;
			case "basementDaisy":
				switch(playerChoice) {
				case "c1": fightCheck(); playDecision(fightBear); break; 
				case "c2": playDecision(intoBuilding); break;
				case "c3": creatureDaisy = true; playerInv[0] = false; playDecision(giveDaisy); updateInventory(playerInv); break; 
				case "c4": break;
				default: break;
				}
			break;
			case "basementVial":
				switch(playerChoice) {
				case "c1": fightCheck(); playDecision(fightBear); break; 
				case "c2": playDecision(intoBuilding); break;
				case "c3": throwVialCheck(); playDecision(throwVial); playerInv[3] = false; bearHP = 15; vialThrown = true; updateInventory(playerInv); break;
				case "c4": break;
				default: break;
				}
			break;
			case "bearDaisy":
				switch(playerChoice) {
				case "c1": playDecision(endScene); break; 
				case "c2": playDecision(intoBuilding); break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "throwVialBasement":
				switch(playerChoice) {
				case "c1": fightCheck(); playDecision(fightBear); break;
				case "c2": playDecision(intoBuilding); 
				case "c3": 
				case "c4":
				}
			case "fight":
				switch(playerChoice) {
				case "c1": retaliateCheck(); playDecision(retaliateBear); loseCheck(retaliateBear); break;
				case "c2": break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "fightBack":
				switch(playerChoice) {
				case "c1": fightCheck(); playDecision(fightBear); break;
				case "c2": playDecision(goDownstairs); break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "fightWin":
				switch(playerChoice) {
				case "c1": playDecision(endScene); break;
				case "c2": playDecision(intoBuilding); break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "finalScene":
				switch(playerChoice) {
				case "c1": createEndScreen(); break; //end screen play again
				case "c2": break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			case "dead":
				switch(playerChoice) {
				case "c1": createLoseScreen(); break; //lose screen play again
				case "c2": break;
				case "c3": break;
				case "c4": break;
				default: break;
				}
			break;
			}
		}
	}
}
