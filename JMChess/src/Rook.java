import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Rook extends Piece
{
	public boolean hasMoved = false;
	
	public Rook(int column, int row, boolean color, int index)
	{
		super(column, row, color, index);
		
		//set the picture
		try {
			if(color)
				pic = ImageIO.read(new File("pics\\rookW.png"));
			
			else
				pic = ImageIO.read(new File("pics\\rookB.png"));
		} catch (IOException e) {e.printStackTrace();}
	}

	//check is a given square is a legal move
	public boolean legalMove(int newColumn, int newRow)
	{
		boolean legal = false;
		
		//check if its a legal type of movement
		if(!legalMovement(newColumn, newRow))
			return false;

		//is there a path
		legal = legalPath(newColumn, newRow, true);
		
		return legal;
	}
	
	//diagonal or vertical movement
	private boolean legalMovement(int newColumn, int newRow)
	{
		boolean legal = false;
		
		if((newRow != row && newColumn == column) || (newRow == row && newColumn != column))
			return true;

		return legal;
	}
	
	//is there a path
	private boolean legalPath(int newColumn, int newRow, boolean first)
	{
		int rowChange;
		int columnChange;
		Piece[] pieces;
		
		pieces = this.isTheaterPiece ? Theater.board.pieces : Driver.board.pieces;
	
		
		for(int index = 0; index < pieces.length; index++)
		{
			if(pieces[index] != null)
			{
				if(pieces[index].getColumn() == newColumn && pieces[index].getRow() == newRow && pieces[index].isWhite() == this.isWhite)
					return false;
			}
		}
		
		//check for pieces in the path
		if(!first)
		{
			for(int index = 0; index < pieces.length; index++)
			{
				if(pieces[index] != null)
				{
					if(pieces[index].getColumn() == newColumn && pieces[index].getRow() == newRow)
						return false;
				}
			}
		}
		
		//get the direction of movement
		if((column - newColumn) < 0)
			columnChange = -1;
		
		else if((column - newColumn) > 0)
			columnChange = 1;
		
		else
			columnChange = 0;
		
		if((row - newRow) < 0)
			rowChange = -1;
		
		else if((row - newRow) > 0)
			rowChange = 1;
		
		else
			rowChange = 0;
		
		//if this is the last step
		if(newColumn + columnChange == column && newRow + rowChange == row)
		{
			for(int index = 0; index < pieces.length; index++)
			{
				if(pieces[index] != null)
				{
					if(pieces[index].getColumn() == newColumn && pieces[index].getRow() == newRow && pieces[index].isWhite() == this.isWhite())
						return false;
				}
			}
			
			return true;
		}
		
		//make another step recursively
		return legalPath(newColumn +columnChange, newRow + rowChange, false);
	}
}
