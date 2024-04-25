# Game Playing Algorithms

This project consists of four parts, each focusing on implementing different game playing algorithms using various programming languages such as C, C++, Java, or Python. The project requires writing two programs for each part, with specific requirements outlined below.

## Part I: MINIMAX 

### MiniMaxOpening
- Program for playing a move in the opening phase of the game.
- Accepts three command-line parameters: input board position file, output board position file, and search depth.
- Implements the MINIMAX algorithm with the provided static estimation function.
- Example usage: 
          MiniMaxOpening board1.txt board2.txt 2
- Example output:
          Input position: xxxxxxxxxWxxxxxxBxxxxxx
          Output position: xxxxxxxxxWxxWxxxBxxxxxx
          Positions evaluated by static estimation: 9
          MINIMAX estimate: 9987

### MiniMaxGame
- Program for playing in the midgame/endgame phase.
- Accepts three command-line parameters: input board position file, output board position file, and search depth.
- Implements the MINIMAX algorithm with the provided static estimation function.
- Example usage:
        MiniMaxGame board3.txt board4.txt 3
  - Example output:
        Input position: xxxxxxxxxxWWxWWxBBBxxxx
        Output position: xxxxxxxxWWWxWWxBBBBxxxx
        Positions evaluated by static estimation: 125
        MINIMAX estimate: 9987

  
## Part II: ALPHA-BETA Pruning 

### ABOpening
- Implementation of the ALPHA-BETA pruning algorithm for the opening phase.
- Behaves the same as MiniMaxOpening but with improved efficiency through pruning.
- Returns the same estimate values as MiniMaxOpening.

### ABGame
- Implementation of the ALPHA-BETA pruning algorithm for the midgame/endgame phase.
- Behaves the same as MiniMaxGame but with improved efficiency through pruning.
- Returns the same estimate values as MiniMaxGame.

## Part III: PLAY A GAME FOR BLACK 

### MiniMaxOpeningBlack
- Similar to MiniMaxOpening but computes Black's move instead of White's move.
- Accepts three command-line parameters: input board position file, output board position file, and search depth.

### MiniMaxGameBlack
- Similar to MiniMaxGame but computes Black's move instead of White's move.
- Accepts three command-line parameters: input board position file, output board position file, and search depth.

## Part IV: STATIC ESTIMATION 

### MiniMaxOpeningImproved
- Rewritten version of MiniMaxOpening with an improved static estimation function.

### MiniMaxGameImproved
- Rewritten version of MiniMaxGame with an improved static estimation function.

