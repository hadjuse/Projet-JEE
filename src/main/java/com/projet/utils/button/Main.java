package com.projet.utils.button;

import com.projet.utils.button.action.MoveDown;
import com.projet.utils.button.action.MoveLeft;

public class Main {
    public static void main(String[] args) {
        Button button = new Button(new MoveDown(), "Move down");
        button.getButtonActionStrategy().action();
        button.setButtonStrategy(new MoveLeft());
        button.getButtonActionStrategy().action();
    }
}
