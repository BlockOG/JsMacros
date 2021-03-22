package xyz.wagyourtail.jsmacros.client.gui.settings.settingtypes;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import xyz.wagyourtail.jsmacros.client.gui.elements.TextInput;
import xyz.wagyourtail.jsmacros.client.gui.settings.AbstractSettingGroupContainer;
import xyz.wagyourtail.jsmacros.client.gui.settings.SettingsOverlay;

import java.lang.reflect.InvocationTargetException;

public class StringField extends AbstractSettingType<String> {
    
    public StringField(int x, int y, int width, TextRenderer textRenderer, AbstractSettingGroupContainer parent, SettingsOverlay.SettingField<String> field) {
        super(x, y, width, textRenderer.fontHeight + 2, textRenderer, parent, field);
    }
    
    @Override
    public void init() {
        super.init();
        try {
            addButton(new TextInput(x + width/2, y, width / 2, height, textRenderer, 0xFF101010, 0, 0xFF4040FF, 0xFFFFFF, setting.get(), null, (value) -> {
                try {
                    setting.set(value);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void setPos(int x, int y, int width, int height) {
        super.setPos(x, y, width, height);
        for (AbstractButtonWidget btn : buttons) {
            btn.y = y;
        }
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        textRenderer.draw(matrices, trimmed(settingName, width / 2), x, y + 1, 0xFFFFFF);
    }
    
}
