package miniapp.Enum;

import java.awt.Color;

/**
 * 颜色枚举
 */
public enum LineColorEnum{
//        Color Red =             new Color(0xF44336);
//        Color Pink =            new Color(0xE91E63);
//        Color Purple =          new Color(0x9C27B0);
//        Color DeepPurple =      new Color(0x673AB7);
//        Color Indigo =          new Color(0x3F51B5);
//        Color Blue =            new Color(0x2196F3);
//        Color LightBlue =       new Color(0x03A9F4);
//        Color Cyan =            new Color(0x00BCD4);
//        Color Teal =            new Color(0x009688);
//        Color Green =           new Color(0x4CAF50);
//        Color LightGreen =      new Color(0x8BC34A);
//        Color Lime =            new Color(0xCDDC39);
//        Color Yellow =          new Color(0xFFEB3B);
//        Color Amber =           new Color(0xFFC107);
//        Color Orange =          new Color(0xFF9800);
//        Color DeepOrange =      new Color(0xFF5722);
//        Color Brown =           new Color(0x795548);
//        Color Grey =            new Color(0x9E9E9E);
//        Color BlueGrey =        new Color(0x607D8B);
//        Color Black =           new Color(0x000000);
//        Color White =           new Color(0xFFFFFF);

        Red             (new Color(0xF44336)),
        Pink            (new Color(0xE91E63)),
        Purple          (new Color(0x9C27B0)),
        DeepPurple      (new Color(0x673AB7)),
        Indigo          (new Color(0x3F51B5)),
        Blue            (new Color(0x2196F3)),
        LightBlue       (new Color(0x03A9F4)),
        Cyan            (new Color(0x00BCD4)),
        Teal            (new Color(0x009688)),
        Green           (new Color(0x4CAF50)),
        LightGreen      (new Color(0x8BC34A)),
        Lime            (new Color(0xCDDC39)),
        Yellow          (new Color(0xFFEB3B)),
        Amber           (new Color(0xFFC107)),
        Orange          (new Color(0xFF9800)),
        DeepOrange      (new Color(0xFF5722)),
        Brown           (new Color(0x795548)),
        Grey            (new Color(0x9E9E9E)),
        BlueGrey        (new Color(0x607D8B)),
        Black           (new Color(0x000000)),
        White           (new Color(0xFFFFFF));

        private Color color;
        LineColorEnum(Color color) {
                this.color = color;
        }
        public Color getColor() {
                return color;
        }

        public void setColor(Color color) {
                this.color = color;
        }
}
