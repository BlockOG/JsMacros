package xyz.wagyourtail.jsmacros.forge.client;

import net.minecraftforge.fml.ExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import xyz.wagyourtail.jsmacros.client.JsMacros;
import xyz.wagyourtail.jsmacros.client.api.classes.CommandBuilder;
import xyz.wagyourtail.jsmacros.forge.client.api.classes.CommandBuilderForge;
import xyz.wagyourtail.jsmacros.forge.client.forgeevents.ForgeEvents;

@Mod(JsMacros.MOD_ID)
public class JsMacrosForge {

    public JsMacrosForge() {
        // needs to be earlier because forge does this too late and Core.instance ends up null for first sound event
        JsMacros.onInitialize();
    }

    @Mod.EventHandler
    public void onInitialize(FMLInitializationEvent event) {

        // initialize loader-specific stuff
        CommandBuilder.createNewBuilder = CommandBuilderForge::new;

        ForgeEvents.init();

        ClientRegistry.registerKeyBinding(JsMacros.keyBinding);

        // load fabric-style plugins
        FakeFabricLoader.instance.loadEntries();
    }

    public void onInitializeClient(FMLClientSetupEvent event) {
        JsMacros.onInitializeClient();

    @SubscribeEvent
    public void onMouse(InputEvent.MouseInputEvent mouseEvent) {
        if (Mouse.getEventButtonState() ^ FKeyBind.pressedKeys.contains(Mouse.getEventButton() - 100))
            new EventKey(Mouse.getEventButton() - 100, 0, Mouse.getEventButtonState() ? 1 : 0, BaseScreen.createModifiers());
    }

    public static class ShimClassLoader extends ClassLoader {
        public ShimClassLoader() {
            super(ShimClassLoader.class.getClassLoader());
        }

        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            try {
                return super.loadClass(name);
            } catch (StringIndexOutOfBoundsException e) {
                throw new ClassNotFoundException();
            }
        }
    }
}
