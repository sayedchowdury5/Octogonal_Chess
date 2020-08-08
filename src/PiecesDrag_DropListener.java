
import java.net.URL;
import java.util.List;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


//start PiecesDrag_DropListener.java
public class PiecesDrag_DropListener implements MouseListener, MouseMotionListener
{

	private List<Piece_pic> guiPieces;
	private ChessInterface chessGui;

	//private Piece_pic dragPiece;
	private int dragOffsetX;
	private int dragOffsetY;


	public PiecesDrag_DropListener(List<Piece_pic> guiPieces, ChessInterface chessGui)
	{
		this.guiPieces = guiPieces;
		this.chessGui = chessGui;
	}

	public void SoundClipTest(int type)
	{
			String filename = "";

			switch (type)
			{
				case typeOf_Piece.TYPE_BISHOP: filename += "b"; break;
				case typeOf_Piece.TYPE_KING: filename += "k"; break;
				case typeOf_Piece.TYPE_KNIGHT: filename += "n"; break;
				case typeOf_Piece.TYPE_PAWN: filename += "p"; break;
				case typeOf_Piece.TYPE_QUEEN: filename += "q"; break;
				case typeOf_Piece.TYPE_ROOK: filename += "r"; break;
				case 10 : filename += "a"; break;
			}
			filename += ".wav";
	        try
			{
				URL backGroundMusic = getClass().getResource("/song/" + filename);
	            		AudioInputStream audioInputStream =AudioSystem.getAudioInputStream(backGroundMusic);
	            		Clip clip = AudioSystem.getClip();
	            		clip.open(audioInputStream);
	            		clip.start( );
	        }
	        catch(Exception ex)
	        {
				System.out.println(ex);
			}
	}



	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent evt)
	{
		int x = evt.getPoint().x;
		int y = evt.getPoint().y;

		// find out which piece to move.
		// we check the list from top to buttom
		// (therefore we itereate in reverse order)
		//
		for (int i = this.guiPieces.size()-1; i >= 0; i--)
		{
			Piece_pic guiPiece = this.guiPieces.get(i);
			if (guiPiece.isCaptured()) continue;

			if(mouseOverPiece(guiPiece,x,y))
			{

				if( (	this.chessGui.getGameState() == Game_State.GAME_STATE_WHITE&& guiPiece.getColor() == typeOf_Piece.COLOR_WHITE) ||
					(	this.chessGui.getGameState() == Game_State.GAME_STATE_BLACK&& guiPiece.getColor() == typeOf_Piece.COLOR_BLACK))
				{
					// calculate offset, because we do not want the drag piece
					// to jump with it's upper left corner to the current mouse
					// position
					//
					SoundClipTest(guiPiece.getPiece().getType());
					this.dragOffsetX = x - guiPiece.getX();
					this.dragOffsetY = y - guiPiece.getY();
					this.chessGui.setDragPiece(guiPiece);
					this.chessGui.repaint();
					break;
				}
			}
		}

		// move drag piece to the top of the list
		if(this.chessGui.getDragPiece() != null)
		{
			this.guiPieces.remove( this.chessGui.getDragPiece() );
			this.guiPieces.add(this.chessGui.getDragPiece());
		}
	}

	/**
	 * check whether the mouse is currently over this piece
	 * @param piece the playing piece
	 * @param x x coordinate of mouse
	 * @param y y coordinate of mouse
	 * @return true if mouse is over the piece
	 */
	private boolean mouseOverPiece(Piece_pic guiPiece, int x, int y)
	{

		return guiPiece.getX() <= x
			&& guiPiece.getX()+guiPiece.getWidth() >= x
			&& guiPiece.getY() <= y
			&& guiPiece.getY()+guiPiece.getHeight() >= y;
	}

	@Override
	public void mouseReleased(MouseEvent evt)
	{
		if( this.chessGui.getDragPiece() != null)
		{
			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;

			// set game piece to the new location if possible
			//
			chessGui.pieceNewLocation(this.chessGui.getDragPiece(), x, y);
			this.chessGui.repaint();
			this.chessGui.setDragPiece(null);
		}
	}

	@Override
	public void mouseDragged(MouseEvent evt)
	{
		if(this.chessGui.getDragPiece() != null)
		{

			int x = evt.getPoint().x - this.dragOffsetX;
			int y = evt.getPoint().y - this.dragOffsetY;

			System.out.println(
					"row:"+ChessInterface.convertYToRow(y)
					+" column:"+ChessInterface.convertXToColumn(x));

			Piece_pic dragPiece = this.chessGui.getDragPiece();
			dragPiece.setX(x);
			dragPiece.setY(y);

			this.chessGui.repaint();
		}

	}
	@Override
	public void mouseMoved(MouseEvent arg0) {}

}

//end PiecesDrag_DropListener.java