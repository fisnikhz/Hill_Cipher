package com.example.hill_cipher;

import java.util.ArrayList;

public class Hill_Cipher {
    public static String encrypt(String input_text, int[][] key) {
        input_text = input_text.replaceAll("[^A-Za-zËÇëç]", "").toUpperCase();
        System.out.println(input_text);

        int padding = (key.length - (input_text.length() % key.length)) % key.length;
        input_text += "Y".repeat(padding);

        int[] input_text_nr = new int[input_text.length()];
        for (int i = 0; i < input_text.length(); i++) {

            input_text_nr[i] = (int) input_text.charAt(i) - 65;
        }

        int[][] input_text_matrix = new int[key.length][input_text_nr.length / key.length];
        int index = 0;
        for (int j = 0; j < input_text_matrix[0].length; j++) {
            for (int i = 0; i < input_text_matrix.length; i++) {
                input_text_matrix[i][j] = input_text_nr[index++];
            }
        }

        int[][] cipherTextMatrix = new int[key.length][input_text_matrix[0].length];
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < input_text_matrix[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < key.length; k++) {
                    sum += key[i][k] * input_text_matrix[k][j];
                }
                cipherTextMatrix[i][j] = sum % 28;
            }
        }

        StringBuilder cipherTextBuilder = new StringBuilder();
        for (int j = 0; j < cipherTextMatrix[0].length; j++) {
            for (int i = 0; i < cipherTextMatrix.length; i++) {
                cipherTextBuilder.append((char) (cipherTextMatrix[i][j] + 65));
            }
        }

        return cipherTextBuilder.toString();
    }


    static int mod28(int x) {
        return x >= 0 ? (x % 28) : 28 - (Math.abs(x) % 28);
    }


    static ArrayList<ArrayList<Integer>> getCofactor(ArrayList<ArrayList<Integer>> mat, int p, int q, int n) {
        ArrayList<ArrayList<Integer>> temp = new ArrayList<>();

        for (int row = 0; row < n; row++) {
            if (row == p) {
                continue;
            }

            ArrayList<Integer> tempRow = new ArrayList<>();
            for (int col = 0; col < n; col++) {
                if (col == q) {
                    continue;
                }

                tempRow.add(mat.get(row).get(col));
            }
            temp.add(tempRow);
        }

        return temp;
    }

    static int determinantOfMatrix(ArrayList<ArrayList<Integer>> mat, int n) {
        if (n == 1) {
            return mat.get(0).get(0);
        }

        int sign = 1;
        int determinant = 0;

        for (int f = 0; f < n; f++) {
            ArrayList<ArrayList<Integer>> subMatrix = getSubMatrix(mat, 0, f, n);
            int subDeterminant = determinantOfMatrix(subMatrix, n - 1);
            determinant += sign * mat.get(0).get(f) * subDeterminant;
            sign = -sign;
        }

        return mod28(determinant);
    }

    static ArrayList<ArrayList<Integer>> getSubMatrix(ArrayList<ArrayList<Integer>> mat, int p, int q, int n) {
        ArrayList<ArrayList<Integer>> subMatrix = new ArrayList<>();

        for (int row = 0; row < n - 1; row++) {
            ArrayList<Integer> subRow = new ArrayList<>();
            for (int col = 0; col < n - 1; col++) {
                int rowIndex = row < p ? row : row + 1;
                int colIndex = col < q ? col : col + 1;
                subRow.add(mat.get(rowIndex).get(colIndex));
            }
            subMatrix.add(subRow);
        }

        return subMatrix;
    }


    static int findInverseDeterminant(int R, int D, int level) {
        int i = 0;
        int[] p = {0, 1, 0};
        int[] q = new int[100];

        while (R != 0) {
            q[i] = D / R;
            int oldD = D;
            D = R;
            R = oldD % R;
            if (i > 1) {
                p[i % 3] = mod28(p[(i - 2) % level] - p[(i - 1) % level] * q[i - 2]);
            }
            i++;
        }
        if (i == 1) {
            return 1;
        } else {
            return mod28(p[(i - 2) % level] - p[(i - 1) % level] * q[i - 2]);
        }
    }

    public static String decryption(String ciphertext, ArrayList<ArrayList<Integer>> key) {
        int[] ciphertextNumbers = new int[ciphertext.length()];
        for (int i = 0; i < ciphertext.length(); i++) {
            ciphertextNumbers[i] = (int) ciphertext.charAt(i) - 65;
        }
        int n = key.size();
        int[][] ciphertextMatrix = new int[n][ciphertextNumbers.length / n];
        int index = 0;
        for (int j = 0; j < ciphertextMatrix[0].length; j++) {
            for (int i = 0; i < ciphertextMatrix.length; i++) {
                ciphertextMatrix[i][j] = ciphertextNumbers[index++];
            }
        }

        int[][] inverseKey = new int[n][n];
        int det = determinantOfMatrix(key, n);
        int detInverse = findInverseDeterminant(mod28(det), 28, n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ArrayList<ArrayList<Integer>> cofactor = getCofactor(key, i, j, n);
                inverseKey[j][i] = mod28((int) (detInverse * Math.pow(-1, i + j) * determinantOfMatrix(cofactor, n - 1)));
            }
        }

        int[][] input_textMatrix = new int[n][ciphertextMatrix[0].length];
        for (int i = 0; i < inverseKey.length; i++) {
            for (int j = 0; j < ciphertextMatrix[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < inverseKey.length; k++) {
                    sum += inverseKey[i][k] * ciphertextMatrix[k][j];
                }
                input_textMatrix[i][j] = mod28(sum);
            }
        }

        StringBuilder input_text_builder = new StringBuilder();
        for (int j = 0; j < input_textMatrix[0].length; j++) {
            for (int i = 0; i < input_textMatrix.length; i++) {
                if (input_textMatrix[i][j] == 26) {
                    input_text_builder.append('Ë');
                } else if (input_textMatrix[i][j] == 27) {
                    input_text_builder.append('Ç');
                } else {
                    input_text_builder.append((char) (input_textMatrix[i][j] + 65));
                }
            }
        }

        int padding = (n - (ciphertext.length() % n)) % n;
        if (padding > 0) {
            input_text_builder.delete(input_text_builder.length() - padding, input_text_builder.length());
        }

        return input_text_builder.toString();
    }

}