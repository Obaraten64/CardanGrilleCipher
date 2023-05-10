package add;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TextConsumer implements KeyListener {
    JTextArea input;
    int key;

    public TextConsumer(JTextArea text, int key) {
        input = text;
        this.key = key;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(input.getText().length() > (key + 1)) {
            e.consume();
            String shortened = input.getText().substring(0, key);
            input.setText(shortened);
        }else if(input.getText().length() >= key) {
            e.consume();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
