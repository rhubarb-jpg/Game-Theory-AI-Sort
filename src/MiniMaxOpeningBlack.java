import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

public class MiniMaxOpeningBlack {
    int position = 0;
    int approx_pos = 0;

    public static void main(String[] args) throws IOException {
        File board1 = new File(args[0]);
        File board2 = new File(args[1]);
        int depth = Integer.parseInt(args[2]);

        FileInputStream file_input = new FileInputStream(board1);
        PrintWriter file_output = new PrintWriter(new FileWriter(board2));
        Scanner input = new Scanner(file_input);
        char[] board_input = input.next().toCharArray();
        MiniMaxOpeningBlack miniMaxBlack = new MiniMaxOpeningBlack();
        char[] swapped_input = FUNCTION_SWAPBOARD(board_input);
        char[] swapped_out_board = miniMaxBlack.MAXIMUM_MINIMUM(swapped_input, depth);
        char[] outboard = FUNCTION_SWAPBOARD(swapped_out_board);
        System.out.println("Board Position: " + new String(outboard));
        System.out.println("Positions evaluated by static estimation: " + miniMaxBlack.position);
        System.out.println("MINIMAX estimate: " + - miniMaxBlack.approx_pos);
        file_output.println("Board Position : " + new String(outboard));
        file_output.println("Positions evaluated by static estimation : " + miniMaxBlack.position);
        file_output.println("MiniMax estimate : " + - miniMaxBlack.approx_pos);
        input.close();
        file_output.close();
    }

