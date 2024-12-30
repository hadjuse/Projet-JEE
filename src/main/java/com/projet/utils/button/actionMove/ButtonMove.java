package com.projet.utils.button.actionMove;

import com.projet.utils.button.ButtonStrategy;

public class ButtonMove {
    private ButtonStrategy buttonStrategy;
    private String label;

    public ButtonMove(ButtonStrategy buttonStrategy, String label) {
        setButtonStrategy(buttonStrategy);
        setLabel(label);
    }

    public void setButtonStrategy(ButtonStrategy buttonStrategy) {
        this.buttonStrategy = buttonStrategy;
    }

    public ButtonStrategy getButtonActionStrategy() {
        return buttonStrategy;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}