public class CardanGrille {
    private static int[][] keyMatrix;
    private static char[][] matrix;

    public static void makeKeyMatrix(int key, int[] keyArr) throws Exception {
        if (key <= 1) {
            makeMatrixNull();
            throw new Exception("ERROR: Key is less than 1, no sense to cipher text");
        }
        keyMatrix = new int[key][key];

        for (int i : keyArr) {
            if ((i >= (key * key)) || (i < 0)) {
                throw new Exception("ERROR: One of the indexes is not a positive number or more than key^2");
            } else {
                keyMatrix[i / key][i % key] = 1;
            }
        }

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < key; j++) {
                if (keyMatrix[i][j] != 1) { keyMatrix[i][j] = 0; }
            }
        }
    }

    private static int[][] rotation(int[][] arr) {
        int length = arr.length;
        int[][] newArr = new int[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                newArr[j][length - i - 1] = arr[i][j];
            }
        }
        return newArr;
    }

    public static String code(String str, int key) {
        str = CardanGrille.formatText(str);
        matrix = new char[key][key];
        int index = 0;
        char symbol = 0;

        if (str.charAt(0) >= 'а' || str.charAt(0) <= 'я') {
            symbol = 'а';
        } else if (str.charAt(0) >= 'a' || str.charAt(0) <= 'z') {
            symbol = 'a';
        }

        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < key; i++) {
                for (int j = 0; j < key; j++) {
                    if (keyMatrix[i][j] == 1) {
                        if (index < str.length()) {
                            matrix[i][j] = str.charAt(index++);
                        }
                    }
                }
            }
            keyMatrix = rotation(keyMatrix);
        }

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < key; j++) {
                if (matrix[i][j] == '\u0000') {
                    if (symbol != 0) {
                        matrix[i][j] = symbol++;
                    } else {
                        matrix[i][j] = ' ';
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (char[] c : matrix) {
            sb.append(new String(c));
        }
        return sb.toString();
    }

    public static String decode(String str, int key) {
        str = CardanGrille.formatText(str);
        matrix = new char[key][key];
        StringBuilder sb = new StringBuilder();
        int index = 0;

        for (int i = 0; i < key; i++) {
            for (int j = 0; j < key; j++) {
                if (index < str.length()) {
                    matrix[i][j] = str.charAt(index++);
                }
            }
        }

        for (int k = 0; k < 4; k++) {
            for (int i = 0; i < key; i++) {
                for (int j = 0; j < key; j++) {
                    if (keyMatrix[i][j] == 1) {
                        if (matrix[i][j] != ' ') {
                            sb.append(matrix[i][j]);
                        }
                    }
                }
            }
            keyMatrix = rotation(keyMatrix);
        }

        return sb.toString();
    }

    public static String showMatrix() {
        StringBuilder str = new StringBuilder();
        str.append("<html><body>");
        try {
            for (int i = 0; i < matrix.length; i++) {
                for (int j = 0; j < matrix[0].length; j++) {
                    if (matrix[i][j] != ' ') {
                        str.append(matrix[i][j]).append(" ");
                    } else {
                        str.append("SPC").append(" ");
                    }
                }
                str.append("<br>");
            }
        } catch (NullPointerException exception) {
            return "ERROR: Matrix not created, back to main screen";
        }
        str.append("</body></html>");
        return str.toString();
    }

    public static void makeMatrixNull() {
        matrix = null;
    }

    public static String formatText(String str) {
        return str.toLowerCase().replaceAll("[^a-z а-я]", "");
    }
}
