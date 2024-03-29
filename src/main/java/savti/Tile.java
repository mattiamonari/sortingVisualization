package savti;

import javafx.scene.image.WritableImage;

public class Tile implements Comparable<Tile> {
    int initialPosition;
    WritableImage tile;
    private int currentPosition;
    private int x;
    private int y;

    public Tile(WritableImage image, int position, int x, int y) {
        this.setCurrentPosition(position);
        this.x = x;
        this.y = y;
        this.tile = image;
        this.initialPosition = position;
    }

    public Tile(Tile tile) {
        this.setX(tile.getX());
        this.setY(tile.getY());
        this.setCurrentPosition(tile.getCurrentPosition());
        this.initialPosition = tile.initialPosition;
        this.tile = tile.getTile();
    }

    public static Tile newInstance(Tile tile) {
        return new Tile(tile);
    }

    @Override
    public int compareTo(Tile o) {
        return Integer.compare(this.initialPosition, o.initialPosition);
    }

    @Override
    public String toString() {
        return "Posizione: " + getCurrentPosition() + "\t x: " + x + " y: " + y;
    }

    public WritableImage getTile() {
        return tile;
    }

    public void setTile(WritableImage tile) {
        this.tile = tile;
    }

    public double getWidth() {
        return tile.getWidth();
    }

    public double getHeight() {
        return tile.getHeight();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public WritableImage getTile(WritableImage tile) {
        return tile;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}
