package elements.pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import elements.Board;
import elements.Piece;
import elements.Tile;
import game.chess.GameScreen;
import interaccionFichero.LectorLineas;
import utils.Image;
import utils.Render;
import utils.Resources;

import java.util.ArrayList;

public class Mage extends Piece {

    public Mage(Boolean color, int x, int y, Board board) {
        super(color, Render.app.getManager().get(Resources.MAGE_PATH, Texture.class), x, y, board);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    protected void updateXY(int x, int y) {
        board.getTile(this.x, this.y).attacked = false;

        if (x == this.x && y == this.y){
            kiCharge = 2;
        } else {
            kiCharge = 0;
        }

        updateSprite(kiCharge);

        super.updateXY(x, y);

        if(color) {
            GameScreen.whiteKing.set(x, y);
        }else {

            GameScreen.blackKing.set(x, y);
        }
    }

    /**
     * Actualiza el sprite del mago según su ki
     * @param kiCharge valor del ki
     */
    public void updateSprite(int kiCharge) {
        if (kiCharge > 0){
            sprite = new Image(Render.app.getManager().get(Resources.MAGE_PATH,Texture.class));
        } else {
            sprite = new Image(Render.app.getManager().get(Resources.MAGE_PATH2,Texture.class));
        }
    }

    protected void simulateMovement(Tile currentTile, Vector2 move, ArrayList<Vector2> removeMovements) {
        Tile nextTile = board.getTile((int)move.x,(int) move.y);
        Piece nextTilePiece = null;
        if(nextTile.getPiece()!=null) {
            nextTilePiece = nextTile.getPiece();
        }
        currentTile.simulateMoveTo(nextTile);

        //check king
        if(color && !isKingSafe(GameScreen.blackPieces, new Vector2(move.x, move.y))) {
            removeMovements.add(move);
        }else if(!color && !isKingSafe(GameScreen.whitePieces, new Vector2(move.x, move.y))) {
            removeMovements.add(move);
        }

        undoLastMovement(currentTile,  nextTile, nextTilePiece);
    }

    /**
     * Añade a movements todos los movimientos posibles del rey ki, en todas las direcciones, 1 sola casilla, excepto hacia delante y atrás
     * <p>Si está cargado tiene alcanze aumentado</p>
     */
    @Override
    public ArrayList<Vector2> posibleMovements() {
        ArrayList<Vector2> movements = new ArrayList<>();
        movements.add(new Vector2(x,y)); //addMovements elimina el movimiento
        addMovement(x+1,y+1, board, movements);
        addMovement(x+1,y, board, movements);
        addMovement(x+1,y-1, board, movements);
        addMovement(x-1,y+1, board, movements);
        addMovement(x-1,y, board, movements);
        addMovement(x-1,y-1, board, movements);
        if (kiCharge > 0){
            addMovement(x,y-1, board, movements);
            addMovement(x,y+1, board, movements);
            addMovement(x, y+2,board,movements);
            addMovement(x, y-2,board,movements);
            addMovement(x-2,y,board,movements);
            addMovement(x+2,y,board,movements);
            addMovement(x+2, y+2,board,movements);
            addMovement(x-2, y-2,board,movements);
            addMovement(x-2,y+2,board,movements);
            addMovement(x+2,y-2,board,movements);
        }
        return movements;
    }

    public void addMovement(float x, float y, Board board, ArrayList<Vector2> movements) {
        if(board.getTile(x, y)!=null && !sameColor(board.getTile(x, y).getPiece())) {
            movements.add(new Vector2(x, y));
        }
    }

    public void dispose() {
        sprite.dispose();
    }

    @Override
    public String getInfo() {
        LectorLineas Reader, configReader;
        configReader = new LectorLineas("files/config.txt");
        String config = configReader.leerLinea(1);
        Reader = new LectorLineas("files/lang/" + config + "Modified.txt");
        switch (config) {
            case "esp/":
                return Reader.leerTramo(34, 41);
            case "eng/":
                return Reader.leerTramo(35, 42);
            default:
                throw new IllegalArgumentException("Configuración errónea");
        }
    }

    private boolean isSafe(float start, float y, float dest) {
        boolean res=true;
        for(int i=(int) start+1; i<dest;i++) {
            if(board.getTile(i, y).piece!=null) {
                res=false;
            }else if (color && !isTileSafe(GameScreen.blackPieces, new Vector2(i, y))) {
                res = false;
            } else if (!color && !isTileSafe(GameScreen.whitePieces, new Vector2(i, y))) {
                res = false;
            }
        }
        return res;
    }

    protected boolean isTileSafe(ArrayList<Piece> pieces, Vector2 pos) {
        boolean isSafe = true;
        for (Piece piece : pieces) {
            if(piece.hasBeenMoved || !(piece instanceof King)) {
                if (piece.posibleMovements().contains(pos)) {
                    isSafe = false;
                }
            }
        }
        return isSafe;
    }


    @Override
    public Boolean sameColor(Piece piece) {
        boolean same=false;
        if(piece!=null) {
            same=color==piece.color();
        }
        return same;
    }

    @Override
    public String toString() {
        String str = super.toString();
        return str.replace("X", "Ki King");
    }
}
