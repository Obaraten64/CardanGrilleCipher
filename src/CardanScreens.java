import add.*;

import javax.swing.*;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardanScreens {
    public static void mainScreen(JFrame f) {
        GridBagConstraints gdc = Initializer.initializeInterface();
        f.getContentPane().removeAll();
        //remove section|
        JLabel label = new JLabel("Select between encoding and decoding your text");
        label.setOpaque(true);
        f.add(label, gdc);

        gdc.anchor = GridBagConstraints.WEST;
        gdc.gridy++;
        JButton codeButton = new JButton("Code");
        codeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardanScreens.secondScreen(f, "Code");
            }
        });
        f.add(codeButton, gdc);

        gdc.anchor = GridBagConstraints.EAST;
        JButton decodeButton = new JButton("Decode");
        decodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardanScreens.secondScreen(f, "Decode");
            }
        });
        f.add(decodeButton, gdc);
        //remove section|
        f.revalidate();
        f.repaint();
    }

    private static void secondScreen(JFrame f, String chosen) {
        GridBagConstraints gdc = Initializer.initializeInterface();
        f.getContentPane().removeAll();
        //remove section|
        JLabel keyLabel = new JLabel(((char)0x2193) + " Enter your key here " + ((char)0x2193));
        keyLabel.setOpaque(true);
        f.add(keyLabel, gdc);
        gdc.gridx += 1;

        JLabel keyArrLabel = new JLabel(((char)0x2193) + " Enter your letter location indexes here " + ((char)0x2193));
        keyArrLabel.setOpaque(true);
        f.add(keyArrLabel, gdc);
        gdc.gridx += 1;

        JLabel textLabel = new JLabel(((char)0x2193) + " Enter your text here " + ((char)0x2193));
        textLabel.setOpaque(true);
        f.add(textLabel, gdc);
        gdc.gridx = 0;
        gdc.gridy++;

        JTextArea inputKey = new JTextArea(1, 20);
        inputKey.setEditable(true);
        inputKey.setBackground(Color.LIGHT_GRAY);
        f.add(inputKey, gdc);
        gdc.gridx += 1;

        JTextArea inputKeyArr = new JTextArea(1, 20);
        inputKeyArr.setEditable(true);
        inputKeyArr.setBackground(Color.LIGHT_GRAY);
        f.add(inputKeyArr, gdc);
        gdc.gridx += 1;

        JTextArea inputText = new JTextArea(1, 20);
        inputText.setEditable(true);
        inputText.setBackground(Color.LIGHT_GRAY);
        f.add(inputText, gdc);
        gdc.gridx = 0;
        gdc.gridy++;

        JTextPane exampleKey = new JTextPane();
        exampleKey.setText("Example: \"4\"");
        exampleKey.setEditable(false);
        f.add(exampleKey, gdc);
        gdc.gridx += 1;

        JTextPane exampleKeyArr = new JTextPane();
        exampleKeyArr.setText("Example: \"2 7 9 12\"");
        exampleKeyArr.setEditable(false);
        f.add(exampleKeyArr, gdc);
        gdc.gridx += 1;

        JTextPane exampleText = new JTextPane();
        exampleText.setText("Example: \"Перестановки\"");
        exampleText.setEditable(false);
        f.add(exampleText, gdc);
        gdc.gridx--;
        gdc.gridy++;

        //symbols limits
        inputKey.addKeyListener(new TextConsumer(inputKey, 1));
        inputKeyArr.addKeyListener(new TextConsumer(inputKeyArr, 40));
        inputText.addKeyListener(new TextConsumer(inputText, 40));
        //symbols limits

        JButton button = new JButton(chosen);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int key = Integer.parseInt(inputKey.getText());
                    CardanGrille.makeKeyMatrix(key, Initializer.parseIntArr(inputKeyArr.getText()));
                    String str = switch (chosen) {
                        case ("Code") -> CardanGrille.code(inputText.getText(), key);
                        case ("Decode") -> CardanGrille.decode(inputText.getText(), key);
                        default -> throw new IllegalStateException("Unexpected value: " + chosen);
                    };
                    CardanScreens.thirdScreen(f, str);
                } catch (Exception exception) {
                    CardanGrille.makeMatrixNull();
                    if (exception instanceof NumberFormatException) {
                        CardanScreens.thirdScreen(f, "ERROR: Key or indexes in not a number");
                    } else {
                        CardanScreens.thirdScreen(f, exception.getMessage());
                    }
                }
            }
        });
        f.add(button, gdc);
        //remove section|
        f.revalidate();
        f.repaint();
    }

    private static void thirdScreen(JFrame f, String output) {
        GridBagConstraints gdc = Initializer.initializeInterface();
        f.getContentPane().removeAll();
        //remove section|
        JPanel panelForButtons = new JPanel(new GridLayout(0, 1));
        //panel struct
        JTextArea textArea = new JTextArea(1, 20);
        textArea.setText(output);
        textArea.setEditable(false);
        panelForButtons.add(textArea, gdc);

        JButton button = new JButton("Go to main screen");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardanScreens.mainScreen(f);
            }
        });
        panelForButtons.add(button, gdc);
        //panel struct
        gdc.weighty = 1.0;
        f.add(panelForButtons, gdc);

        gdc.gridy++;
        gdc.weighty = 0.1;
        gdc.anchor = GridBagConstraints.PAGE_END;

        JButton matrixButton = new JButton("Show matrix");
        matrixButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardanScreens.additionalScreen(f, output);
            }
        });
        f.add(matrixButton, gdc);
        //remove section|
        f.revalidate();
        f.repaint();
    }

    private static void additionalScreen(JFrame f, String output) {
        GridBagConstraints gdc = Initializer.initializeInterface();
        f.getContentPane().removeAll();
        //remove section|
        JLabel label = new JLabel();
        label.setText(CardanGrille.showMatrix());
        label.setOpaque(true);
        //label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 25)); //Bigger symbols
        f.add(label, gdc);
        gdc.gridy++;

        JButton button = new JButton("Back to previous screen");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardanScreens.thirdScreen(f, output);
            }
        });
        f.add(button, gdc);
        //remove section|
        f.revalidate();
        f.repaint();
    }
}