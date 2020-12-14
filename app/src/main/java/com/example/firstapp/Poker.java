package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Poker<value, type, val> extends AppCompatActivity {

    Random rand = new Random();

    int NUM_CARDS = 52;
    int pTurns = 0;
    int dTurns = 0;

    public static String[] types = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
    public static int[] value = {2,3,4,5,6,7,8,9,10,10,10,10,11};
    public char move = ' ';

    void shuffle(int[] cards)
    {
        for (int i = 51; i >= 1; i--){
            int j = rand.nextInt(i);
            int temp = cards[j];
            cards[j] = cards[i];
            cards[i] = temp;
        }
    }

    int cardValue(int id)
    {
        int valueIndex;
        if (id >=0 && id <=12){
            valueIndex = id;
        } else {
            while (id > 12){
                id = id - 13;
            }
            valueIndex = id;
        }

        return value[valueIndex];
    }

    int getBestScore(int[] hands, int numCards)
    {
        int total = 0;
        int numOfAce = 0;
        int tempTotal = 0;
        for (int i = 0; i < numCards; i++){
            total += cardValue(hands[i]);
            if (cardValue(hands[i]) == 11){
                numOfAce++;
            }
        }

        if (total > 21){
            for (int i = 1; i <= numOfAce; i++) {
                tempTotal = total - 10;
                total = tempTotal;
                if (total <= 21){
                    break;
                }
            }
        }

        return total;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poker);
        TextView pFirstCardView = (TextView)findViewById(R.id.pFirstCardView);
        TextView pSecondCardView = (TextView)findViewById(R.id.pSecondCardView);
        TextView pThirdCardView = (TextView)findViewById(R.id.pThirdCardView);
        TextView pFourthCardView = (TextView)findViewById(R.id.pFourthCardView);
        TextView pFifthCardView = (TextView)findViewById(R.id.pFifthCardView);
        TextView dFirstCardView = (TextView)findViewById(R.id.dFirstCardView);
        TextView dSecondCardView = (TextView)findViewById(R.id.dSecondCardView);
        TextView dThirdCardView = (TextView)findViewById(R.id.dThirdCardView);
        TextView dFourthCardView = (TextView)findViewById(R.id.dFourthCardView);
        TextView dFifthCardView = (TextView)findViewById(R.id.dFifthCardView);
        TextView resultView = (TextView)findViewById(R.id.resultText);
        Button draw = (Button) findViewById(R.id.draw);
        Button stand = (Button) findViewById(R.id.stand);

        int[] dhand = {0,0,0,0,0};
        int[] phand = {0,0,0,0,0};

        char replay = 'y';

        while (replay == 'y') {
            int[] deck = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21
                    ,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40
                    ,41,42,43,44,45,46,47,48,49,50,51};

            shuffle(deck);

            int pIndex = 0;
            int dIndex = 0;
            int deckIndex = 0;

            phand[pIndex] = deck[deckIndex];
            dhand[dIndex] = deck[deckIndex + 1];
            phand[pIndex + 1] = deck[deckIndex + 2];
            dhand[dIndex + 1] = deck[deckIndex + 3];

            int typeIndex = 0;
            if (phand[0] >=0 && phand[0] <=12){
                typeIndex = phand[0];
            } else {
                while (phand[0] > 12) {
                    phand[0] = phand[0] - 13;
                }
                typeIndex = phand[0];
            }

            pFirstCardView.setText(types[typeIndex]);

            if (phand[1] >=0 && phand[1] <=12){
                typeIndex = phand[1];
            } else {
                while (phand[1] > 12) {
                    phand[1] = phand[1] - 13;
                }
                typeIndex = phand[1];
            }

            pSecondCardView.setText(types[typeIndex]);

            if (dhand[0] >=0 && dhand[0] <=12){
                typeIndex = dhand[0];
            } else {
                while (dhand[0] > 12) {
                    dhand[0] = dhand[0] - 13;
                }
                typeIndex = dhand[0];
            }

            dFirstCardView.setText(types[typeIndex]);

            int dFirstHand = dhand[deckIndex + 1];

            if (getBestScore(phand, pIndex + 1) == 21 || getBestScore(dhand, dIndex + 1) == 21){
                break;
            }

            while (getBestScore(phand, pIndex + 2) < 21){
                draw.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        move = 'h';
                    }
                });

                stand.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        move = 's';
                    }
                });

                String test = String.valueOf(move);
                pFirstCardView.setText(test);
                if (move == 'h') {
                    pFirstCardView.setText("testing the code");
                    phand[pIndex + 2] = deck[deckIndex + 4];
                    pIndex++;
                    deckIndex++;
                    pTurns++;
                    if (phand[pIndex + 2] >=0 && phand[pIndex + 2] <=12){
                        typeIndex = phand[pIndex + 2];
                    } else {
                        while (phand[pIndex + 2] > 12) {
                            phand[pIndex + 2] = phand[pIndex + 2] - 13;
                        }
                        typeIndex = phand[pIndex + 2];
                    }
                    if (pTurns == 1){
                        pThirdCardView.setText(types[typeIndex]);
                    } else if (pTurns == 2){
                        pFourthCardView.setText(types[typeIndex]);
                    } else if (pTurns == 3 && getBestScore(phand, 5) <= 21){
                        pFifthCardView.setText(types[typeIndex]);
                        resultView.setText("You Win: You drew 5 cards without busting!");
                        return;
                    } else {
                        pFifthCardView.setText(types[typeIndex]);
                        resultView.setText("You Lose: You Bust!");
                        return;
                    }
                } else if (move == 's') {
                    break;
                } else {
                    return;
                }

                if (getBestScore(phand, pIndex + 2) > 21) {
                    break;
                }
            }

            if (getBestScore(phand, pIndex + 2) <= 21) {
                while (getBestScore(dhand, dIndex + 2) < 17) {
                    dhand[dIndex + 2] = deck[deckIndex + 4];
                    dIndex++;
                    deckIndex++;
                    dTurns++;
                    if (dTurns == 3 && getBestScore(phand, 5) <= 21){
                        resultView.setText("You Lose: Dealer drew 5 cards without busting!");
                        return;
                    }
                }
            }

            if (getBestScore(phand, pIndex + 2) > 21){
                resultView.setText("You Lose: You Bust!");
            } else if (getBestScore(dhand, dIndex + 2) > 21){
                resultView.setText("You Win: Dealer Bust!");
            } else if (getBestScore(phand, pIndex + 2) < getBestScore(dhand, dIndex + 2)){
                resultView.setText("You Lose: " + getBestScore(phand, pIndex + 2) + " is less than " + getBestScore(dhand, dIndex+ 2));
            } else if (getBestScore(phand, pIndex + 2) > getBestScore(dhand, dIndex + 2)){
                resultView.setText("You Win: " + getBestScore(phand, pIndex + 2) + " is greater than " + getBestScore(dhand, dIndex + 2));
            } else if (getBestScore(phand, pIndex + 2) == getBestScore(dhand, dIndex + 2)){
                resultView.setText("You Tie: " + getBestScore(phand, pIndex + 2) + " is equal to " + getBestScore(dhand, dIndex + 2));
            }


            if (dhand.length >= 2){
                if (dhand[1] >=0 && dhand[1] <=12){
                    typeIndex = dhand[1];
                } else {
                    while (dhand[1] > 12) {
                        dhand[1] = dhand[1] - 13;
                    }
                    typeIndex = dhand[1];
                }
                dSecondCardView.setText(types[typeIndex]);
            }

            if (dTurns >= 1){
                if (dhand[2] >=0 && dhand[2] <=12){
                    typeIndex = dhand[2];
                } else {
                    while (dhand[2] > 12) {
                        dhand[2] = dhand[2] - 13;
                    }
                    typeIndex = dhand[2];
                }
                dThirdCardView.setText(types[typeIndex]);
            }

            if (dTurns >= 2){
                if (dhand[3] >=0 && dhand[3] <=12){
                    typeIndex = dhand[3];
                } else {
                    while (dhand[3] > 12) {
                        dhand[3] = dhand[3] - 13;
                    }
                    typeIndex = dhand[3];
                }
                dFourthCardView.setText(types[typeIndex]);
            }

            if (dTurns == 3){
                if (dhand[4] >=0 && dhand[4] <=12){
                    typeIndex = dhand[4];
                } else {
                    while (dhand[4] > 12) {
                        dhand[4] = dhand[4] - 13;
                    }
                    typeIndex = dhand[4];
                }
                dFifthCardView.setText(types[typeIndex]);
            }


            replay = 'n';
        }



    }
}