/*
PROJECT:GROUP 2_1

AUTHORS AND MATRIC NUMBERS:

ABU SAYED                   59395
ENG YEE WEI                 55882
ISMA KHALIL BIN SEMAN       50542
MELVIN PAUL PETER           52468
NUR AIN BINTI RAMLEE        58932

HONOUR CODE: WE PLEDGE THAT THIS PROGRAM REPRESENTS OUR OWN PROGRAM CODE. 
WE RECEIVED HELP FROM NIELTON DIDI
IN DEVELOPING AND DEBUGGING OUR PROGRAM.
*/
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import javax.sound.sampled.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;
import javax.swing.JButton;
import javax.swing.JOptionPane;
/**
 * all x and y coordinates point to the upper left position of a component all
 * lists are treated as 0 being the bottom and size-1 being the top piece
 *
 */
public class ChessInterface extends JPanel
{
       //set the layout of chess interface
	private static final long serialVersionUID = -8207574964820892354L;

	private static final int BOARD_START_X = 300;
	private static final int BOARD_START_Y = 100;

	private static final int SQUARE_WIDTH = 50;
	private static final int SQUARE_HEIGHT = 50;

	private static final int PIECE_WIDTH = 48;
	private static final int PIECE_HEIGHT = 48;

	private static final int PIECES_START_X = BOARD_START_X + (int)(SQUARE_WIDTH/2.0 - PIECE_WIDTH/2.0);
	private static final int PIECES_START_Y = BOARD_START_Y + (int)(SQUARE_HEIGHT/2.0 - PIECE_HEIGHT/2.0);

	private static final int DRAG_TARGET_SQUARE_START_X = BOARD_START_X - (int)(PIECE_WIDTH/2.0);
	private static final int DRAG_TARGET_SQUARE_START_Y = BOARD_START_Y - (int)(PIECE_HEIGHT/2.0);

	private Image imgBackground; // background image
	private JLabel lblGameState; // label displaying the current game state
        private JLabel playertext1;
	private Game_State chessGame;
        

	// all gui game pieces of the running game
	// the gui game pieces wrap the actual game pieces of the chess game
	private List<Piece_pic> guiPieces = new ArrayList<Piece_pic>();
	private Piece_pic dragPiece; // currently dragged game piece

	private Move lastMove; // the last executed move (used for highlighting)
      
        private JButton restart = new JButton("Restart");
        private JButton winner = new JButton("Winner Board");
        
	public ChessInterface(String player1, String player2)
	{
                
		this.setLayout(null);
		try
		{
	         	// Open an audio input stream.
	         	AudioInputStream audioInputStream = AudioSystem.getAudioInputStream
			(this.getClass().getResource("/song/yiruma.wav/"));
	         	// Get a sound clip resource.
	         	Clip clip = AudioSystem.getClip();
	         	// Open audio clip and load samples from the audio input stream.
	        	clip.open(audioInputStream);
	         	clip.start();
		}
	      	catch (UnsupportedAudioFileException e)
	      	{
	         	e.printStackTrace();
	     	}
	      	catch (IOException e)
	      	{
	         	e.printStackTrace();
	      	}
	      	catch (LineUnavailableException e)
	      	{
	         	e.printStackTrace();
      		}
		// background
		URL urlBackgroundImg = getClass().getResource("/img/bg.jpg");
		this.imgBackground = new ImageIcon(urlBackgroundImg).getImage();

		// create chess game
		this.chessGame = new Game_State();

		//wrap game pieces into their graphical representation
		for (typeOf_Piece piece : this.chessGame.getPieces())
		{
			createAndAddGuiPiece(piece);
		}


		// add listeners to enable drag and drop
		//
		PiecesDrag_DropListener listener = new PiecesDrag_DropListener(this.guiPieces,this);
		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);

		// label to display game state
		String lblText = this.getGameStateAsText();
		this.lblGameState = new JLabel(lblText);
		lblGameState.setBounds(0, 30, 80, 30);
		lblGameState.setForeground(Color.WHITE);
		this.add(lblGameState);
               
                
		// create application frame and set visible
		//
		JFrame f = new JFrame();
		f.setSize(80, 80);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(this);
		f.setSize(imgBackground.getWidth(null), imgBackground.getHeight(null)+35);
		f.setLocationRelativeTo(null);
                
