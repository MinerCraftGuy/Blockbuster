package mchorse.blockbuster.client.gui.framework.elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI elements collection
 * 
 * This class is responsible for handling a collection of elements
 */
public class GuiElements implements IGuiElement, IGuiLegacy
{
    public List<IGuiElement> elements = new ArrayList<IGuiElement>();

    public void add(IGuiElement element)
    {
        this.elements.add(element);
    }

    @Override
    public void resize(int width, int height)
    {
        for (IGuiElement element : this.elements)
        {
            element.resize(width, height);
        }
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

    @Override
    public boolean isVisible()
    {
        return true;
    }

    @Override
    public boolean handleMouseInput(int mouseX, int mouseY) throws IOException
    {
        for (IGuiElement element : this.elements)
        {
            if (element instanceof IGuiLegacy)
            {
                if (((IGuiLegacy) element).handleMouseInput(mouseX, mouseY))
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        for (IGuiElement element : this.elements)
        {
            if (element.isEnabled())
            {
                element.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    @Override
    public void mouseScrolled(int mouseX, int mouseY, int scroll)
    {
        for (IGuiElement element : this.elements)
        {
            if (element.isEnabled())
            {
                element.mouseScrolled(mouseX, mouseY, scroll);
            }
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state)
    {
        for (IGuiElement element : this.elements)
        {
            if (element.isEnabled())
            {
                element.mouseReleased(mouseX, mouseY, state);
            }
        }
    }

    @Override
    public boolean hasActiveTextfields()
    {
        for (IGuiElement element : this.elements)
        {
            if (element.isEnabled() && element.hasActiveTextfields())
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean handleKeyboardInput() throws IOException
    {
        for (IGuiElement element : this.elements)
        {
            if (element instanceof IGuiLegacy)
            {
                if (((IGuiLegacy) element).handleKeyboardInput())
                {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void keyTyped(char typedChar, int keyCode)
    {
        for (IGuiElement element : this.elements)
        {
            if (element.isEnabled())
            {
                element.keyTyped(typedChar, keyCode);
            }
        }
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks)
    {
        for (IGuiElement element : this.elements)
        {
            if (element.isVisible())
            {
                element.draw(mouseX, mouseY, partialTicks);
            }
        }
    }
}