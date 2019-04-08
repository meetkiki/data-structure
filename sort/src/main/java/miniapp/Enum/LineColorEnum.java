package miniapp.Enum;

import java.awt.Color;

/**
 * 颜色枚举
 */
public enum LineColorEnum{
        /**
         * 红
         */
        Red             (new Color(0xF44336)),
        /**
         * 粉
         */
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
        Gold            (new Color(255,215,0)),
        Orange          (new Color(0xFF9800)),
        DeepOrange      (new Color(0xFF5722)),
        Tomato          (new Color(255,99,71)),
        Brown           (new Color(0x795548)),
        /**
         * 灰
         */
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
