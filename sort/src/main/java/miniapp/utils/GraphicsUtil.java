package miniapp.utils;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GraphicsUtil {

    public static void makeBufferedImageTransparent(BufferedImage image)
    {
        Graphics2D bufferedGraphics = null;
        try {
            bufferedGraphics = image.createGraphics();

            bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
            bufferedGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
            bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        } finally {
            if(bufferedGraphics != null)
            {
                bufferedGraphics.dispose();
            }
        }
    }

}
