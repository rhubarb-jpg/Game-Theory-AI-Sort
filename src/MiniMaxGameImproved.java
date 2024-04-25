import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.*;

public class MiniMaxGameImproved {
    int position = 0;
    int approx_pos = 0;
    static int play = 0;

    public static void main(String[] args) throws IOException {
        File board1 = new File(args[0]);
        File board2 = new File(args[1]);
        int depth = Integer.parseInt(args[2]);
        play = depth;

        FileInputStream file_input = new FileInputStream(board1);
        PrintWriter file_output = new PrintWriter(new FileWriter(board2));
        Scanner input = new Scanner(file_input);
        char[] inpboard = input.next().toCharArray();
        MiniMaxGameImproved minimaxgameimproved = new MiniMaxGameImproved();
        char[] board_output = minimaxgameimproved.MAXIMUM_MINIMUM(inpboard, depth);
        System.out.println("Board Position: " + new String(board_output));
        System.out.println("Positions evaluated by static estimation: " + minimaxgameimproved.position);
        System.out.println("MINIMAX estimate: " + minimaxgameimproved.approx_pos);
        file_output.println("Board Position : " + new String(board_output));
        file_output.println("Positions evaluated by static estimation : " + minimaxgameimproved.position);
        file_output.println("MiniMax estimate : " + minimaxgameimproved.approx_pos);
        input.close();
        file_output.close();
    }

    public char[] MAXIMUM_MINIMUM(char[] c, int depth) {
        if (depth == 0) {
            position++;
        } else if (depth > 0) {
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
        }
        return c;
    }

    public char[] MINIMUM_MAXIMUM(char[] c, int depth) {
        if (depth == 0) {
            position++;
        } else if (depth > 0) {
            depth--;
            ArrayList < char[] > node_c2;
            char[] board_MAX;
            char[] board_MIN = new char[50];
            node_c2 = MINIMUM_MOVE(c);
            int v = Integer.MAX_VALUE;
            for (char[] y: node_c2) {
                board_MAX = MAXIMUM_MINIMUM(y, depth);
                if (v > STATIC_ESTIMATE(board_MAX)) {
                    v = STATIC_ESTIMATE(board_MAX);
                    board_MIN = y;
                }
            }
            return board_MIN;
        }
        return c;
    }

    private ArrayList < char[] > FUNCTION_APPEND(char[] board) {
        ArrayList < char[] > listGeneBoardPositions;
        int w = 0;
        for (char c : board) {
            if (c == 'W') {
                w++;
            }
        }
        if (w == 3) {
            listGeneBoardPositions = FUNCTION_HOP(board);
        } else {
            listGeneBoardPositions = FUNCTION_MOVE(board);
        }
        return listGeneBoardPositions;
    }

    public ArrayList < char[] > MINIMUM_MOVE(char[] x) {
        char[] board_switch = x.clone();
        for (int i = 0; i < board_switch.length; i++) {
            if (board_switch[i] == 'W') {
                board_switch[i] = 'B';
                continue;
            }
            if (board_switch[i] == 'B') {
                board_switch[i] = 'W';
            }
        }
        ArrayList < char[] > black_move;
        ArrayList < char[] > black_move_switch = new ArrayList<>();
        black_move = FUNCTION_APPEND(board_switch);
        for (char[] y: black_move) {
            for (int i = 0; i < y.length; i++) {
                if (y[i] == 'W') {
                    y[i] = 'B';
                    continue;
                }
                if (y[i] == 'B') {
                    y[i] = 'W';
                }
            }
            black_move_switch.add(y);
        }
        return black_move_switch;
    }

    public static ArrayList < char[] > FUNCTION_HOP(char[] board) {
        ArrayList < char[] > board_position = new ArrayList<>();
        char[] b;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'W') {
                for (int j = 0; j < board.length; j++) {
                    if (board[j] == 'x') {
                        b = board.clone();
                        b[i] = 'x';
                        b[j] = 'W';
                        if (FUNCTION_PARAM(j, b)) {
                            FUNCTION_GENDELETE(b, board_position);
                        } else {
                            board_position.add(b);
                        }
                    }
                }
            }
        }
        return board_position;
    }

    public static ArrayList < char[] > FUNCTION_MOVE(char[] board) {
        ArrayList < char[] > board_position = new ArrayList<>();
        char[] b;
        int[] n;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'W') {
                //get list of FUNCTION_NEIGHBORS of location;
                n = FUNCTION_NEIGHBORS(i);
                for (int j: n) {
                    if (board[j] == 'x') {
                        b = board.clone();
                        b[i] = 'x';
                        b[j] = 'W';
                        if (FUNCTION_PARAM(j, b)) {
                            FUNCTION_GENDELETE(b, board_position);
                        } else {
                            board_position.add(b);
                        }
                    }
                }
            }
        }
        return board_position;
    }
    private static void FUNCTION_GENDELETE(char[] x, ArrayList < char[] > list_pos) {
        char[] b;
        for (int i = 0; i < x.length; i++) {
            if (x[i] == 'B') {
                if (!FUNCTION_PARAM(i, x)) {
                    b = x.clone();
                    b[i] = 'x';
                    list_pos.add(b);
                }
            }
        }
        if (list_pos.isEmpty()) {
            list_pos.add(x);
        }
    }
    
    public int STATIC_ESTIMATE(char[] board) {
        int w = 0;
        int b = 0;
        int depth = play;
        ArrayList < char[] > numBlackMovesList = MINIMUM_MOVE(board);
        int blackMoveCount = numBlackMovesList.size();
        int mills = 0;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 'x') {
                if (FUNCTION_PARAM(i, board)) {
                    mills++;
                }
            }
        }
        for (char c : board) {
            if (c == 'W') {
                w++;
            } else if (c == 'B') {
                b++;
            }
        }
        if (b <= 2) {
            return 10000;
        } else if (w <= 2) {
            return -10000;
        } else if (blackMoveCount == 0) {
            return 10000;
        } else { 
            return ((1000 * (w - b) * depth) - blackMoveCount + (100 * mills));
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
    private static int[] FUNCTION_NEIGHBORS(int i) {
        switch(i) {
            case 0:
                return new int[] { 1,3,8 };

            case 1:
                return new int[] { 0,2,4 };

            case 2:
                return new int[] { 1,5,13 };

            case 3:
                return new int[] { 0,4,6,9 };

            case 4:
                return new int[] { 1,3,5 };

            case 5:
                return new int[] { 2,4,7,12 };

            case 6:
                return new int[] { 3,7,10 };

            case 7:
                return new int[] { 5,6,11 };

            case 8:
                return new int[] { 0,9,20 };

            case 9:
                return new int[] { 3,8,10,17 };

            case 10:
                return new int[] { 6,9,14 };

            case 11:
                return new int[] { 7,12,16 };

            case 12:
                return new int[] { 5,11,13,19 };

            case 13:
                return new int[] { 2,12,22 };

            case 14:
                return new int[] { 10,15,17 };

            case 15:
                return new int[] { 14,16,18 };

            case 16:
                return new int[] { 11,15,19 };

            case 17:
                return new int[] { 9,14,18,20 };

            case 18:
                return new int[] { 15,17,19,21 };

            case 19:
                return new int[] { 12,16,18,22 };

            case 20:
                return new int[] { 8,17,21 };

            case 21:
                return new int[] { 18,20,22 };

            case 22:
                return new int[] { 13,19,21 };

            default:
                return new int[] {};
        }
    }
}