                JLabel playertext1 = new JLabel ("Player 1 (white):");
                playertext1.setBounds(60,10,400,40);
                playertext1.setFont(new Font("TIMES NEW ROMAN", Font.BOLD,20));
                playertext1.setForeground(Color.BLACK);
                playertext1.setBackground(Color.BLACK);
                playertext1.setVisible(true);
                add(playertext1);
                
                JTextField JTFplayer1 = new JTextField();
                JTFplayer1.setBounds(220,7,300,40);
                Home homepage = new Home();
                JTFplayer1.setText(player1);
                JTFplayer1.setFont(new Font("TIMES NEW ROMAN", Font.BOLD,20));
               // System.out.println("asd"+homepage.getname1()+"asd");
                add(JTFplayer1);
                
                JLabel playertext2 = new JLabel ("Player 2 (black):");
                playertext2.setBounds(660,10,400,40);
                playertext2.setFont(new Font("TIMES NEW ROMAN", Font.BOLD,20));
                playertext2.setForeground(Color.BLACK);
                playertext2.setBackground(Color.BLACK);
                playertext2.setVisible(true);
                add(playertext2);
                
                JTextField JTFplayer2 = new JTextField();
                JTFplayer2.setBounds(820,7,300,40);
                Home homepage1 = new Home();
                JTFplayer2.setText(player2);
                JTFplayer2.setFont(new Font("TIMES NEW ROMAN", Font.BOLD,20));
               // System.out.println("asd"+homepage.getname1()+"asd");
                add(JTFplayer2);
                
                restart.setBounds(50,(int)(55*11),100,40);
                restart.setFont(new Font("TIMES NEW ROMAN",Font.BOLD,18));
                add(restart);
                
