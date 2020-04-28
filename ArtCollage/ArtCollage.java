/*************************************************************************
 *  Compilation:  javac ArtCollage.java
 *  Execution:    java ArtCollage
 *
 *  @author: Arnav Lakkavajjala
 *
 *************************************************************************/

import java.awt.Color;

public class ArtCollage {

    private String filenamed;
    
	// The orginal picture
    private Picture original;

    // The collage picture
    private Picture collage;

    // The collage Picture consists of collageDimension X collageDimension tiles
    private int collageDimension;

    // A tile consists of tileDimension X tileDimension pixels
    private int tileDimension;
   
    /*
     * One-argument Constructor
     * 1. set default values of collageDimension to 4 and tileDimension to 100
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension,
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename) {
    filenamed = filename;
    collageDimension = 4;
    tileDimension = 100;
    original = new Picture(filename);
    int w = tileDimension*collageDimension;
    int h = tileDimension*collageDimension;
    collage = new Picture(w, h);
    for(int tcol = 0; tcol < w; tcol++) {
    	for(int trow = 0; trow < h; trow++) {
    		int scol = tcol*original.width()/w;
    		int srow = trow*original.height()/h;
    		Color color = original.get(scol, srow);
    		collage.set(tcol, trow, color);
    	}
    	}
    }

    /*
     * Three-arguments Constructor
     * 1. set default values of collageDimension to cd and tileDimension to td
     * 2. initializes original with the filename image
     * 3. initializes collage as a Picture of tileDimension*collageDimension x tileDimension*collageDimension,
     *    where each pixel is black (see all constructors for the Picture class).
     * 4. update collage to be a scaled version of original (see scaling filter on Week 9 slides)
     *
     * @param filename the image filename
     */
    public ArtCollage (String filename, int td, int cd) {
    	filenamed = filename;
    	collageDimension = cd;
    	tileDimension = td;
    	original = new Picture(filename);
    	int w = tileDimension*collageDimension;
    	int h = tileDimension*collageDimension;
    	collage = new Picture(w, h);
    	for(int tcol = 0; tcol < w; tcol++) {
    		for(int trow = 0; trow < h; trow++) {
    			int scol = tcol*original.width()/w;
    			int srow = trow*original.height()/h;
    			Color color = original.get(scol, srow);
    			collage.set(tcol, trow, color);
    		}
    	}
    }

    /*
     * Returns the collageDimension instance variable
     *
     * @return collageDimension
     */
    public int getCollageDimension() {
    return this.collageDimension;
    }

    /*
     * Returns the tileDimension instance variable
     *
     * @return tileDimension
     */
    public int getTileDimension() {
    return this.tileDimension;
    }

    /*
     * Returns original instance variable
     *
     * @return original
     */
    public Picture getOriginalPicture() {
    return this.original;
    }

    /*
     * Returns collage instance variable
     *
     * @return collage
     */
    public Picture getCollagePicture() {
    return this.collage;
    }
   
    /*
     * Display the original image
     * Assumes that original has been initialized
     */
    public void showOriginalPicture() {
    original.show();
    }

    /*
     * Display the collage image
     * Assumes that collage has been initialized
     */
    public void showCollagePicture() {
    collage.show();
    }

    /*
     * Replaces the tile at collageCol,collageRow with the image from filename
     * Tile (0,0) is the upper leftmost tile
     *
     * @param filename image to replace tile
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void replaceTile (String filename,  int collageCol, int collageRow) {
    	int startrow = collageRow*this.getTileDimension();
    	int startcol = collageCol*this.getTileDimension();
    	Picture picture = new Picture(filename);
    	Picture scaled = new Picture(this.getTileDimension(),this.getTileDimension());
    	for(int tcol = 0; tcol < scaled.width(); tcol++) {
    	    for(int trow = 0; trow < scaled.height(); trow++) {
    	    	int scol = tcol*picture.width()/scaled.width();
    	    	int srow = trow*picture.height()/scaled.height();
    	    	Color color = picture.get(scol, srow);
    	    	scaled.set(tcol, trow, color);
    	    }
    	    for(int i = 0; i < this.getTileDimension(); i++) {
    		for(int j = 0; j < this.getTileDimension(); j++) {
        			Color color = scaled.get(i, j);
        			collage.set(i+startcol, j+startrow, color);
        		}
    	    }
    	}
    }
   
    /*
     * Makes a collage of tiles from original Picture
     * original will have collageDimension x collageDimension tiles, each tile
     * has tileDimension X tileDimension pixels
     */
    public void makeCollage () {
    	for(int i = 0; i < this.getCollageDimension(); i++) {
    		for(int j = 0; j < this.getCollageDimension(); j++) {
        		this.replaceTile(filenamed, i, j);
        	}
    	}
    }

