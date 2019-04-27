package game;


import common.ImageConstant;

import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tao
 */
public class GameContext {
    /**
     * 图片常量
     */
    private static Map<ImageConstant, ImageIcon> resources = new HashMap<>(32);

    /**
     * 加载图片资源
     */
    static{
        ImageConstant[] values = ImageConstant.values();
        for (ImageConstant constant : values) {
            resources.put(constant,new ImageIcon(GameContext.class.getClassLoader().getResource(constant.getResources())));
        }
    }


    public static Map<ImageConstant, ImageIcon> getResources() {
        return resources;
    }
}
