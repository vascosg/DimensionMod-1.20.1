package net.agentefreitas.dimensionmod.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class BookScreen extends BookEditScreen {

    private boolean isSigning;
    private int frameTick;
    private String title = "";

    private static final Component EDIT_TITLE_LABEL = Component.translatable("book.editTitle");
    private static final Component FINALIZE_WARNING_LABEL = Component.translatable("book.finalizeWarning");
    private static final FormattedCharSequence BLACK_CURSOR = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.BLACK));
    private static final FormattedCharSequence GRAY_CURSOR = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.GRAY));

    private Component pageMsg = CommonComponents.EMPTY;


    private final Component ownerText;

    public BookScreen(Player pOwner, ItemStack pBook, InteractionHand pHand) {
        super(pOwner, pBook, pHand);
        this.ownerText = Component.translatable("book.byAuthor", pOwner.getName()).withStyle(ChatFormatting.DARK_GRAY);
    }

    /**
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        this.setFocused((GuiEventListener)null);
        int i = (this.width - 192) / 2;
        int j = 2;
        pGuiGraphics.blit(BookViewScreen.BOOK_LOCATION, i, 2, 0, 0, 192, 192);
        if (this.isSigning) {
            boolean flag = this.frameTick / 6 % 2 == 0;
            FormattedCharSequence formattedcharsequence = FormattedCharSequence.composite(FormattedCharSequence.forward(this.title, Style.EMPTY), flag ? BLACK_CURSOR : GRAY_CURSOR);
            int k = this.font.width(EDIT_TITLE_LABEL);
            pGuiGraphics.drawString(this.font, EDIT_TITLE_LABEL, i + 36 + (114 - k) / 2, 34, 0, false);
            int l = this.font.width(formattedcharsequence);
            pGuiGraphics.drawString(this.font, formattedcharsequence, i + 36 + (114 - l) / 2, 50, 0, false);
            int i1 = this.font.width(this.ownerText);
            pGuiGraphics.drawString(this.font, this.ownerText, i + 36 + (114 - i1) / 2, 60, 0, false);
            pGuiGraphics.drawWordWrap(this.font, FINALIZE_WARNING_LABEL, i + 36, 82, 114, 0);
        } else {
            int j1 = this.font.width(this.pageMsg);
            pGuiGraphics.drawString(this.font, this.pageMsg, i - j1 + 192 - 44, 18, 0, false);
            BookScreen.DisplayCache bookeditscreen$displaycache = this.getDisplayCache();

            for(BookEditScreen.LineInfo bookeditscreen$lineinfo : bookeditscreen$displaycache.lines) {
                pGuiGraphics.drawString(this.font, bookeditscreen$lineinfo.asComponent, bookeditscreen$lineinfo.x, bookeditscreen$lineinfo.y, -16777216, false);
            }

            this.renderHighlight(pGuiGraphics, bookeditscreen$displaycache.selection);
            this.renderCursor(pGuiGraphics, bookeditscreen$displaycache.cursor, bookeditscreen$displaycache.cursorAtEnd);
        }

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }**/

    private static final ResourceLocation CUSTOM_BOOK_TEXTURE = new ResourceLocation("dimensionmod", "textures/gui/dimension_diary_background.png");

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(pGuiGraphics); // Renderiza o fundo padrão

        int i = (this.width - 192) / 2; // Posição X do livro
        int j = 2;                     // Posição Y do livro

        // Renderiza a textura personalizada em vez da textura vanilla
        RenderSystem.setShaderTexture(0, CUSTOM_BOOK_TEXTURE);
        pGuiGraphics.blit(CUSTOM_BOOK_TEXTURE, i, j, 0, 0, 192, 192);

        // Renderiza o texto, cursor, e conteúdo original
        super.render(pGuiGraphics, mouseX, mouseY, partialTicks);
    }



}
