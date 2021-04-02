package com.javarush.task.task39.task3905;

public class PhotoPaint {
    public boolean paintFill(Color[][] image, int x, int y, Color desiredColor) {
        if (x < 0 || y < 0 || y >= image.length || x >= image[0].length || desiredColor == image[y][x]) {
            return false;
        }
        Color changingColor = image[y][x];
        fillColor(image, x, y, desiredColor, changingColor);
        return true;
    }

    private void fillColor(Color[][] image, int x, int y, Color desiredColor, Color changingColor) {
        if (x < 0 || y < 0 || y >= image.length || x >= image[0].length || changingColor != image[y][x]) {
            return;
        }
        image[y][x] = desiredColor;
        fillColor(image, x - 1, y, desiredColor, changingColor);
        fillColor(image, x, y - 1, desiredColor, changingColor);
        fillColor(image, x + 1, y, desiredColor, changingColor);
        fillColor(image, x, y + 1, desiredColor, changingColor);
    }
}
