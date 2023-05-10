package add;

import javax.swing.*;

import java.awt.*;

public class Initializer {

    public static GridBagConstraints initializeInterface() {
        GridBagConstraints gdc = new GridBagConstraints();
        gdc.gridx = 0;
        gdc.gridy = 0;
        gdc.insets = new Insets(8, 8, 8, 8);
        return gdc;
    }

    public static JLabel createBG(JFrame f, ImageIcon img) {
        JLabel label = new JLabel();
        label.setLayout(new GridBagLayout());
        label.setSize(f.getWidth(), f.getHeight());
        label.setIcon(new ImageIcon((img)
                .getImage().getScaledInstance(f.getWidth(), f.getHeight(), Image.SCALE_SMOOTH)));
        return label;
    }

    public static int[] parseIntArr(String str) {
        String[] strArr = str.split(" ");
        int[] arr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            arr[i] = Integer.parseInt(strArr[i]);
        }
        return arr;
    }
}
