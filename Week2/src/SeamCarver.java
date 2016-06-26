import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;

import java.awt.*;

public class SeamCarver {

    Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    // current picture
    public Picture picture() {
        return picture;
    }

    // width of current picture
    public int width() {
        return picture.width();
    }

    // height of current picture
    public int height() {
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x == 0 || y == 0 || x == width() - 1 || y == height() - 1)
            return 1000.0;

        Color xPrev = picture.get(x - 1, y);
        Color xNext = picture.get(x + 1, y);
        Color yPrev = picture.get(x, y - 1);
        Color yNext = picture.get(x, y + 1);
        double redX = xNext.getRed() - xPrev.getRed();
        double greenX = xNext.getGreen() - xPrev.getGreen();
        double blueX = xNext.getBlue() - xPrev.getBlue();
        double redY = yNext.getRed() - yPrev.getRed();
        double greenY = yNext.getGreen() - yPrev.getGreen();
        double blueY = yNext.getBlue() - yPrev.getBlue();

        double xGradSq = redX * redX + blueX * blueX + greenX * greenX;
        double yGradSq = redY * redY + blueY * blueY + greenY * greenY;

        return Math.sqrt(xGradSq + yGradSq);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {

        return new int[0];
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {

        return new int[0];
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {

    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {

    }

    public static void main(String[] args) {
        String abs = "/home/michal/Coursera/Algorithms_2/Week2/src/";
        String[] paths = {"10x10.png", "3x7.png", "7x10.png", "3x4.png"};
        for (String path : paths) {
            StdOut.printf("\nImage: %s\n", abs + path);
            SeamCarver sc = new SeamCarver(new Picture(abs + path));
            StdOut.printf("Img size: %d %d\n", sc.width(), sc.height());
            for (int i=0;i<sc.height();i++) {
                for (int j = 0; j < sc.width(); j++)
                    StdOut.printf("%f\t", sc.energy(j,i));
                StdOut.println();
            }

        }
    }
}
