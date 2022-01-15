package xyz.wagyourtail.jsmacros.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import xyz.wagyourtail.jsmacros.client.config.ClientConfigV2;
import xyz.wagyourtail.jsmacros.client.config.Profile;
import xyz.wagyourtail.jsmacros.client.event.EventRegistry;
import xyz.wagyourtail.jsmacros.client.gui.screens.KeyMacrosScreen;
import xyz.wagyourtail.jsmacros.client.movement.MovementQueue;
import xyz.wagyourtail.jsmacros.core.Core;
import xyz.wagyourtail.wagyourgui.BaseScreen;

import java.io.File;
import java.util.Objects;

public class JsMacros {
    public static final String MOD_ID = "jsmacros";
    public static final Logger LOGGER  = LogManager.getLogger();
    public static KeyBinding keyBinding = new KeyBinding("jsmacros.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, "jsmacros.title");
    public static BaseScreen prevScreen;
    protected static final File configFolder;

    static {
        ServiceLoader<ConfigFolder> cf = ServiceLoader.load(ConfigFolder.class);
        if (cf.iterator().hasNext()) {
            configFolder = cf.iterator().next().getFolder();
        } else {
            throw new NullPointerException("Config folder provider not found");
        }
    }

    public static final Core<Profile, EventRegistry> core = Core.createInstance(EventRegistry::new, Profile::new, configFolder.getAbsoluteFile(), new File(configFolder, "Macros"), LOGGER);

    public static void onInitialize() {
        // this is first, we just want core loaded here
        try {
            core.config.addOptions("client", ClientConfigV2.class);
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        // HINT TO EXTENSION DEVS: Use this init to add your shit before any scripts are actually run
    }


    public static void onInitializeClient() {
        // this comes later, we want to do core's deferred init here
        core.deferredInit();

        prevScreen = new KeyMacrosScreen(null);

        // Init MovementQueue
        MovementQueue.clear();
    }

    static public Text getKeyText(String translationKey) {
        try {
            return new LiteralText(getLocalizedName(InputUtil.fromName(translationKey)));
        } catch(Exception e) {
            return new LiteralText(translationKey);
        }
    }
    
    static public String getScreenName(Screen s) {
        if (s == null) return null;
        if (s instanceof ContainerScreen) {
            //add more ?
            if (s instanceof GenericContainerScreen) {
                return String.format("%d Row Chest", ((GenericContainerScreen) s).getContainer().getRows());
            } else if (s instanceof Generic3x3ContainerScreen) {
                return "3x3 Container";
            } else if (s instanceof AnvilScreen) {
                return "Anvil";
            } else if (s instanceof BeaconScreen) {
                return "Beacon";
            } else if (s instanceof BlastFurnaceScreen) {
                return "Blast Furnace";
            } else if (s instanceof BrewingStandScreen) {
                return "Brewing Stand";
            } else if (s instanceof CraftingTableScreen) {
                return "Crafting Table";
            } else if (s instanceof EnchantingScreen) {
                return "Enchanting Table";
            } else if (s instanceof FurnaceScreen) {
                return "Furnace";
            } else if (s instanceof GrindstoneScreen) {
                return "Grindstone";
            } else if (s instanceof HopperScreen) {
                return "Hopper";
            } else if (s instanceof LoomScreen) {
                return "Loom";
            } else if (s instanceof MerchantScreen) {
                return "Villager";
            } else if (s instanceof ShulkerBoxScreen) {
                return "Shulker Box";
            } else if (s instanceof SmokerScreen) {
                return "Smoker";
            } else if (s instanceof CartographyTableScreen) {
                return "Cartography Table";
            } else if (s instanceof StonecutterScreen) {
                return "Stonecutter";
            } else if (s instanceof InventoryScreen) {
                return "Survival Inventory";
            } else if (s instanceof HorseScreen) {
                return "Horse";
            } else if (s instanceof CreativeInventoryScreen) {
                return "Creative Inventory";
            } else {
                return s.getClass().getName();
            }
        } else if (s instanceof ChatScreen) {
            return "Chat";
        }
        Text t = s.getTitle();
        String ret = "";
        if (t != null) ret = t.getString();
        if (ret.equals("")) ret = "unknown";
        return ret;
    }
    
    @Deprecated
    static public String getLocalizedName(InputUtil.KeyCode keyCode) {
        String string = keyCode.getName();
        int i = keyCode.getKeyCode();
        String string2 = null;
        switch(keyCode.getCategory()) {
            case KEYSYM:
                string2 = InputUtil.getKeycodeName(i);
                break;
            case SCANCODE:
                string2 = InputUtil.getScancodeName(i);
                break;
            case MOUSE:
                String string3 = I18n.translate(string);
                string2 = Objects.equals(string3, string) ? I18n.translate(InputUtil.Type.MOUSE.getName(), i + 1) : string3;
        }
    
        return string2 == null ? I18n.translate(string) : string2;
     }
    
    @Deprecated
    static public MinecraftClient getMinecraft() {
        return MinecraftClient.getInstance();
    }
    

    public static int[] range(int end) {
        return range(0, end, 1);
    }
    
    public static int[] range(int start, int end) {
        return range(start, end, 1);
    }
    
    public static int[] range(int start, int end, int iter) {
        int[] a = new int[end-start];
        for (int i = start; i < end; i+=iter) {
            a[i-start] = i;
        }
        return a;
    }


    public static Object tryAutoCastNumber(Class<?> returnType, Object number) {
        if ((returnType == int.class || returnType == Integer.class) && !(number instanceof Integer)) {
            number = ((Number) number).intValue();
        } else if ((returnType == float.class || returnType == Float.class) && !(number instanceof Float)) {
            number = ((Number) number).floatValue();
        } else if ((returnType == double.class || returnType == Double.class) && !(number instanceof Double)) {
            number = ((Number) number).doubleValue();
        } else if ((returnType == short.class || returnType == Short.class) && !(number instanceof Short)) {
            number = ((Number) number).shortValue();
        } else if ((returnType == long.class || returnType == Long.class) && !(number instanceof Long)) {
            number = ((Number) number).longValue();
        } else if ((returnType == char.class || returnType == Character.class) && !(number instanceof Character)) {
            number = (char) ((Number) number).intValue();
        } else if ((returnType == byte.class || returnType == Byte.class) && !(number instanceof Byte)) {
            number = ((Number) number).byteValue();
        }
        return number;
    }
}