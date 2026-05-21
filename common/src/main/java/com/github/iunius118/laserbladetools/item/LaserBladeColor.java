package com.github.iunius118.laserbladetools.item;

import net.minecraft.util.Mth;

public enum LaserBladeColor {
    WHITE(0xFFFFFFFF, 0xFFF9FFFE, "white"),
    ORANGE(0xFFFFA500, 0xFFF9801D, "orange"),
    MAGENTA(0xFFFF00FF, 0xFFC74EBD, "magenta"),
    LIGHT_BLUE(0xFF00AAFF, 0xFF3AB3DA, "light_blue"),
    YELLOW(0xFFFFEE00, 0xFFFED83D, "yellow"),
    LIME(0xFF00FF00, 0xFF80C71F, "lime"),
    PINK(0xFFFFC0CB, 0xFFF38BAA, "pink"),
    GRAY(0xFF808080, 0xFF474F52, "gray"),
    LIGHT_GRAY(0xFFD3D3D3, 0xFF9D9D97, "light_gray"),
    CYAN(0xFF00FFFF, 0xFF169C9C, "cyan"),
    PURPLE(0xFF9D00FF, 0xFF8932B8, "purple"),
    BLUE(0xFF0000FF, 0xFF3C44AA, "blue"),
    BROWN(0xFF964B00, 0xFF835432, "brown"),
    GREEN(0xFF32CD32, 0xFF5E7C16, "green"),
    RED(0xFFFF0000, 0xFFB02E26, "red"),
    BLACK(0xFF020202, 0xFF1D1D21, "black"),
    ;

    private final int outerBladeColor;
    private final int innerBladeColor;
    private final int handleColor;
    private final String colorName;

    LaserBladeColor(int outerBladeColor, int innerBladeColor, int handleColor, String colorName) {
        this.outerBladeColor = outerBladeColor;
        this.innerBladeColor = innerBladeColor;
        this.handleColor = handleColor;
        this.colorName = colorName;
    }

    LaserBladeColor(int bladeColor, int handleColor, String colorName) {
        this(bladeColor, bladeColor, handleColor, colorName);
    }


    public static LaserBladeColor get(int index) {
        final LaserBladeColor[] values = LaserBladeColor.values();
        return values[Mth.clamp(index, 0, values.length - 1)];
    }

    public int bladeColor() {
        return outerBladeColor;
    }

    public int outerBladeColor() {
        return outerBladeColor;
    }

    public int innerBladeColor() {
        return innerBladeColor;
    }

    public int handleColor() {
        return handleColor;
    }

    public String colorName() {
        return colorName;
    }

    public int partColor(int part) {
        return switch(part) {
            case 0 -> handleColor;
            case 1 -> outerBladeColor;
            case 2 -> innerBladeColor;
            default -> 0;
        };
    }
}