                winner.setBounds(200,(int)(55*11),170,40);
                winner.setFont(new Font("TIMES NEW ROMAN",Font.BOLD,18));
                add(winner);
         
             
                restart.addActionListener(new ActionListener(){
                
                    public void actionPerformed(ActionEvent actionevent){
                    
                            Object[] options = {"Yes","No"};
                            //parent component, String message, title, options type,message type,icon,options,user choice)
                            int restart_choice = JOptionPane.showOptionDialog(null, "Are you sure you want to restart the game?", "Game restart", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,options,options[1]);
                            
                             if(restart_choice == 0){
                                                
                             int changeName = JOptionPane.showOptionDialog(null, "Are you going to change player names?", "Message", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null,options,options[1]);
                                
                             if(changeName == 0){
                                
                                setVisible(false);
                                Home homepage = new Home();
                                homepage.setVisible(true);
                                
                         
                                }else{
                                 
                                    setVisible(false); 
                                    ChessInterface chess = new ChessInterface (player1,player2);
                                    chess.setVisible(true);
                                   
                                }      
                                
                             }else{
                                 
                                 JOptionPane.showMessageDialog(null,"Game will continue.");
                             }
                    }
                });
        
        }
     
	/**
	 * @return textual description of current game state
	 */
	private String getGameStateAsText()
	{
		String state = "unknown";
		switch (this.chessGame.getGameState())
		{
			case Game_State.GAME_STATE_BLACK: state = "black";break;
			case Game_State.GAME_STATE_END: state = "end";break;
			case Game_State.GAME_STATE_WHITE: state = "white";break;
		}
		return state;
	}

	/**
	 * create a game piece
	 *
	 * @param color color constant
	 * @param type type constant
	 * @param x x position of upper left corner
	 * @param y y position of upper left corner
	 */
	private void createAndAddGuiPiece(typeOf_Piece piece)
	{
		Image img = this.getImageForPiece(piece.getColor(), piece.getType());
		Piece_pic guiPiece = new Piece_pic(img, piece);
		this.guiPieces.add(guiPiece);
	}

	/**
	 * load image for given color and type. This method translates the color and
	 * type information into a filename and loads that particular file.
	 *
	 * @param color color constant
	 * @param type type constant
	 * @return image
	 */
	private Image getImageForPiece(int color, int type)
	{
		String filename = "";
		filename += (color == typeOf_Piece.COLOR_WHITE ? "w" : "b");
		switch (type)
		{
			case typeOf_Piece.TYPE_BISHOP:
				filename += "b";
				break;
			case typeOf_Piece.TYPE_KING:
				filename += "k";
				break;
			case typeOf_Piece.TYPE_KNIGHT:
				filename += "n";
				break;
			case typeOf_Piece.TYPE_PAWN:
				filename += "p";
				break;
			case typeOf_Piece.TYPE_QUEEN:
				filename += "q";
				break;
			case typeOf_Piece.TYPE_ROOK:
				filename += "r";
				break;
		}
		filename += ".gif";

		URL urlPieceImg = getClass().getResource("/img/" + filename);
		return new ImageIcon(urlPieceImg).getImage();
	}

	@Override
	protected void paintComponent(Graphics g)
	{
		// draw background
		g.drawImage(this.imgBackground, 0, 0, null);

				//white square
		        for(int i = 300; i <= 800; i+=100)
		        {
		            for(int j = 100; j <= 600; j+=100)
		            {
						if(j==100)
						{
							for(int a=500; a <= 650; a+=100)
							{
								g.setColor(Color.WHITE);
								g.fillRoundRect(a, j, 50, 50, 10, 10);
							}
						}
						else if(j==200)
						{
							for(int a=400; a <= 750; a+=100)
							{
								g.setColor(Color.WHITE);
								g.fillRoundRect(a, j, 50, 50, 10, 10);
							}
						}
						else if(j==600)
						{
							for(int a=500; a <= 650; a+=100)
							{
								g.setColor(Color.WHITE);
								g.fillRoundRect(a, j, 50, 50, 10, 10);
							}
						}
						else if(j==500)
						{
							for(int a=400; a <= 750; a+=100)
							{
								g.setColor(Color.WHITE);
								g.fillRoundRect(a, j, 50, 50, 10, 10);
							}
						}
						else
						{
							g.setColor(Color.WHITE);
							g.fillRoundRect(i, j, 50, 50, 10, 10);
						}
		            }
		        }

		        for(int i = 350; i <= 800; i+=100)
		        {
		            for(int j = 150; j <= 600; j+=100)
		            {
						if(j==150)
						{
							for(int b=450; b <= 650; b+=100)
							{
								g.setColor(Color.WHITE);
								g.fillRoundRect(b, j, 50, 50, 10, 10);
							}
						}
						else if(j==250)
						{
							for(int b=350; b <= 750; b+=100)
							{
								g.setColor(Color.WHITE);
								g.fillRoundRect(b, j, 50, 50, 10, 10);
							}
						}
						else if(j==550)
						{
							for(int b=450; b <= 650; b+=100)
							{
								g.setColor(Color.WHITE);
								g.fillRoundRect(b, j, 50, 50, 10, 10);
							}
						}
						else
						{
							g.setColor(Color.WHITE);
							g.fillRoundRect(i, j, 50, 50, 10, 10);
						}
		            }
		        }

		        //black square
		        for(int i = 350; i <= 800; i+=100)
		        {
		            for(int j = 100; j <= 600; j+=100)
		            {
                                                if(j==100)
						{
							for(int a=450; a <= 650; a+=100)
							{
								g.setColor(Color.GREEN);
								g.fillRoundRect(a, j, 50, 50, 10, 10);
							}
						}
						else if(j==200)
						{
							for(int a=350; a<= 750; a+=100)
							{
								g.setColor(Color.GREEN);
								g.fillRoundRect(a, j, 50, 50, 10, 10);
							}
						}
						else if(j==600)
						{
							for(int a=450; a <= 650; a+=100)
							{
								g.setColor(Color.GREEN);
								g.fillRoundRect(a, j, 50, 50, 10, 10);
							}
						}
						else
						{
							g.setColor(Color.GREEN);
							g.fillRoundRect(i, j, 50, 50, 10, 10);
						}
		            }
		        }

		        for(int i = 300; i <= 800; i+=100)
		        {
		            for(int j = 150; j <= 600; j+=100)
		            {
						if(j==150)
						{
							for(int b=400; b <= 700; b+=100)
							{
								g.setColor(Color.GREEN);
								g.fillRoundRect(b, j, 50, 50, 10, 10);
							}
						}
						else if(j==550)
						{
							for(int b=400; b <= 700; b+=100)
							{
								g.setColor(Color.GREEN);
								g.fillRoundRect(b, j, 50, 50, 10, 10);
							}
						}
						else
						{
								g.setColor(Color.GREEN);
								g.fillRoundRect(i, j, 50, 50, 10, 10);
						}
		            }
        }

		// draw pieces
		for (Piece_pic guiPiece : this.guiPieces)
		{
			if( !guiPiece.isCaptured())
			{
				g.drawImage(guiPiece.getImage(), guiPiece.getX(), guiPiece.getY(), null);
			}
		}

		// draw last move, if user is not dragging game piece
		if( !isUserDraggingPiece() && this.lastMove != null )
		{
			int highlightSourceX = convertColumnToX(this.lastMove.sourceColumn);
			int highlightSourceY = convertRowToY(this.lastMove.sourceRow);
			int highlightTargetX = convertColumnToX(this.lastMove.targetColumn);
			int highlightTargetY = convertRowToY(this.lastMove.targetRow);

			g.setColor(Color.YELLOW);
			g.drawRoundRect( highlightSourceX+4, highlightSourceY+4, SQUARE_WIDTH-11, SQUARE_HEIGHT-11,10,10);
			g.drawRoundRect( highlightTargetX+4, highlightTargetY+4, SQUARE_WIDTH-11, SQUARE_HEIGHT-11,10,10);
			//g.drawLine(highlightSourceX+SQUARE_WIDTH/2, highlightSourceY+SQUARE_HEIGHT/2
			//		, highlightTargetX+SQUARE_WIDTH/2, highlightTargetY+SQUARE_HEIGHT/2);
		}

		// draw valid target locations, if user is dragging a game piece
		if( isUserDraggingPiece() )
		{

			MoveValidator moveValidator = this.chessGame.getMoveValidator();

			// iterate the complete board to check if target locations are valid
			for (int column = typeOf_Piece.COLUMN_A; column <= typeOf_Piece.COLUMN_K; column++)
			{
				for (int row = typeOf_Piece.ROW_1; row <= typeOf_Piece.ROW_11; row++)
				{
					int sourceRow = this.dragPiece.getPiece().getRow();
					int sourceColumn = this.dragPiece.getPiece().getColumn();

					// check if target location is valid
					if( moveValidator.isMoveValid( new Move(sourceRow, sourceColumn, row, column)) )
					{

						int highlightX = convertColumnToX(column);
						int highlightY = convertRowToY(row);

						// draw a black drop shadow by drawing a black rectangle with an offset of 1 pixel
						g.setColor(Color.BLACK);
						g.drawRoundRect( highlightX+5, highlightY+5, SQUARE_WIDTH-11, SQUARE_HEIGHT-11,10,10);
						// draw the highlight
						g.setColor(Color.GREEN);
						g.drawRoundRect( highlightX+4, highlightY+4, SQUARE_WIDTH-11, SQUARE_HEIGHT-11,10,10);
					}
				}
			}
		}


		// draw game state label
		this.lblGameState.setText(this.getGameStateAsText());
	}

	/**
	 * @return true if user is currently dragging a game piece
	 */
	private boolean isUserDraggingPiece()
	{
		return this.dragPiece != null;
	}


	/**
	 * @return current game state
	 */
	public int getGameState()
	{
		return this.chessGame.getGameState();
	}

	/**
	 * convert logical column into x coordinate
	 * @param column
	 * @return x coordinate for column
	 */
	public static int convertColumnToX(int column)
	{
		return PIECES_START_X + SQUARE_WIDTH * column;
	}

	/**
	 * convert logical row into y coordinate
	 * @param row
	 * @return y coordinate for row
	 */
	public static int convertRowToY(int row)
	{
		return PIECES_START_Y + SQUARE_HEIGHT * (typeOf_Piece.ROW_11 - row);
	}

	/**
	 * convert x coordinate into logical column
	 * @param x
	 * @return logical column for x coordinate
	 */
	public static int convertXToColumn(int x)
	{
		return (x - DRAG_TARGET_SQUARE_START_X)/SQUARE_WIDTH;
	}

	/**
	 * convert y coordinate into logical row
	 * @param y
	 * @return logical row for y coordinate
	 */
	public static int convertYToRow(int y)
	{
		return typeOf_Piece.ROW_11 - (y - DRAG_TARGET_SQUARE_START_Y)/SQUARE_HEIGHT;
	}

	/**
	 * change location of given piece, if the location is valid.
	 * If the location is not valid, move the piece back to its original
	 * position.
	 * @param dragPiece
	 * @param x
	 * @param y
	 */
	public void pieceNewLocation(Piece_pic dragPiece, int x, int y)
	{
		int targetRow = ChessInterface.convertYToRow(y);
		int targetColumn = ChessInterface.convertXToColumn(x);

		if( targetRow < typeOf_Piece.ROW_1 || targetRow > typeOf_Piece.ROW_11 || targetColumn < typeOf_Piece.COLUMN_A || targetColumn > typeOf_Piece.COLUMN_K)
		{
			// reset piece position if move is not valid
			dragPiece.resetToUnderlyingPiecePosition();
		}
		//NEW RESTRICTED AREA WHITE
		else if(targetRow == typeOf_Piece.ROW_1 && targetColumn < typeOf_Piece.COLUMN_D || targetRow == typeOf_Piece.ROW_1 && targetColumn > typeOf_Piece.COLUMN_H )
		{
			dragPiece.resetToUnderlyingPiecePosition();
		}
		else if(targetRow == typeOf_Piece.ROW_2 && targetColumn < typeOf_Piece.COLUMN_C || targetRow == typeOf_Piece.ROW_2 && targetColumn > typeOf_Piece.COLUMN_I )
		{
			dragPiece.resetToUnderlyingPiecePosition();
		}
		else if(targetRow == typeOf_Piece.ROW_3 && targetColumn < typeOf_Piece.COLUMN_B || targetRow == typeOf_Piece.ROW_3 && targetColumn > typeOf_Piece.COLUMN_J )
		{
			dragPiece.resetToUnderlyingPiecePosition();
		}
		//NEW RESTRICTED AREA BLACK
		else if(targetRow == typeOf_Piece.ROW_11 && targetColumn < typeOf_Piece.COLUMN_D || targetRow == typeOf_Piece.ROW_11 && targetColumn > typeOf_Piece.COLUMN_H )
		{
			dragPiece.resetToUnderlyingPiecePosition();
		}
		else if(targetRow == typeOf_Piece.ROW_10 && targetColumn < typeOf_Piece.COLUMN_C || targetRow == typeOf_Piece.ROW_10 && targetColumn > typeOf_Piece.COLUMN_I )
		{
			dragPiece.resetToUnderlyingPiecePosition();
		}
		else if(targetRow == typeOf_Piece.ROW_9 && targetColumn < typeOf_Piece.COLUMN_B || targetRow == typeOf_Piece.ROW_9 && targetColumn > typeOf_Piece.COLUMN_J )
		{
			dragPiece.resetToUnderlyingPiecePosition();
		}
		else
		{
			//change model and update gui piece afterwards
			System.out.println("moving piece to "+targetRow+"/"+targetColumn);
			Move move = new Move( dragPiece.getPiece().getRow(), dragPiece.getPiece().getColumn()
					, targetRow, targetColumn);
			boolean wasMoveSuccessfull = this.chessGame.movePiece( move );

			// if the last move was successfully executed, we remember it for
			// highlighting it in the user interface
			if( wasMoveSuccessfull )
			{
				this.lastMove = move;
			}

			dragPiece.resetToUnderlyingPiecePosition();
		}
	}

	/**
	 * @param guiPiece - set the gui piece that the user is current dragging
	 */
	public void setDragPiece(Piece_pic guiPiece)
	{
		this.dragPiece = guiPiece;
	}
	public static void main(String[] args)
	{
               
//             new ChessInterface();
             
              
	}

	/**
	 * @return the gui piece that the user is currently dragging
	 */
	public Piece_pic getDragPiece()
	{
		return this.dragPiece;
	}

 
}

