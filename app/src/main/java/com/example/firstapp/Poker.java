package com.example.firstapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Poker<value, type, val> extends AppCompatActivity {

    Random rand = new Random();

    int pTurns = 0;
    int dTurns = 0;
    int pIndex = 0;
    int dIndex = 0;
    int deckIndex = 0;
    int typeIndex = 0;
    int suitIndex = 0;

    public static String[] types = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    public static int[] value = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};
    public static String[] suit = {"H", "S", "D", "C"};

    void shuffle(int[] cards) {
        for (int i = 51; i >= 1; i--) {
            int j = rand.nextInt(i);
            int temp = cards[j];
            cards[j] = cards[i];
            cards[i] = temp;
        }
    }

    int cardValue(int id) {
        int valueIndex;
        if (id >= 0 && id <= 12) {
            valueIndex = id;
        } else {
            while (id > 12) {
                id = id - 13;
            }
            valueIndex = id;
        }

        return value[valueIndex];
    }

    int getBestScore(int[] hands, int numCards) {
        int total = 0;
        int numOfAce = 0;
        int tempTotal = 0;
        for (int i = 0; i < numCards; i++) {
            total += cardValue(hands[i]);
            if (cardValue(hands[i]) == 11) {
                numOfAce++;
            }
        }

        if (total > 21) {
            for (int i = 1; i <= numOfAce; i++) {
                tempTotal = total - 10;
                total = tempTotal;
                if (total <= 21) {
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
        TextView pFirstCardView = (TextView) findViewById(R.id.pFirstCardView);
        TextView pSecondCardView = (TextView) findViewById(R.id.pSecondCardView);
        TextView pThirdCardView = (TextView) findViewById(R.id.pThirdCardView);
        TextView pFourthCardView = (TextView) findViewById(R.id.pFourthCardView);
        TextView pFifthCardView = (TextView) findViewById(R.id.pFifthCardView);
        TextView dFirstCardView = (TextView) findViewById(R.id.dFirstCardView);
        TextView dSecondCardView = (TextView) findViewById(R.id.dSecondCardView);
        TextView dThirdCardView = (TextView) findViewById(R.id.dThirdCardView);
        TextView dFourthCardView = (TextView) findViewById(R.id.dFourthCardView);
        TextView dFifthCardView = (TextView) findViewById(R.id.dFifthCardView);
        TextView resultView = (TextView) findViewById(R.id.resultText);
        TextView cardView5 = (TextView) findViewById(R.id.cardView5);
        TextView cardView6 = (TextView) findViewById(R.id.cardView6);
        TextView cardView7 = (TextView) findViewById(R.id.cardView7);
        TextView cardView8 = (TextView) findViewById(R.id.cardView8);
        TextView cardView9 = (TextView) findViewById(R.id.cardView9);
        TextView cardView10 = (TextView) findViewById(R.id.cardView10);
        Button draw = (Button) findViewById(R.id.draw);
        Button stand = (Button) findViewById(R.id.stand);
        Button replay = (Button) findViewById(R.id.replay);

        replay.setVisibility(View.GONE);
        replay.setClickable(false);

        int[] dhand = {0, 0, 0, 0, 0};
        int[] phand = {0, 0, 0, 0, 0};

        char replays = 'y';

        while (replays == 'y') {
            int[] deck = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21
                    , 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40
                    , 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};

            shuffle(deck);

            phand[pIndex] = deck[deckIndex];
            dhand[dIndex] = deck[deckIndex + 1];
            phand[pIndex + 1] = deck[deckIndex + 2];
            dhand[dIndex + 1] = deck[deckIndex + 3];

            typeIndex = 0;
            if (phand[0] >= 0 && phand[0] <= 12) {
                typeIndex = phand[0];
            } else {
                while (phand[0] > 12) {
                    phand[0] = phand[0] - 13;
                }
                typeIndex = phand[0];
            }

            pFirstCardView.setText(types[typeIndex]);

            if (phand[1] >= 0 && phand[1] <= 12) {
                typeIndex = phand[1];
            } else {
                while (phand[1] > 12) {
                    phand[1] = phand[1] - 13;
                }
                typeIndex = phand[1];
            }

            pSecondCardView.setText(types[typeIndex]);

            if (dhand[0] >= 0 && dhand[0] <= 12) {
                typeIndex = dhand[0];
            } else {
                while (dhand[0] > 12) {
                    dhand[0] = dhand[0] - 13;
                }
                typeIndex = dhand[0];
            }

            dFirstCardView.setText(types[typeIndex]);

            if (getBestScore(phand, 2) == 21 && getBestScore(dhand, 2) == 21) {
                draw.setVisibility(View.GONE);
                draw.setClickable(false);
                stand.setVisibility(View.GONE);
                stand.setClickable(false);
                resultView.setText("Tie: You Both Got 21!");
                replay.setVisibility(View.VISIBLE);
                if (dhand[1] >= 0 && dhand[1] <= 12) {
                    typeIndex = dhand[1];
                } else {
                    while (dhand[1] > 12) {
                        dhand[1] = dhand[1] - 13;
                    }
                    typeIndex = dhand[1];
                }
                dSecondCardView.setText(types[typeIndex]);
            } else if (getBestScore(phand, 2) == 21){
                draw.setVisibility(View.GONE);
                draw.setClickable(false);
                stand.setVisibility(View.GONE);
                stand.setClickable(false);
                resultView.setText("You Win: You Got 21!");
                replay.setVisibility(View.VISIBLE);
                if (dhand[1] >= 0 && dhand[1] <= 12) {
                    typeIndex = dhand[1];
                } else {
                    while (dhand[1] > 12) {
                        dhand[1] = dhand[1] - 13;
                    }
                    typeIndex = dhand[1];
                }
                dSecondCardView.setText(types[typeIndex]);
            } else if (getBestScore(dhand, 2) == 21){
                draw.setVisibility(View.GONE);
                draw.setClickable(false);
                stand.setVisibility(View.GONE);
                stand.setClickable(false);
                resultView.setText("You Lose: Dealer Got 21!");
                replay.setVisibility(View.VISIBLE);
                if (dhand[1] >= 0 && dhand[1] <= 12) {
                    typeIndex = dhand[1];
                } else {
                    while (dhand[1] > 12) {
                        dhand[1] = dhand[1] - 13;
                    }
                    typeIndex = dhand[1];
                }
                dSecondCardView.setText(types[typeIndex]);
            }

            draw.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getBestScore(phand, pIndex + 2) < 21) {
                        pIndex++;
                        deckIndex++;
                        phand[pIndex + 1] = deck[deckIndex + 4];
                        pTurns++;
                        if (phand[pIndex + 1] >= 0 && phand[pIndex + 1] <= 12) {
                            typeIndex = phand[pIndex + 1];
                        } else {
                            while (phand[pIndex + 1] > 12) {
                                phand[pIndex + 1] = phand[pIndex + 1] - 13;
                            }
                            typeIndex = phand[pIndex + 1];
                        }
                        if (pTurns == 1) {
                            pThirdCardView.setText(types[typeIndex]);
                            cardView5.setBackgroundResource(R.drawable.card);
                        } else if (pTurns == 2) {
                            pFourthCardView.setText(types[typeIndex]);
                            cardView6.setBackgroundResource(R.drawable.card);
                        } else if (pTurns == 3 && getBestScore(phand, pIndex + 1) <= 21) {
                            pFifthCardView.setText(types[typeIndex]);
                            draw.setVisibility(View.GONE);
                            draw.setClickable(false);
                            stand.setVisibility(View.GONE);
                            stand.setClickable(false);
                            resultView.setText("You Win: You drew 5 cards without busting!");
                            replay.setVisibility(View.VISIBLE);
                            cardView7.setBackgroundResource(R.drawable.card);
                        } else {
                            pFifthCardView.setText(types[typeIndex]);
                            cardView7.setBackgroundResource(R.drawable.card);
                            draw.setVisibility(View.GONE);
                            draw.setClickable(false);
                            stand.setVisibility(View.GONE);
                            stand.setClickable(false);
                            resultView.setText("You Busted: " + getBestScore(phand, pIndex + 2) + " is greater than 21");
                            replay.setVisibility(View.VISIBLE);
                            if (dhand[1] >= 0 && dhand[1] <= 12) {
                                typeIndex = dhand[1];
                            } else {
                                while (dhand[1] > 12) {
                                    dhand[1] = dhand[1] - 13;
                                }
                                typeIndex = dhand[1];
                            }
                            dSecondCardView.setText(types[typeIndex]);
                        }

                        if (getBestScore(phand, pIndex + 2) > 21) {
                            draw.setVisibility(View.GONE);
                            draw.setClickable(false);
                            stand.setVisibility(View.GONE);
                            stand.setClickable(false);
                            resultView.setText("You Busted: " + getBestScore(phand, pIndex + 2) + " is greater than 21");
                            replay.setVisibility(View.VISIBLE);
                            if (dhand[1] >= 0 && dhand[1] <= 12) {
                                typeIndex = dhand[1];
                            } else {
                                while (dhand[1] > 12) {
                                    dhand[1] = dhand[1] - 13;
                                }
                                typeIndex = dhand[1];
                            }
                            dSecondCardView.setText(types[typeIndex]);
                        }

                    } else {
                        draw.setVisibility(View.GONE);
                        draw.setClickable(false);
                        stand.setVisibility(View.GONE);
                        stand.setClickable(false);
                        resultView.setText("You Busted: " + getBestScore(phand, pIndex + 2) + " is greater than 21");
                        replay.setVisibility(View.VISIBLE);
                        if (dhand[1] >= 0 && dhand[1] <= 12) {
                            typeIndex = dhand[1];
                        } else {
                            while (dhand[1] > 12) {
                                dhand[1] = dhand[1] - 13;
                            }
                            typeIndex = dhand[1];
                        }
                        dSecondCardView.setText(types[typeIndex]);
                    }
                }
            });

            stand.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getBestScore(phand, pIndex + 2) <= 21) {
                        while (getBestScore(dhand, dIndex + 2) < 17) {
                            dhand[dIndex + 2] = deck[deckIndex + 4];
                            dIndex++;
                            deckIndex++;
                            dTurns++;
                            if (dTurns >= 1) {
                                if (dhand[2] >= 0 && dhand[2] <= 12) {
                                    typeIndex = dhand[2];
                                } else {
                                    while (dhand[2] > 12) {
                                        dhand[2] = dhand[2] - 13;
                                    }
                                    typeIndex = dhand[2];
                                }
                                dThirdCardView.setText(types[typeIndex]);
                                cardView8.setBackgroundResource(R.drawable.card);
                            }
                            if (dTurns >= 2) {
                                if (dhand[3] >= 0 && dhand[3] <= 12) {
                                    typeIndex = dhand[3];
                                } else {
                                    while (dhand[3] > 12) {
                                        dhand[3] = dhand[3] - 13;
                                    }
                                    typeIndex = dhand[3];
                                }
                                dFourthCardView.setText(types[typeIndex]);
                                cardView9.setBackgroundResource(R.drawable.card);
                            }
                            if (dTurns == 3) {
                                if (dhand[4] >= 0 && dhand[4] <= 12) {
                                    typeIndex = dhand[4];
                                } else {
                                    while (dhand[4] > 12) {
                                        dhand[4] = dhand[4] - 13;
                                    }
                                    typeIndex = dhand[4];
                                }
                                dFifthCardView.setText(types[typeIndex]);
                                cardView10.setBackgroundResource(R.drawable.card);
                            }
                            if (dTurns == 3 && getBestScore(phand, 5) <= 21) {
                                draw.setVisibility(View.GONE);
                                draw.setClickable(false);
                                stand.setVisibility(View.GONE);
                                stand.setClickable(false);
                                resultView.setText("You Lose: Dealer drew 5 cards without busting!");
                                replay.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                    if (getBestScore(phand, pIndex + 2) > 21) {
                        resultView.setText("You Busted: " + getBestScore(phand, pIndex + 2) + " is greater than 21");
                        draw.setVisibility(View.GONE);
                        draw.setClickable(false);
                        stand.setVisibility(View.GONE);
                        stand.setClickable(false);
                        replay.setVisibility(View.VISIBLE);
                    } else if (getBestScore(dhand, dIndex + 2) > 21) {
                        resultView.setText("You Win: Dealer Bust!");
                        draw.setVisibility(View.GONE);
                        draw.setClickable(false);
                        stand.setVisibility(View.GONE);
                        stand.setClickable(false);
                        replay.setVisibility(View.VISIBLE);
                    } else if (getBestScore(phand, pIndex + 2) < getBestScore(dhand, dIndex + 2)) {
                        resultView.setText("You Lose: " + getBestScore(phand, pIndex + 2) + " is less than " + getBestScore(dhand, dIndex + 2));
                        draw.setVisibility(View.GONE);
                        draw.setClickable(false);
                        stand.setVisibility(View.GONE);
                        stand.setClickable(false);
                        replay.setVisibility(View.VISIBLE);
                    } else if (getBestScore(phand, pIndex + 2) > getBestScore(dhand, dIndex + 2)) {
                        resultView.setText("You Win: " + getBestScore(phand, pIndex + 2) + " is greater than " + getBestScore(dhand, dIndex + 2));
                        draw.setVisibility(View.GONE);
                        draw.setClickable(false);
                        stand.setVisibility(View.GONE);
                        stand.setClickable(false);
                        replay.setVisibility(View.VISIBLE);
                    } else if (getBestScore(phand, pIndex + 2) == getBestScore(dhand, dIndex + 2)) {
                        resultView.setText("You Tie: " + getBestScore(phand, pIndex + 2) + " is equal to " + getBestScore(dhand, dIndex + 2));
                        draw.setVisibility(View.GONE);
                        draw.setClickable(false);
                        stand.setVisibility(View.GONE);
                        stand.setClickable(false);
                        replay.setVisibility(View.VISIBLE);
                    }
                    if (dhand.length >= 2) {
                        if (dhand[1] >= 0 && dhand[1] <= 12) {
                            typeIndex = dhand[1];
                        } else {
                            while (dhand[1] > 12) {
                                dhand[1] = dhand[1] - 13;
                            }
                            typeIndex = dhand[1];
                        }
                        dSecondCardView.setText(types[typeIndex]);
                    }
                }
            });

          replay.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  replay.setVisibility(View.GONE);
                  replay.setClickable(true);
                  draw.setVisibility(View.VISIBLE);
                  draw.setClickable(true);
                  stand.setVisibility(View.VISIBLE);
                  stand.setClickable(true);
                  pTurns = 0;
                  dTurns = 0;
                  pIndex = 0;
                  dIndex = 0;
                  deckIndex = 0;
                  typeIndex = 0;

                  shuffle(deck);

                  phand[pIndex] = deck[deckIndex];
                  dhand[dIndex] = deck[deckIndex + 1];
                  phand[pIndex + 1] = deck[deckIndex + 2];
                  dhand[dIndex + 1] = deck[deckIndex + 3];

                  if (phand[0] >= 0 && phand[0] <= 12) {
                      typeIndex = phand[0];
                  } else {
                      while (phand[0] > 12) {
                          phand[0] = phand[0] - 13;
                      }
                      typeIndex = phand[0];
                  }

                  pFirstCardView.setText(types[typeIndex]);

                  if (phand[1] >= 0 && phand[1] <= 12) {
                      typeIndex = phand[1];
                  } else {
                      while (phand[1] > 12) {
                          phand[1] = phand[1] - 13;
                      }
                      typeIndex = phand[1];
                  }

                  pSecondCardView.setText(types[typeIndex]);

                  if (dhand[0] >= 0 && dhand[0] <= 12) {
                      typeIndex = dhand[0];
                  } else {
                      while (dhand[0] > 12) {
                          dhand[0] = dhand[0] - 13;
                      }
                      typeIndex = dhand[0];
                  }

                  dFirstCardView.setText(types[typeIndex]);

                  resultView.setText("");

                  cardView5.setBackgroundColor(Color.WHITE);
                  cardView6.setBackgroundColor(Color.WHITE);
                  cardView7.setBackgroundColor(Color.WHITE);
                  cardView8.setBackgroundColor(Color.WHITE);
                  cardView9.setBackgroundColor(Color.WHITE);
                  cardView10.setBackgroundColor(Color.WHITE);
                  pThirdCardView.setText("");
                  pFourthCardView.setText("");
                  pFifthCardView.setText("");
                  dThirdCardView.setText("");
                  dFourthCardView.setText("");
                  dFifthCardView.setText("");
              }
          });

            replays = 'n';
        }
    }
}