    /*
     * Colorizes the tile at (collageCol, collageRow) with component
     * (see Week 9 slides, the code for color separation is at the
     *  book's website)
     *
     * @param component is either red, blue or green
     * @param collageCol tile column
     * @param collageRow tile row
     */
    public void colorizeTile (String component,  int collageCol, int collageRow) {
    	int startrow = collageRow*this.getTileDimension();
    	int startcol = collageCol*this.getTileDimension();
    	Picture picture = new Picture(this.getOriginalPicture());
    	Picture scaled = new Picture(this.getTileDimension(),this.getTileDimension());
    	for(int tcol = 0; tcol < scaled.width(); tcol++) {
    	    for(int trow = 0; trow < scaled.height(); trow++) {
    	    	int scol = tcol*picture.width()/scaled.width();
    	    	int srow = trow*picture.height()/scaled.height();
    	    	Color color = picture.get(scol, srow);
    	    	Color epic = picture.get(scol, srow);
    	    	if(component.equals("red")) {
    	    		epic = new Color(color.getRed(),0,0);
    	    	}
    	    	else if(component.equals("blue")) {
    	    		epic = new Color(0,0,color.getBlue());
    	    	}
    	    	else if(component.equals("green")) {
    	    		epic = new Color(0,color.getGreen(),0);
    	    	}
    	    		scaled.set(tcol, trow, epic);
    	    	}
    	    }
    	    for(int i = 0; i < this.getTileDimension(); i++) {
    	    	for(int j = 0; j < this.getTileDimension(); j++) {
        			Color color = scaled.get(i, j);
        			collage.set(i+startcol, j+startrow, color);
        		}
    	    }
    	}

    /*
     * Greyscale tile at (collageCol, collageRow)
     * (see Week 9 slides, the code for luminance is at the book's website)
     *
     * @param collageCol tile column
     * @param collageRow tile row
     */

    public void greyscaleTile (int collageCol, int collageRow) {
    	int startrow = collageRow*this.getTileDimension();
    	int startcol = collageCol*this.getTileDimension();
    	Picture picture = new Picture(this.getOriginalPicture());
    	Picture scaled = new Picture(this.getTileDimension(),this.getTileDimension());
    	for(int tcol = 0; tcol < scaled.width(); tcol++) {
    	    for(int trow = 0; trow < scaled.height(); trow++) {
    	    	int scol = tcol*picture.width()/scaled.width();
    	    	int srow = trow*picture.height()/scaled.height();
    	    	Color color = picture.get(scol, srow);
    	    	Color epic = Luminance.toGray(color);
    	    	scaled.set(tcol, trow, epic);
    	    	}
    	    }
    	    for(int i = 0; i < this.getTileDimension(); i++) {
    	    	for(int j = 0; j < this.getTileDimension(); j++) {
        			Color color = scaled.get(i, j);
        			collage.set(i+startcol, j+startrow, color);
        		}
    	    }
    }


    // Test client
    public static void main (String[] args) {
    	// Creates a collage of 3x3 tiles. Each tile dimension is 200x200 pixels
    	ArtCollage art = new ArtCollage("ariel.jpg", 200, 3);
    	art.makeCollage();
    	// Converts the tile at col 1, row 0 from color to greyscale
    	art.greyscaleTile(1, 0);
    	art.showCollagePicture();
    }
}