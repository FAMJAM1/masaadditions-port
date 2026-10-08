package com.red.masaadditions.litematica_additions.util;

import fi.dy.masa.litematica.gui.GuiSchematicBrowserBase;
import fi.dy.masa.litematica.gui.GuiSchematicSave;
import fi.dy.masa.litematica.gui.GuiSchematicSaveBase;
import fi.dy.masa.litematica.schematic.LitematicaSchematic;
import fi.dy.masa.malilib.gui.GuiTextFieldGeneric;
import fi.dy.masa.malilib.gui.Message;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetFileBrowserBase;
import fi.dy.masa.malilib.util.FileUtils;
import fi.dy.masa.malilib.util.KeyCodes;
import fi.dy.masa.malilib.render.GuiContext;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.jetbrains.annotations.Nullable;

public abstract class GuiFileImportBase extends GuiSchematicBrowserBase implements ISelectionListener<WidgetFileBrowserBase.DirectoryEntry> {
    protected GuiTextFieldGeneric textField;
    protected String lastText = "";
    protected String defaultText = "";
    protected final LitematicaSchematic schematic;

    public GuiFileImportBase(LitematicaSchematic schematic) {
        super(10, 70);

        this.schematic = schematic;

        this.textField = new GuiTextFieldGeneric(10, 32, 160, 20, this.font);
        this.textField.setMaxLength(256);
        this.textField.setFocused(true);
    }

    @Override
    public int getBrowserHeight() {
        return this.height - 80;
    }

    @Override
    public void initGui() {
        super.initGui();

        boolean focused = this.textField.isFocused();
        String text = this.textField.getValue();
        int pos = this.textField.getCursorPosition();
        this.textField = new GuiTextFieldGeneric(10, 32, this.width - 196, 20, this.font);
        this.textField.setValue(text);
        this.textField.moveCursorTo(pos, false);
        this.textField.setFocused(focused);

        WidgetFileBrowserBase.DirectoryEntry entry = this.getListWidget().getLastSelectedEntry();

        // Only set the text field contents if it hasn't been set already.
        // This prevents overwriting any user input text when switching to a newly created directory
        if (this.lastText.isEmpty()) {
            if (entry != null && entry.getType() != WidgetFileBrowserBase.DirectoryEntryType.DIRECTORY && entry.getType() != WidgetFileBrowserBase.DirectoryEntryType.INVALID) {
                this.setTextFieldText(FileUtils.getNameWithoutExtension(entry.getName()));
            } else if (this.schematic != null) {
                this.setTextFieldText(this.schematic.getMetadata().getName());
            } else {
                this.setTextFieldText(this.defaultText);
            }
        }

        this.createButton(this.textField.getX() + this.textField.getWidth() + 12, 32);
    }

    protected void setTextFieldText(String text) {
        this.lastText = text;
        this.textField.setValue(text);
        this.textField.moveCursorToEnd(false);
    }

    protected String getTextFieldText() {
        return this.textField.getValue();
    }

    protected abstract IButtonActionListener createButtonListener(GuiSchematicSaveBase.ButtonType type);

    private int createButton(int x, int y) {
        String label = StringUtils.translate(GuiSchematicSave.ButtonType.SAVE.getLabelKey());
        int width = this.getStringWidth(label) + 10;
        ButtonGeneric button = new ButtonGeneric(x, y, width, 20, label, "litematica.gui.label.schematic_save.hoverinfo.hold_shift_to_overwrite");
        this.addButton(button, this.createButtonListener(GuiSchematicSave.ButtonType.SAVE));
        return x + width + 4;
    }

    @Override
    public void setString(String string) {
        this.setNextMessageType(Message.MessageType.ERROR);
        super.setString(string);
    }

    @Override
    public void drawContents(GuiContext context, int mouseX, int mouseY, float partialTicks) {
        super.drawContents(context, mouseX, mouseY, partialTicks);

        this.textField.extractWidgetRenderState(context.getGuiGraphics(), mouseX, mouseY, partialTicks);
    }

    @Override
    public void onSelectionChange(@Nullable WidgetFileBrowserBase.DirectoryEntry entry) {
        if (entry != null && entry.getType() != WidgetFileBrowserBase.DirectoryEntryType.DIRECTORY && entry.getType() != WidgetFileBrowserBase.DirectoryEntryType.INVALID) {
            this.setTextFieldText(FileUtils.getNameWithoutExtension(entry.getName()));
        }
    }

    @Override
    protected ISelectionListener<WidgetFileBrowserBase.DirectoryEntry> getSelectionListener() {
        return this;
    }

    @Override
    public boolean onMouseClicked(MouseButtonEvent event, boolean doubled) {
        if (this.textField.mouseClicked(event, doubled)) {
            return true;
        }

        return super.onMouseClicked(event, doubled);
    }

    @Override
    public boolean onKeyTyped(KeyEvent event) {
        if (this.textField.keyPressed(event)) {
            this.getListWidget().clearSelection();
            return true;
        } else if (event.key() == KeyCodes.KEY_TAB) {
            this.textField.setFocused(!this.textField.isFocused());
            return true;
        }

        return super.onKeyTyped(event);
    }

    @Override
    public boolean onCharTyped(CharacterEvent event) {
        if (this.textField.charTyped(event)) {
            this.getListWidget().clearSelection();
            return true;
        }

        return super.onCharTyped(event);
    }
}