    public static char[] FUNCTION_SWAPBOARD(char[] x) {
        char[] board = x.clone();
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'W') {
                board[i] = 'B';
                continue;
            }
            if (board[i] == 'B') {
                board[i] = 'W';
            }
        }
        return board;
    }

    public char[] MAXIMUM_MINIMUM(char[] c, int depth) {
        if (depth > 0) {
            depth--;
            ArrayList < char[] > node_c;
            char[] board_MIN;
            char[] board_MAX = new char[50];
            node_c = FUNCTION_APPEND(c);
            int v = Integer.MIN_VALUE;
            for (char[] y: node_c) {
                board_MIN = MINIMUM_MAXIMUM(y, depth);
                if (v < STATIC_ESTIMATE(board_MIN)) {
                    v = STATIC_ESTIMATE(board_MIN);
                    approx_pos = v;
                    board_MAX = y;
                }
            }
            return board_MAX;
        } else if (depth == 0) {
            position++;
        }
        return c;
    }

    public char[] MINIMUM_MAXIMUM(char[] c, int depth) {
        if (depth > 0) {
            depth--;
            ArrayList < char[] > node_c2;
            char[] board_MAX;
            char[] board_MIN = new char[50];
            node_c2 = FUNCTION_MOVE_WHITE(c);
            int v = Integer.MAX_VALUE;
            for (char[] y: node_c2) {
                board_MAX = MAXIMUM_MINIMUM(y, depth);
                if (v > STATIC_ESTIMATE(board_MAX)) {
                    v = STATIC_ESTIMATE(board_MAX);
                    board_MIN = y;
                }
            }
            return board_MIN;
        } else if (depth == 0) {
            position++;
        }
        return c;
    }

    public int STATIC_ESTIMATE(char[] board) {
        int w = 0;
        int b = 0;
        for (char c : board) {
            if (c == 'W') {
                w++;
            } else if (c == 'B') {
                b++;
            }
        }
        return w - b;
    }
    public static ArrayList < char[] > FUNCTION_APPEND(char[] board) {
        ArrayList < char[] > y = new ArrayList<>();
        char[] b;
        for (int loc = 0; loc < board.length; loc++) {
            if (board[loc] == 'x') {
                b = board.clone();
                b[loc] = 'W';
                if (FUNCTION_PARAM(loc, b)) {
                    FUNCTION_DEDUCT(b, y);
                } else {
                    y.add(b);
                }
            }
        }
        return y;
    }
    private static void FUNCTION_DEDUCT(char[] x, ArrayList < char[] > y) {
        //x = board
        char[] b;
        for (int i = 0; i < x.length; i++) {
            if (x[i] == 'B') {
                if (!FUNCTION_PARAM(i, x)) {
                    b = x.clone();
                    b[i] = 'x';
                    y.add(b);
                }
            }
        }
        if (y.isEmpty()) {
            y.add(x);
        }
    }
    
    public static boolean FUNCTION_PARAM(int loc, char[] b) {
        char c = b[loc];
        if (c != 'x') {
            switch (loc) {
                case 0:
                    return (b[1] == c && b[2] == c) || (b[3] == c && b[6] == c) || (b[8] == c && b[20] == c);
                case 1:
                    return b[0] == c && b[2] == c;

                case 2:
                    return (b[0] == c && b[1] == c) || (b[5] == c && b[7] == c) || (b[13] == c && b[22] == c);

                case 3:
                    return (b[0] == c && b[6] == c) || (b[4] == c && b[5] == c) || (b[9] == c && b[17] == c);

                case 4:
                    return b[3] == c && b[5] == c;

                case 5:
                    return (b[2] == c && b[7] == c) || (b[3] == c && b[4] == c) || (b[12] == c && b[19] == c);

                case 6:
                    return (b[0] == c && b[3] == c) || (b[10] == c && b[14] == c);

                case 7:
                    return (b[2] == c && b[5] == c) || (b[11] == c && b[16] == c);

                case 8:
                    return (b[0] == c && b[20] == c) || (b[9] == c && b[10] == c);

                case 9:
                    return (b[8] == c && b[10] == c) || (b[3] == c && b[17] == c);

                case 10:
                    return (b[8] == c && b[9] == c) || (b[6] == c && b[14] == c);

                case 11:
                    return (b[7] == c && b[16] == c) || (b[12] == c && b[13] == c);

                case 12:
                    return (b[11] == c && b[13] == c) || (b[5] == c && b[19] == c);

                case 13:
                    return (b[11] == c && b[12] == c) || (b[2] == c && b[22] == c);

                case 14:
                    return (b[6] == c && b[10] == c) || (b[15] == c && b[16] == c) || (b[17] == c && b[20] == c);

                case 15:
                    return (b[14] == c && b[16] == c) || (b[18] == c && b[21] == c);

                case 16:
                    return (b[14] == c && b[15] == c) || (b[19] == c && b[22] == c) || (b[7] == c && b[11] == c);

                case 17:
                    return (b[3] == c && b[9] == c) || (b[18] == c && b[19] == c) || (b[14] == c && b[20] == c);

                case 18:
                    return (b[15] == c && b[21] == c) || (b[17] == c && b[19] == c);

                case 19:
                    return (b[17] == c && b[18] == c) || (b[5] == c && b[12] == c) || (b[16] == c && b[22] == c);

                case 20:
                    return (b[0] == c && b[8] == c) || (b[21] == c && b[22] == c) || (b[14] == c && b[17] == c);

                case 21:
                    return (b[20] == c && b[22] == c) || (b[15] == c && b[18] == c);

                case 22:
                    return (b[20] == c && b[21] == c) || (b[16] == c && b[19] == c) || (b[2] == c && b[13] == c);
                default:
                    return false;
            }
        }
        return false;
    }

    public ArrayList < char[] > FUNCTION_MOVE_WHITE(char[] board) {
        char[] board_swap = board.clone();
        for (int i = 0; i < board_swap.length; i++) {
            if (board_swap[i] == 'W') {
                board_swap[i] = 'B';
                continue;
            }
            if (board_swap[i] == 'B') {
                board_swap[i] = 'W';
            }
        }
        ArrayList < char[] > white_move;
        ArrayList < char[] > white_move_switch = new ArrayList<>();
        white_move = FUNCTION_APPEND(board_swap);
        for (char[] y: white_move) {
            for (int i = 0; i < y.length; i++) {
                if (y[i] == 'W') {
                    y[i] = 'B';
                    continue;
                }
                if (y[i] == 'B') {
                    y[i] = 'W';
                }
            }
            white_move_switch.add(y);
        }
        return white_move_switch;
    }
}